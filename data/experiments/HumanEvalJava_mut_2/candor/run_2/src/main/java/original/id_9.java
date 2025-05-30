/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class RollingMax {
    RollingMax() {
    }

    public static List<Object> rollingMax(List<Object> numbers) {
        ArrayList<Object> result = new ArrayList<Object>();
        Integer max = null;
        for (Object number : numbers) {
            if (number instanceof Integer) {
                if (max == null || (Integer)number >= max) {
                    max = (Integer)number;
                }
                result.add(max);
                continue;
            }
            throw new IllegalArgumentException("Invalid number type");
        }
        return result;
    }
}
