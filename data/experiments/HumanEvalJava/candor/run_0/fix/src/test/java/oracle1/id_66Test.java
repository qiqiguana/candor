package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digitsum.
*/
class DigitsumTest {
    @Test
    void testDigitSum_UpperCaseCharacters_ReturnsCorrectSum() {
        // Arrange
        String input = "aAaaaXa";
        int expectedSum = 153;

        // Act
        int actualSum = Digitsum.digitSum(input);

        // Assert
        assertEquals(expectedSum, actualSum);
    }
    
    @Test
        void testNothing(){
            Digitsum s = new Digitsum();
            }
    @Test
    public void test_EmptyString() {
        assertEquals(0, Digitsum.digitSum(""));
    }
    @Test
    public void test_NoUpperCaseCharacters() {
        assertEquals(0, Digitsum.digitSum("abcdef"));
    }
    @Test
    public void test_SingleUpperCaseCharacter() {
        assertEquals(65, Digitsum.digitSum("A"));
    }
    @Test
    public void test_MultipleUpperCaseCharacters() {
        assertEquals(0, Digitsum.digitSum("ABC"));
    }
                                    
}