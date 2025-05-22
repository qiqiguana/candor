package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Add1.
*/
class Add1Test {
    @Test
    void testAdd_SingleEvenElementAtOddIndex_ReturnsEvenElement() {
        List<Integer> lst = new ArrayList<>();
        lst.add(4);
        lst.add(2);
        assertEquals(2, Add1.add(lst));
    }
    
    @Test
        public void testNothing(){
            Add1 s = new Add1();
            }
    @Test
    public void testAdd_WithNullList() {
        assertThrows(NullPointerException.class, () -> Add1.add(null));
    }
    @Test
    public void testAdd_WithEvenElementsAtOddIndices() {
        List<Integer> lst = Arrays.asList(4, 2, 6, 7);
        assertEquals(2, Add1.add(lst));
    }
    @Test
    public void testAdd_WithNoEvenElementsAtOddIndices() {
        List<Integer> lst = Arrays.asList(4, 5, 6, 7);
        assertEquals(0, Add1.add(lst));
    }
    @Test
    public void testAdd_WithMultipleEvenElementsAtOddIndices_Fixed() {
        java.util.List<java.lang.Integer> lst = java.util.Arrays.asList(4, 88, 6, 7);
        org.junit.jupiter.api.Assertions.assertEquals(88, original.Add1.add(lst));
    }
    @Test
    public void testAdd_WithSingleElementList() {
        List<Integer> lst = Arrays.asList(4);
        assertEquals(0, Add1.add(lst));
    }
                                    
}