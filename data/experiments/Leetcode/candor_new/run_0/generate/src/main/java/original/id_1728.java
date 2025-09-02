package original;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
class Solution1728 {
    private final int[] dirs = {-1, 0, 1, 0, -1};

    public boolean canMouseWin(String[] grid, int catJump, int mouseJump) {
  /**
  A game is played by a cat and a mouse named Cat and Mouse. The environment is represented by a grid of size rows x cols, where each element is a wall, floor, player (Cat, Mouse), or food. Players are represented by the characters &#39;C&#39;(Cat),&#39;M&#39;(Mouse). Floors are represented by the character &#39;.&#39; and can be walked on. Walls are represented by the character &#39;#&#39; and cannot be walked on. Food is represented by the character &#39;F&#39; and can be walked on. There is only one of each character &#39;C&#39;, &#39;M&#39;, and &#39;F&#39; in grid. Mouse and Cat play according to the following rules: Mouse moves first, then they take turns to move. During each turn, Cat and Mouse can jump in one of the four directions (left, right, up, down). They cannot jump over the wall nor outside of the grid. catJump, mouseJump are the maximum lengths Cat and Mouse can jump at a time, respectively. Cat and Mouse can jump less than the maximum length. Staying in the same position is allowed. Mouse can jump over Cat. The game can end in 4 ways: If Cat occupies the same position as Mouse, Cat wins. If Cat reaches the food first, Cat wins. If Mouse reaches the food first, Mouse wins. If Mouse cannot get to the food within 1000 turns, Cat wins. Given a rows x cols matrix grid and two integers catJump and mouseJump, return true if Mouse can win the game if both Cat and Mouse play optimally, otherwise return false. &nbsp; Example 1: Input: grid = [&quot;####F&quot;,&quot;#C...&quot;,&quot;M....&quot;], catJump = 1, mouseJump = 2 Output: true Explanation: Cat cannot catch Mouse on its turn nor can it get the food before Mouse. Example 2: Input: grid = [&quot;M.C...F&quot;], catJump = 1, mouseJump = 4 Output: true Example 3: Input: grid = [&quot;M.C...F&quot;], catJump = 1, mouseJump = 3 Output: false &nbsp; Constraints: rows == grid.length cols = grid[i].length 1 &lt;= rows, cols &lt;= 8 grid[i][j] consist only of characters &#39;C&#39;, &#39;M&#39;, &#39;F&#39;, &#39;.&#39;, and &#39;#&#39;. There is only one of each character &#39;C&#39;, &#39;M&#39;, and &#39;F&#39; in grid. 1 &lt;= catJump, mouseJump &lt;= 8
  */
        int m = grid.length;
        int n = grid[0].length();
        int catStart = 0, mouseStart = 0, food = 0;
        List<Integer>[] gMouse = new List[m * n];
        List<Integer>[] gCat = new List[m * n];
        Arrays.setAll(gMouse, i -> new ArrayList<>());
        Arrays.setAll(gCat, i -> new ArrayList<>());

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = grid[i].charAt(j);
                if (c == '#') {
                    continue;
                }
                int v = i * n + j;
                if (c == 'C') {
                    catStart = v;
                } else if (c == 'M') {
                    mouseStart = v;
                } else if (c == 'F') {
                    food = v;
                }

                for (int d = 0; d < 4; ++d) {
                    for (int k = 0; k <= mouseJump; k++) {
                        int x = i + k * dirs[d];
                        int y = j + k * dirs[d + 1];
                        if (x < 0 || x >= m || y < 0 || y >= n || grid[x].charAt(y) == '#') {
                            break;
                        }
                        gMouse[v].add(x * n + y);
                    }
                    for (int k = 0; k <= catJump; k++) {
                        int x = i + k * dirs[d];
                        int y = j + k * dirs[d + 1];
                        if (x < 0 || x >= m || y < 0 || y >= n || grid[x].charAt(y) == '#') {
                            break;
                        }
                        gCat[v].add(x * n + y);
                    }
                }
            }
        }

        return calc(gMouse, gCat, mouseStart, catStart, food) == 1;
    }

    private int calc(
        List<Integer>[] gMouse, List<Integer>[] gCat, int mouseStart, int catStart, int hole) {
        int n = gMouse.length;
        int[][][] degree = new int[n][n][2];
        int[][][] ans = new int[n][n][2];
        Deque<int[]> q = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                degree[i][j][0] = gMouse[i].size();
                degree[i][j][1] = gCat[j].size();
            }
        }

        for (int i = 0; i < n; i++) {
            ans[hole][i][1] = 1;
            ans[i][hole][0] = 2;
            ans[i][i][1] = 2;
            ans[i][i][0] = 2;
            q.offer(new int[] {hole, i, 1});
            q.offer(new int[] {i, hole, 0});
            q.offer(new int[] {i, i, 0});
            q.offer(new int[] {i, i, 1});
        }

        while (!q.isEmpty()) {
            int[] state = q.poll();
            int m = state[0], c = state[1], t = state[2];
            int result = ans[m][c][t];
            for (int[] prevState : getPrevStates(gMouse, gCat, state, ans)) {
                int pm = prevState[0], pc = prevState[1], pt = prevState[2];
                if (pt == result - 1) {
                    ans[pm][pc][pt] = result;
                    q.offer(prevState);
                } else {
                    degree[pm][pc][pt]--;
                    if (degree[pm][pc][pt] == 0) {
                        ans[pm][pc][pt] = result;
                        q.offer(prevState);
                    }
                }
            }
        }

        return ans[mouseStart][catStart][0];
    }

    private List<int[]> getPrevStates(
        List<Integer>[] gMouse, List<Integer>[] gCat, int[] state, int[][][] ans) {
        int m = state[0], c = state[1], t = state[2];
        int pt = t ^ 1;
        List<int[]> pre = new ArrayList<>();
        if (pt == 1) {
            for (int pc : gCat[c]) {
                if (ans[m][pc][1] == 0) {
                    pre.add(new int[] {m, pc, pt});
                }
            }
        } else {
            for (int pm : gMouse[m]) {
                if (ans[pm][c][0] == 0) {
                    pre.add(new int[] {pm, c, 0});
                }
            }
        }
        return pre;
    }
}