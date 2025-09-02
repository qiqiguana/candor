package original;

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
        List<Object> expectedOutput = new ArrayList<>();
        expectedOutput.add(2);
        expectedOutput.add(5);
        expectedOutput.add(6);
        assertEquals(expectedOutput, GetPositive.getPositive(input));
    }
    
    @Test
        public void testNothing(){
            GetPositive s = new GetPositive();
            }
    @Test
    public void testGetPositive_NonIntegerValue() {
        List<Object> input = new ArrayList<Object>() {{ add("String"); }};
        assertThrows(Exception.class, () -> GetPositive.getPositive(input));
    }
                                    
}