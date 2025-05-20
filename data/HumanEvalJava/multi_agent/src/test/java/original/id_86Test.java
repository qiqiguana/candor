package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AntiShuffle.
*/
class AntiShuffleTest {
    @Test
    void testAntiShuffle_SimpleInput() {
        String input = "hello";
        String expectedOutput = "ehllo";
        assertEquals(expectedOutput, AntiShuffle.antiShuffle(input));
    }
}