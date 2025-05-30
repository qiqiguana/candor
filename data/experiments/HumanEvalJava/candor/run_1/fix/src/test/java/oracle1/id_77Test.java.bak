package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Iscube.
*/
class IscubeTest {
    @Test
    void iscube_should_return_true_for_zero() {
        // Given:
        int input = 0;
        boolean expected = true;

        // When:
        boolean actual = Iscube.iscube(input);

        // Then:
        assertEquals(expected, actual);
    }
    
    @Test
        void testNothing(){
            Iscube s = new Iscube();
            }
    @Test
    public void testIscube_PositiveCubeOfPositiveNumber() {
    	Boolean result = Iscube.iscube(27);
    	assertTrue(result);
    }
    @Test
    public void testIscube_PositiveCubeOfNegativeNumber() {
    	Boolean result = Iscube.iscube(-27);
    	assertTrue(result);
    }
    @Test
    public void testIscube_NonCubeNumber() {
    	Boolean result = Iscube.iscube(10);
    	assertFalse(result);
    }
    @Test
    public void testIscube_Zero() {
    	Boolean result = Iscube.iscube(0);
    	assertTrue(result);
    }
    @Test
    public void testIscube_One() {
    	Boolean result = Iscube.iscube(1);
    	assertTrue(result);
    }
    @Test
    public void testIscube_LargeCubeNumber() {
    	Boolean result = Iscube.iscube(1000000);
    	assertTrue(result);
    }
                                    
}