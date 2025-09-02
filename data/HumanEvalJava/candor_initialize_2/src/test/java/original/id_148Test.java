package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Bf.
*/
class BfTest {
    @Test
    void testBf() {
        String planet1 = "Jupiter";
        String planet2 = "Neptune";
        List<Object> result = Bf.bf(planet1, planet2);
        List<Object> expected = new ArrayList<>();
        expected.add("Saturn");
        expected.add("Uranus");
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            Bf s = new Bf();
            }
    @Test
    void test_Bf_instantiation() {
        assertDoesNotThrow(() -> new Bf());
    }
    @Test
    void test_bf_function_negative_test_for_unordered_planets() {
        List<Object> result = Bf.bf("Jupiter", "Mars");
        assertTrue(result.isEmpty());
    }
    @Test
    void test_bf_function_edge_case_test_for_adjacent_planets() {
        List<Object> result = Bf.bf("Venus", "Earth");
        assertTrue(result.isEmpty());
    }
    @Test
    public void Test_planets_in_reverse_order() {
        List<Object> result = Bf.bf("Neptune", "Venus");
        List<Object> expected = new ArrayList<>();
        expected.add("Earth");
        expected.add("Mars");
        expected.add("Jupiter");
        expected.add("Saturn");
        expected.add("Uranus");
        assertEquals(expected, result);
    }
    @Test
    public void Test_planets_with_index1_greater_than_index2() {
        List<Object> result = Bf.bf("Mars", "Earth");
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    @Test
    public void TestUncoveredBranchWithValidPlanets() {
        List<Object> expected = new ArrayList<>();
        expected.add("Saturn");
        expected.add("Uranus");
        assertEquals(expected, Bf.bf("Jupiter", "Neptune"));
    }
    @Test
    public void TestUncoveredBranchWithValidPlanetsReverseOrder() {
        List<Object> expected = new ArrayList<>();
        expected.add("Saturn");
        expected.add("Uranus");
        assertEquals(expected, Bf.bf("Neptune", "Jupiter"));
    }
    @Test
    public void TestUncoveredBranchWithSamePlanet() {
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Bf.bf("Earth", "Earth"));
    }
    @Test
    public void TestUncoveredBranchWithInvalidPlanets() {
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Bf.bf("Jupiter", "Mars"));
    }
                                    
}