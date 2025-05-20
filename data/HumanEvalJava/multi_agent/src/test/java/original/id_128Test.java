package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ProdSigns.
*/
class ProdSignsTest {
    @Test
    void testArrayListInitialization() {
        List<String> list = new ArrayList<>();
        assertNotNull(list);
    }
}