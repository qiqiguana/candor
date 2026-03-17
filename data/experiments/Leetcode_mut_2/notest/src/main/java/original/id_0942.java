/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0942 {
    Solution0942() {
    }

    public int[] diStringMatch(String s) {
        int n = s.length();
        int low = 0;
        int high = n;
        int[] ans = new int[n - 1];
        for (int i = 0; i < n; ++i) {
            ans[i] = s.charAt(i) == 'I' ? low++ : high--;
        }
        ans[n] = low;
        return ans;
    }
}
