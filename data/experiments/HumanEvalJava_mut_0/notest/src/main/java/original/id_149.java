package original;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class SortedListSum {
    /**
     * Write a function that accepts a list of strings as a parameter,
     * deletes the strings that have odd lengths from it,
     * and returns the resulted list with a sorted order,
     * The list is always a list of strings and never an array of numbers,
     * and it may contain duplicates.
     * The order of the list should be ascending by length of each word, and you
     * should return the list sorted by that rule.
     * If two words have the same length, sort the list alphabetically.
     * The function should return a list of strings in sorted order.
     * You may assume that all words will have the same length.
     * For example:
     * assert sortedListSum(["aa", "a", "aaa"]) => ["aa"]
     * assert sortedListSum(["ab", "a", "aaa", "cd"]) => ["ab", "cd"]
     *
     * > sortedListSum(["aa", "a", "aaa"])
     * ["aa"]
     * > sortedListSum(["school", "AI", "asdf", "b"])
     * ["AI", "asdf", "school"]
     * > sortedListSum(["d", "b", "c", "a"])
     * []
     * > sortedListSum(["d", "dcba", "abcd", "a"])
     * ["abcd", "dcba"]
     * > sortedListSum(["AI", "ai", "au"])
     * ["AI", "ai", "au"]
     * > sortedListSum(["a", "b", "b", "c", "c", "a"])
     * []
     * > sortedListSum(["aaaa", "bbbb", "dd", "cc"])
     * ["cc", "dd", "aaaa", "bbbb"]
     */
    public static List<Object> sortedListSum(List<String> lst) {
        List<Object> result = new ArrayList<Object>();
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).length() % 2 == 0) {
                result.add(lst.get(i));
            }
        }
        Collections.sort(result, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1.toString().length() == o2.toString().length()) {
                    return o1.toString().compareTo(o2.toString());
                } else {
                    return o1.toString().length() - o2.toString().length();
                }
            }
        });
        return result;
    }


}