package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AnyInt.
*/
class AnyIntTest {

@Test
void testAnyInt_WithIntegerInputs_ReturnsTrue() {
   // Arrange and Act
   boolean result = AnyInt.anyInt(5, 2, 7);
   
   // Assert
   assertTrue(result);
}

@Test
    public void testNothing(){
        AnyInt s = new AnyInt();
        }
@Test
public void testAnyInt_Positive_OneNumberIsSumOfOtherTwo() {
    assertTrue(AnyInt.anyInt(5, 2, 7));
}
@Test
public void testAnyInt_Negative_NoNumberIsSumOfOtherTwo() {
    assertFalse(AnyInt.anyInt(3, 2, 2));
}
@Test
public void testAnyInt_EdgeCase_ZeroValues() {
    assertTrue(AnyInt.anyInt(0, 0, 0));
}
@Test
public void testAnyInt_Negative_NonIntegerValues() {
    assertFalse(AnyInt.anyInt(3.6, -2.2, 2));
}
@Test
public void testAnyInt_EdgeCase_NegativeValues() {
    assertTrue(AnyInt.anyInt(-3, -2, -5));
}
@Test
public void testAnyInt_Positive_OneNumberIsSumOfOtherTwo1() {
    assertTrue(AnyInt.anyInt(-2, -3, -5));
}
@Test
public void testAnyInt_PositiveScenario_SumOfTwoNumbersEqualsThirdNumber() {
    assertTrue(AnyInt.anyInt(2, 3, 5));
}
@Test
public void testAnyInt_NegativeScenario_NonIntegerInputs() {
    assertFalse(AnyInt.anyInt(2.5, 3, 4));
}
@Test
public void testAnyInt_EdgeCase_ZeroInputs() {
    assertTrue(AnyInt.anyInt(0, 0, 0));
}
@Test
public void testAnyInt_SpecificFunctionality_NegativeNumbers() {
    assertTrue(AnyInt.anyInt(-2, 3, 1));
}
@Test
public void testAnyInt_PositiveScenario_AllEqualInputs() {
    assertFalse(AnyInt.anyInt(4, 4, 4));
}
@Test
public void Positive_test_with_integer_values() {
    assertTrue(original.AnyInt.anyInt(1, 2, 3));
}
@Test
public void Negative_test_with_non_integer_values() {
    assertFalse(original.AnyInt.anyInt(1.5, 2.5, 3.5));
}
@Test
public void Edge_case_test_with_zero_values() {
    assertTrue(original.AnyInt.anyInt(0, 0, 0));
}
@Test
public void Edge_case_test_with_negative_values_2() {
    assertTrue(original.AnyInt.anyInt(-1, -2, -3));
}
@Test
public void Specific_functionality_test_with_equal_values() {
    assertTrue(original.AnyInt.anyInt(2, 2, 4));
}
@Test
public void Positive_test_with_large_integer_values() {
    assertTrue(original.AnyInt.anyInt(1000000, 2000000, 3000000));
}
@Test
public void Negative_test_with_NaN() {
    assertFalse(original.AnyInt.anyInt(Double.NaN, 1.0, 2.0));
}
@Test
public void Negative_test_with_Infinity() {
    assertFalse(original.AnyInt.anyInt(Double.POSITIVE_INFINITY, 1.0, 2.0));
}
                                
}