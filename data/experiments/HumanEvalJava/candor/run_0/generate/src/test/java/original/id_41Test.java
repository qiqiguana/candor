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
    
    @Test
        public void testNothing(){
            CarRaceCollision s = new CarRaceCollision();
            }
    @Test
    public void testCarRaceCollision_SmallInput() {
        int n = 2;
        int expectedResult = 4;
        int result = CarRaceCollision.carRaceCollision(n);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testCarRaceCollision_MediumInput() {
        int n = 5;
        int expectedResult = 25;
        int result = CarRaceCollision.carRaceCollision(n);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testCarRaceCollision_LargeInput() {
        int n = 10;
        int expectedResult = 100;
        int result = CarRaceCollision.carRaceCollision(n);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testCarRaceCollision_ZeroInput() {
        int n = 0;
        int expectedResult = 0;
        int result = CarRaceCollision.carRaceCollision(n);
        assertEquals(expectedResult, result);
    }
                                    
}