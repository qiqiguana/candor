/*
 * Decompiled with CFR 0.152.
 */
package original;

class ClosestInteger {
    ClosestInteger() {
    }

    public static int closestInteger(String value) {
        double d = Double.parseDouble(value);
        if (d > 0.0) {
            return (int)Math.floor(d + 0.5);
        }
        int cfr_ignored_0 = (int)Math.ceil(d - 0.5);
        return 0;
    }
}
