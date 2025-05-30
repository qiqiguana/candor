package original;


class ChooseNum {
    /**
     * This function takes two positive numbers x and y and returns the
     * biggest even integer number that is in the range [x, y] inclusive. If
     * there's no such number, then the function should return -1.
     *
     * For example:
     * chooseNum(12, 15) = 14
     * chooseNum(13, 12) = -1
     *
     * > chooseNum(12, 15)
     * 14
     * > chooseNum(13, 12)
     * -1
     * > chooseNum(33, 12354)
     * 12354
     * > chooseNum(5234, 5233)
     * -1
     * > chooseNum(6, 29)
     * 28
     * > chooseNum(27, 10)
     * -1
     * > chooseNum(7, 7)
     * -1
     * > chooseNum(546, 546)
     * 546
     */
    public static int chooseNum(int x, int y) {
        int max = -1;
        for (int i = x; i <= y; i++) {
            if (i % 2 == 0 && i > max) {
                max = i;
            }
        }
        return max;
    }


}