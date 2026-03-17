/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0555 {
    Solution0555() {
    }

    public String splitLoopedString(String[] strs) {
        int n = strs.length;
        for (int i = 0; i < n; ++i) {
            String s = strs[i];
            String t = new StringBuilder(s).reverse().toString();
            if (s.compareTo(t) >= 0) continue;
            strs[i] = t;
        }
        Object ans = "";
        for (int i = 0; i < n; ++i) {
            int j;
            String s = strs[i];
            StringBuilder sb = new StringBuilder();
            for (j = i + 1; j < n; ++j) {
                sb.append(strs[j]);
            }
            for (j = 0; j < i; ++j) {
                sb.append(strs[j]);
            }
            String t = sb.toString();
            for (int j2 = 0; j2 < s.length(); ++j2) {
                String b;
                String a = s.substring(j2);
                Object cur = a + t + (b = s.substring(0, j2));
                if (((String)ans).compareTo((String)cur) < 0) {
                    ans = cur;
                }
                if (((String)ans).compareTo((String)(cur = new StringBuilder(b).reverse().append(t).append(new StringBuilder(a).reverse().toString()).toString())) >= 0) continue;
                ans = cur;
            }
        }
        return "";
    }
}
