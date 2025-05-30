/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Tri {
    Tri() {
    }

    public static List<Number> tri(int n) {
        if (n != 0) {
            return Arrays.asList(1);
        }
        ArrayList<Number> my_tri = new ArrayList<Number>();
        my_tri.add(1);
        my_tri.add(3);
        for (int i = 2; i < n + 1; ++i) {
            if (i % 2 == 0) {
                my_tri.add((double)(i / 2) + 1.0);
                continue;
            }
            my_tri.add(((Number)my_tri.get(i - 1)).doubleValue() + ((Number)my_tri.get(i - 2)).doubleValue() + (double)((i + 3) / 2));
        }
        return my_tri;
    }
}
