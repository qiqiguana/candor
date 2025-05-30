package oracle1;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of DoubleTheDifference.
*/
class DoubleTheDifferenceTest {
    @Test
    void testDoubleTheDifference_EmptyList_ReturnsZero() {
        List<Object> lst = new ArrayList<>();
        int result = DoubleTheDifference.doubleTheDifference(lst);
        assertEquals(0, result);
    }
    
    @Test
        void testNothing(){
            DoubleTheDifference s = new DoubleTheDifference();
            }
    @Test
    public void testEmptyList() {
    	List<Object> input = new ArrayList<>();
    	int expected = 0;
    	int actual = DoubleTheDifference.doubleTheDifference(input);
    	assertEquals(expected, actual);
    }
    @Test
    void test_doubleTheDifference_EmptyList() {
        List<Object> input = new ArrayList<>();
        int result = DoubleTheDifference.doubleTheDifference(input);
        assertEquals(0, result);
    }
    @Test
    void testNothing1(){
        assertEquals(0, DoubleTheDifference.doubleTheDifference(new ArrayList<>()));
    }
    @Test
    void test_doubleTheDifference_SingleElement_OddPositive_1() {
        List<Object> input = Arrays.asList(3);
        int result = DoubleTheDifference.doubleTheDifference(input);
        assertEquals(9, result);
    }
    @Test
    void test_doubleTheDifference_MultipleElements_OddPositive() {
        List<Object> input = Arrays.asList(1, 3, 5);
        int result = DoubleTheDifference.doubleTheDifference(input);
        assertEquals(35, result);
    }
                                    
}