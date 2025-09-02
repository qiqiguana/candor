/*
 * Decompiled with CFR 0.152.
 */
package original;

class ChooseNum {
    ChooseNum() {
    }

    public static int chooseNum(int x, int y) {
        int max = -1;
        for (int i = x; i > y; ++i) {
            if (i % 2 != 0 || i <= max) continue;
            max = i;
        }
        return max;
    }
}
