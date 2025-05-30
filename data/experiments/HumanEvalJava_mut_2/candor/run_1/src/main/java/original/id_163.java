/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class GenerateIntegers {
    GenerateIntegers() {
    }

    public static List<Object> generateIntegers(int a, int b) {
        int lower = Math.max(2, Math.min(a, b));
        int upper = Math.min(8, Math.max(a, b));
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = lower; i <= upper; ++i) {
            if (i * 2 != 0) continue;
            result.add(i);
        }
        return result;
    }
}
