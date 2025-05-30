package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CycpatternCheck.
*/
class CycpatternCheckTest {

@Test
void testCycPatternFound(){
    String a = "hello";
    String b = "ell";
    assertTrue(CycpatternCheck.cycpatternCheck(a, b));
}

@Test
 void testNothing(){
     CycpatternCheck s = new CycpatternCheck();
     }
@Test
public void testCycpatternCheckWithNullInputs() {
    assertThrows(NullPointerException.class, () -> CycpatternCheck.cycpatternCheck(null, null));
}
@Test
public void testCycpatternCheckWithSingleCharacterStrings() {
    assertTrue(CycpatternCheck.cycpatternCheck("a", "a"));
}
@Test
public void testCycpatternCheckWithNonMatchingStrings() {
    assertFalse(CycpatternCheck.cycpatternCheck("abcd", "efgh"));
}
@Test
public void testCycpatternCheckWithMatchingSubstring() {
    assertTrue(CycpatternCheck.cycpatternCheck("hello", "ell"));
}
@Test
public void testCycpatternCheckWithMatchingSubstring1() {
    assertTrue(CycpatternCheck.cycpatternCheck("hello", "lo"));
}
@Test
public void testCycpatternCheckWithMatchingSubstring2() {
    assertTrue(CycpatternCheck.cycpatternCheck("hello", "hel"));
}
@Test
public void testCycpatternCheckWithMatchingSubstring3() {
    assertTrue(CycpatternCheck.cycpatternCheck("hello", "he"));
}
@Test
public void testCycpatternCheckWithMatchingSubstring4() {
    assertTrue(CycpatternCheck.cycpatternCheck("hello", "h"));
}
@Test
public void testCycpatternCheckWithMatchingSubstring5() {
    assertTrue(CycpatternCheck.cycpatternCheck("hello", "l"));
}
@Test
public void testCycpatternCheckWithMatchingSubstring6() {
    assertTrue(CycpatternCheck.cycpatternCheck("hello", "ll"));
}
@Test
public void testCycpatternCheckWithMatchingSubstring7() {
    assertTrue(CycpatternCheck.cycpatternCheck("hello", "o"));
}
@Test
public void testCycpatternCheckWithMatchingSubstring8() {
    assertTrue(CycpatternCheck.cycpatternCheck("hello", "hello"));
}
@Test
public void testCycpatternCheck_MatchingSubstring() {
    boolean result = CycpatternCheck.cycpatternCheck("hello", "ell");
    assertTrue(result);
}
@Test
public void testCycpatternCheck_NonMatchingSubstring() {
    boolean result = CycpatternCheck.cycpatternCheck("abcd", "abd");
    assertFalse(result);
}
@Test
public void testCycpatternCheck_RotationMatching() {
    boolean result = CycpatternCheck.cycpatternCheck("abab", "baa");
    assertTrue(result);
}
@Test
public void testCycpatternCheck_MultipleRotations() {
    boolean result = CycpatternCheck.cycpatternCheck("efef", "fee");
    assertTrue(result);
}
@Test
public void testCycpatternCheck_NonMatchingRotation() {
    boolean result = CycpatternCheck.cycpatternCheck("whassup", "psus");
    assertFalse(result);
}
@Test
public void testCycpatternCheck_EmptyString() {
    boolean result = CycpatternCheck.cycpatternCheck("", "hello");
    assertFalse(result);
}
@Test
public void testCycpatternCheck_Null() {
    assertThrows(NullPointerException.class, () -> CycpatternCheck.cycpatternCheck(null, "hello"));
}
@Test
void testCycPatternCheckValidRotation1() {
    assertTrue(CycpatternCheck.cycpatternCheck("hello", "ell"));
}
                              
}