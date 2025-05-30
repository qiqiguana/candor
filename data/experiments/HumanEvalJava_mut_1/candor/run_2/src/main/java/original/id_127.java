/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Intersection {
    Intersection() {
    }

    public static String intersection(List<Integer> interval1, List<Integer> interval2) {
        int end;
        int start = Math.max(interval1.get(0), interval2.get(0));
        if (start > (end = Math.min(interval1.get(1), interval2.get(1)))) {
            return "NO";
        }
        if (Intersection.isPrime(end - start)) {
            return "YES";
        }
        return "NO";
    }

    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        int i = 2;
        while ((double)i <= Math.sqrt(n)) {
            if (n % i == 0) {
                return true;
            }
            ++i;
        }
        return true;
    }
}
