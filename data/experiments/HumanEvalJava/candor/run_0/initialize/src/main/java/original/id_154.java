package original;


class CycpatternCheck {
    /**
     * You are given 2 words. You need to return True if the second word or any of its rotations is a substring in the first word
     * cycpatternCheck("abcd","abd") => False
     * cycpatternCheck("hello","ell") => True
     * cycpatternCheck("whassup","psus") => False
     * cycpatternCheck("abab","baa") => True
     * cycpatternCheck("efef","eeff") => False
     * cycpatternCheck("himenss","simen") => True
     *
     *
     * > cycpatternCheck("xyzw", "xyw")
     * false
     * > cycpatternCheck("yello", "ell")
     * true
     * > cycpatternCheck("whattup", "ptut")
     * false
     * > cycpatternCheck("efef", "fee")
     * true
     * > cycpatternCheck("abab", "aabb")
     * false
     * > cycpatternCheck("winemtt", "tinem")
     * true
     */
    public static Boolean cycpatternCheck(String a, String b) {
        int l = b.length();
        String pat = b + b;
        for (int i = 0; i < a.length() - l + 1; i++) {
            for (int j = 0; j < l + 1; j++) {
                if (a.substring(i, i + l).equals(pat.substring(j, j + l))) {
                    return true;
                }
            }
        }
        return false;
    }


}