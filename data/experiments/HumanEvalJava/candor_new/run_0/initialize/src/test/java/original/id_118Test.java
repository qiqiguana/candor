package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetClosestVowel.
*/
class GetClosestVowelTest {

    @Test
    void testGetClosestVowel() {
        assertEquals("u", GetClosestVowel.getClosestVowel("yogurt"));
    }
}