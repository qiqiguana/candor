package original;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetPositive.
*/
class GetPositiveTest {

    @Test
    void testGetPositiveShouldReturnOnlyPositiveNumbers() {
        List<Object> input = new ArrayList<>();
        input.add(-1);
        input.add(2);
        input.add(-4);
        input.add(5);
        input.add(6);
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(5);
        expected.add(6);
        assertEquals(expected, GetPositive.getPositive(input));
    }
    
    @Test
        public void testNothing(){
            GetPositive s = new GetPositive();
            }
    @Test
    public void testGetPositiveWithEmptyList() {
    	List<Object> result = GetPositive.getPositive(new ArrayList<>());
    	assertTrue(result.isEmpty());
    }
    @Test
    public void testGetPositiveWithNullList() {
    	assertThrows(NullPointerException.class, () -> GetPositive.getPositive(null));
    }
    @Test
    public void testGetPositiveWithNonIntegerValues() {
    	List<Object> input = Arrays.asList("a", 1, "b", 2, "c");
    	List<Object> expected = Arrays.asList(1, 2);
    	assertEquals(expected, GetPositive.getPositive(input));
    }
                                    

}