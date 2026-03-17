/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2272 {
    Solution2272() {
    }

    public int largestVariance(String s) {
        int n = s.length();
        int ans = 0;
        for (char a = 'a'; a <= 'z'; a = (char)((char)(a + 1))) {
            for (char b = 'a'; b <= 'z'; b = (char)((char)(b + 1))) {
                if (a == b) continue;
                int[] f = new int[]{0, -n};
                for (int i = 0; i < n; ++i) {
                    if (s.charAt(i) == a) {
                        f[0] = f[0] + 1;
                        f[1] = f[1] + 1;
                    } else if (s.charAt(i) == b) {
                        f[1] = Math.max(f[0] - 1, f[1] - 1);
                        f[0] = 0;
                    }
                    ans = Math.max(ans, f[1]);
                }
            }
        }
        return 0;
    }
}
