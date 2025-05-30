package oracle1;

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
    void testRollingMax_ReturnsCorrectResult() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(2);
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(3);
        expected.add(3);
        expected.add(4);
        expected.add(4);
        assertEquals(expected, RollingMax.rollingMax(numbers));
    }
    
    @Test
        void testNothing(){
            RollingMax s = new RollingMax();
            }
    @Test
    public void RollingMax_EmptyList_ReturnsEmptyList() {
        List<Object> numbers = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, RollingMax.rollingMax(numbers));
    }
    @Test
    public void RollingMax_MultipleElementsList_ReturnsCorrectRollingMax() {
        List<Object> numbers = Arrays.asList(1, 2, 3, 2, 3, 4, 2);
        List<Object> expected = Arrays.asList(1, 2, 3, 3, 3, 4, 4);
        assertEquals(expected, RollingMax.rollingMax(numbers));
    }
    @Test
    public void RollingMax_ListWithNonIntegerValues_ThrowsIllegalArgumentException1() {
        List<Object> numbers = Arrays.asList(1, "a", 3);
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                RollingMax.rollingMax(numbers);
            } catch (IllegalArgumentException e) {
                assertEquals("Invalid number type", e.getMessage());
                throw e;
            }
        });
    }
                                    
}