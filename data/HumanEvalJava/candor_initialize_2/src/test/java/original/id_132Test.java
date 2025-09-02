package original;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsNested.
*/
class IsNestedTest {
    @Test
    void testIsNested() {
        assertTrue(IsNested.isNested("[[][]]"));
    }
    
    @Test
        public void testNothing(){
            IsNested s = new IsNested();
            }
    @Test
    public void testEmptyString() {
    	Boolean result = IsNested.isNested("");
    	assertFalse(result);
    }
    @Test
    public void testSingleBracket() {
    	Boolean result = IsNested.isNested("[");
    	assertFalse(result);
    }
    @Test
    public void testNestedBrackets() {
    	Boolean result = IsNested.isNested("[[[[[]]]]");
    	assertTrue(result);
    }
    @Test
    public void testNoNestedBrackets() {
    	Boolean result = IsNested.isNested("[][]");
    	assertFalse(result);
    }
    @Test
    public void testPartiallyNestedBrackets() {
    	Boolean result = IsNested.isNested("[[]]][[[");
    	assertTrue(result);
    }
                                    
}