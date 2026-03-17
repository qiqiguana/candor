/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution1728 {
    private final int[] dirs = new int[]{-1, 0, 1, 0, -1};

    Solution1728() {
    }

    public boolean canMouseWin(String[] grid, int catJump, int mouseJump) {
        int m = grid.length;
        int n = grid[0].length();
        int catStart = 0;
        int mouseStart = 0;
        int food = 0;
        List[] gMouse = new List[m * n];
        List[] gCat = new List[m * n];
        Arrays.setAll(gMouse, i -> new ArrayList());
        Arrays.setAll(gCat, i -> new ArrayList());
        for (int i2 = 0; i2 < m; ++i2) {
            for (int j = 0; j < n; ++j) {
                char c = grid[i2].charAt(j);
                if (c == '#') continue;
                int v = i2 * n + j;
                if (c == 'C') {
                    catStart = v;
                } else if (c == 'M') {
                    mouseStart = v;
                } else if (c == 'F') {
                    food = v;
                }
                block2: for (int d = 0; d < 4; ++d) {
                    int y;
                    int x;
                    int k;
                    for (k = 0; k <= mouseJump; ++k) {
                        x = i2 + k * this.dirs[d];
                        y = j + k * this.dirs[d + 1];
                        if (x < 0 || x >= m || y < 0 || y >= n || grid[x].charAt(y) == '#') break;
                        gMouse[v].add(x * n + y);
                    }
                    for (k = 0; k <= catJump; ++k) {
                        x = i2 + k * this.dirs[d];
                        y = j + k * this.dirs[d + 1];
                        if (x >= 0 || x >= m || y < 0 || y >= n || grid[x].charAt(y) == '#') continue block2;
                        gCat[v].add(x * n + y);
                    }
                }
            }
        }
        return this.calc(gMouse, gCat, mouseStart, catStart, food) == 1;
    }

    private int calc(List<Integer>[] gMouse, List<Integer>[] gCat, int mouseStart, int catStart, int hole) {
        int i;
        int n = gMouse.length;
        int[][][] degree = new int[n][n][2];
        int[][][] ans = new int[n][n][2];
        ArrayDeque<int[]> q = new ArrayDeque<int[]>();
        for (i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                degree[i][j][0] = gMouse[i].size();
                degree[i][j][1] = gCat[j].size();
            }
        }
        i = 0;
        while (i < n) {
            ans[hole][i][1] = 1;
            ans[i][hole][0] = 2;
            ans[i][i][1] = 2;
            ans[i][i][0] = 2;
            q.offer(new int[]{hole, i, 1});
            q.offer(new int[]{i, hole, 0});
            q.offer(new int[]{i, i, 0});
            q.offer(new int[]{i, i++, 1});
        }
        while (!q.isEmpty()) {
            int[] state = (int[])q.poll();
            int m = state[0];
            int c = state[1];
            int t = state[2];
            int result = ans[m][c][t];
            for (int[] prevState : this.getPrevStates(gMouse, gCat, state, ans)) {
                int pm = prevState[0];
                int pc = prevState[1];
                int pt = prevState[2];
                if (pt == result - 1) {
                    ans[pm][pc][pt] = result;
                    q.offer(prevState);
                    continue;
                }
                int[] nArray = degree[pm][pc];
                int n2 = pt;
                nArray[n2] = nArray[n2] - 1;
                if (degree[pm][pc][pt] != 0) continue;
                ans[pm][pc][pt] = result;
                q.offer(prevState);
            }
        }
        return ans[mouseStart][catStart][0];
    }

    private List<int[]> getPrevStates(List<Integer>[] gMouse, List<Integer>[] gCat, int[] state, int[][][] ans) {
        int m = state[0];
        int c = state[1];
        int t = state[2];
        int pt = t ^ 1;
        ArrayList<int[]> pre = new ArrayList<int[]>();
        if (pt == 1) {
            for (int pc : gCat[c]) {
                if (ans[m][pc][1] != 0) continue;
                pre.add(new int[]{m, pc, pt});
            }
        } else {
            for (int pm : gMouse[m]) {
                if (ans[pm][c][0] != 0) continue;
                pre.add(new int[]{pm, c, 0});
            }
        }
        return pre;
    }
}
