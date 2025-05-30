package original;

import java.util.ArrayList;
import java.util.List;


class SelectWords {
    /**
     * Given a string s and a natural number n, you have been tasked to implement
     * a function that returns a list of all words from string s that contain exactly
     * n consonants, in order these words appear in the string s.
     * If the string s is empty then the function should return an empty list.
     * Note: you may assume the input string contains only letters and spaces.
     * Examples:
     * selectWords("Mary had a little lamb", 4) ==> ["little"]
     * selectWords("Mary had a little lamb", 3) ==> ["Mary", "lamb"]
     * selectWords("simple white space", 2) ==> []
     * selectWords("Hello world", 4) ==> ["world"]
     * selectWords("Uncle sam", 3) ==> ["Uncle"]
     *
     * > selectWords("Mary had a little lamb", 4)
     * ["little"]
     * > selectWords("Mary had a little lamb", 3)
     * ["Mary", "lamb"]
     * > selectWords("simple white space", 2)
     * []
     * > selectWords("Hello world", 4)
     * ["world"]
     * > selectWords("Uncle sam", 3)
     * ["Uncle"]
     * > selectWords("", 4)
     * []
     * > selectWords("a b c d e f", 1)
     * ["b", "c", "d", "f"]
     */
    public static List<Object> selectWords(String s, int n) {
        List<Object> result = new ArrayList<>();
        String[] words = s.split(" ");
        for (String word : words) {
            int consonants = 0;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u' && c != 'A' && c != 'E' && c != 'I' && c != 'O' && c != 'U') {
                    consonants++;
                }
            }
            if (consonants == n) {
                result.add(word);
            }
        }
        return result;
    }


}