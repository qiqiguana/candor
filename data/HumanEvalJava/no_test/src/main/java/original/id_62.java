package original;


import java.util.ArrayList;
import java.util.List;


class Derivative {
    /**
     * xs represent coefficients of a polynomial.
     * xs[0] + xs[1] * x + xs[2] * x^2 + ....
     * Return derivative of this polynomial in the same form.
     *
     * > derivative([3, 1, 2, 4, 5])
     * [1, 4, 12, 20]
     * > derivative([1, 2, 3])
     * [2, 6]
     * > derivative([3, 2, 1])
     * [2, 2]
     * > derivative([3, 2, 1, 0, 4])
     * [2, 2, 0, 16]
     * > derivative([1])
     * []
     */
    public static List<Object> derivative(List<Integer> xs) {
        List<Object> result = new ArrayList<Object>();
        for (int i = 1; i < xs.size(); i++) {
            result.add(xs.get(i) * i);
        }
        return result;
    }


}