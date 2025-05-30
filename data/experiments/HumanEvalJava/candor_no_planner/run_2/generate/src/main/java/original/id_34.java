package original;


import java.util.*;


class Unique {
    /**
     * Return sorted unique elements in a list
     *
     * > unique([5, 3, 5, 2, 3, 3, 9, 0, 123])
     * [0, 2, 3, 5, 9, 123]
     * > unique([5, 3, 5, 2, 3, 3, 9, 0, 123])
     * [0, 2, 3, 5, 9, 123]
     */
    public static List<Integer> unique(List<Integer> l) {
        Set<Integer> set = new HashSet<Integer>();
        for (Integer i : l) {
            set.add(i);
        }
        List<Integer> result = new ArrayList<Integer>(set);
        Collections.sort(result);
        return result;
    }

}