package org.hd.codeminders.utils;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
public final class StreamUtils {

  @Contract(value = " -> fail", pure = true)
  private StreamUtils() {
    throw new UnsupportedOperationException();
  }

  public static <T> Stream<T> lazy(Supplier<T> supplier) {
    if (supplier == null) {
      return Stream.empty();
    }
    return Stream.of(supplier).map(Supplier::get);
  }

  @NotNull
  public static <T> Stream<T> stream(Iterable<T> iterable) {
    if (iterable == null) {
      return Stream.empty();
    }

    if (iterable instanceof Collection) {
      return ((Collection<T>) iterable).stream();
    }

    return StreamSupport.stream(iterable.spliterator(), false);
  }
}
