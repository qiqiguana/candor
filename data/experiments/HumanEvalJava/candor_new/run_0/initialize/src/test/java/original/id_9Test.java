package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RollingMax.
*/
class RollingMaxTest {

    @Test
    void testRollingMax_WithListContainingOnlyOneElement_ReturnsListWithSameElement() {
        // Arrange
        List<Object> numbers = new ArrayList<>();
        numbers.add(10);
        List<Object> expected = new ArrayList<>();
        expected.add(10);
        
        // Act
        List<Object> result = RollingMax.rollingMax(numbers);
        
        // Assert
        assertEquals(expected, result);
    }
}