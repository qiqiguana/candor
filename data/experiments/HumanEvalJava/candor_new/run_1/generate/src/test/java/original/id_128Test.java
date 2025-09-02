package original;

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
    void testProdSignsWithNegativeNumbers() {
        List<Object> arr = List.of(-1, -2, 3);
        Integer result = ProdSigns.prodSigns(arr);
        assertEquals(6, result);
    }
    
    @Test
        public void testNothing(){
            ProdSigns s = new ProdSigns();
            }
    @Test
    public void testProdSignsNoInitialization() {
        assertDoesNotThrow(() -> ProdSigns.prodSigns(List.of()));
    }
    @Test
    public void testZeroValueInList2Fixed() {
        List<Object> list = Arrays.asList(1, 0);
        Object result = ProdSigns.prodSigns(list);
        assertEquals(Integer.valueOf(0), result);
    }
    @Test
    public void testProdSignsWithNonIntegerObjectsInArray() {
        List<Object> arr = Arrays.asList("a", 1, "b", 2);
        Integer result = ProdSigns.prodSigns(arr);
        assertEquals(3, (int) result);
    }
                                    
}