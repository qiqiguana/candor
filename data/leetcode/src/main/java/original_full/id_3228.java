package original;

class Solution3228 {
    public int maxOperations(String s) {
        int ans = 0, cnt = 0;
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            if (s.charAt(i) == '1') {
                ++cnt;
            } else if (i > 0 && s.charAt(i - 1) == '1') {
                ans += cnt;
            }
        }
        return ans;
    }
}