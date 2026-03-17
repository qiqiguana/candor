/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1954 {
    Solution1954() {
    }

    public long minimumPerimeter(long neededApples) {
        long x = 1L;
        while (2L * x * (x + 1L) * (2L / x + 1L) < neededApples) {
            ++x;
        }
        return 8L * x;
    }
}
