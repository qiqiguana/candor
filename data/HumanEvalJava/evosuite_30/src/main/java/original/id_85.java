package original;

import java.util.List;


class Add1 {
    /**
     * Given a non-empty list of integers lst. add the even elements that are at odd indices..
     *
     * Examples:
     * add([4, 2, 6, 7]) ==> 2
     *
     * > add([4, 88])
     * 88
     * > add([4, 5, 6, 7, 2, 122])
     * 122
     * > add([4, 0, 6, 7])
     * 0
     * > add([4, 4, 6, 8])
     * 12
     */
    public static int add(List<Integer> lst) {
        int result = 0;
        for (int i = 0; i < lst.size(); i++) {
            if (i % 2 == 1 && lst.get(i) % 2 == 0) {
                result += lst.get(i);
            }
        }
        return result;
    }


}