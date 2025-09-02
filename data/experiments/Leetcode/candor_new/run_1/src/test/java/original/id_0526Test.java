package original;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0526.
*/
class Solution0526Test {

    @Test
    void testCountArrangement() {
        Solution0526 solution = new Solution0526();
        int n = 3;
        int expected = 3;
        assertEquals(expected, solution.countArrangement(n));
    }
}