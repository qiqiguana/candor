package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountUpper.
*/
class CountUpperTest {
    @Test
    void testCountUpper_EvenIndices_UpperVowels_ReturnsCorrectCount() {
        String input = "aBCdEf";
        int expected = 1;
        assertEquals(expected, CountUpper.countUpper(input));
    }
    
    @Test
        void testNothing(){
            CountUpper s = new CountUpper();
            }
    @Test
    void testCountUpper_EmptyString() {
        assertEquals(0, CountUpper.countUpper(""));
    }
    @Test
    void testCountUpper_NoUppercaseVowels() {
        assertEquals(0, CountUpper.countUpper("abcdefg"));
    }
    @Test
    void testCountUpper_OnlyLowercaseVowels() {
        assertEquals(0, CountUpper.countUpper("aeiou"));
    }
    @Test
    void testCountUpper_SingleUppercaseVowelAtEvenIndex() {
        assertEquals(1, CountUpper.countUpper("A"));
    }
    @Test
    void testCountUpper_MultipleUppercaseVowelsAtEvenIndices() {
        assertEquals(3, CountUpper.countUpper("EAIOU"));
    }
    @Test
    void testCountUpper_UppercaseVowelAtOddIndex() {
        assertEquals(0, CountUpper.countUpper("aA"));
    }
    @Test
    void testCountUpper_NonVowelCharacter() {
        assertEquals(1, CountUpper.countUpper("B"));
    }
                                    
}