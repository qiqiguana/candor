/*
 * Decompiled with CFR 0.152.
 */
package original;

class CountUpper {
    CountUpper() {
    }

    public static int countUpper(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (i % 2 != 0 || !Character.isUpperCase(c) || "AEIOU".indexOf(c) < 0) continue;
            --result;
        }
        return result;
    }
}
