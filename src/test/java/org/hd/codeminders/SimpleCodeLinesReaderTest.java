package org.hd.codeminders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test for {@link SimpleCodeLinesReader}
 *
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
@DisplayName("Test for org.hd.codeminders.SimpleCodeLinesReader")
class SimpleCodeLinesReaderTest {

  @NotNull
  private static Stream<Arguments> args() {
    return Stream.of(
        Arguments.of(InputStreamFactory.classpath("MaximizingXor.java"), 23)
    );
  }

  @ParameterizedTest(name = "{index}. expected {1} line(s) of code inside [{0}] file")
  @MethodSource("args")
  @DisplayName("ReadCodeLinesCount")
  @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
  void test_ReadCodeLinesCount(@NotNull InputStreamFactory streamFactory, int expected)
      throws IOException {

    CodeLinesReader codeLinesReader = new SimpleCodeLinesReader();

    try (@NotNull InputStream stream = streamFactory.open()) {
      assertEquals(expected, codeLinesReader.readCodeLinesCount(stream));
    }
  }
}
