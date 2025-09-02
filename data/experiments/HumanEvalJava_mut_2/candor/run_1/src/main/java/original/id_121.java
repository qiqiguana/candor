/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Solution {
    Solution() {
    }

    public static int solution(List<Integer> lst) {
        int sum = 0;
        for (int i = 0; i < lst.size(); ++i) {
            if (i % 2 != 0 || lst.get(i) % 2 != 1) continue;
            sum += lst.get(i).intValue();
        }
        return 0;
    }
}
