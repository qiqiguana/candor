package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringXor.
*/
class StringXorTest {
    @Test
    void testStringXorShouldReturnCorrectResultWhenInputsAreDifferent() {
        // Arrange
        String a = "010";
        String b = "110";

        // Act
        String result = StringXor.stringXor(a, b);

        // Assert
        assertEquals("100", result);
    }
    
    @Test
     void testNothing(){
         StringXor s = new StringXor();
         }
    @Test
    public void testStringXor_HappyPath() {
    	String a = "010";
    	String b = "110";
    	String expected = "100";
    	assertEquals(expected, StringXor.stringXor(a, b));
    }
    @Test
    public void testStringXor_SadPath_NullInput() {
    	String a = null;
    	String b = "101010";
    	assertThrows(NullPointerException.class, () -> StringXor.stringXor(a, b));
    }
    @Test
    public void testStringXor_EmptyStrings() {
    	String a = "";
    	String b = "";
    	String expected = "";
    	assertEquals(expected, StringXor.stringXor(a, b));
    }
    @Test
    public void testStringXor_SingleCharacterStrings() {
    	String a = "1";
    	String b = "0";
    	String expected = "1";
    	assertEquals(expected, StringXor.stringXor(a, b));
    }
    @Test
    public void testStringXor_AllOnesAndZeros() {
    	String a = "111111";
    	String b = "000000";
    	String expected = "111111";
    	assertEquals(expected, StringXor.stringXor(a, b));
    }
                                  
}