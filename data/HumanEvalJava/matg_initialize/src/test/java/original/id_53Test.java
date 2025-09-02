package original;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Add.
*/
class AddTest {
    @Test
    void testAddTwoPositiveNumbers() {
        assertEquals(5, Add.add(2, 3));
    }
}