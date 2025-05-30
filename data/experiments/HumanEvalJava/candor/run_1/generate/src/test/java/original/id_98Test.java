package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountUpper.
*/
class CountUpperTest {
    @Test
    void testCountUpper_EvenIndexUppercaseVowel_ReturnsExpectedValue() {
        String input = "aBCdEf";
        int expected = 1;
        int actual = CountUpper.countUpper(input);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            CountUpper s = new CountUpper();
            }
    @Test
    public void testCountUpper_WithEmptyString_ReturnsZero() {
        String input = "";
        int expected = 0;
        int result = CountUpper.countUpper(input);
        assertEquals(expected, result);
    }
    @Test
    public void testCountUpper_WithSingleUppercaseVowelAtEvenIndex_ReturnsOne() {
        String input = "A";
        int expected = 1;
        int result = CountUpper.countUpper(input);
        assertEquals(expected, result);
    }
    @Test
    public void testCountUpper_WithSingleUppercaseVowelAtOddIndex_ReturnsZero() {
        String input = "aB";
        int expected = 0;
        int result = CountUpper.countUpper(input);
        assertEquals(expected, result);
    }
    @Test
    public void testCountUpper_WithMultipleUppercaseVowelsAtEvenIndices_ReturnsCorrectCount() {
        String input = "AEIOU";
        int expected = 3;
        int result = CountUpper.countUpper(input);
        assertEquals(expected, result);
    }
    @Test
    public void testCountUpper_WithNoUppercaseVowels_ReturnsZero() {
        String input = "abcdefg";
        int expected = 0;
        int result = CountUpper.countUpper(input);
        assertEquals(expected, result);
    }
    @Test
    public void testCountUpper_WithNullInput_ThrowsNullPointerException() {
        String input = null;
        assertThrows(NullPointerException.class, () -> CountUpper.countUpper(input));
    }
    @Test
    public void testCountUpper_WithSingleLowercaseVowelAtEvenIndex_ReturnsZero() {
        String input = "a";
        int expected = 0;
        int result = CountUpper.countUpper(input);
        assertEquals(expected, result);
    }
                                    
}