package oracle1;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of ProdSigns.
*/
class ProdSignsTest {
    @Test
    void testProdSignsWithPositiveNumbers() {
        List<Object> arr = List.of(1, 2, 3);
        Integer result = ProdSigns.prodSigns(arr);
        assertEquals(Integer.valueOf(6), result);
    }
    
    @Test
        void testNothing(){
            ProdSigns s = new ProdSigns();
            }
    @Test
    void TestProdSigns_EmptyList_ReturnsNull() {
        java.util.List<java.lang.Object> arr = new java.util.ArrayList<>();
        org.junit.jupiter.api.Assertions.assertNull(oracle1.ProdSigns.prodSigns(arr));
    }
    @Test
    void TestProdSigns_EmptyList_ReturnsNull_1() {
        java.util.List<java.lang.Object> arr = new java.util.ArrayList<>();
        assertNull(oracle1.ProdSigns.prodSigns(arr));
    }
    @Test
    void TestProdSigns_SingleElementList_ReturnsCorrectResult_1() {
        List<Object> arr = List.of(5);
        assertEquals(5, ProdSigns.prodSigns(arr));
    }
    @Test
    void testProdSignsWithEmptyList() {
        assertNull(ProdSigns.prodSigns(List.of()));
    }
    @Test
    public void testEmptyArrayFixed() {
        List<Object> arr = new java.util.ArrayList<>();
        assertNull(oracle1.ProdSigns.prodSigns(arr));
    }
    @Test
    public void testSinglePositiveElement1() {
        List<Object> arr = Arrays.asList(5);
        Integer result = ProdSigns.prodSigns(arr);
        assertNotNull(result);
        assertEquals(5, (int) result);
    }
    @Test
    public void testSingleNegativeElementFixed2() {
        List<Object> arr = Arrays.asList(-3);
        assertEquals(-3, (int) ProdSigns.prodSigns(arr));
    }
    @Test
    public void testMultiplePositiveElements1() {
        List<Object> arr = Arrays.asList(1, 2, 3);
        org.junit.jupiter.api.Assertions.assertEquals((int)6, ProdSigns.prodSigns(arr));
    }
    @Test
    public void testMultipleNegativeElementsv2() {
        List<Object> arr = Arrays.asList(-1, -2, -3);
        assertEquals(Integer.valueOf(-6), ProdSigns.prodSigns(arr));
    }
    @Test
    public void testMultipleNegativeAndPositiveElementsCorrected() {
        java.util.List<java.lang.Object> arr = java.util.Arrays.asList(-1, 2, -3);
        assertEquals(6, (int) oracle1.ProdSigns.prodSigns(arr));
    }
    @Test
    public void testArrayWithZeroFixed1() {
        List<Object> arr = Arrays.asList(0);
        assertEquals((Integer) 0, ProdSigns.prodSigns(arr));
    }
    @Test
    public void testArrayWithZeroFixed2() {
        List<Object> arr = Arrays.asList(0);
        assertEquals((Integer) 0, ProdSigns.prodSigns(arr));
    }
    @Test
    public void testMultipleElementsWithZeros() {
        java.util.List<java.lang.Object> arr = java.util.Arrays.asList(0, 1, -2, 0);
        assertEquals(0, (int) oracle1.ProdSigns.prodSigns(arr));
    }
                                    
}