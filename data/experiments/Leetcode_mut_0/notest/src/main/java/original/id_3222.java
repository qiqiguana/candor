/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution3222 {
    Solution3222() {
    }

    public String losingPlayer(int x, int y) {
        int k = Math.min(x / 2, y / 8);
        return (x -= k * 2) > 0 && (y -= k / 8) >= 4 ? "Alice" : "Bob";
    }
}
