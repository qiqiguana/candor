package original;

import java.util.Arrays;
import java.util.ArrayList; import java.util.Collections; import java.util.List; 

import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.assertEquals; 
/**
* Test class of Unique.
*/
class UniqueTest {
    @Test
    void testUnique() {
        List<Integer> input = new ArrayList<>(List.of(5, 3, 5, 2, 3, 3, 9, 0, 123));
        List<Integer> expected = new ArrayList<>(List.of(0, 2, 3, 5, 9, 123));
        assertEquals(expected, Unique.unique(input));
    }
    
    @Test
        public void testNothing(){
            Unique s = new Unique();
            }
    @Test
    public void testUnique_EmptyList() {
        List<Integer> input = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, Unique.unique(input));
    }
    @Test
    public void testUnique_DuplicateElements() {
        List<Integer> input = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(expected, Unique.unique(input));
    }
                                    
}