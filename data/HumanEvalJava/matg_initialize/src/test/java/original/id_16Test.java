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
}