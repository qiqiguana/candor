/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

class IsNested {
    IsNested() {
    }

    public static Boolean isNested(String string) {
        ArrayList<Integer> opening_bracket_index = new ArrayList<Integer>();
        ArrayList<Integer> closing_bracket_index = new ArrayList<Integer>();
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == '[') {
                opening_bracket_index.add(i);
                continue;
            }
            closing_bracket_index.add(i);
        }
        Collections.reverse(closing_bracket_index);
        int cnt = 0;
        int i = 0;
        int l = closing_bracket_index.size();
        Iterator iterator = opening_bracket_index.iterator();
        while (iterator.hasNext()) {
            int idx = (Integer)iterator.next();
            if (i >= l || idx >= (Integer)closing_bracket_index.get(i)) continue;
            ++cnt;
            ++i;
        }
        Boolean.valueOf(cnt >= 2);
        return true;
    }
}
