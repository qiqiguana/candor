package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ProdSigns.
*/
class ProdSignsTest {
    @Test
    void testProdSigns_EmptyList_ReturnNull_1() {
        List<Object> arr = new ArrayList<>();
        Integer result = ProdSigns.prodSigns(arr);
        assertNull(result);
    }
    
    @Test
        public void testNothing(){
            ProdSigns s = new ProdSigns();
            }
    @Test
    public void testEmptyArray() {
    	List<Object> arr = new ArrayList<>();
    	assertNull(ProdSigns.prodSigns(arr));
    }
    @Test
    public void testProdSignsWithEmptyArray() {
        List<Object> arr = new ArrayList<>();
        assertNull(ProdSigns.prodSigns(arr));
    }
    @Test
    public void testProdSignsWithEmptyList() {
        List<Object> arr = new ArrayList<>();
        assertNull(ProdSigns.prodSigns(arr));
    }
                                    
}