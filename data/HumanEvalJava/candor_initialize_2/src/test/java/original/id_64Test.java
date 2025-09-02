package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of VowelsCount.
*/
class VowelsCountTest {
    @Test
    void testVowelsCount_ForInputStringABCDEF_ReturnsCorrectCount() {
        // Arrange
        String input = "ABCDEF";
        int expected = 2;

        // Act
        int actual = VowelsCount.vowelsCount(input);

        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            VowelsCount s = new VowelsCount();
            }
    @Test
    public void VowelsCount_with_y_at_end_2() {
        assertEquals(2, VowelsCount.vowelsCount("key"));
        assertEquals(2, VowelsCount.vowelsCount("KeY"));
        assertEquals(1, VowelsCount.vowelsCount("bYe"));
    }
    @Test
    public void VowelsCount_with_y_not_at_end_2() {
        String[] inputs = {"bye", "Bye"};
        int expectedResults = 1;
        for (String input : inputs) {
            assertEquals(expectedResults, VowelsCount.vowelsCount(input));
        }
    }
    @Test
    public void VowelsCount_with_multiple_vowels_and_y_at_end_3() {
        String[] inputs = {"ACEDY", "aCeDy"};
        int expectedResults = 3;
        for (String input : inputs) {
            assertEquals(expectedResults, VowelsCount.vowelsCount(input));
        }
    }
    @Test
    public void VowelsCount_with_empty_string_4() {
        String input = "";
        int expectedResults = 0;
        assertEquals(expectedResults, VowelsCount.vowelsCount(input));
    }
    @Test
    public void testVowelsCountLowerCase() {
        String input = "aeiou";
        int expected_result = 5;
        assertEquals(expected_result, VowelsCount.vowelsCount(input));
    }
    @Test
    public void testVowelsCountUpperCase() {
        String input = "AEIOU";
        int expected_result = 5;
        assertEquals(expected_result, VowelsCount.vowelsCount(input));
    }
                                    
}