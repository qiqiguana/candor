package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Add1.
*/
class Add1Test {

    @Test
    void test_add_even_elements_at_odd_indices() {
        List<Integer> lst = List.of(4, 88);
        int result = Add1.add(lst);
        assertEquals(88, result);
    }
    
    @Test
        public void testNothing(){
            Add1 s = new Add1();
            }
    @Test
    public void testAdd1_with_even_elements_at_odd_indices() {
        List<Integer> input = List.of(4, 2, 6, 7);
        int expected_result = 2;
        assertEquals(expected_result, Add1.add(input));
    }
                                    
}