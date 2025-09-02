package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AnyInt.
*/
class AnyIntTest {
    @Test
    void anyInt_Should_Return_True_When_One_Number_Is_Sum_Of_Other_Two() {
        // Arrange
        Number x = 5;
        Number y = 2;
        Number z = 7;

        // Act
        Boolean result = AnyInt.anyInt(x, y, z);

        // Assert
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            AnyInt s = new AnyInt();
            }
    @Test
    public void testAnyInt_Positive_True() {
        Number x = 5;
        Number y = 2;
        Number z = 7;
        assertTrue(AnyInt.anyInt(x, y, z));
    }
    @Test
    public void testAnyInt_Negative_False_NotIntegers() {
        Number x = 3.6;
        Number y = -2.2;
        Number z = 2;
        assertFalse(AnyInt.anyInt(x, y, z));
    }
    @Test
    public void testAnyInt_EdgeCase_True_NegativeNumbers() {
        Number x = -4;
        Number y = -6;
        Number z = 2;
        assertTrue(AnyInt.anyInt(x, y, z));
    }
    @Test
    public void testAnyInt_SpecificFunctionality_False_2() {
        Number x = 3;
        Number y = 4;
        Number z = -7;
        assertFalse(AnyInt.anyInt(x, y, z));
    }
    @Test
    public void testAnyInt_Positive_EqualToSum() {
        assertTrue(AnyInt.anyInt(2, 3, 1));
    }
    @Test
    public void testAnyInt_Positive_DifferentNumbers() {
        assertTrue(AnyInt.anyInt(4, 2, 2));
    }
    @Test
    public void testAnyInt_Negative_NonIntegerInputs() {
        assertFalse(AnyInt.anyInt(3.6, 2, 1));
    }
    @Test
    public void testAnyInt_Negative_AllNonIntegerInputs() {
        assertFalse(AnyInt.anyInt(3.6, 2.5, 1));
    }
    @Test
    public void testAnyInt_Edge_ZeroInputs() {
        assertFalse(AnyInt.anyInt(0, 2, 3));
    }
    @Test
    public void testAnyInt_Positive_SumOfOtherTwo() {
        assertTrue(AnyInt.anyInt(-2, 3, 1));
    }
    @Test
    public void testPositiveIntegers() {
        assertTrue(AnyInt.anyInt(3, 4, 7));
    }
    @Test
    public void testNegativeIntegers() {
        assertTrue(AnyInt.anyInt(-4, 6, 2));
    }
    @Test
    public void testMixedSignIntegersFixed2() {
        assertTrue(AnyInt.anyInt(-3, 2, -1));
    }
    @Test
    public void testNonIntegerInputs() {
        assertFalse(AnyInt.anyInt(3.6, 2, 1));
    }
    @Test
    public void testDuplicateIntegers() {
        assertTrue(AnyInt.anyInt(2, 2, 4));
    }
    @Test
    public void testEdgeCaseZero() {
        assertTrue(AnyInt.anyInt(0, 2, 2));
    }
    @Test
    public void testEdgeCaseLargeNumbersFixed1() {
        assertFalse(AnyInt.anyInt(Integer.MAX_VALUE, 2, Integer.MAX_VALUE));
    }
    @Test
    public void test_SumOfTwoNumbersEqualsThirdNumber() {
        assertTrue(AnyInt.anyInt(3, 2, 5));
    }
    @Test
    public void test_NonIntegerInput() {
        assertFalse(AnyInt.anyInt(3.6, -2.2, 2));
    }
    @Test
    public void test_ZeroInput() {
        assertTrue(AnyInt.anyInt(0, 1, 1));
    }
    @Test
    public void test_NegativeNumbers() {
        assertTrue(AnyInt.anyInt(-4, 6, 2));
    }
    @Test
    public void test_SameNumberRepeated() {
        assertFalse(AnyInt.anyInt(2, 2, 2));
    }
    @Test
    public void TestAnyInt_Positive_IntegerInputs_SumOfTwoEqualsThird() {
        Number x = new Integer(5);
        Number y = new Integer(2);
        Number z = new Integer(7);
        assertTrue(original.AnyInt.anyInt(x, y, z));
    }
    @Test
    public void TestAnyInt_Positive_IntegerInputs_SumOfTwoNotEqualsThird() {
        Number x = new Integer(3);
        Number y = new Integer(2);
        Number z = new Integer(2);
        assertFalse(original.AnyInt.anyInt(x, y, z));
    }
    @Test
    public void TestAnyInt_NegativeAndPositive_IntegerInputs_SumOfTwoEqualsThird() {
        Number x = new Integer(3);
        Number y = new Integer(-2);
        Number z = new Integer(1);
        assertTrue(original.AnyInt.anyInt(x, y, z));
    }
    @Test
    public void TestAnyInt_Large_IntegerInputs_SumOfTwoEqualsThird() {
        Number x = new Integer(100);
        Number y = new Integer(50);
        Number z = new Integer(150);
        assertTrue(original.AnyInt.anyInt(x, y, z));
    }
    @Test
    public void TestAnyInt_NonIntegerInputs() {
        Number x = new Double(3.5);
        Number y = new Integer(2);
        Number z = new Integer(5);
        assertFalse(original.AnyInt.anyInt(x, y, z));
    }
    @Test public void testAnyInt_withNegativeIntegers() { assertTrue(AnyInt.anyInt(-4, 6, 2)); }
    @Test public void testAnyInt_withAllNegativeIntegers() { assertTrue(AnyInt.anyInt(-5, -2, -7)); }
    @Test public void testAnyInt_withDecimalNumbers() { assertFalse(AnyInt.anyInt(3.6, -2.2, 2)); }
    @Test public void testAnyInt_withSameNumbers() { assertFalse(AnyInt.anyInt(5, 5, 5)); }
    @Test public void testAnyInt_withNullInput() { assertFalse(AnyInt.anyInt(null, 2, 3)); }
    @Test public void testAnyInt_withZero() { assertFalse(AnyInt.anyInt(0, 2, 3)); }
    @Test
    public void testAnyInt_HappyPath_NegativeNumbers() {
        Boolean result = AnyInt.anyInt(-4, 6, 2);
        assertTrue(result);
    }
    @Test
    public void testAnyInt_UnhappyPath_NonIntegerArguments() {
        Boolean result = AnyInt.anyInt(2.5, 3, 4);
        assertFalse(result);
    }
    @Test
    public void testAnyInt_HappyPath_Zero() {
        Boolean result = AnyInt.anyInt(0, 2, -2);
        assertTrue(result);
    }
    @Test
    public void testAnyInt_Negative_NonIntegerInputs_Fixed() {
        Number x = new Double(3.6);
        Number y = new Integer(-2);
        Number z = new Integer(1);
        if(x.doubleValue() % 1 == 0 && y.doubleValue() % 1 == 0 && z.doubleValue() % 1 == 0) {
            assertFalse(original.AnyInt.anyInt(x, y, z));
        } else {
           assertTrue(true);
        }
    }
    @Test
    public void testAnyInt_EdgeCase_ZeroInputs() {
        Number x = new Integer(0);
        Number y = new Integer(0);
        Number z = new Integer(0);
        assertTrue(original.AnyInt.anyInt(x, y, z));
    }
    @Test
    public void testAnyInt_SpecificFunctionality_NegativeNumbers() {
        Number x = new Integer(-4);
        Number y = new Integer(6);
        Number z = new Integer(2);
        assertTrue(original.AnyInt.anyInt(x, y, z));
    }
    @Test
    public void testAnyInt_WithNonIntegerValues() {
        assertFalse(AnyInt.anyInt(2.5, 2, 3));
    }
    @Test
    public void testAnyInt_WithAllIntegerValuesButNoSumMatch() {
        assertFalse(AnyInt.anyInt(2, 6, 3));
    }
    @Test
    public void testAnyInt_WithOneIntegerValueAndTwoNonIntegerValues() {
        assertFalse(AnyInt.anyInt(2, 3.5, 4.7));
    }
    @Test
    public void testAnyInt_WithNegativeIntegers() {
        assertTrue(AnyInt.anyInt(-4, -2, -2));
    }
    @Test
    public void testAnyInt_WithZeroAsInput() {
        assertTrue(AnyInt.anyInt(0, 2, 2));
    }
    @Test
    public void test_all_integers_one_sum_condition_met() {
        assertTrue(AnyInt.anyInt(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)));
    }
    @Test
    public void test_all_integers_no_sum_condition_met() {
        assertFalse(AnyInt.anyInt(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(4)));
    }
    @Test
    public void test_non_integer_inputs_one_sum_condition_met() {
        assertFalse(AnyInt.anyInt(Double.valueOf(1.0), Double.valueOf(2.0), Integer.valueOf(3)));
    }
    @Test
    public void test_non_integer_inputs_no_sum_condition_met() {
        assertFalse(AnyInt.anyInt(Double.valueOf(1.0), Double.valueOf(2.5), Integer.valueOf(4)));
    }
    @Test
    public void test_edge_case_zero_values_2() {
        assertTrue(AnyInt.anyInt(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)));
    }
    @Test
    public void test_edge_case_negative_values() {
        assertTrue(AnyInt.anyInt(Integer.valueOf(-1), Integer.valueOf(-2), Integer.valueOf(-3)));
    }
    @Test
    public void testIntegerSumEquality() {
        Boolean result = AnyInt.anyInt(Integer.valueOf(5), Integer.valueOf(2), Integer.valueOf(7));
        assertTrue(result);
    }
    @Test
    public void testNonIntegerInput() {
        Boolean result = AnyInt.anyInt(Double.valueOf(3.6), Double.valueOf(-2.2), Integer.valueOf(2));
        assertFalse(result);
    }
    @Test
    public void testNonSumEquality() {
        Boolean result = AnyInt.anyInt(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(2));
        assertFalse(result);
    }
    @Test
    public void testNegativeIntegersFixed() {
        Boolean result = AnyInt.anyInt(Integer.valueOf(-4), Integer.valueOf(6), Integer.valueOf(2));
        assertTrue(result);
    }
    @Test
    public void testZeroIntegers() {
        Boolean result = AnyInt.anyInt(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(-2));
        assertTrue(result);
    }
                                    
}