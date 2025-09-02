package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StartsOneEnds.
*/
class StartsOneEndsTest {
    @Test
    void testStartsOneEndsWithLength1() {
        assertEquals(1, StartsOneEnds.startsOneEnds(1));
    }
    
    @Test
        public void testNothing(){
            StartsOneEnds s = new StartsOneEnds();
            }
    @Test
    void testStartAndEndValuesAfterMultiplication2() {
        int n = 2;
        int result = StartsOneEnds.startsOneEnds(n);
        assertEquals(18, result);
    }
                                    
}