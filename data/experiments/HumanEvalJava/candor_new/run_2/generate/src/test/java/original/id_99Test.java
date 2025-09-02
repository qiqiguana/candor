package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ClosestInteger.
*/
class ClosestIntegerTest {

    @Test
    void test_closest_integer_for_positive_number() {
        String value = "10";
        int expected = 10;
        assertEquals(expected, ClosestInteger.closestInteger(value));
    }
    
    @Test
        public void testNothing(){
            ClosestInteger s = new ClosestInteger();
            }
    @Test
    public void test_closest_integer_negative_value() {
        double d = Double.parseDouble("-14.5");
        assertEquals(-15, ClosestInteger.closestInteger(String.valueOf(d)));
    }
                                    

}