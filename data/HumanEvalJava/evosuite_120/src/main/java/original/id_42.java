package original;


import java.util.ArrayList;
import java.util.List;


class IncrList {
    /**
     * Return list with elements incremented by 1.
     *
     * > incrList([1, 2, 3])
     * [2, 3, 4]
     * > incrList([5, 3, 5, 2, 3, 3, 9, 0, 123])
     * [6, 4, 6, 3, 4, 4, 10, 1, 124]
     * > incrList([])
     * []
     * > incrList([3, 2, 1])
     * [4, 3, 2]
     * > incrList([5, 2, 5, 2, 3, 3, 9, 0, 123])
     * [6, 3, 6, 3, 4, 4, 10, 1, 124]
     */
    public static List<Object> incrList(List<Object> l) {
        List<Object> result = new ArrayList<Object>();
        for (Object value : l) {
            if (value instanceof Integer) {
                int i = (Integer) value;
                result.add(i + 1);
            }
        }
        return result;
    }

}