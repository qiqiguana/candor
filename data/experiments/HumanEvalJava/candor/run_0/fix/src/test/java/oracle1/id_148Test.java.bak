package oracle1;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		List<Object> result = Bf.bf("Jupiter", "Neptune");
		assertEquals(2, result.size());
	}
 
 @Test
     void testNothing(){
         Bf s = new Bf();
         }
 @Test
 public void testInvalidPlanetNames() {
     List<Object> result = Bf.bf("Makemake", "Jupiter");
     assertEquals(result, new ArrayList<>());
 }
 @Test
 public void testSamePlanetInput() {
     List<Object> result = Bf.bf("Jupiter", "Jupiter");
     assertEquals(result, new ArrayList<>());
 }
 @Test
 public void testSamePlanetInput1() {
     List<Object> result = Bf.bf("Earth", "Earth");
     assertEquals(result, new ArrayList<>());
 }
 @Test
 public void testPlanetsBetweenEarthAndMars() {
   List<Object> result = Bf.bf("Earth", "Mars");
   List<Object> expected = new ArrayList<>();
   assertEquals(expected, result);
 }
 @Test
 public void testInvalidInput() {
   List<Object> result = Bf.bf("Invalid", "Input");
   List<Object> expected = new ArrayList<>();
   assertEquals(expected, result);
 }
 @Test
 public void testEdgeCaseMercuryAndVenus() {
   List<Object> result = Bf.bf("Mercury", "Venus");
   List<Object> expected = new ArrayList<>();
   assertEquals(expected, result);
 }
 @Test
 public void testEdgeCaseNeptuneAndUranus() {
   List<Object> result = Bf.bf("Neptune", "Uranus");
   List<Object> expected = new ArrayList<>();
   assertEquals(expected, result);
 }
 @Test
 public void testEdgeCaseSamePlanet() {
   List<Object> result = Bf.bf("Earth", "Earth");
   List<Object> expected = new ArrayList<>();
   assertEquals(expected, result);
 }
 @Test
 void testSamePlanets() {
     List<Object> result = Bf.bf("Earth", "Earth");
     assertTrue(result.isEmpty());
 }
 @Test
 public void testPlanetsBetweenMercuryAndNeptune(){
 List<java.lang.Object> result = oracle1.Bf.bf("Mercury", "Neptune");
 List<java.lang.Object> expected = java.util.Arrays.asList("Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus");
 assertEquals(expected, result);
 }
 @Test
 public void Test_Adjacent_Planets() {
     List<Object> result = Bf.bf("Venus", "Mercury");
     assertTrue(result.isEmpty());
 }
 @Test
 public void Test_Adjacent_Planets2() {
     List<Object> result = Bf.bf("Mars", "Jupiter");
     assertTrue(result.isEmpty());
 }
 @Test
 public void Test_Same_Planet() {
     List<Object> result = Bf.bf("Earth", "Earth");
     assertTrue(result.isEmpty());
 }
 @Test
 public void Test_Edge_Case_First_And_Last_Planet_Fixed_1() {
     List<java.lang.Object> result = oracle1.Bf.bf("Mercury", "Neptune");
     java.util.List<java.lang.Object> expected = new java.util.ArrayList<>(java.util.Arrays.asList("Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus"));
     assertEquals(expected, result);
 }
 @Test
 public void Test_Edge_Case_Second_And_Second_Last_Planet() {
     List<java.lang.Object> result = Bf.bf("Venus", "Neptune");
     assertEquals(result, java.util.Arrays.asList("Earth", "Mars", "Jupiter", "Saturn", "Uranus"));
 }
 @Test
 public void Test_Adjacent_Planets_Returns_Empty_List() {
     List<Object> result = Bf.bf("Mercury", "Venus");
     assertTrue(result.isEmpty());
 }
 @Test
 public void testBf_HappyPath() {
     List<Object> expected = new ArrayList<>();
     expected.add("Venus");
     expected.add("Earth");
     expected.add("Mars");
     assertEquals(expected, Bf.bf("Mercury", "Jupiter"));
 }
 @Test
 public void testBf_InvalidPlanetNames() {
     List<Object> expected = new ArrayList<>();
     assertEquals(expected, Bf.bf("Makemake", "Haumea"));
 }
 @Test
 public void testBf_SamePlanets() {
     List<Object> expected = new ArrayList<>();
     assertEquals(expected, Bf.bf("Earth", "Earth"));
 }
 @Test
 public void testBf_PlanetNotFound() {
     List<Object> expected = new ArrayList<>();
     assertEquals(expected, Bf.bf("Jupiter", "Sedna"));
 }
 @Test
 public void testBf_PlanetNotFound2() {
     List<Object> expected = new ArrayList<>();
     assertEquals(expected, Bf.bf("Sedna", "Jupiter"));
 }
 @Test
 public void testBf_ReverseOrder(){
     List<Object> expected = new ArrayList<>();
     expected.add("Venus");
     expected.add("Earth");
     expected.add("Mars");
     assertEquals(expected, Bf.bf("Jupiter", "Mercury"));
 }
                                 
}