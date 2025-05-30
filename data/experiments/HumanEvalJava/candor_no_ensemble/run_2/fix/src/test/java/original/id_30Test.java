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
    void testGetPositive() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(-1);
        numbers.add(2);
        numbers.add(-4);
        numbers.add(5);
        numbers.add(6);
        List<Object> result = GetPositive.getPositive(numbers);
        assertEquals(3, result.size());
    }
    
    @Test
        void testNothing(){
            GetPositive s = new GetPositive();
            }
    @Test
    public void testGetPositive_EmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, GetPositive.getPositive(input));
    }
    @Test
    public void testGetPositive_NullInput() {
        assertThrows(NullPointerException.class, () -> GetPositive.getPositive(null));
    }
    @Test
    public void testGetPositive_SinglePositiveElement() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        assertEquals(expected, GetPositive.getPositive(input));
    }
    @Test
    public void testGetPositive_NonIntegerElements() {
        List<Object> input = new ArrayList<>();
        input.add("a");
        input.add(2);
        input.add("b");
        input.add(4);
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(4);
        assertEquals(expected, GetPositive.getPositive(input));
    }
    @Test
    public void testGetPositive_LargeListPositiveNumbers() {
        List<Object> input = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            input.add(i);
        }
        List<Object> expected = new ArrayList<>(input);
        assertEquals(expected, GetPositive.getPositive(input));
    }
    @Test
    public void testGetPositive_ListOfObject1() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        input.add("a");
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        assertEquals(expected, GetPositive.getPositive(input));
    }
                                    

}