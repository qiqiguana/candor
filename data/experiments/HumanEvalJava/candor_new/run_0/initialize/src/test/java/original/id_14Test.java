package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AllPrefixes.
*/
class AllPrefixesTest {
    @Test
    void testAllPrefixes_EmptyString_ReturnsEmptyList() {
        // Arrange
        String input = "";

        // Act
        List<Object> result = AllPrefixes.allPrefixes(input);

        // Assert
        assertTrue(result.isEmpty());
    }
}