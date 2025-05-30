package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea.
*/
class TriangleAreaTest {
    @Test
    void testTriangleArea() {
        assertEquals(7.5, TriangleArea.triangleArea(5, 3), "Unexpected result for triangle area with sides 5 and height 3");
    }
    
    @Test
        public void testNothing(){
            TriangleArea s = new TriangleArea();
            }
                                    
}