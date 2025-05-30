package oracle1;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Unique.
*/
class UniqueTest {
    @Test
    void test_uniqueShouldSortAndReturnUniqueElements() {
        List<Integer> input = Arrays.asList(5, 3, 5, 2, 3, 3, 9, 0, 123);
        List<Integer> expectedOutput = Arrays.asList(0, 2, 3, 5, 9, 123);
        assertEquals(expectedOutput, Unique.unique(input));
    }
    
    @Test
        void testNothing(){
            Unique s = new Unique();
            }
    @Test
    public void testUniqueHappyPath() {
        List<Integer> input = Arrays.asList(5, 3, 5, 2, 3, 3, 9, 0, 123);
        List<Integer> expectedOutput = Arrays.asList(0, 2, 3, 5, 9, 123);
        assertEquals(expectedOutput, Unique.unique(input));
    }
    @Test
    public void testUniqueNoDuplicates() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> expectedOutput = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(expectedOutput, Unique.unique(input));
    }
    @Test
    public void testUniqueEmptyList() {
        List<Integer> input = new ArrayList<>();
        List<Integer> expectedOutput = new ArrayList<>();
        assertEquals(expectedOutput, Unique.unique(input));
    }
    @Test
    public void testUniqueNullInput() {
        assertThrows(NullPointerException.class, () -> Unique.unique(null));
    }
    @Test
    public void testUniqueLargeList() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> expectedOutput = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertEquals(expectedOutput, Unique.unique(input));
    }
    @Test
    public void testUniqueMinValue() {
        List<Integer> input = Arrays.asList(Integer.MIN_VALUE);
        List<Integer> expectedOutput = Arrays.asList(Integer.MIN_VALUE);
        assertEquals(expectedOutput, Unique.unique(input));
    }
    @Test
    public void testUniqueMaxValue() {
        List<Integer> input = Arrays.asList(Integer.MAX_VALUE);
        List<Integer> expectedOutput = Arrays.asList(Integer.MAX_VALUE);
        assertEquals(expectedOutput, Unique.unique(input));
    }
                                    
}