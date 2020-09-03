package org.gd.hackerrank.other;

import org.gd.common.Repeat;
import org.gd.hackerrank.common.HackerRank;

/**
 * https://www.hackerrank.com/challenges/maximizing-xor/
 */
@Repeat("Maximizing Xor")
@HackerRank(difficulty = HackerRank.Level.EASY)
class MaximizingXor {

  @Deprecated
  static int squareSolution(int l, int r) {
    int max = Integer.MIN_VALUE;
    for (int i = l; i <= r; i++) {
      for (int j = i + 1; j <= r; j++) {
        max = Math.max(max, i ^ j);
      }
    }
    return max;
  }

  /**
   * @see org.gd.common.Commons#log2(double)
   */
  static double log2(double a) {
    return Math.log(a) / Math.log(2);
  }

  // another comment
  static int maximizingXor(int l, int r) {
    return (1 << ((int) (log2(l ^ r) + 1))) - 1;
  }
}
