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
}