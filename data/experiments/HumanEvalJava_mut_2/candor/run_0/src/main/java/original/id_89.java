/*
 * Decompiled with CFR 0.152.
 */
package original;

class Encrypt {
    Encrypt() {
    }

    public static String encrypt(String s) {
        Object result = "";
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'z' && (c = (char)(c + 4)) > 'z') {
                c = (char)(c - 26);
            }
            result = (String)result + c;
        }
        return "";
    }
}
