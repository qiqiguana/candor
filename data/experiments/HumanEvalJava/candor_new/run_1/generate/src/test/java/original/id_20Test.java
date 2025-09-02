package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of FindClosestElements.
*/
class FindClosestElementsTest {

    @Test
    void testFindClosestElements_SimpleList_ReturnsCorrectResult() {
        // Arrange
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(2.0);
        numbers.add(3.9);
        numbers.add(4.0);
        numbers.add(5.0);
        numbers.add(2.2);

        List<Double> expected = new ArrayList<>();
        expected.add(3.9);
        expected.add(4.0);

        // Act
        List<Double> result = FindClosestElements.findClosestElements(numbers);

        // Assert
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            FindClosestElements s = new FindClosestElements();
            }
                                    
}