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
    void test_getOddCollatz_ReturnsSortedOddCollatzNumbers() {
        int n = 10;
        List<Integer> result = GetOddCollatz.getOddCollatz(n);
        assertTrue(result.size() > 0);
    }
}