package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Concatenate.
*/
class ConcatenateTest {
    @Test
    void testConcatenateEmptyList() {
        List<Object> emptyList = List.of();
        String result = Concatenate.concatenate(emptyList);
        assertEquals("", result);
    }
    
    @Test
        public void testNothing(){
            Concatenate s = new Concatenate();
            }
    @Test
    public void testConcatenateNullListFixed() {
        List<Object> input = null;
        String expected = "";
        try {
            Concatenate.concatenate(input);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals(expected,"");
        }
    }
    @Test
    public void Test_Concatenate_Empty_List() {
        List<Object> input = List.of();
        String expected = "";
        assertEquals(expected, Concatenate.concatenate(input));
    }
    @Test
    public void Test_Concatenate_Single_Element_List() {
        List<Object> input = List.of("a");
        String expected = "a";
        assertEquals(expected, Concatenate.concatenate(input));
    }
    @Test
    public void Test_Concatenate_Multiple_Element_List() {
        List<Object> input = List.of("a", "b", "c");
        String expected = "abc";
        assertEquals(expected, Concatenate.concatenate(input));
    }
                                    
}