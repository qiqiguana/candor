package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ProdSigns.
*/
class ProdSignsTest {
    @Test
    void testProdSigns_EmptyList_ReturnNull() {
        List<Object> arr = new ArrayList<>();
        Integer result = ProdSigns.prodSigns(arr);
        assertNull(result);
    }
    
    @Test
        public void testNothing(){
            ProdSigns s = new ProdSigns();
            }
    @Test
    public void TestProdSigns_EmptyList_ReturnNull() {
        List<Object> input = new ArrayList<>();
        Integer result = ProdSigns.prodSigns(input);
        assertNull(result);
    }
    @Test
    public void test_ProdSigns_with_empty_list() {
        List<Object> input = new ArrayList<>();
        assertNull(ProdSigns.prodSigns(input));
    }
    @Test
    public void test_ProdSigns_with_multiple_positive_integers_2() {
        List<Object> input = Arrays.asList(1, 2, 3);
        assertEquals(Integer.valueOf(6), ProdSigns.prodSigns(input));
    }
    @Test
    public void test_ProdSigns_with_null_value_in_the_list() {
        List<Object> input = Arrays.asList(null, 1, 2);
        assertEquals(Integer.valueOf(3), ProdSigns.prodSigns(input));
    }
    @Test
    public void testProdSignsPositiveNumbers() {
      List<Object> arr = new ArrayList<>();
      arr.add(new Integer(1));
      arr.add(new Integer(2));
      assertEquals(3, ProdSigns.prodSigns(arr));
    }
    @Test
    public void testProdSignsNegativeNumbersCorrected() {
        List<Object> arr = new ArrayList<>();
        arr.add(new Integer(-1));
        arr.add(new Integer(-2));
        assertEquals(3, ProdSigns.prodSigns(arr));
    }
    @Test
    public void testProdSignsZero() {
      List<Object> arr = new ArrayList<>();
      arr.add(new Integer(0));
      arr.add(new Integer(2));
      assertEquals(0, ProdSigns.prodSigns(arr));
    }
                                    
}