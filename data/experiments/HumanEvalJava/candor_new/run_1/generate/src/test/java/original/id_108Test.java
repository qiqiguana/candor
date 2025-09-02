package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountNums.
*/
class CountNumsTest {
    @Test
    void testCountNumsWithPositiveNumbers() {
        List<Object> numbers = List.of(1, 2, 3);
        int result = CountNums.countNums(numbers);
        assertEquals(3, result);
    }
    
    @Test
        public void testNothing(){
            CountNums s = new CountNums();
            }
                                    
}