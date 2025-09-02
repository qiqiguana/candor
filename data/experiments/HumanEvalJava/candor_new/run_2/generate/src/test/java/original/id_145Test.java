package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

/**
* Test class of OrderByPoints.
*/
class OrderByPointsTest {

@Test
void testOrderByPoints_SimpleList_ReturnsSorted() {
    // Arrange
    List<Object> input = Arrays.asList(1, 11, -1, -11, -12);
    List<Object> expected = Arrays.asList(-1, -11, 1, -12, 11);

    // Act
    List<Object> result = OrderByPoints.orderByPoints(input);

    // Assert
    assertIterableEquals(expected, result);
}
}
