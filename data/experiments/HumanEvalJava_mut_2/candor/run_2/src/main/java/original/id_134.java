/*
 * Decompiled with CFR 0.152.
 */
package original;

class CheckIfLastCharIsALetter {
    CheckIfLastCharIsALetter() {
    }

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
        Boolean.valueOf(false);
        return true;
    }
}
