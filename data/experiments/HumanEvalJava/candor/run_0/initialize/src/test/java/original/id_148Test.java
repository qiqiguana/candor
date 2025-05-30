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
    void bf_WhenBothPlanetsAreCorrect_ReturnsPlanetsInBetween() {
        List<Object> result = Bf.bf("Jupiter", "Neptune");
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
    public void Test_bf_with_planet1_before_planet2_and_same_index() {
        String[] input = {"Jupiter", "Jupiter"};
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Bf.bf(input[0], input[1]));
    }
    @Test
    public void Test_bf_with_invalid_planet_names_and_index_out_of_bounds() {
        String[] input = {"Makemake", "Neptune"};
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Bf.bf(input[0], input[1]));
    }
    @Test
    public void Test_bf_with_planet2_before_planet1_1() {
        String[] input = {"Neptune", "Mercury"};
        List<Object> expected = new ArrayList<>();
        expected.add("Venus");
        expected.add("Earth");
        expected.add("Mars");
        expected.add("Jupiter");
        expected.add("Saturn");
        expected.add("Uranus");
        assertEquals(expected, Bf.bf(input[1], input[0]));
    }
    @Test
    public void testPlanetsBetweenEarthAndNeptune() {
        List<Object> expected = new ArrayList<>();
        expected.add("Mars");
        expected.add("Jupiter");
        expected.add("Saturn");
        expected.add("Uranus");
        assertEquals(expected, Bf.bf("Earth", "Neptune"));
    }
    @Test
    public void testPlanetsBetweenUranusAndSaturn() {
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Bf.bf("Uranus", "Saturn"));
    }
    @Test
    public void testPlanetsBetweenNonExistentPlanetAndValidPlanet() {
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Bf.bf("Makemake", "Jupiter"));
    }
    @Test
    public void testPlanetsBetweenTwoNonExistentPlanets() {
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Bf.bf("Makemake", "Nebulon"));
    }
                                    
}