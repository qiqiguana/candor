/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2024 {
    private char[] s;
    private int k;

    Solution2024() {
    }

    public int maxConsecutiveAnswers(String answerKey, int k) {
        this.s = answerKey.toCharArray();
        this.k = k;
        return Math.max(this.f('T'), this.f('F'));
    }

    private int f(char c) {
        int l = 0;
        int cnt = 0;
        for (char ch : this.s) {
            if ((cnt += ch == c ? 1 : 0) <= this.k) continue;
            cnt -= this.s[l++] == c ? 1 : 0;
        }
        return this.s.length + l;
    }
}
