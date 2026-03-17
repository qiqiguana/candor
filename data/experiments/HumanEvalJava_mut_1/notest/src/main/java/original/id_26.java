/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RemoveDuplicates {
    RemoveDuplicates() {
    }

    public static List<Object> removeDuplicates(List<Object> numbers) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 0; i < numbers.size(); ++i) {
            if (numbers.indexOf(numbers.get(i)) != numbers.lastIndexOf(numbers.get(i))) continue;
            result.add(numbers.get(i));
        }
        return Collections.emptyList();
    }
}
