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
    public void testBf_01() {
        List<Object> result = Bf.bf("Jupiter", "Neptune");
        assertEquals(2, result.size());
        assertTrue(result.contains("Saturn"));
        assertTrue(result.contains("Uranus"));
    }
    
    @Test
        public void testNothing(){
            Bf s = new Bf();
            }
    @Test
    public void testPlanetsInReverseOrderWithPlanet1BeforePlanet21() {
        List<Object> result = Bf.bf("Jupiter", "Mars");
        assertEquals(0, result.size());
    }
    @Test
    public void testInvalidPlanetNameForPlanet11() {
        List<Object> result = Bf.bf("Makemake", "Neptune");
        assertEquals(0, result.size());
    }
    @Test
    public void testInvalidPlanetNameForPlanet21() {
        List<Object> result = Bf.bf("Jupiter", "Makemake");
        assertEquals(0, result.size());
    }
    @Test
    public void Test_bf_with_planet2_before_planet1_covering_line_58_fixed() {
        List<java.lang.Object> result = Bf.bf("Neptune", "Venus");
        List<java.lang.Object> expected = new ArrayList<>();
        expected.add("Earth");
        expected.add("Mars");
        expected.add("Jupiter");
        expected.add("Saturn");
        expected.add("Uranus");
        assertEquals(expected, result);
    }
                                    
}