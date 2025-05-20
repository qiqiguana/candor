package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Add1.
*/
class Add1Test {

    @Test
    void test_add_even_elements_at_odd_indices() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(4);
        numbers.add(88);
        assertEquals(88, Add1.add(numbers));
    }
    @Test
    public void testAdd() {
        List<Integer> lst = List.of(4, 2, 6, 7);
        int result = Add1.add(lst);
        assertEquals(2, result);
    }
    @Test
    public void testAdd2() {
        List<Integer> lst = List.of(4, 88);
        int result = Add1.add(lst);
        assertEquals(88, result);
    }
    @Test
    public void testAdd3() {
        List<Integer> lst = List.of(4, 5, 6, 7, 2, 122);
        int result = Add1.add(lst);
        assertEquals(122, result);
    }
    @Test
    public void testAdd4() {
        List<Integer> lst = List.of(4, 0, 6, 7);
        int result = Add1.add(lst);
        assertEquals(0, result);
    }
    @Test
    public void testAdd5() {
        List<Integer> lst = List.of(4, 4, 6, 8);
        int result = Add1.add(lst);
        assertEquals(12, result);
    }
    @Test
    public void testAddEmptyList() {
        List<Integer> lst = List.of();
        int result = Add1.add(lst);
        assertEquals(0, result);
    }
    @Test
    public void testAddNull() {
        try {
            Add1.add(null);
            assert false;
        } catch (NullPointerException e) {
            assert true;
        }
    }
    public class TestAdd {
        @Test
        public void testAddEvenAtOddIndices() {
            List<Integer> lst = new ArrayList<>();
            lst.add(4);
            lst.add(2);
            lst.add(6);
            lst.add(7);
            int result = Add1.add(lst);
            assertEquals(2, result);
        }
    }
    public void testAdd1ClassInitialization() {
    	// Verify class initialization
    	try {
    		Add1.class.getConstructor();
    	} catch (NoSuchMethodException e) {
    		fail("Unexpected exception");
    	}
    }
}