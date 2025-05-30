package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea.
*/
class TriangleAreaTest {
    @Test
    void testTriangleArea()
    {
        // Arrange and Act
        Double result = TriangleArea.triangleArea(5, 3);
        // Assert
        assertEquals(7.5, result, 0.1);
    }
    
    @Test
        void testNothing(){
            TriangleArea s = new TriangleArea();
            }
    @Test
    public void testTriangleArea_ValidInput() {
        Double result = TriangleArea.triangleArea(5, 3);
        assertEquals(7.5, result, 0.01);
    }
    @Test
    public void testTriangleArea_AnotherValidInput() {
        Double result = TriangleArea.triangleArea(2, 2);
        assertEquals(2.0, result, 0.01);
    }
    @Test
    public void testTriangleArea_ExtremelySmallInput() {
        Double result = TriangleArea.triangleArea(1, 2);
        assertEquals(1.0, result, 0.01);
    }
    @Test
    public void testTriangleArea_InvalidInput_NegativeValue() {
        Double result = TriangleArea.triangleArea(-5, 3);
        assertNotNull(result);
    }
                                    
}