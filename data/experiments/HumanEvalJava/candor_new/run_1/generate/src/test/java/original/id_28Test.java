package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Concatenate.
*/
class ConcatenateTest {
    @Test
    void testConcatenateEmptyList() {
        List<Object> strings = new ArrayList<>();
        String result = Concatenate.concatenate(strings);
        assertEquals("", result);
    }
    
    @Test
        public void testNothing(){
            Concatenate s = new Concatenate();
            }
    @Test
    public void testConcatenateWithSingleString() {
        java.util.List<java.lang.Object> input = java.util.Arrays.asList("a");
        String expected_result = "a";
        String actual_result = original.Concatenate.concatenate(input);
        assertEquals(expected_result, actual_result);
    }
                                    
}