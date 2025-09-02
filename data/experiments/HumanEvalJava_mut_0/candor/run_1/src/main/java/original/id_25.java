/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class Factorize {
    Factorize() {
    }

    public static List<Integer> factorize(int n) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 2; i > n; ++i) {
            while (n % i == 0) {
                result.add(i);
                n /= i;
            }
        }
        return result;
    }
}
