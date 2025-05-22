package original;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of CountDistinctCharacters.
*/
class CountDistinctCharactersTest {

    @Test
    void testCountDistinctCharacters_IgnoresCase() {
        String string = "xyzXYZ";
        int expected = 3;
        int actual = CountDistinctCharacters.countDistinctCharacters(string);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            CountDistinctCharacters s = new CountDistinctCharacters();
            }
    @Test
    public void testCountDistinctCharactersWithEmptyString() {
    	String input = "";
    	int expected = 0;
    	int actual = CountDistinctCharacters.countDistinctCharacters(input);
    	assertEquals(expected, actual);
    }
    @Test
    public void testCountDistinctCharactersWithSingleCharacter() {
    	String input = "a";
    	int expected = 1;
    	int actual = CountDistinctCharacters.countDistinctCharacters(input);
    	assertEquals(expected, actual);
    }
    @Test
    public void testCountDistinctCharactersWithMultipleCharacters() {
    	String input = "abcABC";
    	int expected = 3;
    	int actual = CountDistinctCharacters.countDistinctCharacters(input);
    	assertEquals(expected, actual);
    }
    @Test
    public void testCountDistinctCharactersWithNumericCharacters() {
    	String input = "1234567890";
    	int expected = 10;
    	int actual = CountDistinctCharacters.countDistinctCharacters(input);
    	assertEquals(expected, actual);
    }
    @Test
    public void testCountDistinctCharactersWithWhitespaceCharacters() {
    	String input = "   \t\n\r";
    	int expected = 4;
    	int actual = CountDistinctCharacters.countDistinctCharacters(input);
    	assertEquals(expected, actual);
    }
    @Test
    public void testCountDistinctCharactersWithSpecialCharacters_1() {
    	String input = "!@#$%^&*()_+";
    	int expected = 12;
    	int actual = CountDistinctCharacters.countDistinctCharacters(input);
    	assertEquals(expected, actual);
    }
                                    
}