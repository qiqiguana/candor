package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of OrderByPoints.
*/
class OrderByPointsTest {
    @Test
    void testOrderByPoints_EmptyList_ReturnsEmptyList() {
        List<Object> nums = new ArrayList<>();
        List<Object> result = OrderByPoints.orderByPoints(nums);
        assertTrue(result.isEmpty());
    }
    
    @Test
        public void testNothing(){
            OrderByPoints s = new OrderByPoints();
            }
    @Test
    public void testOrderByPoints_HappyPath_PositiveNumbers() {
        List<Object> input = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Object> expectedOutput = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(expectedOutput, OrderByPoints.orderByPoints(input));
    }
    @Test
    public void testOrderByPoints_HappyPath_NegativeNumbers() {
      List<Object> input = new ArrayList<>(Arrays.asList(1, 11, -1, -11, -12));
      List<Object> expectedOutput = new ArrayList<>(Arrays.asList(-1, -11, 1, -12, 11));
      assertEquals(expectedOutput, OrderByPoints.orderByPoints(input));
    }
    @Test
    public void testOrderByPoints_HappyPath_SameDigitSum_Fixed() {
        List<Object> input = new ArrayList<>(Arrays.asList(1234, 423, 463, 145, 2, 423, 423, 53, 6, 37, 3457, 3, 56, 0, 46));
        List<Object> expectedOutput = new ArrayList<>(Arrays.asList(0, 2, 3, 6, 53, 423, 423, 423, 1234, 145, 37, 46, 56, 463, 3457));
        assertEquals(expectedOutput, OrderByPoints.orderByPoints(input));
    }
    @Test
    public void testOrderByPoints_SpecificFunctionality_DuplicateNumbers() {
        List<Object> input = new ArrayList<>(Arrays.asList(1, 11, -1, -11, -12));
        List<Object> expectedOutput = new ArrayList<>(Arrays.asList(-1, -11, 1, -12, 11));
        assertEquals(expectedOutput, OrderByPoints.orderByPoints(input));
    }
    @Test
    public void testOrderByPoints_HappyPath_SameDigitSum1() {
        List<Object> input = new ArrayList<>(Arrays.asList(1234, 423, 463, 145, 2, 423, 423, 53, 6, 37, 3457, 3, 56, 0, 46));
        List<Object> expectedOutput = new ArrayList<>(Arrays.asList(0, 2, 3, 6, 53, 423, 423, 423, 1234, 145, 37, 46, 56, 463, 3457));
        assertEquals(expectedOutput, OrderByPoints.orderByPoints(input));
    }
    @Test
    public void testOrderByPoints_HappyPath_SameDigitSum2() {
        List<Object> input = new ArrayList<>(Arrays.asList(1234, 423, 463, 145, 2, 423, 423, 53, 6, 37, 3457, 3, 56, 0, 46));
        List<Object> expectedOutput = new ArrayList<>(Arrays.asList(0, 2, 3, 6, 53, 423, 423, 423, 1234, 145, 37, 46, 56, 463, 3457));
        assertEquals(expectedOutput, OrderByPoints.orderByPoints(input));
    }
                                    
}