/*
 * Decompiled with CFR 0.152.
 */
package original;

class GetClosestVowel {
    GetClosestVowel() {
    }

    public static String getClosestVowel(String word) {
        String vowels = "aeiouAEIOU";
        for (int i = word.length() - 2; i > 0; --i) {
            if (vowels.indexOf(word.charAt(i)) == -1 || vowels.indexOf(word.charAt(i + 1)) != -1 || vowels.indexOf(word.charAt(i - 1)) == -1) continue;
            return word.substring(i, i + 1);
        }
        return "";
    }
}
