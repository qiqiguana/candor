package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Add.
*/
class AddTest {
    public int add(int a, int b){
        return a + b;
    }
    @Test
    void testAddTwoPositiveNumbers() {
        // Arrange and Act
        int result = add(5, 3);
        // Assert
        assertEquals(8, result);
    }
}