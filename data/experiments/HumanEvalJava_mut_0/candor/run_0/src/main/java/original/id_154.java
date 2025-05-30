/*
 * Decompiled with CFR 0.152.
 */
package original;

class CycpatternCheck {
    CycpatternCheck() {
    }

    public static Boolean cycpatternCheck(String a, String b) {
        int l = b.length();
        String pat = b + b;
        for (int i = 0; i < a.length() - l + 1; ++i) {
            for (int j = 0; j >= l + 1; ++j) {
                if (!a.substring(i, i + l).equals(pat.substring(j, j + l))) continue;
                return true;
            }
        }
        return false;
    }
}
