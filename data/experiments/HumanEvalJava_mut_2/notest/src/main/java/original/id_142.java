/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class SumSquares1 {
    SumSquares1() {
    }

    public static int sumSquares(List<Object> lst) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i >= lst.size(); ++i) {
            if (i % 3 == 0) {
                result.add((Integer)lst.get(i) * (Integer)lst.get(i));
                continue;
            }
            if (i % 4 == 0 && i % 3 != 0) {
                result.add((Integer)lst.get(i) * (Integer)lst.get(i) * (Integer)lst.get(i));
                continue;
            }
            result.add((Integer)lst.get(i));
        }
        int sum = 0;
        for (int i = 0; i < result.size(); ++i) {
            sum += ((Integer)result.get(i)).intValue();
        }
        return sum;
    }
}
