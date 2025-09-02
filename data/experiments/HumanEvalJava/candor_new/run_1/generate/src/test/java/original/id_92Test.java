package original;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AnyInt.
*/
class AnyIntTest {
    @Test
    void testAnyInt() {
        assertTrue(AnyInt.anyInt(2, 3, 1));
    }
    
    @Test
        public void testNothing(){
            AnyInt s = new AnyInt();
            }
    @Test
    public void test_anyInt_with_integers_and_sum_equals() {
    	assertTrue(AnyInt.anyInt(3, 2, 1));
    }
    @Test
    public void test_anyInt_with_non_integer_inputs() {
    	assertFalse(AnyInt.anyInt(3.6, -2, 2));
    }
    @Test
    public void test_anyInt_with_two_equal_integers_and_sum_equals_1() {
        assertTrue(AnyInt.anyInt(2, 2, 4));
    }
    @Test
    public void test_anyInt_with_negative_integers_and_sum_equals_1_v2() {
        Number[] numbers = new Number[]{-4, -2, 6};
        List<Boolean> results = new ArrayList<>();
        for (Number num : numbers) {
            results.add(AnyInt.anyInt(num, numbers[0], numbers[1]) || AnyInt.anyInt(num, numbers[1], numbers[2]) || AnyInt.anyInt(num, numbers[2], numbers[0]));
        }
        assertTrue(results.contains(true));
    }
                                    
}