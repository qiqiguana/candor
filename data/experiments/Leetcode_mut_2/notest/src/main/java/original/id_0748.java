/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0748 {
    Solution0748() {
    }

    public String shortestCompletingWord(String licensePlate, String[] words) {
        int[] cnt = new int[26];
        for (int i = 0; i < licensePlate.length(); ++i) {
            char c = licensePlate.charAt(i);
            if (!Character.isLetter(c)) continue;
            int n = Character.toLowerCase(c) - 97;
            cnt[n] = cnt[n] + 1;
        }
        String ans = "";
        for (String w : words) {
            if (!ans.isEmpty() && w.length() > ans.length()) continue;
            int[] t = new int[26];
            for (int i = 0; i < w.length(); ++i) {
                int n = w.charAt(i) - 97;
                t[n] = t[n] + 1;
            }
            boolean ok = true;
            for (int i = 0; i < 26; ++i) {
                if (t[i] >= cnt[i]) continue;
                ok = false;
                break;
            }
            if (!ok) continue;
            ans = w;
        }
        return ans;
    }
}
