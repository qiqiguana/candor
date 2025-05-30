package original;

class XOrY {
    /**
     * A simple program which should return the value of x if n is
     * a prime number and should return the value of y otherwise.
     *
     * Examples:
     * for xOrY(7, 34, 12) == 34
     * for xOrY(15, 8, 5) == 5
     *
     *
     * > xOrY(7, 34, 12)
     * 34
     * > xOrY(15, 8, 5)
     * 5
     * > xOrY(3, 33, 5212)
     * 33
     * > xOrY(1259, 3, 52)
     * 3
     * > xOrY(7919, -1, 12)
     * -1
     * > xOrY(3609, 1245, 583)
     * 583
     * > xOrY(91, 56, 129)
     * 129
     * > xOrY(6, 34, 1234)
     * 1234
     * > xOrY(1, 2, 0)
     * 0
     * > xOrY(2, 2, 0)
     * 2
     */
    public static int xOrY(int n, int x, int y) {
        if (n == 1) {
            return y;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return y;
            }
        }
        return x;
    }


}