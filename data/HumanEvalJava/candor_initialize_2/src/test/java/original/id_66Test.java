package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digitsum.
*/
class DigitsumTest {
    @Test
    void testDigitSum_WithMixedCaseString_ReturnsCorrectSum() {
        String input = "abAB";
        int expectedSum = 131;
        int actualSum = Digitsum.digitSum(input);
        assertEquals(expectedSum, actualSum);
    }
    
    @Test
        public void testNothing(){
            Digitsum s = new Digitsum();
            }
    @Test
    public void TestDigitSumClassInitialization() {
    	try {
    		Digitsum sum = new Digitsum();
    	} catch (Exception e) {
    		assert false;
    	}
    }
    @Test
    public void TestDigitSumMethodWithEmptyString() {
    	int result = Digitsum.digitSum("");
    	assertEquals(0, result);
    }
    @Test
    public void TestDigitSumMethodWithOnlyLowercaseLetters() {
    	int result = Digitsum.digitSum("abcdef");
    	assertEquals(0, result);
    }
    @Test
    public void TestDigitSumMethodWithOnlyUppercaseLetters() {
        int result = Digitsum.digitSum("ABCDEF");
        assertEquals(405, result);
    }
    @Test
    public void testUpper_case_letters() {
        String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int expected_result = 2015;
        assertEquals(expected_result, Digitsum.digitSum(input));
    }
    @Test
    public void testLower_case_letters() {
        String input = "abcdefghijklmnopqrstuvwxyz";
        int expected_result = 0;
        assertEquals(expected_result, Digitsum.digitSum(input));
    }
    @Test
    public void testMixed_case_letters_corrected_1() {
        String input = "aBcDeFgHiJkLmNoPqRsTuVwXyZ";
        int expected_result = 0;
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                expected_result += c;
            }
        }
        assertEquals(expected_result, Digitsum.digitSum(input));
    }
    @Test
    public void testNon_letter_characters() {
        String input = "1234567890!@#$%^&*()_+";
        int expected_result = 0;
        assertEquals(expected_result, Digitsum.digitSum(input));
    }
                                    
}