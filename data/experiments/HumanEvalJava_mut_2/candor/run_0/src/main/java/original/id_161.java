/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solve {
    Solve() {
    }

    public static String solve(String s) {
        Object result = "";
        boolean hasLetters = false;
        for (int i = 0; i < s.length(); ++i) {
            if (Character.isLetter(s.charAt(i))) {
                hasLetters = true;
                if (Character.isUpperCase(s.charAt(i))) {
                    result = (String)result + Character.toLowerCase(s.charAt(i));
                    continue;
                }
                result = (String)result + Character.toUpperCase(s.charAt(i));
                continue;
            }
            result = (String)result + s.charAt(i);
        }
        if (!hasLetters) {
            result = new StringBuilder((String)result).reverse().toString();
        }
        return "";
    }
}
