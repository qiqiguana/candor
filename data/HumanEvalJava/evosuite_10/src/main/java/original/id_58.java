package original;


import java.util.*;


class Common {
    /**
     * Return sorted unique common elements for two lists.
     *
     * > common([1, 4, 3, 34, 653, 2, 5], [5, 7, 1, 5, 9, 653, 121])
     * [1, 5, 653]
     * > common([5, 3, 2, 8], [3, 2])
     * [2, 3]
     * > common([4, 3, 2, 8], [3, 2, 4])
     * [2, 3, 4]
     * > common([4, 3, 2, 8], [])
     * []
     */
    public static List<Object> common(List<Integer> l1, List<Object> l2) {
        Set<Integer> set = new HashSet<Integer>();
        for (Integer value : l1) {
            set.add(value);
        }
        List<Object> result = new ArrayList<Object>();
        for (Object value : l2) {
            if (value instanceof Integer) {
                int i = (Integer) value;
                if (set.contains(i) && !result.contains(i)) {
                    result.add(i);
                }
            }
        }
        Collections.sort(result, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Integer && o2 instanceof Integer) {
                    return (Integer) o1 - (Integer) o2;
                }
                return 0;
            }
        });
        return result;
    }


}