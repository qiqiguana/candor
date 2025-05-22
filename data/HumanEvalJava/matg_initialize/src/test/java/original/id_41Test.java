package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CarRaceCollision.
*/
class CarRaceCollisionTest {
    @Test
    void testCarRaceCollision_CollisionCount() {
        int expected = 16;
        int actual = CarRaceCollision.carRaceCollision(4);
        assertEquals(expected, actual);
    }
}