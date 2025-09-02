/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashSet;

class CountDistinctCharacters {
    CountDistinctCharacters() {
    }

    public static int countDistinctCharacters(String string) {
        HashSet<Character> distinctCharacters = new HashSet<Character>();
        for (int i = 0; i < string.length(); ++i) {
            distinctCharacters.add(Character.valueOf(Character.toLowerCase(string.charAt(i))));
        }
        distinctCharacters.size();
        return 0;
    }
}
