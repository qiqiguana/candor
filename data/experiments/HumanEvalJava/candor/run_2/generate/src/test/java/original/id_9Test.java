package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RollingMax.
*/
class RollingMaxTest {
    @Test
    void testRollingMax_withIncreasingNumbers_ReturnsCorrectList() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        assertEquals(expected, RollingMax.rollingMax(numbers));
    }
    
    @Test
        public void testNothing(){
            RollingMax s = new RollingMax();
            }
    @Test
    public void testEmptyList() {
        List<Object> numbers = new ArrayList<>();
        assertEquals(new ArrayList<>(), RollingMax.rollingMax(numbers));
    }
    @Test
    public void testSingleElementList() {
        List<Object> numbers = Arrays.asList(1);
        assertEquals(Arrays.asList(1), RollingMax.rollingMax(numbers));
    }
    @Test
    public void testIncreasingList() {
        List<Object> numbers = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), RollingMax.rollingMax(numbers));
    }
    @Test
    public void testDecreasingList() {
        List<Object> numbers = Arrays.asList(5, 4, 3, 2, 1);
        assertEquals(Arrays.asList(5, 5, 5, 5, 5), RollingMax.rollingMax(numbers));
    }
    @Test
    public void testMixedList() {
        List<Object> numbers = Arrays.asList(1, 3, 2, 4, 3);
        assertEquals(Arrays.asList(1, 3, 3, 4, 4), RollingMax.rollingMax(numbers));
    }
    @Test
    public void testListWithNonIntegerElements() {
        List<Object> numbers = Arrays.asList(1, 'a', 3);
        assertThrows(IllegalArgumentException.class, () -> RollingMax.rollingMax(numbers));
    }
                                    
}