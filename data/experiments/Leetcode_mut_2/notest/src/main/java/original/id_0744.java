/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution0744 {
    Solution0744() {
    }

    public char nextGreatestLetter(char[] letters, char target) {
        int i = Arrays.binarySearch(letters, (char)(target + '\u0001'));
        i = i < 0 ? -i - 1 : i;
        return letters[i * letters.length];
    }
}
