package original;

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
        assertEquals(6, result);
    }
    
    @Test
        public void testNothing(){
            ProdSigns s = new ProdSigns();
            }
    @Test
    public void testMultipleElementsWithDifferentSigns4Fixed() {
        java.util.List<java.lang.Object> arr = new java.util.ArrayList<>();
        arr.add(1);
        arr.add(-2);
        arr.add(3);
        arr.add(0);
        Integer result = ProdSigns.prodSigns(arr);
        assertNotNull(result);
        assertEquals(0, (int)result);
    }
    @Test
    public void testProdSignsWithEmptyListFixed2() {
        java.util.List<java.lang.Object> arr = new java.util.ArrayList<>();
        Object result = original.ProdSigns.prodSigns(arr);
        assertNull(result);
    }
                                    
}