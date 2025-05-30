package original;


import java.util.List;


class MatchParens {
    /**
     * * You are given a list of two strings, both strings consist of open
     * parentheses '(' or close parentheses ')' only.
     * Your job is to check if it is possible to concatenate the two strings in
     * some order, that the resulting string will be good.
     * A string S is considered to be good if and only if all parentheses in S
     * are balanced. For example: the string '(())()' is good, while the string
     * '())' is not.
     * Return 'Yes' if there's a way to make a good string, and return 'No' otherwise.
     *
     * Examples:
     * matchParens(['()(', ')']) == 'Yes'
     * matchParens([')', ')']) == 'No'
     *
     * > matchParens(["()(", ")"])
     * "Yes"
     * > matchParens([")", ")"])
     * "No"
     * > matchParens(["(()(())", "())())"])
     * "No"
     * > matchParens([")())", "(()()("])
     * "Yes"
     * > matchParens(["(())))", "(()())(("])
     * "Yes"
     * > matchParens(["()", "())"])
     * "No"
     * > matchParens(["(()(", "()))()"])
     * "Yes"
     * > matchParens(["((((", "((())"])
     * "No"
     * > matchParens([")(()", "(()("])
     * "No"
     * > matchParens([")(", ")("])
     * "No"
     * > matchParens(["(", ")"])
     * "Yes"
     * > matchParens([")", "("])
     * "Yes"
     */
    public static String matchParens(List<String> lst) {
        String s1 = lst.get(0);
        String s2 = lst.get(1);
        String s1s2 = s1 + s2;
        String s2s1 = s2 + s1;

        int count = 0;
        boolean flag = true;
        for (int i = 0; i < s1s2.length(); i++) {
            if (s1s2.charAt(i) == '(') {
                count++;
            } else {
                count--;
            }
            if (count < 0) {
                flag = false;
            }
        }
        if (flag && count == 0) {
            return "Yes";
        }
        count = 0;
        for (int i = 0; i < s2s1.length(); i++) {
            if (s2s1.charAt(i) == '(') {
                count++;
            } else {
                count--;
            }
            if (count < 0) {
                return "No";
            }
        }
        if (count == 0) {
            return "Yes";
        } else {
            return "No";
        }
    }


}