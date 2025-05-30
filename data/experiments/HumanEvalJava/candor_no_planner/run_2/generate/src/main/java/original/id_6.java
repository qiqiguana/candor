package original;


import java.util.ArrayList;
import java.util.List;


class ParseNestedParens {
    /**
     * Input to this function is a string represented multiple groups for nested parentheses separated by spaces.
     * For each of the group, output the deepest level of nesting of parentheses.
     * E.g. (()()) has maximum two levels of nesting while ((())) has three.
     *
     * > parseNestedParens("(()()) ((())) () ((())()())")
     * [2, 3, 1, 3]
     * > parseNestedParens("() (()) ((())) (((())))")
     * [1, 2, 3, 4]
     * > parseNestedParens("(()(())((())))")
     * [4]
     */
    public static List<Integer> parseNestedParens(String parenString) {
        List<Integer> result = new ArrayList<Integer>();
        int count = 0;
        int maximum_depth = 0;
        for (int i = 0; i < parenString.length(); i++) {
            if (parenString.charAt(i) == '(') {
                count++;
                maximum_depth = Math.max(maximum_depth, count);
            } else if (parenString.charAt(i) == ')') {
                count--;
                if (count == 0) {
                    result.add(maximum_depth);
                    maximum_depth = 0;

                }
            }
        }
        return result;
    }


}