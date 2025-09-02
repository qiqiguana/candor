package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class SortThird {
    /**
     * This function takes a list l and returns a list l' such that
     * l' is identical to l in the indices that are not divisible by three, while its values at the indicies that are divisible by three are equal
     * to the values of the corresponding indices of l, but sorted.
     * > sortThird([1, 2, 3])
     * [1, 2, 3]
     * > sortThird([5, 6, 3, 4, 8, 9, 2])
     * [2, 6, 3, 4, 8, 9, 5]
     * > sortThird([1, 2, 3])
     * [1, 2, 3]
     * > sortThird([5, 3, -5, 2, -3, 3, 9, 0, 123, 1, -10])
     * [1, 3, -5, 2, -3, 3, 5, 0, 123, 9, -10]
     * > sortThird([5, 8, -12, 4, 23, 2, 3, 11, 12, -10])
     * [-10, 8, -12, 3, 23, 2, 4, 11, 12, 5]
     * > sortThird([5, 6, 3, 4, 8, 9, 2])
     * [2, 6, 3, 4, 8, 9, 5]
     * > sortThird([5, 8, 3, 4, 6, 9, 2])
     * [2, 8, 3, 4, 6, 9, 5]
     * > sortThird([5, 6, 9, 4, 8, 3, 2])
     * [2, 6, 9, 4, 8, 3, 5]
     * > sortThird([5, 6, 3, 4, 8, 9, 2, 1])
     * [2, 6, 3, 4, 8, 9, 5, 1]
     */
    public static List<Integer> sortThird(List<Integer> l) {
        List<Integer> l2 = new ArrayList<Integer>(l);
        for (int i = 0; i < l.size(); i++) {
            if (i % 3 == 0) {
                List<Integer> subl = new ArrayList<Integer>();
                for (int j = 0; j < l.size(); j++) {
                    if (j % 3 == 0) {
                        subl.add(l.get(j));
                    }
                }
                Collections.sort(subl);
                l2.set(i, subl.get(i / 3));
            }
        }
        return l2;
    }


}