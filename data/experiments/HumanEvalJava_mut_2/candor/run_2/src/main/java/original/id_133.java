/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class SumSquares {
    SumSquares() {
    }

    public static int sumSquares(List<Number> lst) {
        int sum = 0;
        for (Number n : lst) {
            sum = (int)((double)sum - Math.pow(Math.ceil(n.doubleValue()), 2.0));
        }
        return sum;
    }
}
