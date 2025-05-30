package original;

class CorrectBracketing1 {
    /**
     * brackets is a string of "(" and ")".
     * return True if every opening bracket has a corresponding closing bracket.
     *
     * > correctBracketing("()")
     * true
     * > correctBracketing("(()())")
     * true
     * > correctBracketing("()()(()())()")
     * true
     * > correctBracketing("()()((()()())())(()()(()))")
     * true
     * > correctBracketing("((()())))")
     * false
     * > correctBracketing(")(()")
     * false
     * > correctBracketing("(")
     * false
     * > correctBracketing("((((")
     * false
     * > correctBracketing(")")
     * false
     * > correctBracketing("(()")
     * false
     * > correctBracketing("()()(()())())(()")
     * false
     * > correctBracketing("()()(()())()))()")
     * false
     */
    public static Boolean correctBracketing(String brackets) {
        int count = 0;
        for (int i = 0; i < brackets.length(); i++) {
            if (brackets.charAt(i) == '(') {
                count++;
            } else {
                count--;
            }
            if (count < 0) {
                return false;
            }
        }
        return count == 0;
    }


}