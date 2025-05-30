package original;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of NextSmallest.
*/
class NextSmallestTest {
    @Test
    void test_nextSmallest_withMultipleElements(){
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(2,NextSmallest.nextSmallest(list));
    }
    
    @Test
        public void testNothing(){
            NextSmallest s = new NextSmallest();
            }
    @Test
    public void NextSmallest_EmptyList_ReturnNull() {
        List<Object> input = new ArrayList<>();
        assertNull(NextSmallest.nextSmallest(input));
    }
    @Test
    public void NextSmallest_SingleElementList_ReturnNull() {
        List<Object> input = Arrays.asList(1);
        assertNull(NextSmallest.nextSmallest(input));
    }
    @Test
    public void NextSmallest_TwoDistinctElements_ReturnSecondSmallest() {
        List<Object> input = Arrays.asList(2, 1);
        assertEquals((Integer) 2, NextSmallest.nextSmallest(input));
    }
    @Test
    public void NextSmallest_DuplicateSmallestElements_ReturnNull() {
        List<Object> input = Arrays.asList(1, 1);
        assertEquals(null, NextSmallest.nextSmallest(input));
    }
    @Test
    public void NextSmallest_DuplicateElements_ReturnSecondSmallest1() {
        List<Object> input = Arrays.asList(1, 1, 1, 0);
        assertEquals((Integer) 1, NextSmallest.nextSmallest(input));
    }
    @Test
    public void NextSmallest_SingleElementList_ReturnsNull() {
        List<Object> lst = Arrays.asList(1);
        assertEquals(null, NextSmallest.nextSmallest(lst));
    }
    @Test
    public void NextSmallest_TwoEqualElements_ReturnsNull() {
        List<Object> lst = Arrays.asList(1, 1);
        assertEquals(null, NextSmallest.nextSmallest(lst));
    }
    @Test
    public void NextSmallest_SimpleList_ReturnsSecondSmallest() {
        List<Object> lst = Arrays.asList(1, 2, 3);
        assertEquals(2, NextSmallest.nextSmallest(lst));
    }
    @Test
    public void NextSmallest_SimpleList_ReturnsSecondSmallest_2() {
        List<Object> lst = Arrays.asList(3, 1, 2);
        assertEquals(2, NextSmallest.nextSmallest(lst));
    }
                                    
}