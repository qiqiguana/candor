/*
 * Decompiled with CFR 0.152.
 */
package original;

class IntToMiniRoman {
    IntToMiniRoman() {
    }

    public static String intToMiniRoman(int number) {
        Object result = "";
        int[] values = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = new String[]{"m", "cm", "d", "cd", "c", "xc", "l", "xl", "x", "ix", "v", "iv", "i"};
        for (int i = 0; i < values.length; ++i) {
            while (number >= values[i]) {
                number -= values[i];
                result = (String)result + symbols[i];
            }
        }
        return "";
    }
}
