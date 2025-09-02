package original;

class Solution1678 {
    public String interpret(String command) {
        return command.replace("()", "o").replace("(al)", "al");
    }
}