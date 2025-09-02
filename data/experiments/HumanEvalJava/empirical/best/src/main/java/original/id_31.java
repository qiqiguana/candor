package original;

class IsPrime {
    /**
     * Return true if a given number is prime, and false otherwise.
     *
     * > isPrime(6)
     * false
     * > isPrime(101)
     * true
     * > isPrime(11)
     * true
     * > isPrime(13441)
     * true
     * > isPrime(61)
     * true
     * > isPrime(4)
     * false
     * > isPrime(1)
     * false
     * > isPrime(5)
     * true
     * > isPrime(11)
     * true
     * > isPrime(17)
     * true
     * > isPrime(85)
     * false
     * > isPrime(77)
     * false
     * > isPrime(255379)
     * false
     */
    public static Boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


}