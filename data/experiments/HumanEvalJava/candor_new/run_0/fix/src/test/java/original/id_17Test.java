package original;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ParseMusic.
*/
class ParseMusicTest {
	@Test
	void testParseMusic_EmptyString_ReturnsEmptyList() {
		// Arrange and Act
		List<Object> result = ParseMusic.parseMusic("");
		// Assert
		assertTrue(result.isEmpty());
	}
 
 @Test
     public void testNothing(){
         ParseMusic s = new ParseMusic();
         }
 @Test
 public void TestParseMusic_O_HalfNote() {
     List<Integer> expected = Arrays.asList(2);
     List<Object> result = ParseMusic.parseMusic("o|");
     assertEquals(expected, result);
 }
 @Test
 public void TestParseMusic_DOT_QuarterNote() {
     List<Object> expected = Arrays.asList();
     List<Object> result = ParseMusic.parseMusic("|.");
     assertEquals(expected, result);
 }
 @Test
 public void TestParseMusic_MultipleNotesFixed() {
     List<Object> expected = Arrays.asList(4, 2, 1);
     List<Object> result = ParseMusic.parseMusic("o o| .|");
     assertEquals(expected, result);
 }
 @Test
 public void testWholeNote() {
 	List<Object> result = ParseMusic.parseMusic("o");
 	assertEquals(List.of(4), result);
 }
                                 
}