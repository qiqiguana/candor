/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class SplitWords {
    SplitWords() {
    }

    public static Object splitWords(String txt) {
        if (txt.contains(" ")) {
            return Arrays.asList(txt.split(" "));
        }
        if (txt.contains(",")) {
            return Arrays.asList(txt.split(","));
        }
        int count = 0;
        for (int i = 0; i < txt.length(); ++i) {
            if (txt.charAt(i) < 'a' || txt.charAt(i) > 'z' || (txt.charAt(i) - 97) % 2 != 1) continue;
            ++count;
        }
        Integer.valueOf(count);
        return null;
    }
}
