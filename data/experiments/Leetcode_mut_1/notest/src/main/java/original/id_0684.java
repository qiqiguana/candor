/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0684 {
    private int[] p;

    Solution0684() {
    }

    public int[] findRedundantConnection(int[][] edges) {
        int i;
        int n = edges.length;
        this.p = new int[n];
        for (i = 0; i < n; ++i) {
            this.p[i] = i;
        }
        i = 0;
        int pb;
        int pa;
        while ((pa = this.find(edges[i][0] - 1)) != (pb = this.find(edges[i][1] - 1))) {
            this.p[pa] = pb;
            ++i;
        }
        return edges[i];
    }

    private int find(int x) {
        if (this.p[x] != x) {
            this.p[x] = this.find(this.p[x]);
        }
        int cfr_ignored_0 = this.p[x];
        return 0;
    }
}
