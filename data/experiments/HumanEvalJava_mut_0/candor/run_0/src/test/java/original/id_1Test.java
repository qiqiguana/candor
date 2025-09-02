package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SeparateParenGroups.
*/
class SeparateParenGroupsTest {

    @Test
    void testSeparateParenGroups_EmptyString_ReturnsEmptyList() {
        // Arrange
        String parenString = "";
        List<String> expectedResult = new ArrayList<>();

        // Act
        List<String> actualResult = SeparateParenGroups.separateParenGroups(parenString);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}
