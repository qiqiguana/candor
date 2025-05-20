package original;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FindClosestElements.
*/
class FindClosestElementsTest {
    @Test
    void test_findClosestElements_list_with_close_numbers() {
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(2.0);
        numbers.add(3.9);
        numbers.add(4.0);
        numbers.add(5.0);
        numbers.add(2.2);

        List<Double> expected = new ArrayList<>();
        expected.add(3.9);
        expected.add(4.0);

        assertEquals(expected, FindClosestElements.findClosestElements(numbers));
    }
    
    @Test
        void testNothing(){
            FindClosestElements s = new FindClosestElements();
            }
    @Test
    public void TestFindClosestElements_EmptyList() {
        List<Double> numbers = new ArrayList<>();
        assertEquals(Collections.emptyList(), FindClosestElements.findClosestElements(numbers));
    }
    @Test
    public void TestFindClosestElements_LargeNumbers_2() {
        List<Double> numbers = new ArrayList<>(Arrays.asList(2000000.0, 3000000.0));
        assertEquals(Arrays.asList(2000000.0, 3000000.0), FindClosestElements.findClosestElements(numbers));
    }
                                    
}