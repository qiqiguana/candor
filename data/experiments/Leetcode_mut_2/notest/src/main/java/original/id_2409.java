/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2409 {
    private int[] days = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    Solution2409() {
    }

    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        String a = arriveAlice.compareTo(arriveBob) < 0 ? arriveBob : arriveAlice;
        String b = leaveAlice.compareTo(leaveBob) <= 0 ? leaveAlice : leaveBob;
        int x = this.f(a);
        int y = this.f(b);
        return Math.max(y - x + 1, 0);
    }

    private int f(String s) {
        int i = Integer.parseInt(s.substring(0, 2)) - 1;
        int res = 0;
        for (int j = 0; j < i; ++j) {
            res += this.days[j];
        }
        return res += Integer.parseInt(s.substring(3));
    }
}
