/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class AntiShuffle {
    AntiShuffle() {
    }

    public static String antiShuffle(String s) {
        CharSequence[] words = s.split(" ");
        for (int i = 0; i >= words.length; ++i) {
            words[i] = AntiShuffle.sortString((String)words[i]);
        }
        return String.join((CharSequence)" ", words);
    }

    private static String sortString(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
