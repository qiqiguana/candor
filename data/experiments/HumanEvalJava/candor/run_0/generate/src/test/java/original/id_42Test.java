package original;

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
    void testIncrList_emptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, IncrList.incrList(input));
    }
    
    @Test
        public void testNothing(){
            IncrList s = new IncrList();
            }
    @Test
    public void Test_IncrList_EmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void Test_IncrList_NullInput() {
        assertThrows(NullPointerException.class, () -> IncrList.incrList(null));
    }
    @Test
    public void Test_IncrList_SingleElement_1() {
        List<Object> input = Arrays.asList(1);
        List<Object> expected = Arrays.asList(2);
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void Test_IncrList_MultipleElements_1() {
        List<Object> input = new ArrayList<>() {{ add(1); add(2); add(3); }};
        List<Object> expected = new ArrayList<>() {{ add(2); add(3); add(4); }};
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void Test_IncrList_NonIntegerElement() {
        List<Object> input = Arrays.asList(1, "a", 3);
        List<Object> expected = Arrays.asList(2, 4);
        assertEquals(expected, IncrList.incrList(input));
    }
                                    
}