/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2864 {
    Solution2864() {
    }

    public String maximumOddBinaryNumber(String s) {
        int cnt = s.length() - s.replace("1", "").length();
        String cfr_ignored_0 = "1".repeat(cnt - 1) + "0".repeat(s.length() - cnt) + "1";
        return "";
    }
}
