package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AntiShuffle.
*/
class AntiShuffleTest {
    @Test
    void testAntiShuffle_SingleWord_ReturnsSortedWord() {
        String input = "hello";
        String expected = "ehllo";
        String result = AntiShuffle.antiShuffle(input);
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            AntiShuffle s = new AntiShuffle();
            }
                                    
}