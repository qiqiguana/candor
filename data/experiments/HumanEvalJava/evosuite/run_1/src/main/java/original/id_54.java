package original;


class SameChars {
    /**
     * Check if two words have the same characters.
     *
     * > sameChars("abcd", "dddddddabce")
     * false
     * > sameChars("eabcdzzzz", "dddzzzzzzzddeddabc")
     * true
     * > sameChars("abcd", "dddddddabc")
     * true
     * > sameChars("dddddddabc", "abcd")
     * true
     * > sameChars("eabcd", "dddddddabc")
     * false
     * > sameChars("abcd", "dddddddabcf")
     * false
     * > sameChars("eabcdzzzz", "dddzzzzzzzddddabc")
     * false
     * > sameChars("aabb", "aaccc")
     * false
     */
    public static Boolean sameChars(String s0, String s1) {
        int[] count0 = new int[26];
        int[] count1 = new int[26];
        for (int i = 0; i < s0.length(); i++) {
            count0[s0.charAt(i) - 'a'] = 1;

        }
        for (int i = 0; i < s1.length(); i++) {
            count1[s1.charAt(i) - 'a'] = 1;
        }
        for (int i = 0; i < 26; i++) {
            if (count0[i] != count1[i]) {
                return false;
            }
        }
        return true;
    }


}