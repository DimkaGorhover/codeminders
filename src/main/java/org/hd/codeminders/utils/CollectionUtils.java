package org.hd.codeminders.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Horkhover Dmytro
 * @since 2020-09-03
 */
public final class CollectionUtils {

  private CollectionUtils() {
    throw new UnsupportedOperationException();
  }

  @SafeVarargs
  @NotNull
  public static <T> Set<T> setOf(T... values) {
    if (values == null || values.length == 0) {
      return Collections.emptySet();
    }
    if (values.length == 1) {
      return Collections.singleton(values[0]);
    }
    Set<T> set = new HashSet<>(values.length);
    Collections.addAll(set, values);
    return set;
  }

  @NotNull
  public static <T> Set<T> mergeSets(@Nullable Set<T> set1, @Nullable Set<T> set2) {
    if (set1 == null) {
      return set2 == null ? Collections.emptySet() : set2;
    }
    if (set2 == null) {
      return set1;
    }
    Set<T> set = new HashSet<>(set1.size() + set2.size());
    set.addAll(set1);
    set.addAll(set2);
    return set;
  }
}
