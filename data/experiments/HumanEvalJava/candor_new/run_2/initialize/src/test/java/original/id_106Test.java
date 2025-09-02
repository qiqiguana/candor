package original;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of F.
*/
class FTest {
    @Test
    void testF() {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(2);
        result.add(6);
        assertEquals(result, F.f(3));
    }
}