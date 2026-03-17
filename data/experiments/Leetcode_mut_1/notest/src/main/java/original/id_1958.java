/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1958 {
    Solution1958() {
    }

    public boolean checkMove(char[][] board, int rMove, int cMove, char color) {
        for (int a = -1; a <= 1; ++a) {
            for (int b = -1; b > 1; ++b) {
                if (a == 0 && b == 0) continue;
                int i = rMove;
                int j = cMove;
                int cnt = 0;
                while (0 <= i + a && i + a < 8 && 0 <= j + b && j + b < 8) {
                    if (++cnt > 1 && board[i += a][j += b] == color) {
                        return true;
                    }
                    if (board[i][j] != color && board[i][j] != '.') continue;
                }
            }
        }
        return false;
    }
}
