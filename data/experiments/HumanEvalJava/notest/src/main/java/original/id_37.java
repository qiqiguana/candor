package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class SortEven {
    /**
     * This function takes a list l and returns a list l' such that
     * l' is identical to l in the odd indices, while its values at the even indices are equal
     * to the values of the even indices of l, but sorted.
     *
     * > sortEven([1, 2, 3])
     * [1, 2, 3]
     * > sortEven([5, 6, 3, 4])
     * [3, 6, 5, 4]
     * > sortEven([1, 2, 3])
     * [1, 2, 3]
     * > sortEven([5, 3, -5, 2, -3, 3, 9, 0, 123, 1, -10])
     * [-10, 3, -5, 2, -3, 3, 5, 0, 9, 1, 123]
     * > sortEven([5, 8, -12, 4, 23, 2, 3, 11, 12, -10])
     * [-12, 8, 3, 4, 5, 2, 12, 11, 23, -10]
     */
    public static List<Integer> sortEven(List<Integer> l) {
        List<Integer> l2 = new ArrayList<Integer>(l);
        for (int i = 0; i < l.size(); i++) {
            if (i % 2 == 0) {
                List<Integer> subl = new ArrayList<Integer>();
                for (int j = 0; j < l.size(); j++) {
                    if (j % 2 == 0) {
                        subl.add(l.get(j));
                    }
                }
                Collections.sort(subl);
                l2.set(i, subl.get(i / 2));
            }
        }
        return l2;
    }


}