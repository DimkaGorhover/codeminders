package org.hd.codeminders;

import java.io.IOException;
import java.io.InputStream;
import org.jetbrains.annotations.NotNull;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-04
 */
public interface InputStreamFactory {

  @NotNull
  static InputStreamFactory classpath(@NotNull String filePath) {
    return new ClasspathInputStreamFactory(filePath);
  }

  @NotNull
  InputStream open() throws IOException;
}
