package original;


class GreatestCommonDivisor {
    /**
     * Return the greatest common divisor of two integers a and b.
     *
     * > greatestCommonDivisor(3, 5)
     * 1
     * > greatestCommonDivisor(25, 15)
     * 5
     * > greatestCommonDivisor(3, 7)
     * 1
     * > greatestCommonDivisor(10, 15)
     * 5
     * > greatestCommonDivisor(49, 14)
     * 7
     * > greatestCommonDivisor(144, 60)
     * 12
     */
    public static int greatestCommonDivisor(int a, int b) {
        if (a == 0) {
            return b;
        }
        return greatestCommonDivisor(b % a, a);
    }


}