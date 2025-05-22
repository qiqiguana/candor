package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CanArrange.
*/
class CanArrangeTest {
    @Test
    void testCanArrange_Return3_WhenArrayIsSortedInAscendingOrderWithOneElementNotFollowingTheSequence() {
        List<Object> inputList = new ArrayList<>();
        inputList.add(1);
        inputList.add(2);
        inputList.add(4);
        inputList.add(3);
        inputList.add(5);
        int expectedIndex = 3;
        int actualIndex = CanArrange.canArrange(inputList);
        assertEquals(expectedIndex, actualIndex);
    }
    
    @Test
        public void testNothing(){
            CanArrange s = new CanArrange();
            }
    @Test
    public void testCanArrange_EmptyArray() {
        List<Object> arr = new ArrayList<>();
        assertEquals(-1, CanArrange.canArrange(arr));
    }
    @Test
    public void testCanArrange_SingleElementArray() {
        List<Object> arr = Arrays.asList(5);
        assertEquals(-1, CanArrange.canArrange(arr));
    }
    @Test
    public void testCanArrange_NoDecreasingElements_2() {
        List<Object> arr = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(-1, CanArrange.canArrange(arr));
    }
    @Test
    public void testCanArrange_SingleDecreasingElement_2() {
        List<Object> arr = Arrays.asList(1, 2, 3, 2, 5);
        assertEquals(3, CanArrange.canArrange(arr));
    }
    @Test
    public void testCanArrange_MultipleDecreasingElements() {
        List<Object> arr = Arrays.asList(1, 2, 3, 4, 3, 2);
        assertEquals(5, CanArrange.canArrange(arr));
    }
                                    
}