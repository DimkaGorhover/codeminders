package org.hd.codeminders;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.jetbrains.annotations.NotNull;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
class SimpleCodeLinesReader implements CodeLinesReader {

  @Override
  public int readCodeLinesCount(@NotNull File file) {

    requireNonNull(file, "\"file\" cannot be null");

    if (!file.exists()) {
      throw new IllegalStateException("file \"" + file + "\" doesn't exist");
    }

    if (!file.isFile()) {
      throw new IllegalStateException("\"" + file + "\" isn't file");
    }

    try {
      return readCodeLinesCount(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int readCodeLinesCount(@NotNull InputStream inputStream) {
    requireNonNull(inputStream, "\"inputStream\" cannot be null");

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      int count = 0;
      boolean commentBlock = false;
      String line;
      while ((line = reader.readLine()) != null) {

        line = line.trim();

        if (line.isEmpty() || line.startsWith("//")) {
          continue;
        }

        if (line.startsWith("/*")) {
          commentBlock = true;
        }

        if (line.endsWith("*/")) {
          commentBlock = false;
          continue;
        }

        if (!commentBlock) {
          count++;
        }

      }
      return count;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
