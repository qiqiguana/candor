package oracle1;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IncrList.
*/
class IncrListTest {
    @Test
    void test_incrList_EmptyArray_ReturnEmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> result = IncrList.incrList(input);
        assertEquals(0, result.size());
    }
    
    @Test
     void testNothing(){
         IncrList s = new IncrList();
         }
    @Test
    public void testIncrListWithEmptyList() {
        List<Object> input = new ArrayList<>();
        assertEquals(new ArrayList<>(), IncrList.incrList(input));
    }
    @Test
    public void testIncrListWithNullInput() {
        assertEquals(Collections.emptyList(), IncrList.incrList(null));
    }
    @Test
    public void testIncrListWithSingleElement() {
        List<Object> input = Arrays.asList(1);
        assertEquals(Arrays.asList((Object)2), IncrList.incrList(input));
    }
                                  
}