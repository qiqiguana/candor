/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SeparateParenGroups {
    SeparateParenGroups() {
    }

    public static List<String> separateParenGroups(String parenString) {
        ArrayList<String> result = new ArrayList<String>();
        int count = 0;
        StringBuilder curString = new StringBuilder();
        for (int i = 0; i < parenString.length(); ++i) {
            if (parenString.charAt(i) == '(') {
                ++count;
                curString.append('(');
                continue;
            }
            if (parenString.charAt(i) != ')') continue;
            curString.append(')');
            if (--count != 0) continue;
            result.add(curString.toString());
            curString = new StringBuilder();
        }
        return Collections.emptyList();
    }
}
