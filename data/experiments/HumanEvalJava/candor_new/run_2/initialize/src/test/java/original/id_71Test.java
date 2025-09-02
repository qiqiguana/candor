package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea1.
*/
class TriangleArea1Test {
    @Test
    void testTriangleArea2() {
        assertEquals(-1, (int) TriangleArea1.triangleArea(3, 4, 12), "The area should be -1 for invalid triangle sides");
    }
}
