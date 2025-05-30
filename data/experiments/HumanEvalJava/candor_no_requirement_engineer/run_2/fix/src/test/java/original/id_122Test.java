package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of AddElements.
*/
class AddElementsTest {
    @Test
    void test_addElements_withAtMostTwoDigits() {
        List<Integer> arr = List.of(111, 21, 3, 4000, 5, 6, 7, 8, 9);
        int k = 4;
        assertEquals(24, AddElements.addElements(arr, k));
    }
    
    @Test
        void testNothing(){
            AddElements s = new AddElements();
            }
    @Test
    public void testAddElementsWithKLessThanLengthOfArrayAndAllElementsHaveAtMostTwoDigits1() {
        List<Integer> arr = Arrays.asList(10, 20, 30, 40, 50);
        int k = 3;
        assertEquals(60, AddElements.addElements(arr, k));
    }
                                    
}