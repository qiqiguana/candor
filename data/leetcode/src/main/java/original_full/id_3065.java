package original;

class Solution3065 {
    public int minOperations(int[] nums, int k) {
        int ans = 0;
        for (int x : nums) {
            if (x < k) {
                ++ans;
            }
        }
        return ans;
    }
}