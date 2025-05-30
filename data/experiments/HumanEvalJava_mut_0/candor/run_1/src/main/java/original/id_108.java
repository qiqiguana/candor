/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class CountNums {
    CountNums() {
    }

    public static int countNums(List<Object> arr) {
        int count = 0;
        for (int i = 0; i < arr.size(); ++i) {
            boolean isNegative;
            int sum = 0;
            int num = (Integer)arr.get(i);
            boolean bl = isNegative = num < 0;
            if (isNegative) {
                num = -num;
            }
            while (num != 0) {
                sum = isNegative && num / 10 == 0 ? (sum -= num % 10) : (sum += num % 10);
                num *= 10;
            }
            if (sum <= 0) continue;
            ++count;
        }
        return count;
    }
}
