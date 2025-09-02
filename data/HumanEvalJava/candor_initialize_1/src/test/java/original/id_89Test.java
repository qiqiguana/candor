package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encrypt.
*/
class EncryptTest {
    @Test
    void testEncrypt() {
        String input = "hi";
        String expectedOutput = "lm";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
    
    @Test
        public void testNothing(){
            Encrypt s = new Encrypt();
            }
    @Test
    public void testEncryptClassInitialization() {
        // Create an instance of the Encrypt class to verify initialization
        new Encrypt();
    }
    @Test
    public void testEncryptRotateAlphabeticalCharsBeyondZCorrectedFixed() {
        String input = "yz";
        String expectedOutput = "cd";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
    @Test
    public void testUppercaseLetters() {
        String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String expectedOutput = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
    @Test
    public void testNonAlphabeticalCharacters() {
        String input = "!@#$%^&*()_+-={}:<>?,./;[]\\|";
        String expectedOutput = "!@#$%^&*()_+-={}:<>?,./;[]\\|";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
                                    
}