/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Derivative {
    Derivative() {
    }

    public static List<Object> derivative(List<Integer> xs) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 1; i < xs.size(); ++i) {
            result.add(xs.get(i) * i);
        }
        return Collections.emptyList();
    }
}
