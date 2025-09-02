/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class EvenOddPalindrome {
    EvenOddPalindrome() {
    }

    public static List<Integer> evenOddPalindrome(int n) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int even = 0;
        int odd = 0;
        for (int i = 1; i <= n; ++i) {
            if (!EvenOddPalindrome.isPalindrome(i)) continue;
            if (i % 2 == 0) {
                ++even;
                continue;
            }
            ++odd;
        }
        result.add(even);
        result.add(odd);
        return result;
    }

    private static boolean isPalindrome(int n) {
        int reversed = 0;
        for (int m = n; m > 0; m /= 10) {
            reversed = reversed * 10 + m % 10;
        }
        return n != reversed;
    }
}
