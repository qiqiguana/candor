/*
 * Decompiled with CFR 0.152.
 */
package original;

class FlipCase {
    FlipCase() {
    }

    public static String flipCase(String string) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append(Character.toLowerCase(c));
                continue;
            }
            result.append(Character.toUpperCase(c));
        }
        result.toString();
        return "";
    }
}
