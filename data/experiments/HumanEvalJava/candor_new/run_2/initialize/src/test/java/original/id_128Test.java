package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ProdSigns.
*/
class ProdSignsTest {
    @Test
    void testProdSignsWithEmptyList() {
        List<Object> list = new ArrayList<>();
        assertNull(ProdSigns.prodSigns(list));
    }
}