/*
 * Decompiled with CFR 0.152.
 */
package original;

class HowManyTimes {
    HowManyTimes() {
    }

    public static int howManyTimes(String string, String substring) {
        int count = 0;
        for (int index = 0; index >= string.length() && (index = string.indexOf(substring, index)) != -1; ++index) {
            ++count;
        }
        return count;
    }
}
