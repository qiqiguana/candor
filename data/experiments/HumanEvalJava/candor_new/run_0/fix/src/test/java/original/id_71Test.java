package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea1.
*/
class TriangleArea1Test {
    @Test
    void testValidTriangle() {
        assertEquals(6.00, (double) TriangleArea1.triangleArea(3, 4, 5), 0.01);
    }
    
    @Test
        public void testNothing(){
            TriangleArea1 s = new TriangleArea1();
            }
    @Test
    public void testTriangleWithInvalidSides() {
    	Number result = TriangleArea1.triangleArea(1, 2, 10);
    	assertEquals(-1, result.intValue());
    }
    @Test
    public void TestTriangleWithNonPositiveSideLengths() {
        Number result1 = TriangleArea1.triangleArea(0, 2, 10);
        assertEquals(-1.0, result1.doubleValue(), 0.01);
        
        Number result2 = TriangleArea1.triangleArea(-1, -2, -10);
        assertEquals(-1.0, result2.doubleValue(), 0.01);
    }
    @Test
    public void testTriangleArea_When_bPlusCIsLessThanOrEqualToA_ReturnMinusOne() {
        // Arrange
        int a = 4;
        int b = 1;
        int c = 2;
    
        // Act
        Number result = TriangleArea1.triangleArea(a, b, c);
    
        // Assert
        assertEquals(-1, result);
    }
                                    
}