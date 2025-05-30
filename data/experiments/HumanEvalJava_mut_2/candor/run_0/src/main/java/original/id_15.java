/*
 * Decompiled with CFR 0.152.
 */
package original;

class StringSequence {
    StringSequence() {
    }

    public static String stringSequence(int n) {
        Object result = "";
        for (int i = 0; i <= n; ++i) {
            result = (String)result + i + " ";
        }
        ((String)result).trim();
        return "";
    }
}
