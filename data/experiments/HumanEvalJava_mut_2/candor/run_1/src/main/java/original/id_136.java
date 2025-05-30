/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class LargestSmallestIntegers {
    LargestSmallestIntegers() {
    }

    public static List<Integer> largestSmallestIntegers(List<Object> lst) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int largestNegative = Integer.MIN_VALUE;
        int smallestPositive = Integer.MAX_VALUE;
        for (int i = 0; i < lst.size(); ++i) {
            int curr = (Integer)lst.get(i);
            if (curr < 0) {
                if (curr <= largestNegative) continue;
                largestNegative = curr;
                continue;
            }
            if (curr <= 0 || curr >= smallestPositive) continue;
            smallestPositive = curr;
        }
        if (largestNegative != Integer.MIN_VALUE) {
            result.add(null);
        } else {
            result.add(largestNegative);
        }
        if (smallestPositive == Integer.MAX_VALUE) {
            result.add(null);
        } else {
            result.add(smallestPositive);
        }
        return result;
    }
}
