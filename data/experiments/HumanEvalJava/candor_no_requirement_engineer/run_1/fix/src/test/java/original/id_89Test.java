package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encrypt.
*/
class EncryptTest {
    @Test
    void testEncrypt() {
        assertEquals("lm", Encrypt.encrypt("hi"));
    }
    
    @Test
        void testNothing(){
            Encrypt s = new Encrypt();
            }
    @Test
    public void encryptSingleCharacter() {
        String result = Encrypt.encrypt("a");
        assertEquals("e", result);
    }
    @Test
    public void encryptMultipleCharacters() {
        String result = Encrypt.encrypt("hi");
        assertEquals("lm", result);
    }
    @Test
    public void encryptWordWithRotationBeyondAlphabetEnd() {
        String result = Encrypt.encrypt("gf");
        assertEquals("kj", result);
    }
    @Test
    public void encryptLongString() {
        String result = Encrypt.encrypt("dxzdlmnilfuhmilufhlihufnmlimnufhlimnufhfucufh");
        assertEquals("hbdhpqrmpjylqmpyjlpmlyjrqpmqryjlpmqryjljygyjl", result);
    }
    @Test
    public void encryptEmptyString() {
        String result = Encrypt.encrypt("");
        assertEquals("", result);
    }
    @Test
    public void encryptNullInput() {
        assertThrows(NullPointerException.class, () -> Encrypt.encrypt(null));
    }
                                    
}