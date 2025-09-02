package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea.
*/
class TriangleAreaTest {
    @Test
    void testTriangleArea() {
        Double result = TriangleArea.triangleArea(5, 3);
        assertEquals(7.5, result, "Unexpected area value");
    }
    
    @Test
        public void testNothing(){
            TriangleArea s = new TriangleArea();
            }
                                    
}