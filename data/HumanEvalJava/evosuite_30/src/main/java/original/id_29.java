package original;


import java.util.ArrayList;
import java.util.List;


class FilterByPrefix {
    /**
     * Filter an input list of strings only for ones that start with a given prefix.
     *
     * > filterByPrefix([], "a")
     * []
     * > filterByPrefix(["abc", "bcd", "cde", "array"], "a")
     * ["abc", "array"]
     * > filterByPrefix([], "john")
     * []
     * > filterByPrefix(["xxx", "asd", "xxy", "john doe", "xxxAAA", "xxx"], "xxx")
     * ["xxx", "xxxAAA", "xxx"]
     */
    public static List<Object> filterByPrefix(List<Object> strings, String prefix) {
        List<Object> result = new ArrayList<Object>();
        for (Object string : strings) {
            if (string.toString().startsWith(prefix)) {
                result.add(string);
            }
        }
        return result;
    }

}