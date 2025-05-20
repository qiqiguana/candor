package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Search.
*/
class SearchTest {
    @Test
    void testSearch_ReturnsExpectedResult() {
        // Arrange
        List<Integer> lst = List.of(1, 2, 2, 3, 3, 3);

        // Act
        int result = Search.search(lst);

        // Assert
        assertEquals(3, result);
    }
}
