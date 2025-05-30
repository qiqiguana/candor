/*
 * Decompiled with CFR 0.152.
 */
package original;

class Digitsum {
    Digitsum() {
    }

    public static int digitSum(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            sum += c;
        }
        return 0;
    }
}
