package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IntToMiniRoman.
*/
class IntToMiniRomanTest {

    @Test
    void testIntToMiniRoman() {
        assertEquals("xix", IntToMiniRoman.intToMiniRoman(19));
    }
    
    @Test
        public void testNothing(){
            IntToMiniRoman s = new IntToMiniRoman();
            }
    @Test
    public void test_int_to_mini_roman_happy_path() {
        int[] inputs = {19, 152, 426, 500, 1, 4, 43, 90, 94, 532, 900, 994, 1000};
        String[] expectedOutputs = {"xix", "clii", "cdxxvi", "d", "i", "iv", "xliii", "xc", "xciv", "dxxxii", "cm", "cmxciv", "m"};
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(expectedOutputs[i], IntToMiniRoman.intToMiniRoman(inputs[i]));
        }
    }
    @Test
    public void test_int_to_mini_roman_specific_numbers() {
        int[] inputs = {12, 15, 21};
        String[] expectedOutputs = {"xii", "xv", "xxi"};
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(expectedOutputs[i], IntToMiniRoman.intToMiniRoman(inputs[i]));
        }
    }
                                    
}