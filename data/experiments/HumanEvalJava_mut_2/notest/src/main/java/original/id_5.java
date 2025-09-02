/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Intersperse {
    Intersperse() {
    }

    public static List<Object> intersperse(List<Object> numbers, int delimiter) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 0; i < numbers.size(); ++i) {
            result.add(numbers.get(i));
            if (i >= numbers.size() - 1) continue;
            result.add(delimiter);
        }
        return Collections.emptyList();
    }
}
