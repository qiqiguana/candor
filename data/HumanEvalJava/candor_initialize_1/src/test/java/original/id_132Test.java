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
        String input = "[[]]";
        boolean expected = true;
        assertEquals(expected, IsNested.isNested(input));
    }
    
    @Test
        public void testNothing(){
            IsNested s = new IsNested();
            }
    @Test
    public void Test_isNested_EmptyString() {
        assertFalse(IsNested.isNested(""));
    }
    @Test
    public void Test_isNested_SingleBracket1() {
        assertFalse(IsNested.isNested("["));
    }
    @Test
    public void Test_isNested_SingleBracket2() {
        assertFalse(IsNested.isNested("]"));
    }
    @Test
    public void Test_isNested_InvalidInput() {
        assertFalse(IsNested.isNested("hello"));
    }
    @Test
    public void Test_isNested_MultipleOpeningBrackets1() {
        assertFalse(IsNested.isNested("[[["));
    }
    @Test
    public void Test_isNested_MultipleOpeningBrackets2() {
        assertFalse(IsNested.isNested("[[[["));
    }
    @Test
    public void Test_isNested_MultipleClosingBrackets1() {
        assertFalse(IsNested.isNested("]]"));
    }
    @Test
    public void Test_isNested_MultipleClosingBrackets2() {
        assertFalse(IsNested.isNested("]]]]"));
    }
    @Test
    public void Test_isNested_AlternatingBrackets1() {
        assertTrue(IsNested.isNested("[[]]"));
    }
    @Test
    public void Test_isNested_AlternatingBrackets2() {
        assertTrue(IsNested.isNested("[[[]]]"));
    }
    @Test
    public void Test_EmptyString() {
        assertFalse(IsNested.isNested(""));
    }
    @Test
    public void Test_SingleOpeningBracket() {
        assertFalse(IsNested.isNested("["));
    }
    @Test
    public void Test_SingleClosingBracket() {
        assertFalse(IsNested.isNested("]"));
    }
    @Test
    public void Test_OpeningAndClosingBrackets() {
        assertFalse(IsNested.isNested("[][]"));
    }
    @Test
    public void Test_NestedBrackets_MultipleLevels_2() {
        assertTrue(IsNested.isNested("[[[[]]]]") == true);
    }
    @Test
    public void Test_IsNested_ValidInput() {
        assertTrue(IsNested.isNested("[[][]]") == true);
    }
                                    
}