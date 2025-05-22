package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetMaxTriples.
*/
class GetMaxTriplesTest {
    @Test
    void testGetMaxTriplesSmallInput() {
        int n = 5;
        int expected = 1;
        int actual = GetMaxTriples.getMaxTriples(n);
        assertEquals(expected, actual);
    }
    
    @Test
        void testNothing(){
            GetMaxTriples s = new GetMaxTriples();
            }
    @Test
    public void testSmallInput() {
        int n = 5;
        assertEquals(1, GetMaxTriples.getMaxTriples(n));
    }
    @Test
    public void testMediumInput() {
        int n = 10;
        assertEquals(36, GetMaxTriples.getMaxTriples(n));
    }
    @Test
    public void testLargeInput() {
        int n = 100;
        assertEquals(C(33,3)+C(67,3), GetMaxTriples.getMaxTriples(n));
    }
    @Test
    public void testEdgeCaseMinValue() {
        int n = 1;
        assertEquals(0, GetMaxTriples.getMaxTriples(n));
    }
                                    
}