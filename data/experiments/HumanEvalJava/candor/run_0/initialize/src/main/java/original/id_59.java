package original;


class LargestPrimeFactor {
    /**
     * Return the largest prime factor of n. Assume n > 1 and is not a prime.
     *
     * > largestPrimeFactor(2048)
     * 2
     * > largestPrimeFactor(15)
     * 5
     * > largestPrimeFactor(27)
     * 3
     * > largestPrimeFactor(63)
     * 7
     * > largestPrimeFactor(330)
     * 11
     * > largestPrimeFactor(13195)
     * 29
     */
    public static int largestPrimeFactor(int n) {
        int max = 0;
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                max = i;
                n /= i;
                i--;
            }
        }
        return max;
    }


}