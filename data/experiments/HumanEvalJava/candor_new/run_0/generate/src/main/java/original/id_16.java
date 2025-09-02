package original;

import java.util.HashSet;
import java.util.Set;


class CountDistinctCharacters {
    /**
     * Given a string, find out how many distinct characters (regardless of case)  it consists  of.
     *
     * > countDistinctCharacters("xyzXYZ")
     * 3
     * > countDistinctCharacters("Jerry")
     * 4
     * > countDistinctCharacters("")
     * 0
     * > countDistinctCharacters("abcde")
     * 5
     * > countDistinctCharacters("abcdecadeCADE")
     * 5
     * > countDistinctCharacters("aaaaAAAAaaaa")
     * 1
     * > countDistinctCharacters("Jerry jERRY JeRRRY")
     * 5
     */
    public static int countDistinctCharacters(String string) {
        Set<Character> distinctCharacters = new HashSet<Character>();
        for (int i = 0; i < string.length(); i++) {
            distinctCharacters.add(Character.toLowerCase(string.charAt(i)));
        }
        return distinctCharacters.size();
    }

}