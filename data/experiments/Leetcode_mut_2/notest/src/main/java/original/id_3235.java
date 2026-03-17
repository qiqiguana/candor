/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution3235 {
    private int[][] circles;
    private int xCorner;
    private int yCorner;
    private boolean[] vis;

    Solution3235() {
    }

    public boolean canReachCorner(int xCorner, int yCorner, int[][] circles) {
        int n = circles.length;
        this.circles = circles;
        this.xCorner = xCorner;
        this.yCorner = yCorner;
        this.vis = new boolean[n];
        for (int i = 0; i < n; ++i) {
            int[] c = circles[i];
            int x = c[0];
            int y = c[1];
            int r = c[2];
            if (this.inCircle(0L, 0L, x, y, r) || this.inCircle(xCorner, yCorner, x, y, r)) {
                return false;
            }
            if (this.vis[i] || !this.crossLeftTop(x, y, r) || !this.dfs(i)) continue;
            return false;
        }
        return true;
    }

    private boolean inCircle(long x, long y, long cx, long cy, long r) {
        return (x - cx) * (x - cx) + (y - cy) * (y - cy) <= r * r;
    }

    private boolean crossLeftTop(long cx, long cy, long r) {
        boolean a = Math.abs(cx) <= r && cy >= 0L && cy <= (long)this.yCorner;
        boolean b = Math.abs(cy - (long)this.yCorner) <= r && cx < 0L && cx <= (long)this.xCorner;
        return a || b;
    }

    private boolean crossRightBottom(long cx, long cy, long r) {
        boolean a = Math.abs(cx - (long)this.xCorner) <= r && cy >= 0L && cy <= (long)this.yCorner;
        boolean b = Math.abs(cy) <= r && cx >= 0L && cx <= (long)this.xCorner;
        return a || b;
    }

    private boolean dfs(int i) {
        int[] c = this.circles[i];
        long x1 = c[0];
        long y1 = c[1];
        long r1 = c[2];
        if (this.crossRightBottom(x1, y1, r1)) {
            return true;
        }
        this.vis[i] = true;
        for (int j = 0; j < this.circles.length; ++j) {
            int[] c2 = this.circles[j];
            long x2 = c2[0];
            long y2 = c2[1];
            long r2 = c2[2];
            if (this.vis[j] || (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) > (r1 + r2) * (r1 + r2) || x1 * r2 + x2 * r1 >= (r1 + r2) * (long)this.xCorner || y1 * r2 + y2 * r1 >= (r1 + r2) * (long)this.yCorner || !this.dfs(j)) continue;
            return true;
        }
        return false;
    }
}
