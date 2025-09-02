package original;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Add1.
*/
class Add1Test {
    @Test
    void test_add() {
        List<Integer> list = List.of(4, 2, 6, 7);
        assertEquals(2, Add1.add(list));
    }
    @Test
    public void testAddMethodWithNullInput() {
    	assertThrows(NullPointerException.class, () -> Add1.add(null));
    }
    @Test
    public void TestEmptyList1() {
        List<Integer> lst = new ArrayList<>();
        int result = Add1.add(lst);
        assertEquals(0, result);
    }
    @Test
    public void TestNullInput() {
        List<Integer> lst = null;
        assertThrows(NullPointerException.class, () -> Add1.add(lst));
    }
    @Test
    public void testAddWithEmptyList() {
        List<Integer> lst = new ArrayList<>();
        assertEquals(0, Add1.add(lst));
    }
}