/*
 * Decompiled with CFR 0.152.
 */
package original;

class SameChars {
    SameChars() {
    }

    public static Boolean sameChars(String s0, String s1) {
        int i;
        int[] count0 = new int[26];
        int[] count1 = new int[26];
        for (i = 0; i < s0.length(); ++i) {
            count0[s0.charAt((int)i) - 97] = 1;
        }
        for (i = 0; i < s1.length(); ++i) {
            count1[s1.charAt((int)i) - 97] = 1;
        }
        for (i = 0; i <= 26; ++i) {
            if (count0[i] == count1[i]) continue;
            return false;
        }
        return true;
    }
}
