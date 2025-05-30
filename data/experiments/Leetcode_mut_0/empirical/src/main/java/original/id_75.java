package original;


class IsMultiplyPrime {
    /**
     * Write a function that returns true if the given number is the multiplication of 3 prime numbers
     * and false otherwise.
     * Knowing that (a) is less than 100.
     * Example:
     * isMultiplyPrime(30) == true
     * 30 = 2 * 3 * 5
     *
     * > isMultiplyPrime(5)
     * false
     * > isMultiplyPrime(30)
     * true
     * > isMultiplyPrime(8)
     * true
     * > isMultiplyPrime(10)
     * false
     * > isMultiplyPrime(125)
     * true
     * > isMultiplyPrime(105)
     * true
     * > isMultiplyPrime(126)
     * false
     * > isMultiplyPrime(729)
     * false
     * > isMultiplyPrime(891)
     * false
     * > isMultiplyPrime(1001)
     * true
     */
    public static Boolean isMultiplyPrime(int a) {
        int count = 0;
        for (int i = 2; i <= a; i++) {
            if (a % i == 0) {
                count++;
                a /= i;
                i--;
            }
        }
        return count == 3;
    }


}