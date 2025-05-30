package original;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Add1.
*/
class Add1Test {

    @Test
    void testAdd_EvenNumbersAtOddIndices_ReturnsSum() {
        // Arrange
        List<Integer> numbers = List.of(4, 2, 6, 7);
        int expectedResult = 2;

        // Act
        int result = Add1.add(numbers);

        // Assert
        assertEquals(expectedResult, result);
    }
    
    @Test
     void testNothing(){
         Add1 s = new Add1();
         }
    @Test
    public void happyPath_Test_with_even_numbers_at_odd_indices_1() {
        List<Integer> input = Arrays.asList(4, 2, 6, 7);
        int expected = 2;
        assertEquals(expected, Add1.add(input));
    }
                                  
}