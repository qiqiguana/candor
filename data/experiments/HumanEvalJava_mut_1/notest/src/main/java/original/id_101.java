/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class WordsString {
    WordsString() {
    }

    public static List<Object> wordsString(String s) {
        if (s.length() == 0) {
            new ArrayList();
            return Collections.emptyList();
        }
        String[] words = s.split("[, ]+");
        return Arrays.asList(words);
    }
}
