package org.hd.codeminders.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test for {@link IOUtils}
 *
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
@DisplayName("Test for org.hd.codeminders.utils.IOUtils")
class IOUtilsTest {

  @TempDir
  static Path tempDir;

  @Test
  @DisplayName("exists")
  @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
  void test_exists() {
    assertFalse(IOUtils.exists(null));
    assertFalse(IOUtils.exists(new File("/aos8ads9sd9sda09")));
    assertTrue(IOUtils.exists(tempDir.toFile()));
  }

  @Test
  @DisplayName("Close")
  @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
  void test_Close() {

    AutoCloseableMock mock = new AutoCloseableMock();
    assertDoesNotThrow(() -> IOUtils.close(mock));
    assertEquals(1, mock.close);

    assertDoesNotThrow(() -> IOUtils.close(null));
    assertDoesNotThrow(() -> IOUtils.close(new ExceptionalAutoCloseable()));
  }

  static class AutoCloseableMock implements AutoCloseable {

    int close = 0;

    @Override
    public void close() {
      close++;
    }
  }

  static class ExceptionalAutoCloseable implements AutoCloseable {

    @Override
    public void close() throws Exception {
      throw new IOException("test");
    }
  }
}
