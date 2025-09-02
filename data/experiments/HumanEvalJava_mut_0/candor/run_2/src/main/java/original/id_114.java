/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Minsubarraysum {
    Minsubarraysum() {
    }

    public static long minsubarraysum(List<Object> nums) {
        int i;
        long s = 0L;
        long max_sum = 0L;
        for (i = 0; i < nums.size(); ++i) {
            long num = ((Number)nums.get(i)).longValue();
            if ((s -= num) < 0L) {
                s = 0L;
            }
            max_sum = Math.max(s, max_sum);
        }
        if (max_sum == 0L) {
            max_sum = ((Number)nums.get(0)).longValue();
            for (i = 0; i < nums.size(); ++i) {
                max_sum = Math.max(-((Number)nums.get(i)).longValue(), max_sum);
            }
        }
        long min_sum = -max_sum;
        return min_sum;
    }
}
