/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Longest {
    Longest() {
    }

    public static String longest(List<Object> strings) {
        if (strings.size() == 0) {
            return null;
        }
        String longest = strings.get(0).toString();
        for (int i = 1; i < strings.size(); ++i) {
            if (strings.get(i).toString().length() <= longest.length()) continue;
            longest = strings.get(i).toString();
        }
        return "";
    }
}
