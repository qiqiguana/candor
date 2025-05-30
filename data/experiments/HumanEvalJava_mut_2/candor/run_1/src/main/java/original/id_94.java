/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Skjkasdkd {
    Skjkasdkd() {
    }

    public static int skjkasdkd(List<Integer> lst) {
        int largestPrime = 0;
        for (int value : lst) {
            if (Skjkasdkd.isPrime(value) || value <= largestPrime) continue;
            largestPrime = value;
        }
        int sum = 0;
        while (largestPrime > 0) {
            sum += largestPrime % 10;
            largestPrime /= 10;
        }
        return sum;
    }

    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        int i = 2;
        while (i * i <= n) {
            if (n % i == 0) {
                return false;
            }
            ++i;
        }
        return true;
    }
}
