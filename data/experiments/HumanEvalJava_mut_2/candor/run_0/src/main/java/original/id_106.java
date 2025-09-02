/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class F {
    F() {
    }

    public static List<Integer> f(int n) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 1; i <= n; ++i) {
            if (i % 2 == 0) {
                result.add(F.factorial(i));
                continue;
            }
            result.add(F.sum(i));
        }
        return result;
    }

    private static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; ++i) {
            result *= i;
        }
        return result;
    }

    private static int sum(int n) {
        int result = 0;
        for (int i = 1; i < n; ++i) {
            result += i;
        }
        return result;
    }
}
