package original;

class AnyInt {
    /**
     * * Create a function that takes 3 numbers.
     * Returns true if one of the numbers is equal to the sum of the other two, and all numbers are integers.
     * Returns false in any other cases.
     *
     * Examples
     * anyInt(5, 2, 7) ➞ True
     *
     * anyInt(3, 2, 2) ➞ False
     *
     * anyInt(3, -2, 1) ➞ True
     *
     * anyInt(3.6, -2.2, 2) ➞ False
     *
     * > anyInt(2, 3, 1)
     * true
     * > anyInt(2.5, 2, 3)
     * false
     * > anyInt(1.5, 5, 3.5)
     * false
     * > anyInt(2, 6, 2)
     * false
     * > anyInt(4, 2, 2)
     * true
     * > anyInt(2.2, 2.2, 2.2)
     * false
     * > anyInt(-4, 6, 2)
     * true
     * > anyInt(2, 1, 1)
     * true
     * > anyInt(3, 4, 7)
     * true
     * > anyInt(3.0, 4, 7)
     * false
     */
    public static Boolean anyInt(Number x, Number y, Number z) {
        if (x instanceof Integer && y instanceof Integer && z instanceof Integer) {
            if (x.intValue() == y.intValue() + z.intValue()) {
                return true;
            }
            if (y.intValue() == x.intValue() + z.intValue()) {
                return true;
            }
            if (z.intValue() == x.intValue() + y.intValue()) {
                return true;
            }
        }
        return false;
    }

}