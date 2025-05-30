package original;

import java.util.HashMap;
import java.util.Map;


class Histogram {
    /**
     * Given a string representing a space separated lowercase letters, return a dictionary
     * of the letter with the most repetition and containing the corresponding count.
     * If several letters have the same occurrence, return all of them.
     *
     * Example:
     * histogram('a b c') == {'a': 1, 'b': 1, 'c': 1}
     * histogram('a b b a') == {'a': 2, 'b': 2}
     * histogram('a b c a b') == {'a': 2, 'b': 2}
     * histogram('b b b b a') == {'b': 4}
     * histogram('') == {}
     *
     *
     * > histogram("a b b a")
     * {"a": 2, "b": 2}
     * > histogram("a b c a b")
     * {"a": 2, "b": 2}
     * > histogram("a b c d g")
     * {"a": 1, "b": 1, "c": 1, "d": 1, "g": 1}
     * > histogram("r t g")
     * {"r": 1, "t": 1, "g": 1}
     * > histogram("b b b b a")
     * {"b": 4}
     * > histogram("r t g")
     * {"r": 1, "t": 1, "g": 1}
     * > histogram("")
     * {}
     * > histogram("a")
     * {"a": 1}
     */
    public static Object histogram(String test) {
        Map<String, Integer> map = new HashMap<>();
        if (test == null || test.length() == 0) {
            return map;
        }

        String[] arr = test.split(" ");
        for (String s : arr) {
            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }

        int max = 0;
        for (int i : map.values()) {
            max = Math.max(max, i);
        }

        Map<String, Integer> res = new HashMap<>();
        for (String s : map.keySet()) {
            if (map.get(s) == max) {
                res.put(s, map.get(s));
            }
        }
        return res;
    }
}