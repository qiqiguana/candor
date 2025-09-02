package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Simplify.
*/
class SimplifyTest {
    @Test
    void test_simplify_returns_true_for_valid_input() {
        String x = "1/5";
        String n = "5/1";
        Boolean result = Simplify.simplify(x, n);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            Simplify s = new Simplify();
            }
    @Test
    public void Test_Simplify_Positive_WholeNumber() {
        String x = "1/5";
        String n = "5/1";
        Boolean expected_result = true;
        Boolean actual_result = Simplify.simplify(x, n);
        assertEquals(expected_result, actual_result);
    }
    @Test
    public void Test_Simplify_Negative_NotWholeNumber() {
        String x = "1/6";
        String n = "2/1";
        Boolean expected_result = false;
        Boolean actual_result = Simplify.simplify(x, n);
        assertEquals(expected_result, actual_result);
    }
    @Test
    public void Test_Simplify_Positive_WholeNumber_DifferentDenominators() {
        String x = "7/10";
        String n = "10/2";
        Boolean expected_result = false;
        Boolean actual_result = Simplify.simplify(x, n);
        assertEquals(expected_result, actual_result);
    }
    @Test
    public void Test_Simplify_Negative_InvalidInput() {
        String x = "a/b";
        String n = "c/d";
        assertThrows(NumberFormatException.class, () -> Simplify.simplify(x, n));
    }
    @Test
    public void Test_Simplify_Positive_WholeNumber1() {
        String x = "4/2";
        String n = "3/3";
        Boolean expected_result = true;
        Boolean actual_result = Simplify.simplify(x, n);
        assertEquals(expected_result, actual_result);
    }
    @Test
    public void Test_Simplify_Negative_InvalidInput1() {
        String x = "a/b";
        String n = "4/3";
        assertThrows(NumberFormatException.class, () -> Simplify.simplify(x, n));
    }
                                    
}