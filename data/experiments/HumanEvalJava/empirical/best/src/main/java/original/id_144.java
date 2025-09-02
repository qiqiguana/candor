package original;


class Simplify {
    /**
     * Your task is to implement a function that will simplify the mathmatical fractionexpression
     * x * n. The function returns True if x * n evaluates to a whole number and False
     * otherwise. Both x and n, are string representation of a fraction, and have the following format,
     * <numerator>/<denominator> where both numerator and denominator are positive whole numbers.
     *
     * You can assume that x, and n are valid fractions, and do not have zero as denominator.
     *
     * simplify("1/5", "5/1") = True
     * simplify("1/6", "2/1") = False
     * simplify("7/10", "10/2") = False
     *
     * > simplify("1/5", "5/1")
     * true
     * > simplify("1/6", "2/1")
     * false
     * > simplify("5/1", "3/1")
     * true
     * > simplify("7/10", "10/2")
     * false
     * > simplify("2/10", "50/10")
     * true
     * > simplify("7/2", "4/2")
     * true
     * > simplify("11/6", "6/1")
     * true
     * > simplify("2/3", "5/2")
     * false
     * > simplify("5/2", "3/5")
     * false
     * > simplify("2/4", "8/4")
     * true
     * > simplify("2/4", "4/2")
     * true
     * > simplify("1/5", "5/1")
     * true
     * > simplify("1/5", "1/5")
     * false
     */
    public static Boolean simplify(String x, String n) {
        String[] a = x.split("/");
        String[] b = n.split("/");
        int numerator = Integer.parseInt(a[0]) * Integer.parseInt(b[0]);
        int denom = Integer.parseInt(a[1]) * Integer.parseInt(b[1]);
        if (numerator % denom == 0) {
            return true;
        }
        return false;
    }


}