package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Unique.
*/
class UniqueTest {
    @Test
    void testUniqueShouldReturnSortedUniqueElements() {
        // Arrange
        List<Integer> inputList = new ArrayList<>(Arrays.asList(5, 3, 5, 2, 3, 3, 9, 0, 123));
        List<Integer> expectedList = new ArrayList<>(Arrays.asList(0, 2, 3, 5, 9, 123));

        // Act
        List<Integer> actualList = Unique.unique(inputList);

        // Assert
        assertEquals(expectedList, actualList);
    }
    
    @Test
        public void testNothing(){
            Unique s = new Unique();
            }
                                    
}