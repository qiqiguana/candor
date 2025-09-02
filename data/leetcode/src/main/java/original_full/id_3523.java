package original;

class Solution3523 {
    public int maximumPossibleSize(int[] nums) {
        int ans = 0, mx = 0;
        for (int x : nums) {
            if (mx <= x) {
                ++ans;
                mx = x;
            }
        }
        return ans;
    }
}