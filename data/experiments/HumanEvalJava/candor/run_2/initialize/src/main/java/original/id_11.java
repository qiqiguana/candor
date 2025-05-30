package original;

class StringXor {
    /**
     * Input are two strings a and b consisting only of 1s and 0s.
     * Perform binary XOR on these inputs and return result also as a string.
     *
     * > stringXor("010", "110")
     * "100"
     * > stringXor("111000", "101010")
     * "010010"
     * > stringXor("1", "1")
     * "0"
     * > stringXor("0101", "0000")
     * "0101"
     */
    public static String stringXor(String a, String b) {
        String result = "";
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                result += "0";
            } else {
                result += "1";
            }
        }
        return result;
    }


}