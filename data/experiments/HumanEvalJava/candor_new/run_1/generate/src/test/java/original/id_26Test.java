package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveDuplicates.
*/
class RemoveDuplicatesTest {

    @Test
    void testRemoveDuplicatesShouldReturnEmptyListWhenInputIsEmpty() {
        List<Object> input = new ArrayList<>();
        List<Object> expectedOutput = new ArrayList<>();
        assertEquals(expectedOutput, RemoveDuplicates.removeDuplicates(input));
    }
    
    @Test
        public void testNothing(){
            RemoveDuplicates s = new RemoveDuplicates();
            }
    @Test
    public void removeDuplicates_DuplicatesAndNoDuplicates() {
        // Arrange
        List<Object> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 2, 4));
        List<Object> expectedResult = new ArrayList<>(Arrays.asList(1, 3, 4));
        
        // Act
        List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
        
        // Assert
        assertEquals(expectedResult, result);
    }
                                    
}