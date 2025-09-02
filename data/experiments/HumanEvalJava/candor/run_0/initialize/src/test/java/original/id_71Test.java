package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea1.
*/
class TriangleArea1Test {

    @Test
    void testTriangleArea_WhenGivenValidSides_ReturnsCorrectArea() {
        double area = (double) TriangleArea1.triangleArea(3, 4, 5);
        assertEquals(6.00, area, 0.01);
    }
    @Test
    public void testValidTriangleFixed() {
        assertEquals(6.0, Math.round((double)original.TriangleArea1.triangleArea(3, 4, 5) * 100.0) / 100.0, 0.01);
    }
    @Test
    public void testInvalidTriangle() {
        assertEquals(-1, original.TriangleArea1.triangleArea(1, 2, 10));
    }
    @Test
    public void testEqualSidesFixed() {
        assertEquals(1.73, (double) original.TriangleArea1.triangleArea(2, 2, 2), 0.01);
    }
    @Test
    public void testZeroSides() {
        assertEquals(-1, original.TriangleArea1.triangleArea(0, 0, 0));
    }
    @Test
    public void testSmallNumbers() {
        assertEquals(-1, original.TriangleArea1.triangleArea(1, 2, 3));
    }
    @Test
    public void testValidTriangle() {
        Number result = TriangleArea1.triangleArea(3, 4, 5);
        assertEquals(6.00, result.doubleValue(), 0.01);
    }
    @Test
    public void testInvalidTriangleWithDecimalReturnType() {
        Number result = TriangleArea1.triangleArea(1, 2, 10);
        assertEquals(-1.0, result.doubleValue(), 0.01);
    }
    @Test
    public void testEquilateralTriangle() {
        Number result = TriangleArea1.triangleArea(2, 2, 2);
        assertEquals(1.73, result.doubleValue(), 0.01);
    }
    @Test
    public void testRightTriangle() {
        Number result = TriangleArea1.triangleArea(3, 4, 5);
        assertEquals(6.00, result.doubleValue(), 0.01);
    }
    @Test
    public void testIsoscelesTriangle() {
        Number result = TriangleArea1.triangleArea(2, 2, 3);
        assertEquals(1.98, result.doubleValue(), 0.01);
    }
    @Test
    public void testZeroSideLength() {
        Number result = TriangleArea1.triangleArea(0, 4, 5);
        assertEquals(-1.0, result.doubleValue(), 0.01);
    }
    @Test
    public void testValidTriangleWithRounding() {
        Number result = TriangleArea1.triangleArea(3, 4, 5);
        assertEquals(6.00, result.doubleValue(), 0.01);
    }
    @Test
    public void testEqualSides() {
    	Number result = TriangleArea1.triangleArea(2, 2, 2);
    	assertEquals(1.73, result.doubleValue(), 0.01);
    }
    @Test
    public void testZeroSide() {
    	Number result = TriangleArea1.triangleArea(0, 3, 4);
    	assertEquals(-1, result.intValue(), 0.01);
    }
    @Test
    public void testNegativeNumbers() {
    	Number result = TriangleArea1.triangleArea(-1, -2, -3);
    	assertEquals(-1, result.intValue(), 0.01);
    }
    @Test
    public void testDecimalNumbersCorrected() {
        Number result = TriangleArea1.triangleArea(3, 4, 5);
        assertEquals(6.0, result.doubleValue(), 0.01);
    }
}