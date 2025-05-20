package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestSmallestIntegers.
*/
class LargestSmallestIntegersTest {
    @Test
    void testLargestSmallestIntegers() {
        List<Object> lst = new ArrayList<>();
        lst.add(-1);
        lst.add(3);
        lst.add(-5);
        lst.add(6);
        lst.add(-2);
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(lst);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(-1, (int)result.get(0));
        assertEquals(3, (int)result.get(1));
    }
    @Test
    void testEmptyList() {
    List<Object> input = new ArrayList<>();
    List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(input);
    assertEquals(result.get(0), null);
    assertEquals(result.get(1), null);
    }
    @Test
    public void testLargestSmallestIntegers_EmptyList() {
        // Arrange
        List<Object> input = new ArrayList<>();
        // Act
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(input);
        // Assert
        assertEquals(null, result.get(0));
    }
    @Test
    public void testLargestSmallestIntegers_SingleElementList() {
        // Arrange
        List<Object> input = new ArrayList<>();
        input.add(5);
        // Act
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(input);
        // Assert
        assertEquals(null, result.get(0));
        assertEquals(5, (int)result.get(1));
    }
}