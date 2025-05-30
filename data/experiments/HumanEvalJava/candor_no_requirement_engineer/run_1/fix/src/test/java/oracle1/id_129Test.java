package oracle1;

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
void testMinPath() {
    List<List<Integer>> grid = new ArrayList<>();
    List<Integer> row1 = new ArrayList<>();
    row1.add(6);
    row1.add(1);
    row1.add(5);
    grid.add(row1);

    List<Integer> row2 = new ArrayList<>();
    row2.add(3);
    row2.add(8);
    row2.add(9);
    grid.add(row2);

    List<Integer> row3 = new ArrayList<>();
    row3.add(2);
    row3.add(7);
    row3.add(4);
    grid.add(row3);
    int k = 8;
    List<Integer> expected = new ArrayList<>();
    for (int i = 0; i < k / 2; i++) {
        expected.add(1);
        expected.add(5);
    }
    assertEquals(expected, Minpath.minpath(grid, k));
}

@Test
    void testNothing(){
        Minpath s = new Minpath();
        }
@Test
void testInstantiation() {
    assertDoesNotThrow(() -> new Minpath());
}
@Test
public void testToString() {
    Minpath s = new Minpath();
    assertNotNull(s.toString());
}
@Test
public void testMinpath2() {
    Minpath minpath = new Minpath();
    int n = 3;
    List<List<Integer>> grid = new ArrayList<>();
    for(int i=0; i < n; i++){
        List<Integer> temp = new ArrayList<>();
        for(int j=0;j<n;j++){
            temp.add((i*n+j)+1);
        }
        grid.add(temp);
    }
    List<Integer> expected = new ArrayList<>();
    for (int i = 0; i < n*2-1; i++) {
        expected.add(1);
    }
    assertEquals(expected, minpath.minpath(grid, n*2 - 1));
}
                                
}