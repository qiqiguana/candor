package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encrypt.
*/
class EncryptTest {

    @Test
    void testEncryptShiftsAlphabetByFourPlaces() {
        String input = "abcd";
        String expectedOutput = "efgh";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
    
    @Test
        public void testNothing(){
            Encrypt s = new Encrypt();
            }
    @Test
    public void testEncryptSingleCharacter() {
        String input = "a";
        String expected = "e";
        assertEquals(expected, Encrypt.encrypt(input));
    }
    @Test
    public void testEncryptShortString() {
        String input = "hi";
        String expected = "lm";
        assertEquals(expected, Encrypt.encrypt(input));
    }
    @Test
    public void testEncryptLongString() {
        String input = "asdfghjkl";
        String expected = "ewhjklnop";
        assertEquals(expected, Encrypt.encrypt(input));
    }
    @Test
    public void testEncryptStringWithNonAlphabetCharacters() {
        String input = "hello world!";
        String expected = "lipps asvph!";
        assertEquals(expected, Encrypt.encrypt(input));
    }
    @Test
    public void testEncryptEmptyString() {
        String input = "";
        String expected = "";
        assertEquals(expected, Encrypt.encrypt(input));
    }
    @Test
    public void testEncryptCorrectShift_1() {
        String input = "ab";
        String expected = "ef";
        assertEquals(expected, Encrypt.encrypt(input));
    }
    @Test
    public void test_encrypt_negative_test_invalid_input() {
        String s = "@#";
        String expected = "@#";
        assertEquals(expected, Encrypt.encrypt(s));
    }
    @Test
    public void test_encrypt_edge_case_test_lowercase_z() {
        String s = "z";
        String expected = "d";
        assertEquals(expected, Encrypt.encrypt(s));
    }
    @Test
    public void test_encrypt_specific_functionality_test_non_alphabetic_chars() {
        String s = "h!i";
        String expected = "l!m";
        assertEquals(expected, Encrypt.encrypt(s));
    }
    @Test
    public void test_encrypt_positive_test_long_string_corrected() {
        String s = "abcdefghijklmnopqrstuvwxyz";
        String expected = "efghijklmnopqrstuvwxyzabcd";
        assertEquals(expected, Encrypt.encrypt(s));
    }
    @Test
    public void test_encrypt_negative_test_null_input() {
        String s = null;
        assertThrows(NullPointerException.class, () -> Encrypt.encrypt(s));
    }
    @Test
    public void EncryptMultipleCharacters() {
        String input = "hi";
        String expectedResult = "lm";
        assertEquals(expectedResult, Encrypt.encrypt(input));
    }
    @Test
    public void EncryptLongString() {
        String input = "asdfghjkl";
        String expectedResult = "ewhjklnop";
        assertEquals(expectedResult, Encrypt.encrypt(input));
    }
    @Test
    public void EncryptStringWithNonAlphabeticCharacters() {
        String input = "hello world!";
        String expectedResult = "lipps asvph!";
        assertEquals(expectedResult, Encrypt.encrypt(input));
    }
    @Test
    public void EncryptEmptyString() {
        String input = "";
        String expectedResult = "";
        assertEquals(expectedResult, Encrypt.encrypt(input));
    }
    @Test
    public void EncryptNullInput() {
        String input = null;
        assertThrows(NullPointerException.class, () -> Encrypt.encrypt(input));
    }
    @Test
    public void EncryptEdgeCase() {
        String input = "a";
        String expected = "e";
        assertEquals(expected, Encrypt.encrypt(input));
    }
    @Test
    void testEncryptNullInput() {
        String s = null;
        assertThrows(NullPointerException.class, () -> Encrypt.encrypt(s));
    }
    @Test void testEncryptSingleCharacterCorrected() { String s = "a"; assertEquals("e", Encrypt.encrypt(s)); }
    @Test
    void testEncryptNonAlphabetCharacters() {
        String s = "hello123!@#";
        assertEquals("lipps123!@#", Encrypt.encrypt(s));
    }
    @Test
    public void testEncryptSingleCharacterFixed() {
        String s = "a";
        int shift = 2 * 2;
        char c = (char) ('a' + (s.charAt(0) - 'a' + shift) % 26);
        assertEquals(String.valueOf(c), Encrypt.encrypt(s));
    }
    @Test
    public void testEncryptMultipleCharacters() {
        assertEquals("lipps", Encrypt.encrypt("hello"));
    }
    @Test
    public void testEncryptNullInput_NullPointerException() {
        assertThrows(NullPointerException.class, () -> Encrypt.encrypt(null));
    }
    @Test
    public void testEncryptAlphabetBoundaryFixed2() {
        assertEquals("cd", Encrypt.encrypt("yz"));
    }
    @Test
    void EncryptNegativeTest() {
        String s = null;
        assertThrows(NullPointerException.class, () -> Encrypt.encrypt(s));
    }
    @Test
    void EncryptEdgeCaseTest() {
        String s = "a";
        assertEquals("e", Encrypt.encrypt(s));
    }
    @Test
    void EncryptLongStringTest() {
        String s = "dxzdlmnilfuhmilufhlihufnmlimnufhlimnufhfucufh";
        assertEquals("hbdhpqrmpjylqmpyjlpmlyjrqpmqryjlpmqryjljygyjl", Encrypt.encrypt(s));
    }
    @Test
    void EncryptEmptyStringTest() {
        String s = "";
        assertEquals("", Encrypt.encrypt(s));
    }
    @Test
    public void testEncrypt_UppercaseAlphabet() {
    String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String expected_result = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    assertEquals(expected_result, Encrypt.encrypt(s));
    }
    @Test
    public void encrypt_with_special_characters_input_1_fixed() {
        String input = "!@#$%^&*()";
        String expectedResult = "!@#$%^&*()";
        assertEquals(expectedResult, Encrypt.encrypt(input));
    }
    @Test
    public void encrypt_with_empty_string_input() {
        String[] inputs = {""};
        String[] expectedResults = {""};
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(expectedResults[i], Encrypt.encrypt(inputs[i]));
        }
    }
    @Test
    public void testEncryptLowercaseLettersWithinAlphabetRange() {
        String input = "ab";
        String expectedOutput = "ef";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
    @Test
    public void testEncryptUppercaseLettersWithinAlphabetRange() {
        String input = "AB";
        String expectedOutput = "AB";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
                                    
}