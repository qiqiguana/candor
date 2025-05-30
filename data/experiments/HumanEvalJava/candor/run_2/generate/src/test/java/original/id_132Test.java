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
        assertTrue(IsNested.isNested(input));
    }
    
    @Test
        public void testNothing(){
            IsNested s = new IsNested();
            }
    @Test
    public void Test_EmptyString_ReturnsFalse() {
        assertFalse(IsNested.isNested(""));
    }
    @Test
    public void Test_SingleOpeningBracket_ReturnsFalse() {
        assertFalse(IsNested.isNested("["));
    }
    @Test
    public void Test_SingleClosingBracket_ReturnsFalse() {
        assertFalse(IsNested.isNested("]"));
    }
    @Test
    public void Test_OpeningAndClosingBracketsNotNested_ReturnsFalse() {
        assertFalse(IsNested.isNested("[]"));
    }
    @Test
    public void Test_OpeningAndClosingBracketsNestedButCountLessThan2_ReturnsFalse_4() {
        assertFalse(IsNested.isNested("[] []"));
    }
    @Test
    public void Test_OpeningAndClosingBracketsNestedButCountLessThan2_ReturnsFalse_5() {
        assertFalse(IsNested.isNested("[]][]"));
    }
    @Test
    public void Test_OpeningAndClosingBracketsNestedButCountLessThan2_ReturnsFalse_6() {
        assertFalse(IsNested.isNested("[ ][]"));
    }
    @Test
    public void Test_OpeningAndClosingBracketsNested() {
        assertTrue(IsNested.isNested("[[ ]]"));
    }
    @Test
    public void Test_OpeningAndClosingBracketsNotNested() {
        assertFalse(IsNested.isNested("[] []"));
    }
    @Test
    public void Test_OpeningButNotClosing() {
        assertFalse(IsNested.isNested("["));
    }
    @Test
    public void Test_ClosingButNotOpening() {
        assertFalse(IsNested.isNested("]"));
    }
    @Test
    public void Test_NoOpeningOrClosing() {
        assertFalse(IsNested.isNested(""));
    }
    @Test
    public void Test_MultipleConsecutiveOpening() {
        assertFalse(IsNested.isNested("[[["));
    }
    @Test
    public void Test_MultipleConsecutiveClosing() {
        assertFalse(IsNested.isNested("]]]"));
    }
    @Test
    public void Test_OpeningAndClosingParenthesesNotNested() {
        assertFalse(IsNested.isNested("( )"));
    }
    @Test
    public void Test_OpeningAndClosingAngleBracketsNotNested() {
        assertFalse(IsNested.isNested("< >"));
    }
    @Test
    public void Test_NestedBrackets() {
        assertTrue(IsNested.isNested("[[ ]]"));
    }
    @Test
    public void Test_MultipleBrackets() {
        assertTrue(IsNested.isNested("[[[ ]]]"));
    }
    @Test
    public void Test_OpeningAndClosingBracketsNested_ReturnsTrue() {
        assertTrue(IsNested.isNested("[[]]"));
    }
    @Test
    public void Test_OpeningAndClosingBracketsNestedButCountLessThan2_ReturnsTrue_1() {
        assertTrue(IsNested.isNested("[[]]"));
    }
    @Test
    public void test_OpeningAndClosingBracketsNestedButCountLessThan2_ReturnsFalse_5() {
        assertFalse(IsNested.isNested("[ 	]"));
    }
    @Test
    public void Test_OpeningAndClosingBracketsNested_ReturnsTrue_10() {
        assertTrue(IsNested.isNested("[[]]").booleanValue());
    }
    @Test
    public void Test_OpeningAndClosingParentheses_Fixed() {
        assertFalse(IsNested.isNested("[()]"));
    }
    @Test
    public void Test_OpeningAndClosingBraces() {
        assertTrue(IsNested.isNested("[[]]"));
    }
                                    
}