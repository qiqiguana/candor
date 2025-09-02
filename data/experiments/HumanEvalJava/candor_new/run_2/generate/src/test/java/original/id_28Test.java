package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Concatenate.
*/
class ConcatenateTest {
    @Test
    void testConcatenate() {
        // TODO implement the test
    }
    
    @Test
        public void testNothing(){
            Concatenate s = new Concatenate();
            }
    @Test
    public void testConcatenateNullInput() {
        List<Object> input = null;
        assertThrows(NullPointerException.class, () -> Concatenate.concatenate(input));
    }
    @Test
    public void test_concatenate_with_multiple_elements_list_2() {
    	List<Object> strings = List.of("x", "y", "z");
    	String expected = "xyz";
    	assertEquals(expected, Concatenate.concatenate(strings));
    }
                                    
}