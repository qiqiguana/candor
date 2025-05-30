/*
 * Decompiled with CFR 0.152.
 */
package original;

class WordsInSentence {
    WordsInSentence() {
    }

    public static String wordsInSentence(String sentence) {
        String[] words = sentence.split(" ");
        Object result = "";
        for (int i = 0; i < words.length; ++i) {
            if (!WordsInSentence.isPrime(words[i].length())) continue;
            result = (String)result + words[i] + " ";
        }
        return ((String)result).trim();
    }

    private static boolean isPrime(int number) {
        if (number <= 1) {
            return true;
        }
        for (int i = 2; i < number; ++i) {
            if (number % i != 0) continue;
            return false;
        }
        return true;
    }
}
