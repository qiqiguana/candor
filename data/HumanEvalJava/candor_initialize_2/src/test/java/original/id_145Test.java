package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of OrderByPoints.
*/
class OrderByPointsTest {
    @Test
    void testOrderByPoints_EmptyList_ReturnsEmptyList() {
        // Arrange
        List<Object> nums = new ArrayList<>();
        
        // Act
        List<Object> result = OrderByPoints.orderByPoints(nums);
        
        // Assert
        assertEquals(0, result.size());
    }
    
    @Test
        public void testNothing(){
            OrderByPoints s = new OrderByPoints();
            }
    @Test
    public void testOrderByPointsWithEqualSumOfDigits() {
        List<Object> input = Arrays.asList(0, 10, 20);
        List<Object> expectedOutput = Arrays.asList(0, 10, 20);
        assertEquals(expectedOutput, OrderByPoints.orderByPoints(input));
    }
    @Test
    public void testOrderByPointsWithLargeNumbers() {
        List<Object> input = Arrays.asList(1234, 2345, 3456, 4567);
        List<Object> expectedOutput = Arrays.asList(1234, 2345, 3456, 4567);
        assertEquals(expectedOutput, OrderByPoints.orderByPoints(input));
    }
                                    
}