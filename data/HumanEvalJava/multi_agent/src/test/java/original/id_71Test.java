package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea1.
*/
class TriangleArea1Test {

    @Test
    void testTriangleArea_InvalidInput_ReturnsMinusOne() {
        int a = 1;
        int b = 2;
        int c = 10;
        Number result = TriangleArea1.triangleArea(a, b, c);
        assertEquals(-1, result);
    }
}