package oracle1;

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
        assertTrue(IsNested.isNested("[[]]"));
    }
    
    @Test
     void testNothing(){
         IsNested s = new IsNested();
         }
    @Test
    public void testEmptyString() {
        assertFalse(IsNested.isNested(""));
    }
    @Test
    public void testSingleOpeningBracket() {
        assertFalse(IsNested.isNested("["));
    }
    @Test
    public void testSingleClosingBracket() {
        assertFalse(IsNested.isNested("]"));
    }
    @Test
    public void testMultipleOpeningBrackets() {
        assertFalse(IsNested.isNested("[[[["));
    }
    @Test
    public void testMultipleClosingBrackets() {
        assertFalse(IsNested.isNested("]] ]] ]"));
    }
    @Test
    public void testNestedBracketsWithSpaces() {
        assertTrue(IsNested.isNested("[ [ ] ]"));
    }
    @Test
    public void testNestedBracketsWithMultipleLevels() {
        assertFalse(IsNested.isNested("[[[ ]]]"));
    }
                                  
}