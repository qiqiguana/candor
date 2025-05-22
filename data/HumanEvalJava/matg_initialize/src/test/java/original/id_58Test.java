package original;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Common.
*/
class CommonTest {

    @Test
    void common_should_return_empty_list_when_second_list_is_empty() {
        // Arrange
        List<Integer> list1 = Arrays.asList(4, 3, 2, 8);
        List<Object> list2 = new ArrayList<>();

        // Act
        List<Object> result = Common.common(list1, list2);

        // Assert
        assertTrue(result.isEmpty());
    }
}