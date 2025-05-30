/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class ParseNestedParens {
    ParseNestedParens() {
    }

    public static List<Integer> parseNestedParens(String parenString) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int count = 0;
        int maximum_depth = 0;
        for (int i = 0; i < parenString.length(); ++i) {
            if (parenString.charAt(i) == '(') {
                maximum_depth = Math.max(maximum_depth, ++count);
                continue;
            }
            if (parenString.charAt(i) != ')' || --count == 0) continue;
            result.add(maximum_depth);
            maximum_depth = 0;
        }
        return result;
    }
}
