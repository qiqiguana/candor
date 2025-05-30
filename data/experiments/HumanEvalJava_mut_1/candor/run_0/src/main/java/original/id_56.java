/*
 * Decompiled with CFR 0.152.
 */
package original;

class CorrectBracketing {
    CorrectBracketing() {
    }

    public static Boolean correctBracketing(String brackets) {
        int count = 0;
        for (int i = 0; i < brackets.length(); ++i) {
            count = brackets.charAt(i) == '<' ? ++count : --count;
            if (count > 0) continue;
            return false;
        }
        return count == 0;
    }
}
