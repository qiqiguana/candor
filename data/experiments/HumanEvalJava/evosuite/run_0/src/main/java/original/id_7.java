package original;


import java.util.ArrayList;
import java.util.List;


class FilterBySubstring {
    /**
     * Filter an input list of strings only for ones that contain given substring
     * > filterBySubstring([], "a")
     * []
     * > filterBySubstring(["abc", "bacd", "cde", "array"], "a")
     * ["abc", "bacd", "array"]
     * > filterBySubstring([], "john")
     * []
     * > filterBySubstring(["xxx", "asd", "xxy", "john doe", "xxxAAA", "xxx"], "xxx")
     * ["xxx", "xxxAAA", "xxx"]
     * > filterBySubstring(["xxx", "asd", "aaaxxy", "john doe", "xxxAAA", "xxx"], "xx")
     * ["xxx", "aaaxxy", "xxxAAA", "xxx"]
     * > filterBySubstring(["grunt", "trumpet", "prune", "gruesome"], "run")
     * ["grunt", "prune"]
     */
    public static List<Object> filterBySubstring(List<Object> strings, String substring) {
        List<Object> result = new ArrayList<Object>();
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).toString().contains(substring)) {
                result.add(strings.get(i));
            }
        }
        return result;
    }


}