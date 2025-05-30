package original;

import java.util.Arrays;

class FileNameCheck {
    /**
     * Create a function which takes a string representing a file's name, and returns
     * 'Yes' if the file's name is valid, and returns 'No' otherwise.
     * A file's name is considered to be valid if and only if all the following conditions
     * are met:
     * - There should not be more than three digits ('0'-'9') in the file's name.
     * - The file's name contains exactly one dot '.'
     * - The substring before the dot should not be empty, and it starts with a letter from
     * the latin alphabet ('a'-'z' and 'A'-'Z').
     * - The substring after the dot should be one of these: ['txt', 'exe', 'dll']
     * Examples:
     * fileNameCheck("example.txt") # => 'Yes'
     * fileNameCheck("1example.dll") # => 'No' (the name should start with a latin alphabet letter)
     *
     * > fileNameCheck("example.txt")
     * "Yes"
     * > fileNameCheck("1example.dll")
     * "No"
     * > fileNameCheck("s1sdf3.asd")
     * "No"
     * > fileNameCheck("K.dll")
     * "Yes"
     * > fileNameCheck("MY16FILE3.exe")
     * "Yes"
     * > fileNameCheck("His12FILE94.exe")
     * "No"
     * > fileNameCheck("_Y.txt")
     * "No"
     * > fileNameCheck("?aREYA.exe")
     * "No"
     * > fileNameCheck("/this_is_valid.dll")
     * "No"
     * > fileNameCheck("this_is_valid.wow")
     * "No"
     * > fileNameCheck("this_is_valid.txt")
     * "Yes"
     * > fileNameCheck("this_is_valid.txtexe")
     * "No"
     * > fileNameCheck("#this2_i4s_5valid.ten")
     * "No"
     * > fileNameCheck("@this1_is6_valid.exe")
     * "No"
     * > fileNameCheck("this_is_12valid.6exe4.txt")
     * "No"
     * > fileNameCheck("all.exe.txt")
     * "No"
     * > fileNameCheck("I563_No.exe")
     * "Yes"
     * > fileNameCheck("Is3youfault.txt")
     * "Yes"
     * > fileNameCheck("no_one#knows.dll")
     * "Yes"
     * > fileNameCheck("1I563_Yes3.exe")
     * "No"
     * > fileNameCheck("I563_Yes3.txtt")
     * "No"
     * > fileNameCheck("final..txt")
     * "No"
     * > fileNameCheck("final132")
     * "No"
     * > fileNameCheck("_f4indsartal132.")
     * "No"
     * > fileNameCheck(".txt")
     * "No"
     * > fileNameCheck("s.")
     * "No"
     */
    public static String fileNameCheck(String fileName) {
        String[] suf = {"txt", "exe", "dll"};
        String[] lst = fileName.split("\\.");
        if (lst.length != 2) {
            return "No";
        }
        if (!Arrays.asList(suf).contains(lst[1])) {
            return "No";
        }
        if (lst[0].length() == 0) {
            return "No";
        }
        if (!Character.isLetter(lst[0].charAt(0))) {
            return "No";
        }
        int t = 0;
        for (int i = 0; i < lst[0].length(); i++) {
            if (Character.isDigit(lst[0].charAt(i))) {
                t++;
            }
        }
        if (t > 3) {
            return "No";
        }
        return "Yes";
    }

}