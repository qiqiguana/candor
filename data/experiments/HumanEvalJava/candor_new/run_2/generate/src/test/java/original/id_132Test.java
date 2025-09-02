package original;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsNested.
*/
class IsNestedTest {
    @Test
    void testIsNested() {
        assertTrue(IsNested.isNested("[[]]") == true);
    }
    
    @Test
        public void testNothing(){
            IsNested s = new IsNested();
            }
    @Test
    public void test_isNested_EmptyString_ReturnsFalse() {
        assertTrue(IsNested.isNested("").equals(false));
    }
    @Test
    public void test_isNested_SingleBracket_ReturnsFalse_2() {
        assertFalse(IsNested.isNested("["));
    }
    @Test
    public void testNonNestedBrackets() {
    	assertFalse(IsNested.isNested("[][]"));
    }
                                    
}