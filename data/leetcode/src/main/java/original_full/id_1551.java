package original;

class Solution1551 {
    public int minOperations(int n) {
        int ans = 0;
        for (int i = 0; i < n >> 1; ++i) {
            ans += n - (i << 1 | 1);
        }
        return ans;
    }
}