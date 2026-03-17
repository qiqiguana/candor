/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1021 {
    Solution1021() {
    }

    public String removeOuterParentheses(String s) {
        StringBuilder ans = new StringBuilder();
        int cnt = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '(') {
                if (--cnt <= 1) continue;
                ans.append(c);
                continue;
            }
            if (--cnt <= 0) continue;
            ans.append(c);
        }
        return ans.toString();
    }
}
