package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea1.
*/
class TriangleArea1Test {
    @Test
    void testTriangleAreaValidInput() {
        assertEquals(6.00, TriangleArea1.triangleArea(3, 4, 5));
    }
}