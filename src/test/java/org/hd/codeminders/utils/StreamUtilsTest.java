package org.hd.codeminders.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Test for {@link StreamUtils}
 *
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
@DisplayName("Test for org.hd.codeminders.utils.StreamUtils")
class StreamUtilsTest {

  @Test
  @DisplayName("Lazy")
  @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
  void test_Lazy() {

    assertFalse(StreamUtils.lazy(null).findAny().isPresent());

    CountableSupplier supplier = new CountableSupplier();
    Stream<Integer> stream = StreamUtils.lazy(supplier);
    assertEquals(0, supplier.get);
    assertEquals(0, stream.findAny().orElseThrow(NullPointerException::new));
    assertEquals(1, supplier.get);
  }

  @Test
  @DisplayName("Stream")
  @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
  void test_Stream() {

    assertFalse(StreamUtils.stream(null).findAny().isPresent());

    List<Integer> expected = Arrays.asList(1, 2, 3);
    List<Integer> actual = StreamUtils.stream(expected).collect(Collectors.toList());
    assertEquals(expected, actual);

    actual = StreamUtils.stream(new ArrayIterable(1, 2, 3)).collect(Collectors.toList());
    assertEquals(expected, actual);
  }

  static class CountableSupplier implements Supplier<Integer> {

    int get = 0;

    @Override
    public Integer get() {
      get++;
      return 0;
    }
  }

  static class ArrayIterable implements Iterable<Integer> {

    private final List<Integer> list;

    public ArrayIterable(int... values) {
      list = new ArrayList<>(values.length);
      for (int value : values) {
        list.add(value);
      }
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
      return list.iterator();
    }
  }
}
