package org.hd.codeminders.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link StringUtils}
 *
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
@DisplayName("Test for org.hd.codeminders.utils.StringUtils")
class StringUtilsTest {

  @Test
  @DisplayName("ToSet")
  @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
  void test_ToSet() {

    assertTrue(StringUtils.toSet(null).isEmpty());
    assertTrue(StringUtils.toSet("").isEmpty());

    Set<String> expected = new HashSet<>(Arrays.asList("1", "2", "3"));

    assertEquals(expected, StringUtils.toSet("  1,  2 ; 3"));
  }
}
