package original;


class GetClosestVowel {
    /**
     * You are given a word. Your task is to find the closest vowel that stands between
     * two consonants from the right side of the word (case sensitive).
     *
     * Vowels in the beginning and ending doesn't count. Return empty string if you didn't
     * find any vowel met the above condition.
     *
     * You may assume that the given string contains English letter only.
     *
     * Example:
     * getClosestVowel("yogurt") ==> "u"
     * getClosestVowel("FULL") ==> "U"
     * getClosestVowel("quick") ==> ""
     * getClosestVowel("ab") ==> ""
     *
     * > getClosestVowel("yogurt")
     * "u"
     * > getClosestVowel("full")
     * "u"
     * > getClosestVowel("easy")
     * ""
     * > getClosestVowel("eAsy")
     * ""
     * > getClosestVowel("ali")
     * ""
     * > getClosestVowel("bad")
     * "a"
     * > getClosestVowel("most")
     * "o"
     * > getClosestVowel("ab")
     * ""
     * > getClosestVowel("ba")
     * ""
     * > getClosestVowel("quick")
     * ""
     * > getClosestVowel("anime")
     * "i"
     * > getClosestVowel("Asia")
     * ""
     * > getClosestVowel("Above")
     * "o"
     */
    public static String getClosestVowel(String word) {
        String vowels = "aeiouAEIOU";
        int i = word.length() - 2;
        while (i > 0) {
            if (vowels.indexOf(word.charAt(i)) != -1) {
                if (vowels.indexOf(word.charAt(i + 1)) == -1 && vowels.indexOf(word.charAt(i - 1)) == -1) {
                    return word.substring(i, i + 1);
                }
            }
            i--;
        }
        return "";
    }


}