package original;

import java.util.Collections;
import java.util.Arrays;
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
        List<Object> expected = new ArrayList<>();
        expected.add("Saturn");
        expected.add("Uranus");
        assertEquals(expected, Bf.bf("Jupiter", "Neptune"));
    }
    
    @Test
        public void testNothing(){
            Bf s = new Bf();
            }
    @Test
    public void testMercuryAndNeptune2() {
    	java.util.List<java.lang.Object> expected = new java.util.ArrayList<>();
    	expected.add("Venus");
    	expected.add("Earth");
    	expected.add("Mars");
    	expected.add("Jupiter");
    	expected.add("Saturn");
    	expected.add("Uranus");
    	java.util.List<java.lang.Object> actual = original.Bf.bf("Mercury", "Neptune");
    	org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
    }
    @Test public void testSamePlanets() { List<java.lang.Object> expected = new java.util.ArrayList<>(); List<java.lang.Object> actual = original.Bf.bf("Earth", "Earth"); org.junit.jupiter.api.Assertions.assertTrue(actual.isEmpty()); }
    @Test
    public void testInvalidPlanetMakemake() {
        List<Object> expected = new ArrayList<>();
        List<Object> actual = Bf.bf("Jupiter", "Makemake");
        assertEquals(expected, actual);
    }
    @Test
    public void testInvalidPlanetMakemake_1() {
        List<Object> expected = new ArrayList<>();
        List<Object> actual = Bf.bf("Jupiter", "Makemake");
        assertEquals(expected, actual);
    }
    @Test
    public void testPlanetNamesAreCorrectAndOrdered() {
        List<Object> result = Bf.bf("Jupiter", "Neptune");
        assertEquals(Arrays.asList("Saturn", "Uranus"), result);
    }
    @Test
    public void testPlanetNamesAreCorrectButReversed() {
        List<Object> result = Bf.bf("Neptune", "Jupiter");
        assertEquals(Arrays.asList("Saturn", "Uranus"), result);
    }
    @Test
    public void testSamePlanetNames2() {
        List<Object> result = Bf.bf("Earth", "Earth");
        assertEquals(java.util.Collections.emptyList(), result);
    }
    @Test
    public void testInvalidPlanetName2() {
        List<Object> result = Bf.bf("Jupiter", "Makemake");
        assertTrue(result.isEmpty());
    }
    @Test
    public void testEdgeCaseOuterPlanets() {
        List<Object> result = Bf.bf("Saturn", "Neptune");
        assertEquals(Arrays.asList("Uranus"), result);
    }
    @Test
    public void testEdgeCaseInnerPlanets() {
        List<Object> result = Bf.bf("Venus", "Earth");
        assertEquals(Arrays.asList(), result);
    }
    @Test
    public void testBf_PlanetsOutOfOrder() {
        List<Object> result = Bf.bf("Neptune", "Mercury");
        assertEquals("[Venus, Earth, Mars, Jupiter, Saturn, Uranus]", result.toString());
    }
    @Test
    public void testBf_InvalidPlanetNames() {
        List<Object> result = Bf.bf("Invalid1", "Invalid2");
        assertEquals("[]", result.toString());
    }
    @Test
    public void testBf_AdjacentPlanets_1() {
        List<Object> result = Bf.bf("Earth", "Mars");
        assertEquals("[]", result.toString());
    }
    @Test
    public void testBf_SinglePlanet() {
        List<Object> result = Bf.bf("Earth", "Earth");
        assertEquals("[]", result.toString());
    }
                                    
}