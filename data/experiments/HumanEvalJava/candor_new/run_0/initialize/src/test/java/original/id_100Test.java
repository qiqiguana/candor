package original;

import java.util.ArrayList;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MakeAPile.
*/
class MakeAPileTest {
    @Test
    void test_makeAPile_WithOddNumber_ShouldReturnCorrectList() {
        // Arrange
        int n = 3;
        List<Integer> expected = new ArrayList<>(List.of(3, 5, 7));

        // Act
        List<Integer> result = MakeAPile.makeAPile(n);

        // Assert
        assertEquals(expected, result);
    }
}