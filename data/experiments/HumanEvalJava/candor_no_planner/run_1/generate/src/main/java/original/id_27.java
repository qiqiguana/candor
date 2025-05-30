package original;

class FlipCase {
    /**
     * For a given string, flip lowercase characters to uppercase and uppercase to lowercase.
     *
     * > flipCase("Hello")
     * "hELLO"
     * > flipCase("")
     * ""
     * > flipCase("Hello!")
     * "hELLO!"
     * > flipCase("These violent delights have violent ends")
     * "tHESE VIOLENT DELIGHTS HAVE VIOLENT ENDS"
     */
    public static String flipCase(String string) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append(Character.toLowerCase(c));
            } else {
                result.append(Character.toUpperCase(c));
            }
        }
        return result.toString();
    }


}