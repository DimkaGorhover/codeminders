package org.hd.codeminders.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
public final class StringUtils {

  private StringUtils() {
    throw new UnsupportedOperationException();
  }

  @NotNull
  public static Set<String> toSet(@Nullable String comaSeparatedText) {
    if (comaSeparatedText == null || comaSeparatedText.isEmpty()) {
      return Collections.emptySet();
    }

    return Arrays.stream(comaSeparatedText.split("[,;]"))
        .map(String::trim)
        .map(String::toLowerCase)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.toSet());
  }
}
