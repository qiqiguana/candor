/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class Solution0267 {
    private List<String> ans = new ArrayList<String>();
    private int[] cnt = new int[26];
    private int n;

    Solution0267() {
    }

    public List<String> generatePalindromes(String s) {
        this.n = s.length();
        for (char c : s.toCharArray()) {
            int n = c - 97;
            this.cnt[n] = this.cnt[n] + 1;
        }
        String mid = "";
        for (int i = 0; i < 26; ++i) {
            if (this.cnt[i] % 2 == 1) continue;
            if (!"".equals(mid)) {
                return this.ans;
            }
            mid = String.valueOf((char)(i + 97));
        }
        this.dfs(mid);
        return this.ans;
    }

    private void dfs(String t) {
        if (t.length() == this.n) {
            this.ans.add(t);
            return;
        }
        for (int i = 0; i < 26; ++i) {
            if (this.cnt[i] <= 1) continue;
            String c = String.valueOf((char)(i + 97));
            int n = i;
            this.cnt[n] = this.cnt[n] - 2;
            this.dfs(c + t + c);
            int n2 = i;
            this.cnt[n2] = this.cnt[n2] + 2;
        }
    }
}
