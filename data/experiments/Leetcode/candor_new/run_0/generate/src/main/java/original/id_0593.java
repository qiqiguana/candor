package original;

class Solution0593 {
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
  /**
  Given the coordinates of four points in 2D space p1, p2, p3 and p4, return true if the four points construct a square. The coordinate of a point pi is represented as [xi, yi]. The input is not given in any order. A valid square has four equal sides with positive length and four equal angles (90-degree angles). &nbsp; Example 1: Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1] Output: true Example 2: Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,12] Output: false Example 3: Input: p1 = [1,0], p2 = [-1,0], p3 = [0,1], p4 = [0,-1] Output: true &nbsp; Constraints: p1.length == p2.length == p3.length == p4.length == 2 -104 &lt;= xi, yi &lt;= 104
  */
        return check(p1, p2, p3) && check(p1, p3, p4) && check(p1, p2, p4) && check(p2, p3, p4);
    }

    private boolean check(int[] a, int[] b, int[] c) {
        int x1 = a[0], y1 = a[1];
        int x2 = b[0], y2 = b[1];
        int x3 = c[0], y3 = c[1];
        int d1 = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
        int d2 = (x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3);
        int d3 = (x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3);
        if (d1 == d2 && d1 + d2 == d3 && d1 > 0) {
            return true;
        }
        if (d1 == d3 && d1 + d3 == d2 && d1 > 0) {
            return true;
        }
        if (d2 == d3 && d2 + d3 == d1 && d2 > 0) {
            return true;
        }
        return false;
    }
}