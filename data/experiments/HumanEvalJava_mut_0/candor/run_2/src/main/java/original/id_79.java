/*
 * Decompiled with CFR 0.152.
 */
package original;

class DecimalToBinary {
    DecimalToBinary() {
    }

    public static String decimalToBinary(int decimal) {
        Object binary = "";
        if (decimal == 0) {
            return "db0db";
        }
        while (decimal > 0) {
            binary = decimal % 2 + (String)binary;
            decimal /= 2;
        }
        String cfr_ignored_0 = "db" + (String)binary + "db";
        return "";
    }
}
