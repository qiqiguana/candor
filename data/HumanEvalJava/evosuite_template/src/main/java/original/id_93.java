package original;

class Encode {
    /**
     * * Write a function that takes a message, and encodes in such a
     * way that it swaps case of all letters, replaces all vowels in
     * the message with the letter that appears 2 places ahead of that
     * vowel in the english alphabet.
     * Assume only letters.
     *
     * Examples:
     * >>> encode('test')
     * 'TGST'
     * >>> encode('This is a message')
     * 'tHKS KS C MGSSCGG'
     *
     * > encode("TEST")
     * "tgst"
     * > encode("Mudasir")
     * "mWDCSKR"
     * > encode("YES")
     * "ygs"
     * > encode("This is a message")
     * "tHKS KS C MGSSCGG"
     * > encode("I DoNt KnOw WhAt tO WrItE")
     * "k dQnT kNqW wHcT Tq wRkTg"
     */
    public static String encode(String message) {
        String result = "";
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c -= 32;
            } else if (c >= 'A' && c <= 'Z') {
                c += 32;
            }
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                c += 2;
            }
            result += c;
        }
        return result;
    }


}