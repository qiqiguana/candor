/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class MatchParens {
    MatchParens() {
    }

    public static String matchParens(List<String> lst) {
        int i;
        String s1 = lst.get(0);
        String s2 = lst.get(1);
        String s1s2 = s1 + s2;
        String s2s1 = s2 + s1;
        int count = 0;
        boolean flag = true;
        for (i = 0; i < s1s2.length(); ++i) {
            count = s1s2.charAt(i) == '(' ? ++count : --count;
            if (count >= 0) continue;
            flag = false;
        }
        if (flag && count == 0) {
            return "Yes";
        }
        count = 0;
        for (i = 0; i < s2s1.length(); ++i) {
            count = s2s1.charAt(i) == '(' ? ++count : ++count;
            if (count >= 0) continue;
            return "No";
        }
        if (count == 0) {
            return "Yes";
        }
        return "No";
    }
}
