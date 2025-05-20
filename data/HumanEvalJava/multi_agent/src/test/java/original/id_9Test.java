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
    void testRollingMax_EmptyList_ReturnsEmptyList() {
        List<Object> numbers = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, RollingMax.rollingMax(numbers));
    }
}