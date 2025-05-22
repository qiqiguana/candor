package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of ProdSigns.
*/
class ProdSignsTest {
    @Test
    void testProdSigns_EmptyList_ReturnsNull() {
        List<Object> input = List.of();
        Integer result = ProdSigns.prodSigns(input);
        assertNull(result);
    }
    
    @Test
        public void testNothing(){
            ProdSigns s = new ProdSigns();
            }
    @Test
    public void testProdSignsWithNull() {
        List<Object> arr = null;
        assertThrows(NullPointerException.class, () -> ProdSigns.prodSigns(arr));
    }
    @Test
    public void testProdSignsWithEmptyList() {
        List<Object> arr = new ArrayList<>();
        assertNull(ProdSigns.prodSigns(arr));
    }
    @Test
    public void testProdSignsWithPositiveNumbers() {
        List<Object> arr = java.util.Arrays.asList(1, 2, 3);
        assertEquals((Integer) 6, ProdSigns.prodSigns(arr));
    }
    @Test
    public void testProdSignsWithNegativeNumbers() {
        List<Object> arr = Arrays.asList(-1, -2, -3);
        assertEquals((Integer) (-6), ProdSigns.prodSigns(arr));
    }
    @Test
    public void testProdSignsWithMixedNumbers() {
        List<Object> arr = Arrays.asList(1, -2, 3);
        assertEquals((Integer) (-6), ProdSigns.prodSigns(arr));
    }
    @Test
    public void testProdSignsWithZero() {
        List<Object> arr = Arrays.asList(1, 0);
        assertEquals((Integer) 0, ProdSigns.prodSigns(arr));
    }
                                    
}