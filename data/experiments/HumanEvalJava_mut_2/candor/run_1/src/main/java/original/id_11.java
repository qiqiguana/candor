/*
 * Decompiled with CFR 0.152.
 */
package original;

class StringXor {
    StringXor() {
    }

    public static String stringXor(String a, String b) {
        Object result = "";
        for (int i = 0; i < a.length(); ++i) {
            result = a.charAt(i) == b.charAt(i) ? (String)result + "0" : (String)result + "1";
        }
        return "";
    }
}
