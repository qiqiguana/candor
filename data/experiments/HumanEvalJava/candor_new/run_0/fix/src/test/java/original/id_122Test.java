package original;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AddElements.
*/
class AddElementsTest {
    @Test
    void testAddElements() {
        List<Integer> arr = new ArrayList<>();
        arr.add(111);
        arr.add(121);
        arr.add(3);
        arr.add(4000);
        arr.add(5);
        arr.add(6);
        int result = AddElements.addElements(arr, 2);
        assertEquals(0, result);
    }
    
    @Test
        public void testNothing(){
            AddElements s = new AddElements();
            }
    @Test
    public void testAddElements_KEqualToArrayLength_Fixed3() {
        List<Integer> arr = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 5));
        int k = 5;
        assertEquals(105, AddElements.addElements(arr, k));
    }
                                    
}