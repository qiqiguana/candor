package oracle1;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Concatenate.
*/
class ConcatenateTest {
    @Test
    void testConcatenateEmptyList() {
        List<Object> list = List.of();
        String result = Concatenate.concatenate(list);
        assertEquals("", result);
    }
    
    @Test
        void testNothing(){
            Concatenate s = new Concatenate();
            }
    @Test
    public void testNullInput() {
    	List<Object> input = null;
    	assertThrows(IllegalArgumentException.class, () -> Concatenate.concatenate(input));
    }
    @Test
    public void testEmptyListFixed() {
        java.util.List<java.lang.Object> input = new java.util.ArrayList<>();
        String expected = "";
        org.junit.jupiter.api.Assertions.assertEquals(expected, oracle1.Concatenate.concatenate(input));
    }
    @Test
    public void testSingleElementListConcatenation() {
        List<Object> input = List.of("a");
        String expected = "a";
        assertEquals(expected, Concatenate.concatenate(input));
    }
    @Test
    public void testConcatenateMultipleElements2() {
        List<Object> input = Arrays.asList("a", "b", "c");
        String expected = "abc";
        assertEquals(expected, Concatenate.concatenate(input));
    }
                                    
}