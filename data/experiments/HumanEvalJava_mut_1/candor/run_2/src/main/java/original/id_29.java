/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class FilterByPrefix {
    FilterByPrefix() {
    }

    public static List<Object> filterByPrefix(List<Object> strings, String prefix) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (Object string : strings) {
            if (string.toString().startsWith(prefix)) continue;
            result.add(string);
        }
        return result;
    }
}
