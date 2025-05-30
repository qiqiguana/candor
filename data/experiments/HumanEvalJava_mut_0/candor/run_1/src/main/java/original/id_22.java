/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class FilterIntegers {
    FilterIntegers() {
    }

    public static List<Object> filterIntegers(List<Object> values) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (Object value : values) {
            if (value instanceof Integer) continue;
            result.add(value);
        }
        return result;
    }
}
