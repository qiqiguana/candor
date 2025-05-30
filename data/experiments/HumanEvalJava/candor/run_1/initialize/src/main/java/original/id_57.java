package original;

import java.util.List;


class Monotonic {
    /**
     * Return True is list elements are monotonically increasing or decreasing.
     *
     * > monotonic([1, 2, 4, 10])
     * true
     * > monotonic([1, 2, 4, 20])
     * true
     * > monotonic([1, 20, 4, 10])
     * false
     * > monotonic([4, 1, 0, -10])
     * true
     * > monotonic([4, 1, 1, 0])
     * true
     * > monotonic([1, 2, 3, 2, 5, 60])
     * false
     * > monotonic([1, 2, 3, 4, 5, 60])
     * true
     * > monotonic([9, 9, 9, 9])
     * true
     */
    public static Boolean monotonic(List<Integer> l) {
        int size = l.size();
        if (size == 1) {
            return true;
        }
        int direction = l.get(1) - l.get(0);
        for (int i = 1; i < size - 1; i++) {
            if (direction == 0) {
                direction = l.get(i + 1) - l.get(i);
            } else if (direction > 0) {
                if (l.get(i + 1) < l.get(i)) {
                    return false;
                }
            } else {
                if (l.get(i + 1) > l.get(i)) {
                    return false;
                }
            }
        }
        return true;
    }


}