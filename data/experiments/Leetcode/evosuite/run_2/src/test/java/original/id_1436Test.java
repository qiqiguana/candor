package original;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1436.
*/
class Solution1436Test {
    @Test
    void testDestCity() {
        Solution1436 solution = new Solution1436();
        List<List<String>> paths = new ArrayList<>();
        List<String> path1 = new ArrayList<>();
        path1.add("London");
        path1.add("New York");
        paths.add(path1);
        
        List<String> path2 = new ArrayList<>();
        path2.add("New York");
        path2.add("Lima");
        paths.add(path2);
        
        String expected = "Lima";
        String actual = solution.destCity(paths);
        assertEquals(expected, actual);
    }
}