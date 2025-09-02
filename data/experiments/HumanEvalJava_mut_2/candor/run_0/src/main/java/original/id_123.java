/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GetOddCollatz {
    GetOddCollatz() {
    }

    public static List<Integer> getOddCollatz(int n) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        while (n != 1) {
            if (n % 2 == 1) {
                result.add(n);
            }
            if (n % 2 == 0) {
                n /= 2;
                continue;
            }
            n = 3 * n - 1;
        }
        result.add(1);
        Collections.sort(result);
        return result;
    }
}
