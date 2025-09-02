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
    void testRollingMax_EmptyList_ReturnsEmptyList() {
        // Arrange
        List<Object> numbers = new ArrayList<>();

        // Act
        List<Object> result = RollingMax.rollingMax(numbers);

        // Assert
        assertTrue(result.isEmpty());
    }
}