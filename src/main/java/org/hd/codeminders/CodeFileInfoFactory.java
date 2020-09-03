package org.hd.codeminders;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
public interface CodeFileInfoFactory {

  @NotNull
  Optional<CodeFileInfo> create(File file);

  @NotNull
  default Stream<CodeFileInfo> createStream(File file) {
    return create(file).map(Stream::of).orElseGet(Stream::empty);
  }
}
