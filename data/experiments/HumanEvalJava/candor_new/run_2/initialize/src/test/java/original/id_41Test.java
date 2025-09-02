package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CarRaceCollision.
*/
class CarRaceCollisionTest {
    @Test
    void testCarRaceCollision_SimpleCase() {
        int n = 2;
        int expected = 4;
        int actual = CarRaceCollision.carRaceCollision(n);
        assertEquals(expected, actual);
    }
}