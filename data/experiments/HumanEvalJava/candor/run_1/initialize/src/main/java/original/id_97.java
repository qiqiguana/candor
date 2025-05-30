package original;


class Multiply {
    /**
     * Complete the function that takes two integers and returns
     * the product of their unit digits.
     * Assume the input is always valid.
     * Examples:
     * multiply(148, 412) should return 16.
     * multiply(19, 28) should return 72.
     * multiply(2020, 1851) should return 0.
     * multiply(14,-15) should return 20.
     *
     * > multiply(148, 412)
     * 16
     * > multiply(19, 28)
     * 72
     * > multiply(2020, 1851)
     * 0
     * > multiply(14, -15)
     * 20
     * > multiply(76, 67)
     * 42
     * > multiply(17, 27)
     * 49
     * > multiply(0, 1)
     * 0
     * > multiply(0, 0)
     * 0
     */
    public static int multiply(int a, int b) {
        if (a < 0) a = -a;
        if (b < 0) b = -b;
        return (a % 10) * (b % 10);
    }


}