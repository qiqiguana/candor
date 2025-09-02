package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveDuplicates.
*/
class RemoveDuplicatesTest {
    @Test
    void testRemoveDuplicatesShouldReturnListWithNoDuplicates() {
        // Arrange
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(2);
        numbers.add(4);

        // Act
        List<Object> result = RemoveDuplicates.removeDuplicates(numbers);

        // Assert
        assertEquals(3, result.size());
    }
    
    @Test
        public void testNothing(){
            RemoveDuplicates s = new RemoveDuplicates();
            }
                                    
}