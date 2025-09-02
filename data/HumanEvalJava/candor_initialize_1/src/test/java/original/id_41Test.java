package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CarRaceCollision.
*/
class CarRaceCollisionTest {
    @Test
    void testCarRaceCollision_PositiveInput_ReturnsCorrectResult() {
        int n = 2;
        int expected = 4;
        assertEquals(expected, CarRaceCollision.carRaceCollision(n));
    }
    
    @Test
        public void testNothing(){
            CarRaceCollision s = new CarRaceCollision();
            }
                                    
}