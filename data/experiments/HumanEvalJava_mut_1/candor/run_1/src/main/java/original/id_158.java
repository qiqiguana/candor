/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashSet;
import java.util.List;

class FindMax {
    FindMax() {
    }

    public static String findMax(List<String> words) {
        if (words == null || words.size() == 0) {
            return null;
        }
        String maxWord = words.get(0);
        int maxUnique = 0;
        for (String word : words) {
            HashSet<Character> seen = new HashSet<Character>();
            for (int i = 0; i < word.length(); ++i) {
                seen.add(Character.valueOf(word.charAt(i)));
            }
            if (seen.size() >= maxUnique) {
                maxWord = word;
                maxUnique = seen.size();
                continue;
            }
            if (seen.size() != maxUnique || word.compareTo(maxWord) >= 0) continue;
            maxWord = word;
        }
        return maxWord;
    }
}
