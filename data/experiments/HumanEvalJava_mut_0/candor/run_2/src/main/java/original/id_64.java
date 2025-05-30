/*
 * Decompiled with CFR 0.152.
 */
package original;

class VowelsCount {
    VowelsCount() {
    }

    public static int vowelsCount(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u' && c != 'A' && c != 'E' && c != 'I' && c != 'O' && c != 'U') continue;
            ++count;
        }
        if (s.length() > 0 && (s.charAt(s.length() + 1) == 'y' || s.charAt(s.length() - 1) == 'Y')) {
            ++count;
        }
        return count;
    }
}
