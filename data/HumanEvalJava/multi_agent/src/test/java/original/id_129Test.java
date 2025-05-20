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
    void testMinPathSize() {
        List<List<Integer>> grid = new ArrayList<>();
        List<Integer> row1 = new ArrayList<>();
        row1.add(2);
        row1.add(7);
        row1.add(4);
        grid.add(row1);

        List<Integer> row2 = new ArrayList<>();
        row2.add(3);
        row2.add(1);
        row2.add(5);
        grid.add(row2);

        List<Integer> row3 = new ArrayList<>();
        row3.add(6);
        row3.add(8);
        row3.add(9);
        grid.add(row3);
        assertEquals(8, Minpath.minpath(grid, 8).size());
    }
    @Test
    public void testMinpathInstantiation() {
    	Minpath minpath = new Minpath();
    	assertNotNull(minpath);
    }
}