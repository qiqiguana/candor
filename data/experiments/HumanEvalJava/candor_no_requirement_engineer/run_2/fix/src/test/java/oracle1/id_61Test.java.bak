package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing1.
*/
class CorrectBracketing1Test {
	@Test
    void testCorrectBracketing_BalancedBrackets_ReturnTrue() {
        String brackets = "(()())";
        Boolean result = CorrectBracketing1.correctBracketing(brackets);
        assertTrue(result);
    }
 
 @Test
     void testNothing(){
         CorrectBracketing1 s = new CorrectBracketing1();
         }
 @Test
 public void testCorrectBracketing_EmptyString() {
     assertTrue(CorrectBracketing1.correctBracketing(""));
 }
 @Test
 public void testCorrectBracketing_SingleOpenBracket() {
     assertFalse(CorrectBracketing1.correctBracketing("("));
 
 }
 @Test
 public void testCorrectBracketing_SingleCloseBracket() {
     assertFalse(CorrectBracketing1.correctBracketing(")"));
 }
 @Test
 public void testCorrectBracketing_MultipleConsecutiveOpenBrackets() {
     assertFalse(CorrectBracketing1.correctBracketing("(((()"));
 }
 @Test
 public void testCorrectBracketing_MultipleConsecutiveCloseBrackets() {
     assertFalse(CorrectBracketing1.correctBracketing(")))))"));
 }
 @Test
 public void testCorrectBracketing_AlternatingOpenAndCloseBrackets() {
     assertTrue(CorrectBracketing1.correctBracketing("()()()()"));
 }
 @Test
 public void testCorrectBracketing_NestedOpenAndCloseBrackets() {
     assertTrue(CorrectBracketing1.correctBracketing("(())(())"));
 }
                                 
}