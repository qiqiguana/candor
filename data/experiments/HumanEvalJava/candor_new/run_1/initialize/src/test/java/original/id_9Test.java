package original;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RollingMax.
*/
class RollingMaxTest {
    @Test
    void testRollingMaxWithEmptyList() {
        List<Object> numbers = new ArrayList<>();
        assertEquals(new ArrayList<>(), RollingMax.rollingMax(numbers));
    }
}
