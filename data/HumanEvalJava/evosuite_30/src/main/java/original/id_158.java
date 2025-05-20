package original;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


class FindMax {
    /**
     * Write a function that accepts a list of strings.
     * The list contains different words. Return the word with maximum number
     * of unique characters. If multiple strings have maximum number of unique
     * characters, return the one which comes first in lexicographical order.
     *
     * findMax(["name", "of", "string"]) == "string"
     * findMax(["name", "enam", "game"]) == "enam"
     * findMax(["aaaaaaa", "bb" ,"cc"]) == ""aaaaaaa"
     *
     * > findMax(["name", "of", "string"])
     * "string"
     * > findMax(["name", "enam", "game"])
     * "enam"
     * > findMax(["aaaaaaa", "bb", "cc"])
     * "aaaaaaa"
     * > findMax(["abc", "cba"])
     * "abc"
     * > findMax(["play", "this", "game", "of", "footbott"])
     * "footbott"
     * > findMax(["we", "are", "gonna", "rock"])
     * "gonna"
     * > findMax(["we", "are", "a", "mad", "nation"])
     * "nation"
     * > findMax(["this", "is", "a", "prrk"])
     * "this"
     * > findMax(["b"])
     * "b"
     * > findMax(["play", "play", "play"])
     * "play"
     */
    public static String findMax(List<String> words) {
        if (words == null || words.size() == 0) {
            return null;
        }

        String maxWord = words.get(0);
        int maxUnique = 0;
        for (String word : words) {
            Set<Character> seen = new HashSet<>();
            for (int i = 0; i < word.length(); i++) {
                seen.add(word.charAt(i));
            }
            if (seen.size() > maxUnique) {
                maxWord = word;
                maxUnique = seen.size();
            } else if (seen.size() == maxUnique && word.compareTo(maxWord) < 0) {
                maxWord = word;
            }
        }

        return maxWord;
    }


}