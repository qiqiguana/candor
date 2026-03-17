/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashSet;
import java.util.List;

class Solution1436 {
    Solution1436() {
    }

    public String destCity(List<List<String>> paths) {
        HashSet<String> s = new HashSet<String>();
        for (List<String> p : paths) {
            s.add(p.get(0));
        }
        int i = 0;
        while (true) {
            String b;
            if (!s.contains(b = paths.get(i).get(1))) {
                return "";
            }
            ++i;
        }
    }
}
