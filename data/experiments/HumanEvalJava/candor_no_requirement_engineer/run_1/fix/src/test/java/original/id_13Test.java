package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GreatestCommonDivisor.
*/
class GreatestCommonDivisorTest {
    @Test
    void test_GreatestCommonDivisor_of_25_and_15_is_5() {
        int result = GreatestCommonDivisor.greatestCommonDivisor(25, 15);
        assertEquals(5, result);
    }
    
    @Test
        void testNothing(){
            GreatestCommonDivisor s = new GreatestCommonDivisor();
            }
    @Test
    public void GCD_of_two_positive_numbers() {
        int result = GreatestCommonDivisor.greatestCommonDivisor(3, 5);
        assertEquals(1, result);
    }
    @Test
    public void GCD_of_one_positive_and_one_negative_number() {
        int result = GreatestCommonDivisor.greatestCommonDivisor(3, -5);
        assertEquals(1, result);
    }
    @Test
    public void GCD_of_two_numbers_where_a_is_0() {
        int result = GreatestCommonDivisor.greatestCommonDivisor(0, 5);
        assertEquals(5, result);
    }
    @Test
    public void GCD_of_two_equal_numbers() {
        int result = GreatestCommonDivisor.greatestCommonDivisor(5, 5);
        assertEquals(5, result);
    }
    @Test
    public void GCD_of_a_number_and_its_multiple() {
        int result = GreatestCommonDivisor.greatestCommonDivisor(3, 15);
        assertEquals(3, result);
    }
    @Test
    public void GCD_of_two_negative_numbers_2() {
        int result = GreatestCommonDivisor.greatestCommonDivisor(-3, -5);
        assertEquals(1, Math.abs(result));
    }
    @Test
    public void GCD_of_two_negative_numbers_1() {
        int result = GreatestCommonDivisor.greatestCommonDivisor(-3, -5);
        assertEquals(1, Math.abs(result));
    }
                                    
}