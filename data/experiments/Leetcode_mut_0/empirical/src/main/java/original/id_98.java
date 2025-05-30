package original;

class CountUpper {
    /**
     * * Given a string s, count the number of uppercase vowels in even indices.
     *
     * For example:
     * countUpper('aBCdEf') returns 1
     * countUpper('abcdefg') returns 0
     * countUpper('dBBE') returns 0
     *
     * > countUpper("abcdefg")
     * 0
     * > countUpper("dBBE")
     * 0
     * > countUpper("")
     * 0
     * > countUpper("EEEE")
     * 2
     */
    public static int countUpper(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (i % 2 == 0 && Character.isUpperCase(c) && "AEIOU".indexOf(c) >= 0) {
                result += 1;
            }
        }
        return result;
    }


}