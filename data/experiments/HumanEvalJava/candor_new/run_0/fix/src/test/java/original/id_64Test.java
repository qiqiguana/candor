package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of VowelsCount.
*/
class VowelsCountTest {
    @Test
    void testVowelCountAtEnd() {
        // Arrange and Act
        int result = VowelsCount.vowelsCount("bye");
        // Assert
        assertEquals(1, result);
    }
    
    @Test
        public void testNothing(){
            VowelsCount s = new VowelsCount();
            }
    @Test
    public void VowelsCount_y_at_end() {
        String[] input = {"my"};
        int expected_result = 1;
        assertEquals(expected_result, VowelsCount.vowelsCount(input[0]));
    }
    @Test
    public void VowelsCount_Y_at_end() {
        String[] input = {"mY"};
        int expected_result = 1;
        assertEquals(expected_result, VowelsCount.vowelsCount(input[0]));
    }
    @Test
    public void VowelsCount_y_at_beginning_1() {
    	String input = "yellow";
    	int expected_result = 2;
    	assertEquals(expected_result, original.VowelsCount.vowelsCount(input));
    }
    @Test
    public void testVowelsCountWithLowercaseLetters() {
        String s = "aeiou";
        int expected = 5;
        assertEquals(expected, VowelsCount.vowelsCount(s));
    }
    @Test
    public void testVowelsCountWithUppercaseLetters() {
        String s = "AEIOU";
        int expected = 5;
        assertEquals(expected, VowelsCount.vowelsCount(s));
    }
                                    
}