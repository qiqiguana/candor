package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Minpath.
*/
class MinpathTest {
    @Test
    void testMinPath()
    {
        List<List<Integer>> grid = new ArrayList<>();
        List<Integer> row1 = new ArrayList<>(List.of(6, 1, 5));
        List<Integer> row2 = new ArrayList<>(List.of(3, 8, 9));
        List<Integer> row3 = new ArrayList<>(List.of(2, 7, 4));
        grid.add(row1);
        grid.add(row2);
        grid.add(row3);

        int k = 8;

        List<Integer> actualResult = Minpath.minpath(grid, k);

        List<Integer> expectedResult = new ArrayList<>(List.of(1, 5, 1, 5, 1, 5, 1, 5));

        assertEquals(expectedResult, actualResult);
    }
}
