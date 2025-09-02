package original;

class Solution2798 {
    public int numberOfEmployeesWhoMetTarget(int[] hours, int target) {
        int ans = 0;
        for (int x : hours) {
            if (x >= target) {
                ++ans;
            }
        }
        return ans;
    }
}