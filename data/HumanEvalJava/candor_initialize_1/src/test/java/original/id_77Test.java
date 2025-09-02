package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Iscube.
*/
class IscubeTest {
    @Test
    void testIscube_WhenInputIsCube_ReturnsTrue() {
        assertTrue(Iscube.iscube(64));
    }
    
    @Test
        public void testNothing(){
            Iscube s = new Iscube();
            }
    @Test
    public void testIscubeInstantiation() {
    	Iscube iscube = new Iscube();
    	assertNotNull(iscube);
    }
    @Test
    public void testIscubeNegativeNonCube() {
    	assertFalse(Iscube.iscube(-2));
    }
                                    
}