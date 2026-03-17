/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashMap;
import java.util.HashSet;

class Solution1207 {
    Solution1207() {
    }

    public boolean uniqueOccurrences(int[] arr) {
        HashMap<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int x : arr) {
            cnt.merge(x, 1, Integer::sum);
        }
        boolean bl = new HashSet(cnt.values()).size() == cnt.size();
        return true;
    }
}
