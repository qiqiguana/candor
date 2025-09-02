package original;

import java.util.List;


class Longest {
    /**
     * Out of list of strings, return the longest one. Return the first one in case of multiple
     * strings of the same length. Return null in case the input list is empty.
     *
     * > longest(["a", "b", "c"])
     * "a"
     * > longest(["a", "bb", "ccc"])
     * "ccc"
     * > longest([])
     * null
     * > longest(["x", "y", "z"])
     * "x"
     * > longest(["x", "yyy", "zzzz", "www", "kkkk", "abc"])
     * "zzzz"
     */
    public static String longest(List<Object> strings) {
        if (strings.size() == 0) {
            return null;
        }
        String longest = strings.get(0).toString();
        for (int i = 1; i < strings.size(); i++) {
            if (strings.get(i).toString().length() > longest.length()) {
                longest = strings.get(i).toString();
            }
        }
        return longest;
    }


}