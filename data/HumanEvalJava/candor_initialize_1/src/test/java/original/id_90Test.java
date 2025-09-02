package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of NextSmallest.
*/
class NextSmallestTest {

    @Test
    void testNextSmallest_EmptyList_ReturnNull() {
        List<Object> lst = new ArrayList<>();
        Integer result = NextSmallest.nextSmallest(lst);
        assertNull(result);
    }
    
    @Test
        public void testNothing(){
            NextSmallest s = new NextSmallest();
            }
    @Test
    public void testNextSmallestClass() {
        assertNotNull(NextSmallest.class);
    }
    @Test
    public void testNextSmallestNullList() {
        List<Object> lst = new ArrayList<>();
        assertNull(NextSmallest.nextSmallest(lst));
    }
    @Test
    public void testNextSmallestSingleElementList() {
        List<Object> lst = new ArrayList<>();
        lst.add(1);
        assertNull(NextSmallest.nextSmallest(lst));
    }
    @Test
    public void testNextSmallestMultiElementListDistinctSmallest() {
        List<Object> lst = new ArrayList<>();
        lst.add(5);
        lst.add(1);
        assertNotNull(NextSmallest.nextSmallest(lst));
    }
    @Test
    public void testNextSmallestNegativeNumbers() {
        List<Object> lst = new ArrayList<>();
        lst.add(-5);
        lst.add(-1);
        assertEquals(-1, (int) NextSmallest.nextSmallest(lst));
    }
    @Test
    public void testNextSmallestDuplicateElementsFixed() {
        List<Object> lst = new ArrayList<>();
        lst.add(5);
        lst.add(1);
        lst.add(5);
        assertNotNull(NextSmallest.nextSmallest(lst));
    }
    @Test
    public void testNextSmallestLargeList() {
        List<Object> lst = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            lst.add(i);
        }
        assertEquals(1, (int) NextSmallest.nextSmallest(lst));
    }
    @Test
    public void testNextSmallestNegativeNumbersFixed1() {
        java.util.List<java.lang.Object> input = new java.util.ArrayList<>(java.util.Arrays.asList(-35, -34, -33));
        Integer expected = -34;
        assertEquals(expected, original.NextSmallest.nextSmallest(input));
    }
                                    
}