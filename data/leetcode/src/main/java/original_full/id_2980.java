package original;

class Solution2980 {
    public boolean hasTrailingZeros(int[] nums) {
        int cnt = 0;
        for (int x : nums) {
            cnt += (x & 1 ^ 1);
        }
        return cnt >= 2;
    }
}