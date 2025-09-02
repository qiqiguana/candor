package original;

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
}