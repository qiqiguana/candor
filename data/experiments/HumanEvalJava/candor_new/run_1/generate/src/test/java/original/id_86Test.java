package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AntiShuffle.
*/
class AntiShuffleTest {

    @Test
    void test_AntiShuffle_empty_string() {
        String input = "";
        String expected = "";
        String actual = AntiShuffle.antiShuffle(input);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            AntiShuffle s = new AntiShuffle();
            }
                                    
}