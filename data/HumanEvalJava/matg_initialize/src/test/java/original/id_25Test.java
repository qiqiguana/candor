package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Factorize.
*/
class FactorizeTest {
    @Test
    void testFactorize_SimpleCase() {
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(3);
        expected.add(3);
        List<Integer> actual = Factorize.factorize(18);
        assertEquals(expected, actual);
    }
}