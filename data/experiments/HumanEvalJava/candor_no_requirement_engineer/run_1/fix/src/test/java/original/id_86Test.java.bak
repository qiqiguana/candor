package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AntiShuffle.
*/
class AntiShuffleTest {
    @Test
    void testAntiShuffle_SingleWord_ReturnsSortedCharacters() {
        // Arrange
        String input = "hello";
        String expectedOutput = "ehllo";
        
        // Act
        String actualOutput = AntiShuffle.antiShuffle(input);
        
        // Assert
        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
        void testNothing(){
            AntiShuffle s = new AntiShuffle();
            }
    @Test
    public void testEmptyString() {
    	String input = "";
    	String expected = "";
    	assertEquals(expected, AntiShuffle.antiShuffle(input));
    }
    @Test
    public void testSingleWord() {
    	String input = "hello";
    	String expected = "ehllo";
    	assertEquals(expected, AntiShuffle.antiShuffle(input));
    }
    @Test
    public void testPunctuation() {
    	String input = "Hi. My name is Mister Robot. How are you?";
    	String expected = ".Hi My aemn is Meirst .Rboot How aer ?ouy";
    	assertEquals(expected, AntiShuffle.antiShuffle(input));
    }
    @Test
    public void testNumbers() {
    	String input = "number";
    	String expected = "bemnru";
    	assertEquals(expected, AntiShuffle.antiShuffle(input));
    }
    @Test
    public void testNullInput() {
    	assertThrows(NullPointerException.class, () -> AntiShuffle.antiShuffle(null));
    }
                                    
}