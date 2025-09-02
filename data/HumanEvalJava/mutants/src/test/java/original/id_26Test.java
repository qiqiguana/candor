package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveDuplicates.
*/
class RemoveDuplicatesTest {
    @Test
    void testRemoveDuplicates_singleElementList() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0));
    }
    
    @Test
        public void testNothing(){
            RemoveDuplicates s = new RemoveDuplicates();
            }
    @Test
    public void testRemoveDuplicatesWithEmptyList() {
    	List<Object> numbers = new ArrayList<>();
    	List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
    	assertEquals(0, result.size());
    }
    @Test
    public void testRemoveDuplicatesWithSingleElementList() {
    	List<Object> numbers = new ArrayList<>();
    	numbers.add(1);
    	List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
    	assertEquals(1, result.size());
    	assertEquals(1, result.get(0));
    }
    @Test
    public void testRemoveDuplicatesWithNoDuplicates() {
    	List<Object> numbers = new ArrayList<>();
    	numbers.add(1);
    	numbers.add(2);
    	numbers.add(3);
    	numbers.add(4);
    	List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
    	assertEquals(4, result.size());
    	assertEquals(1, result.get(0));
    	assertEquals(2, result.get(1));
    	assertEquals(3, result.get(2));
    	assertEquals(4, result.get(3));
    }
    @Test
    public void testRemoveDuplicatesWithAllDuplicates() {
    	List<Object> numbers = new ArrayList<>();
    	numbers.add(1);
    	numbers.add(1);
    	numbers.add(1);
    	numbers.add(1);
    	List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
    	assertEquals(0, result.size());
    }
    @Test
    public void testRemoveDuplicatesWithMixedElements() {
    	List<Object> numbers = new ArrayList<>();
    	numbers.add(1);
    	numbers.add(2);
    	numbers.add(3);
    	numbers.add(4);
    	numbers.add(5);
    	numbers.add(6);
    	List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
    	assertEquals(6, result.size());
    }
    @Test
    public void testRemoveDuplicatesWithNullElements() {
    	List<Object> numbers = new ArrayList<>();
    	numbers.add(null);
    	numbers.add(1);
    	numbers.add(2);
    	numbers.add(null);
    	numbers.add(3);
    	numbers.add(4);
    	List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
    	assertEquals(4, result.size());
    }
                                    
}