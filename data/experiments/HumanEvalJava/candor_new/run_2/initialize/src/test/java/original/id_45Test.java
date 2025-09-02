package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea.
*/
class TriangleAreaTest {
    @Test
    void testTriangleArea() {
        assertEquals(7.5, TriangleArea.triangleArea(5, 3), "Expected triangleArea(5, 3) to return 7.5");
    }
}