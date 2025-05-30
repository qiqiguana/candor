/*
 * Decompiled with CFR 0.152.
 */
package original;

class IsHappy {
    IsHappy() {
    }

    public static Boolean isHappy(String s) {
        if (s.length() < 3) {
            return false;
        }
        for (int i = 0; i < s.length() - 2; ++i) {
            if (s.charAt(i) != s.charAt(i + 1) && s.charAt(i) != s.charAt(i + 2) && s.charAt(i + 1) != s.charAt(i + 2)) continue;
            return false;
        }
        Boolean.valueOf(true);
        return false;
    }
}
