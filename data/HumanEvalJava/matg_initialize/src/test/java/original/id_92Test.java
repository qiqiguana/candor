package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AnyInt.
*/
class AnyIntTest {
    @Test
    void anyInt_Should_Return_True_When_One_Number_Is_Sum_Of_Other_Two() {
        // Arrange
        Number x = 5;
        Number y = 2;
        Number z = 7;

        // Act
        Boolean result = AnyInt.anyInt(x, y, z);

        // Assert
        assertTrue(result);
    }
}