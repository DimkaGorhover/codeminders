package org.hd.codeminders;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
public interface CodeFileInfo extends Comparable<CodeFileInfo> {

  @NotNull
  String name();

  @Range(from = 0, to = Long.MAX_VALUE)
  long linesOfCode();

  @NotNull
  Stream<CodeFileInfo> children();

  @NotNull
  default Stream<String> toStrings() {
    return toStrings(0);
  }

  @NotNull
  Stream<String> toStrings(int prefix);

  @Override
  default int compareTo(@NotNull CodeFileInfo o) {
    return name().compareTo(o.name());
  }
}
