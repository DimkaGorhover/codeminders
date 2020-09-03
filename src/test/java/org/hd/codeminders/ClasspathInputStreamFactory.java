package org.hd.codeminders;

import static java.util.Objects.requireNonNull;

import java.io.InputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-04
 */
public class ClasspathInputStreamFactory implements InputStreamFactory {

  private final String fileName;

  public ClasspathInputStreamFactory(@NotNull String fileName) {
    requireNonNull(fileName, "\"fileName\" cannot be null");
    this.fileName = fileName;

    if (resource() == null) {
      throw new IllegalArgumentException(String.format("file \"%s\" not found", this));
    }
  }

  @Nullable
  private InputStream resource() {
    return ClasspathInputStreamFactory.class.getClassLoader().getResourceAsStream(fileName);
  }

  @Override
  public @NotNull InputStream open() {
    //noinspection ConstantConditions
    return resource();
  }

  @Override
  public String toString() {
    return "classpath:" + fileName;
  }
}
