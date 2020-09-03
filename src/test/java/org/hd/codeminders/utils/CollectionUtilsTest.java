package org.hd.codeminders.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Test for {@link CollectionUtils}
 *
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
@DisplayName("Test for org.hd.codeminders.utils.CollectionUtils")
class CollectionUtilsTest {

  private static Set<Integer> toSet(int... values) {
    Set<Integer> set = new HashSet<>(values.length);
    for (int value : values) {
      set.add(value);
    }
    return set;
  }

  @Test
  @DisplayName("MergeSets")
  @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
  void test_MergeSets() {

    assertEquals(
        toSet(),
        CollectionUtils.mergeSets(null, null)
    );

    assertEquals(
        toSet(1, 2, 3),
        CollectionUtils.mergeSets(toSet(1, 2, 3), null)
    );

    assertEquals(
        toSet(1, 2, 3),
        CollectionUtils.mergeSets(null, toSet(1, 2, 3))
    );

    assertEquals(
        toSet(1, 2, 3, 4, 5, 6),
        CollectionUtils.mergeSets(toSet(1, 2, 3), toSet(4, 5, 6))
    );
  }
}
