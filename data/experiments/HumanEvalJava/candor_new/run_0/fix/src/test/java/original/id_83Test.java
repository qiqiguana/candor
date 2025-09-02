package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StartsOneEnds.
*/
class StartsOneEndsTest {
    @Test
    void testStartsOneEndsWithNEqualTo1() {
        assertEquals(1, StartsOneEnds.startsOneEnds(1));
    }
    
    @Test
        public void testNothing(){
            StartsOneEnds s = new StartsOneEnds();
            }
    @Test
    public void TestStartsOneEndsWithBoundaryCondition() {
        int result = StartsOneEnds.startsOneEnds(2);
        assertEquals(18, result);
    }
                                    
}