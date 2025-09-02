package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Strlen.
*/
class StrlenTest {
    @Test
    void testEmptyStringLength() {
        String input = "";
        int expected = 0;
        int actual = Strlen.strlen(input);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            Strlen s = new Strlen();
            }
                                    
}