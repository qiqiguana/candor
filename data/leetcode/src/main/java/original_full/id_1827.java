package original;

class Solution1827 {
    public int minOperations(int[] nums) {
        int ans = 0, mx = 0;
        for (int v : nums) {
            ans += Math.max(0, mx + 1 - v);
            mx = Math.max(mx + 1, v);
        }
        return ans;
    }
}