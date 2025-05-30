package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea.
*/
class TriangleAreaTest {
    @Test
    void testTriangleArea() {
        assertEquals(7.5, TriangleArea.triangleArea(5, 3), 0.001);
    }
    
    @Test
        public void testNothing(){
            TriangleArea s = new TriangleArea();
            }
    @Test
    public void testTriangleArea_PositveTest_ValidInputs() {
        Double[] expectedResults = {7.5, 2.0, 40.0};
        int[][] inputs = {{5, 3}, {2, 2}, {10, 8}};
        for (int i = 0; i < inputs.length; i++) {
            Double result = TriangleArea.triangleArea(inputs[i][0], inputs[i][1]);
            assertEquals(expectedResults[i], result);
        }
    }
    @Test
    public void testTriangleArea_EdgeCase_MinimumInputValues() {
        Double expectedResult = 0.5;
        int[] input = {1, 1};
        Double result = TriangleArea.triangleArea(input[0], input[1]);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testTriangleArea_NegativeTest_InvalidInputs_ZeroValues() {
    	int[][] inputs = {{0, 3}, {5, 0}};
    	for (int[] input : inputs) {
    		Double result = TriangleArea.triangleArea(input[0], input[1]);
    		assertEquals(0.0, result, 0);
    	}
    }
                                    
}