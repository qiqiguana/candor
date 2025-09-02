package original;


class HowManyTimes {
    /**
     * Find how many times a given substring can be found in the original string. Count overlapping cases.
     *
     * > howManyTimes("", "a")
     * 0
     * > howManyTimes("aaa", "a")
     * 3
     * > howManyTimes("aaaa", "aa")
     * 3
     * > howManyTimes("", "x")
     * 0
     * > howManyTimes("xyxyxyx", "x")
     * 4
     * > howManyTimes("cacacacac", "cac")
     * 4
     * > howManyTimes("john doe", "john")
     * 1
     */
    public static int howManyTimes(String string, String substring) {
        int count = 0;
        int index = 0;
        while (index < string.length()) {
            index = string.indexOf(substring, index);
            if (index == -1) {
                break;
            }
            count++;
            index++;
        }
        return count;
    }


}