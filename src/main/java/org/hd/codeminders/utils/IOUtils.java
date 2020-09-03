package org.hd.codeminders.utils;

import java.io.File;
import org.jetbrains.annotations.Nullable;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
public final class IOUtils {

  private IOUtils() {
    throw new UnsupportedOperationException();
  }

  public static boolean exists(@Nullable File file) {
    return file != null && file.exists();
  }

  public static void close(@Nullable AutoCloseable closeable) {
    if (closeable == null) {
      return;
    }

    try {
      closeable.close();
    } catch (Exception ignored) {
    }
  }
}
