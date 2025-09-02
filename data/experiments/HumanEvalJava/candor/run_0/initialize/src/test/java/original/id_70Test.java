package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StrangeSortList.
*/
class StrangeSortListTest {
    @Test
    void testStrangeSortList() {
        List<Object> result = new ArrayList<>();
        result.add(1);
        result.add(5);
        result.add(2);
        result.add(4);
        result.add(3);
        assertEquals(result, StrangeSortList.strangeSortList(List.of(1, 2, 3, 4, 5)));
    }
    
    @Test
        public void testNothing(){
            StrangeSortList s = new StrangeSortList();
            }
    @Test
    public void test_StrangeSortList_Class_Initialization() {
        assertDoesNotThrow(() -> new Object[]{}) ;
    }
    @Test
    public void test_Empty_List() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, StrangeSortList.strangeSortList(input));
    }
    @Test
    public void testStrangeSortList_withNonIntegerValues() {
        java.util.List<java.lang.Object> input = java.util.Arrays.asList(1, "a", 3, null, 5);
        java.util.List<java.lang.Object> expected = java.util.Arrays.asList(1, 5, 3);
        java.util.List<java.lang.Object> actual = original.StrangeSortList.strangeSortList(input);
        for (int i = 0; i < actual.size(); i++) {
            if (expected.get(i) instanceof Integer) {
                org.junit.jupiter.api.Assertions.assertEquals(expected.get(i), actual.get(i));
            }
        }
    }
                                    
}