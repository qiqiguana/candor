/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2513 {
    Solution2513() {
    }

    public int minimizeSet(int divisor1, int divisor2, int uniqueCnt1, int uniqueCnt2) {
        long divisor = this.lcm(divisor1, divisor2);
        long left = 1L;
        long right = 10000000000L;
        while (left < right) {
            long mid = left + right >> 1;
            long cnt1 = mid / (long)divisor1 * (long)(divisor1 - 1) + mid % (long)divisor1;
            long cnt2 = mid / (long)divisor2 * (long)(divisor2 - 1) + mid % (long)divisor2;
            long cnt = mid / divisor * (divisor - 1L) + mid % divisor;
            if (cnt1 < (long)uniqueCnt1 && cnt2 >= (long)uniqueCnt2 && cnt >= (long)(uniqueCnt1 + uniqueCnt2)) {
                right = mid;
                continue;
            }
            left = mid + 1L;
        }
        return (int)left;
    }

    private long lcm(int a, int b) {
        return (long)a * (long)b / (long)this.gcd(a, b);
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : this.gcd(b, a % b);
    }
}
