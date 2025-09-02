package original;


class SpecialFactorial {
    /**
     * The Brazilian factorial is defined as:
     * brazilian_factorial(n) = n! * (n-1)! * (n-2)! * ... * 1!
     * where n > 0
     *
     * For example:
     * >>> specialFactorial(4)
     * 288
     *
     * The function will receive an integer as input and should return the special
     * factorial of this integer.
     *
     * > specialFactorial(4)
     * 288
     * > specialFactorial(5)
     * 34560
     * > specialFactorial(7)
     * 125411328000L
     * > specialFactorial(1)
     * 1
     */
    public static long specialFactorial(int n) {
        long res = 1;
        for (int i = 1; i <= n; i++) {
            res *= factorial(i);
        }
        return res;
    }

    private static long factorial(int n) {
        long res = 1;
        for (int i = 1; i <= n; i++) {
            res *= i;
        }
        return res;
    }


}