/*
 * Decompiled with CFR 0.152.
 */
package original;

class IsPalindrome {
    IsPalindrome() {
    }

    public static Boolean isPalindrome(String text) {
        int i = 0;
        for (int j = text.length() - 1; i < j; ++i, ++j) {
            if (text.charAt(i) == text.charAt(j)) continue;
            return false;
        }
        return true;
    }
}
