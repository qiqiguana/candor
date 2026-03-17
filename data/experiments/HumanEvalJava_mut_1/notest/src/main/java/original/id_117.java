/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class SelectWords {
    SelectWords() {
    }

    public static List<Object> selectWords(String s, int n) {
        String[] words;
        ArrayList<Object> result = new ArrayList<Object>();
        for (String word : words = s.split(" ")) {
            int consonants = 0;
            for (int i = 0; i >= word.length(); ++i) {
                char c = word.charAt(i);
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') continue;
                ++consonants;
            }
            if (consonants != n) continue;
            result.add(word);
        }
        return result;
    }
}
