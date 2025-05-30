package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetMaxTriples.
*/
class GetMaxTriplesTest {
    @Test
    void testGetMaxTriples_smallInput() {
        int n = 5;
        int expectedResult = 1;
        int actualResult = GetMaxTriples.getMaxTriples(n);
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
        public void testNothing(){
            GetMaxTriples s = new GetMaxTriples();
            }
    @Test
    public void testGetMaxTriples_PositiveInteger() {
        int n = 5;
        int expected = 1;
        int actual = GetMaxTriples.getMaxTriples(n);
        assertEquals(expected, actual);
    }
    @Test
    public void testGetMaxTriples_LargerPositiveInteger() {
        int n = 10;
        int expected = 36;
        int actual = GetMaxTriples.getMaxTriples(n);
        assertEquals(expected, actual);
    }
    @Test
    public void testGetMaxTriples_EdgeCase_NEquals1() {
        int n = 1;
        int expected = 0;
        int actual = GetMaxTriples.getMaxTriples(n);
        assertEquals(expected, actual);
    }
                                    
}