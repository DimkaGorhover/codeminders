package org.hd.codeminders;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hd.codeminders.utils.StreamUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
class SimpleCodeFileInfoFactory implements CodeFileInfoFactory {

  private final Set<String> exclude;
  private final CodeLinesReader codeLinesReader;
  private final int prefixOffset;

  public SimpleCodeFileInfoFactory(
      @Nullable Set<String> exclude,
      @Range(from = 0, to = 1 << 10) int prefixOffset) {
    this.exclude = exclude == null ? Collections.emptySet() : exclude;
    codeLinesReader = new SimpleCodeLinesReader();
    this.prefixOffset = prefixOffset;
  }

  @Override
  public @NotNull Optional<CodeFileInfo> create(@Nullable File file) {
    if (file == null || !file.exists() || exclude.contains(file.getName().toLowerCase())) {
      return Optional.empty();
    }

    if (file.isDirectory()) {
      return Optional.of(new DirectoryCodeFileInfo(file));
    }

    if (file.isFile() && file.getName().endsWith(".java")) {
      return Optional.of(new CodeFileInfoImpl(file));
    }

    return Optional.empty();
  }

  static abstract class AbstractCodeFileInfo implements CodeFileInfo {

    private final String name;

    public AbstractCodeFileInfo(@NotNull File file) {
      requireNonNull(file, "\"file\" cannot be null");
      this.name = file.getName();
    }

    @Override
    public @NotNull String name() {
      return name;
    }

    @Override
    public @NotNull Stream<String> toStrings(int prefix) {
      return StreamUtils.lazy(() -> {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prefix; i++) {
          sb.append(' ');
        }
        return sb.append(name()).append(": ").append(linesOfCode()).toString();
      });
    }
  }

  class DirectoryCodeFileInfo extends AbstractCodeFileInfo {

    private final List<CodeFileInfo> children;
    private final long linesOfCode;

    DirectoryCodeFileInfo(@NotNull File file) {
      super(file);

      children = Arrays.stream(file.listFiles())
          .flatMap(SimpleCodeFileInfoFactory.this::createStream)
          .sorted()
          .collect(Collectors.toList());

      linesOfCode = children.stream()
          .mapToLong(CodeFileInfo::linesOfCode)
          .sum();
    }

    @Override
    public @Range(from = 0, to = Long.MAX_VALUE) long linesOfCode() {
      return linesOfCode;
    }

    @Override
    public @NotNull Stream<CodeFileInfo> children() {
      return children.stream();
    }

    @Override
    public @NotNull Stream<String> toStrings(int prefix) {

      if (linesOfCode() == 0) {
        return Stream.empty();
      }

      return Stream.concat(
          super.toStrings(prefix),
          children().flatMap(codeFileInfo -> codeFileInfo.toStrings(prefix + prefixOffset)));
    }
  }

  class CodeFileInfoImpl extends AbstractCodeFileInfo {

    private final long linesOfCode;

    CodeFileInfoImpl(@NotNull File file) {
      super(file);

      linesOfCode = codeLinesReader.readCodeLinesCount(file);
    }

    @Override
    public @Range(from = 0, to = Long.MAX_VALUE) long linesOfCode() {
      return linesOfCode;
    }

    @Override
    public @NotNull Stream<CodeFileInfo> children() {
      return Stream.empty();
    }
  }
}
