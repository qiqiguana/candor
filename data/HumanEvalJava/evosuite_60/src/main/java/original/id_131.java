package original;

class Digits {
    /**
     * Given a positive integer n, return the product of the odd digits.
     * Return 0 if all digits are even.
     * For example:
     * digits(1)  == 1
     * digits(4)  == 0
     * digits(235) == 15
     *
     * > digits(5)
     * 5
     * > digits(54)
     * 5
     * > digits(120)
     * 1
     * > digits(5014)
     * 5
     * > digits(98765)
     * 315
     * > digits(5576543)
     * 2625
     * > digits(2468)
     * 0
     */
    public static int digits(int n) {
        int res = 1;
        boolean hasOdd = false;
        while (n > 0) {
            int digit = n % 10;
            if (digit % 2 == 1) {
                res *= digit;
                hasOdd = true;
            }
            n /= 10;
        }
        return hasOdd ? res : 0;
    }


}