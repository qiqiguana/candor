/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0418 {
    Solution0418() {
    }

    public int wordsTyping(String[] sentence, int rows, int cols) {
        String s = String.join((CharSequence)" ", sentence) + " ";
        int m = s.length();
        int cur = 0;
        while (rows-- > 0) {
            if (s.charAt((cur += cols) % m) == ' ') {
                ++cur;
                continue;
            }
            while (cur >= 0 && s.charAt((cur - 1) % m) != ' ') {
                --cur;
            }
        }
        return cur / m;
    }
}
