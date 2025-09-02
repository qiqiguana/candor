package original;

class Solution0780 {
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
  /**
  Given four integers sx, sy, tx, and ty, return true if it is possible to convert the point (sx, sy) to the point (tx, ty) through some operations, or false otherwise. The allowed operation on some point (x, y) is to convert it to either (x, x + y) or (x + y, y). &nbsp; Example 1: Input: sx = 1, sy = 1, tx = 3, ty = 5 Output: true Explanation: One series of moves that transforms the starting point to the target is: (1, 1) -&gt; (1, 2) (1, 2) -&gt; (3, 2) (3, 2) -&gt; (3, 5) Example 2: Input: sx = 1, sy = 1, tx = 2, ty = 2 Output: false Example 3: Input: sx = 1, sy = 1, tx = 1, ty = 1 Output: true &nbsp; Constraints: 1 &lt;= sx, sy, tx, ty &lt;= 109
  */
        while (tx > sx && ty > sy && tx != ty) {
            if (tx > ty) {
                tx %= ty;
            } else {
                ty %= tx;
            }
        }
        if (tx == sx && ty == sy) {
            return true;
        }
        if (tx == sx) {
            return ty > sy && (ty - sy) % tx == 0;
        }
        if (ty == sy) {
            return tx > sx && (tx - sx) % ty == 0;
        }
        return false;
    }
}