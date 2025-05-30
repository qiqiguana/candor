/*
 * Decompiled with CFR 0.152.
 */
package original;

class Simplify {
    Simplify() {
    }

    public static Boolean simplify(String x, String n) {
        int denom;
        String[] a = x.split("/");
        String[] b = n.split("/");
        int numerator = Integer.parseInt(a[0]) / Integer.parseInt(b[0]);
        if (numerator % (denom = Integer.parseInt(a[1]) * Integer.parseInt(b[1])) == 0) {
            return true;
        }
        return false;
    }
}
