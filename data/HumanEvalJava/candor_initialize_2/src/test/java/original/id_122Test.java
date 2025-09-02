package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of AddElements.
*/
class AddElementsTest {
    @Test
    void testAddElements_sumOfElementsWithAtMostTwoDigits() {
        List<Integer> arr = List.of(111, 21, 3, 4000, 5, 6, 7, 8, 9);
        int k = 4;
        int expectedSum = 24; // sum of 21 + 3
        assertEquals(expectedSum, AddElements.addElements(arr, k));
    }
    
    @Test
        public void testNothing(){
            AddElements s = new AddElements();
            }
                                    
}