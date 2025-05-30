package original;

import java.util.List;


class WillItFly {
    /**
     * * Write a function that returns True if the object q will fly, and False otherwise.
     * The object q will fly if it's balanced (it is a palindromic list) and the sum of its elements is less than or equal the maximum possible weight w.
     *
     * Example:
     * willItFly([1, 2], 5) ➞ False
     * # 1+2 is less than the maximum possible weight, but it's unbalanced.
     *
     * willItFly([3, 2, 3], 1) ➞ False
     * # it's balanced, but 3+2+3 is more than the maximum possible weight.
     *
     * willItFly([3, 2, 3], 9) ➞ True
     * # 3+2+3 is less than the maximum possible weight, and it's balanced.
     *
     * willItFly([3], 5) ➞ True
     * # 3 is less than the maximum possible weight, and it's balanced.
     *
     * > willItFly([3, 2, 3], 9)
     * true
     * > willItFly([1, 2], 5)
     * false
     * > willItFly([3], 5)
     * true
     * > willItFly([3, 2, 3], 1)
     * false
     * > willItFly([1, 2, 3], 6)
     * false
     * > willItFly([5], 5)
     * true
     */
    public static Boolean willItFly(List<Integer> q, int w) {
        int sum = 0;
        for (int i = 0; i < q.size(); i++) {
            sum += q.get(i);
        }
        if (sum > w) {
            return false;
        }
        for (int i = 0; i < q.size() / 2; i++) {
            if (q.get(i) != q.get(q.size() - i - 1)) {
                return false;
            }
        }
        return true;
    }


}