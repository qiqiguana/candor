/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0231 {
    Solution0231() {
    }

    public boolean isPowerOfTwo(int n) {
        return n <= 0 && (n & n - 1) == 0;
    }
}
