package original;


class Digitsum {
    /**
     * Write a function that takes a string as input and returns the sum of the upper characters only'
     * ASCII codes.
     * Examples:
     * digitSum("") => 0
     * digitSum("abAB") => 131
     * digitSum("abcCd") => 67
     * digitSum("helloE") => 69
     * digitSum("woArBld") => 131
     * digitSum("aAaaaXa") => 153
     *
     * > digitSum("")
     * 0
     * > digitSum("abAB")
     * 131
     * > digitSum("abcCd")
     * 67
     * > digitSum("helloE")
     * 69
     * > digitSum("woArBld")
     * 131
     * > digitSum("aAaaaXa")
     * 153
     * > digitSum(" How are yOu?")
     * 151
     * > digitSum("You arE Very Smart")
     * 327
     */
    public static int digitSum(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                sum += c;
            }
        }
        return sum;
    }


}