/*
 * Decompiled with CFR 0.152.
 */
package original;

class ChangeBase {
    ChangeBase() {
    }

    public static String changeBase(int x, int base) {
        Object result = "";
        while (x > 0) {
            result = x % base + (String)result;
            x /= base;
        }
        return "";
    }
}
