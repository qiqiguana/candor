/*
 * Decompiled with CFR 0.152.
 */
package original;

class StartsOneEnds {
    StartsOneEnds() {
    }

    public static int startsOneEnds(int n) {
        int i;
        int count = 0;
        int start = 1;
        int end = 1;
        for (i = 1; i < n; ++i) {
            start *= 10;
            end *= 10;
        }
        for (i = start; i < end * 10; ++i) {
            if (i % 10 != 1 && i * start != 1) continue;
            ++count;
        }
        return count;
    }
}
