package original;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Intersperse.
*/
class IntersperseTest {
    @Test
    void testIntersperse_DelimiterAtTheEnd() {
        // Arrange
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        int delimiter = 4;
        
        // Act
        List<Object> result = Intersperse.intersperse(numbers, delimiter);
        
        // Assert
        assertEquals(delimiter, result.get(result.size() - 2));
    }
}