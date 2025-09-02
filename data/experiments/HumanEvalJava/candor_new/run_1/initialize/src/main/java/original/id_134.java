package original;


class CheckIfLastCharIsALetter {
    /**
     * * Create a function that returns True if the last character
     * of a given string is an alphabetical character and is not
     * a part of a word, and False otherwise.
     * Note: "word" is a group of characters separated by space.
     *
     * Examples:
     * checkIfLastCharIsALetter("apple pie") ➞ False
     * checkIfLastCharIsALetter("apple pi e") ➞ True
     * checkIfLastCharIsALetter("apple pi e ") ➞ False
     * checkIfLastCharIsALetter("") ➞ False
     *
     * > checkIfLastCharIsALetter("apple")
     * false
     * > checkIfLastCharIsALetter("apple pi e")
     * true
     * > checkIfLastCharIsALetter("eeeee")
     * false
     * > checkIfLastCharIsALetter("A")
     * true
     * > checkIfLastCharIsALetter("Pumpkin pie ")
     * false
     * > checkIfLastCharIsALetter("Pumpkin pie 1")
     * false
     * > checkIfLastCharIsALetter("")
     * false
     * > checkIfLastCharIsALetter("eeeee e ")
     * false
     * > checkIfLastCharIsALetter("apple pie")
     * false
     * > checkIfLastCharIsALetter("apple pi e ")
     * false
     */
    public static Boolean checkIfLastCharIsALetter(String txt) {
        if (txt.length() == 0) {
            return false;
        }
        char lastChar = txt.charAt(txt.length() - 1);
        if (Character.isLetter(lastChar)) {
            if (txt.length() == 1) {
                return true;
            }
            char secondLastChar = txt.charAt(txt.length() - 2);
            if (Character.isLetter(secondLastChar)) {
                return false;
            }
            return true;
        }
        return false;
    }


}