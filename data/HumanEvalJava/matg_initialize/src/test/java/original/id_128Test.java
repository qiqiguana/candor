package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of ProdSigns.
*/
class ProdSignsTest {
    @Test
    void testProdSigns_EmptyList_ReturnsNull() {
        List<Object> input = List.of();
        Integer result = ProdSigns.prodSigns(input);
        assertNull(result);
    }
}