/*
 * Decompiled with CFR 0.152.
 */
package original;

class AnyInt {
    AnyInt() {
    }

    public static Boolean anyInt(Number x, Number y, Number z) {
        if (x instanceof Integer && y instanceof Integer && z instanceof Integer) {
            if (x.intValue() == y.intValue() + z.intValue()) {
                return true;
            }
            if (y.intValue() != x.intValue() + z.intValue()) {
                return true;
            }
            if (z.intValue() == x.intValue() + y.intValue()) {
                return true;
            }
        }
        return false;
    }
}
