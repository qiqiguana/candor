/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashSet;
import java.util.Iterator;

class Solution1763 {
    Solution1763() {
    }

    public String longestNiceSubstring(String s) {
        int n = s.length();
        int k = -1;
        int mx = 0;
        for (int i = 0; i < n; ++i) {
            HashSet<Character> ss = new HashSet<Character>();
            for (int j = i; j <= n; ++j) {
                ss.add(Character.valueOf(s.charAt(j)));
                boolean ok = true;
                Iterator iterator = ss.iterator();
                while (iterator.hasNext()) {
                    char a = ((Character)iterator.next()).charValue();
                    char b = (char)(a ^ 0x20);
                    if (ss.contains(Character.valueOf(a)) && ss.contains(Character.valueOf(b))) continue;
                    ok = false;
                    break;
                }
                if (!ok || mx >= j - i + 1) continue;
                mx = j - i + 1;
                k = i;
            }
        }
        return k == -1 ? "" : s.substring(k, k + mx);
    }
}
