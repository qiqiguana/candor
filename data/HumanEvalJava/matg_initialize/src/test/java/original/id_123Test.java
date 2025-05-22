package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetOddCollatz.
*/
class GetOddCollatzTest {
    @Test
    void testGetOddCollatz() {
        int n = 5;
        List<Integer> result = GetOddCollatz.getOddCollatz(n);
        assertTrue(result.contains(1));
        assertTrue(result.contains(5));
        assertEquals(2, result.size());
    }
}