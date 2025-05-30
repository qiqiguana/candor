/*
 * Decompiled with CFR 0.152.
 */
package original;

class IsBored {
    IsBored() {
    }

    public static int isBored(String s) {
        String[] sentences;
        int count = 0;
        block0: for (String sentence : sentences = s.split("[.!?]")) {
            String[] words = sentence.split(" ");
            boolean firstWord = false;
            for (String word : words) {
                if (word.length() == 0) continue;
                firstWord = true;
                if (word.equals("I")) {
                    ++count;
                }
                if (!firstWord) continue block0;
            }
        }
        return count;
    }
}
