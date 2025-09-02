package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea1.
*/
class TriangleArea1Test {
    @Test
    void testTriangleArea() {
        assertEquals(-1, TriangleArea1.triangleArea(1, 2, 10));
    }
}