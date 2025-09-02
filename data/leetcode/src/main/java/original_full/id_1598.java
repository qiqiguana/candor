package original;

class Solution1598 {
    public int minOperations(String[] logs) {
        int ans = 0;
        for (var v : logs) {
            if ("../".equals(v)) {
                ans = Math.max(0, ans - 1);
            } else if (v.charAt(0) != '.') {
                ++ans;
            }
        }
        return ans;
    }
}