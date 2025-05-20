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
        String input = "xyzXYZ";
        int expected = 3;
        assertEquals(expected, CountDistinctCharacters.countDistinctCharacters(input));
    }
    @Test
    public void test_class_instantiation_1() {
        assertNotNull(new CountDistinctCharacters());
    }
    @Test
    public void test_empty_string_2() {
        assertEquals(0, CountDistinctCharacters.countDistinctCharacters(""));
    }
    @Test
    public void test_single_character_3() {
        assertEquals(1, CountDistinctCharacters.countDistinctCharacters("a"));
    }
    @Test
    public void test_multiple_characters_4() {
        assertEquals(5, CountDistinctCharacters.countDistinctCharacters("abcde"));
    }
    @Test
    public void test_duplicate_characters_5() {
        assertEquals(3, CountDistinctCharacters.countDistinctCharacters("aaaabbbcc"));
    }
}