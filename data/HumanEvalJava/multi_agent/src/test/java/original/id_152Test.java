package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Compare.
*/
class CompareTest {
    @Test
    void compare_should_return_list_with_absolute_differences() {
        // Given
        List<Integer> game = new ArrayList<>(List.of(1, 2, 3, 4, 5, 1));
        List<Integer> guess = new ArrayList<>(List.of(1, 2, 3, 4, 2, -2));

        // When
        List<Integer> result = Compare.compare(game, guess);

        // Then
        assertEquals(List.of(0, 0, 0, 0, 3, 3), result);
    }
}