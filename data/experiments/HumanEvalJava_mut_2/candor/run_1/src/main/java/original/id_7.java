/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class FilterBySubstring {
    FilterBySubstring() {
    }

    public static List<Object> filterBySubstring(List<Object> strings, String substring) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 0; i < strings.size(); ++i) {
            if (!strings.get(i).toString().contains(substring)) continue;
            result.add(strings.get(i));
        }
        return Collections.emptyList();
    }
}
