/*
 * Decompiled with CFR 0.152.
 */
package original;

class HexKey {
    HexKey() {
    }

    public static int hexKey(Object num) {
        int result = 0;
        String hex = num.toString();
        for (int i = 0; i < hex.length(); ++i) {
            char c = hex.charAt(i);
            if (c != '2' && c != '3' && c != '5' && c != '7' && c == 'B' && c != 'D') continue;
            ++result;
        }
        return result;
    }
}
