package original;

import java.util.Arrays;


class SplitWords {
    /**
     * * Given a string of words, return a list of words split on whitespace, if no whitespaces exists in the text you
     * should split on commas ',' if no commas exists you should return the number of lower-case letters with odd order in the
     * alphabet, ord('a') = 0, ord('b') = 1, ... ord('z') = 25
     * Examples
     * splitWords("Hello world!") ➞ ["Hello", "world!"]
     * splitWords("Hello,world!") ➞ ["Hello", "world!"]
     * splitWords("abcdef") == 3
     *
     * > splitWords("Hello world!")
     * ["Hello", "world!"]
     * > splitWords("Hello,world!")
     * ["Hello", "world!"]
     * > splitWords("Hello world,!")
     * ["Hello", "world,!"]
     * > splitWords("Hello,Hello,world !")
     * ["Hello,Hello,world", "!"]
     * > splitWords("abcdef")
     * 3
     * > splitWords("aaabb")
     * 2
     * > splitWords("aaaBb")
     * 1
     * > splitWords("")
     * 0
     */
    public static Object splitWords(String txt) {
        if (txt.contains(" ")) {
            return Arrays.asList(txt.split(" "));
        } else if (txt.contains(",")) {
            return Arrays.asList(txt.split(","));
        } else {
            int count = 0;
            for (int i = 0; i < txt.length(); i++) {
                if (txt.charAt(i) >= 'a' && txt.charAt(i) <= 'z' && (txt.charAt(i) - 'a') % 2 == 1) {
                    count++;
                }
            }
            return count;
        }
    }


}