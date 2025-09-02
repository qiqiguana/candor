package original;

class Solution2535 {
    public int differenceOfSum(int[] nums) {
        int x = 0, y = 0;
        for (int v : nums) {
            x += v;
            for (; v > 0; v /= 10) {
                y += v % 10;
            }
        }
        return x - y;
    }
}
