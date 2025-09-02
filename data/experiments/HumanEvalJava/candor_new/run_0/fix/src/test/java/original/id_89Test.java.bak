package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encrypt.
*/
class EncryptTest {
    @Test
    void testEncryptFunctionality() {
        String input = "hello";
        String expectedOutput = "lipps";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
    
    @Test
        public void testNothing(){
            Encrypt s = new Encrypt();
            }
    @Test
    void testEncryptShiftBeyondAlphabetFixed3() {
        String input = "y";
        String expected = "a";
        char c = original.Encrypt.encrypt(input).charAt(0);
        int shift = 2 * 2;
        char resultChar = (char) ('a' + (input.charAt(0) - 'a' + shift) % 26);
        assertEquals(resultChar, c);
    }
    @Test
    void testEncrypt_uppercase_letters() {
    String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String expected_result = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    assertEquals(expected_result, Encrypt.encrypt(s));
    }
    @Test
    public void testEncryptNonEnglishCharacter() {
        String input = "€";
        String expectedOutput = "€";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
                                    
}