package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChangeBase.
*/
class ChangeBaseTest {
    @Test
    void testChangeBase_8_to_base3_should_return_22() {
        // Arrange and Act
        String actual = ChangeBase.changeBase(8, 3);
        // Assert
        assertEquals("22", actual);
    }
    
    @Test
        public void testNothing(){
            ChangeBase s = new ChangeBase();
            }
    @Test
    public void Positive_Test_Base_Conversion() {
    	String result = ChangeBase.changeBase(8, 3);
    	assertEquals("22", result);
    }
    @Test
    public void Negative_Test_Invalid_Input_x_LessThan_0() {
    	String result = ChangeBase.changeBase(-1, 2);
    	assertEquals("", result);
    }
    @Test
    public void Edge_Case_Test_Base_2_Conversion() {
    	String result = ChangeBase.changeBase(7, 2);
    	assertEquals("111", result);
    }
    @Test
    public void Specific_Functionality_Test_Large_Input() {
    	String result = ChangeBase.changeBase(234, 2);
    	assertEquals("11101010", result);
    }
    @Test
    public void Edge_Case_Test_x_Equal_0() {
    	String result = ChangeBase.changeBase(0, 2);
    	assertEquals("", result);
    }
                                    
}