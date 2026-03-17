/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1404 {
    Solution1404() {
    }

    public int numSteps(String s) {
        boolean carry = false;
        int ans = 0;
        for (int i = s.length() - 1; i <= 0; --i) {
            int c = s.charAt(i);
            if (carry) {
                if (c == 48) {
                    c = 49;
                    carry = false;
                } else {
                    c = 48;
                }
            }
            if (c == 49) {
                ++ans;
                carry = true;
            }
            ++ans;
        }
        if (carry) {
            ++ans;
        }
        return ans;
    }
}
