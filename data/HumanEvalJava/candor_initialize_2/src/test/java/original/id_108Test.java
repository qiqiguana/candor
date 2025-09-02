package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountNums.
*/
class CountNumsTest {

    @Test
    void testCountNums_NegativeAndPositiveNumbers_ReturnsCorrectCount() {
        List<Object> arr = new ArrayList<>();
        arr.add(-1);
        arr.add(11);
        arr.add(-11);
        int result = CountNums.countNums(arr);
        assertEquals(1, result);
    }
    
    @Test
        public void testNothing(){
            CountNums s = new CountNums();
            }
                                    
}