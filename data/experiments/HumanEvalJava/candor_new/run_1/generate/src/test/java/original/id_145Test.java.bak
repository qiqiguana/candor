package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of OrderByPoints.
*/
class OrderByPointsTest {
    @Test
    void test_order_by_points_should_sort_numbers_based_on_digit_sum() {
        // Arrange
        List<Object> numbers = new ArrayList<>(List.of(1, 11, -1, -11, -12));

        // Act
        List<Object> sortedNumbers = OrderByPoints.orderByPoints(numbers);

        // Assert
        assertEquals(List.of(-1, -11, 1, -12, 11), sortedNumbers);
    }
}
