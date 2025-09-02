package original;

class MakePalindrome {
    /**
     * Find the shortest palindrome that begins with a supplied string.
     * Algorithm idea is simple:
     * - Find the longest postfix of supplied string that is a palindrome.
     * - Append to the end of the string reverse of a string prefix that comes before the palindromic suffix.
     *
     * > makePalindrome("")
     * ""
     * > makePalindrome("cat")
     * "catac"
     * > makePalindrome("cata")
     * "catac"
     * > makePalindrome("")
     * ""
     * > makePalindrome("x")
     * "x"
     * > makePalindrome("xyz")
     * "xyzyx"
     * > makePalindrome("xyx")
     * "xyx"
     * > makePalindrome("jerry")
     * "jerryrrej"
     */
    public static String makePalindrome(String string) {
        if (string.length() == 0) {
            return "";
        }
        String reversed = new StringBuilder(string).reverse().toString();
        for (int i = 0; i < string.length(); i++) {
            if (string.substring(i).equals(reversed.substring(0, string.length() - i))) {
                return string + reversed.substring(string.length() - i);
            }
        }
        return null;
    }


}