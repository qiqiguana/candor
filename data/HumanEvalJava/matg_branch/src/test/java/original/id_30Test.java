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
    public void testGetPositive_PositiveNumbers() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertEquals(expected, GetPositive.getPositive(input));
    }
    @Test
    public void testGetPositive_NegativeAndZeroNumbers() {
        List<Object> input = new ArrayList<>();
        input.add(-1);
        input.add(-2);
        input.add(0);
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, GetPositive.getPositive(input));
    }
    @Test
    public void testGetPositive_EmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, GetPositive.getPositive(input));
    }
    @Test
    public void testGetPositive_MixedNumbers() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        input.add(-2);
        input.add(3);
        input.add(-4);
        input.add(5);
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        expected.add(3);
        expected.add(5);
        assertEquals(expected, GetPositive.getPositive(input));
    }
    @Test
    public void testGetPositive_NonIntegerValues() {
        List<Object> input = new ArrayList<>();
        input.add(1.2);
        input.add("a");
        input.add(true);
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, GetPositive.getPositive(input));
    }
                                    
}