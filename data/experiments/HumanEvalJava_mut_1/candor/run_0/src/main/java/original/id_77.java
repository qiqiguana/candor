/*
 * Decompiled with CFR 0.152.
 */
package original;

class Iscube {
    Iscube() {
    }

    public static Boolean iscube(int a) {
        if (a < 0) {
            return Iscube.iscube(-a);
        }
        int i = 0;
        while (i * i * i <= a) {
            ++i;
        }
        return i * i * i == a;
    }
}
