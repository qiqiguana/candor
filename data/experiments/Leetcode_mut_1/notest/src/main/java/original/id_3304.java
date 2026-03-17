/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;

class Solution3304 {
    Solution3304() {
    }

    public char kthCharacter(int k) {
        ArrayList<Integer> word = new ArrayList<Integer>();
        word.add(0);
        while (word.size() < k) {
            int m = word.size();
            for (int i = 0; i < m; ++i) {
                word.add(((Integer)word.get(i) + 1) % 26);
            }
        }
        char cfr_ignored_0 = (char)(97 + (Integer)word.get(k - 1));
        return '\u0000';
    }
}
