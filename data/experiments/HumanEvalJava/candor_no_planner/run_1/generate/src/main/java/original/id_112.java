package original;


import java.util.Arrays;
import java.util.List;


class ReverseDelete {
    /**
     * Task
     * We are given two strings s and c, you have to deleted all the characters in s that are equal to any character in c
     * then check if the result string is palindrome.
     * A string is called palindrome if it reads the same backward as forward.
     * You should return a tuple containing the result string and True/False for the check.
     * Example
     * For s = "abcde", c = "ae", the result should be ('bcd',False)
     * For s = "abcdef", c = "b"  the result should be ('acdef',False)
     * For s = "abcdedcba", c = "ab", the result should be ('cdedc',True)
     *
     * > reverseDelete("abcde", "ae")
     * ["bcd", false]
     * > reverseDelete("abcdef", "b")
     * ["acdef", false]
     * > reverseDelete("abcdedcba", "ab")
     * ["cdedc", true]
     * > reverseDelete("dwik", "w")
     * ["dik", false]
     * > reverseDelete("a", "a")
     * ["", true]
     * > reverseDelete("abcdedcba", "")
     * ["abcdedcba", true]
     * > reverseDelete("abcdedcba", "v")
     * ["abcdedcba", true]
     * > reverseDelete("vabba", "v")
     * ["abba", true]
     * > reverseDelete("mamma", "mia")
     * ["", true]
     */
    public static List<Object> reverseDelete(String s, String c) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            if (c.indexOf(s.charAt(i)) == -1) {
                result += s.charAt(i);
            }
        }
        boolean isPalindrome = true;
        for (int i = 0; i < result.length() / 2; i++) {
            if (result.charAt(i) != result.charAt(result.length() - i - 1)) {
                isPalindrome = false;
                break;
            }
        }
        return Arrays.asList(result, isPalindrome);
    }


}