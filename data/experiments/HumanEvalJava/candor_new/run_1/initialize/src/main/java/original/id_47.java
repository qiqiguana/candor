package original;

import java.util.Collections;
import java.util.List;


class Median {
    /**
     * Return median of elements in the list l.
     *
     * > median([3, 1, 2, 4, 5])
     * 3
     * > median([-10, 4, 6, 1000, 10, 20])
     * 8.0
     * > median([5])
     * 5
     * > median([6, 5])
     * 5.5
     * > median([8, 1, 3, 9, 9, 2, 7])
     * 7
     */
    public static Number median(List<Integer> l) {
        Collections.sort(l);
        int size = l.size();
        if (size % 2 == 0) {
            return (l.get(size / 2) + l.get(size / 2 - 1)) / 2.0;
        } else {
            return l.get(size / 2);
        }
    }


}