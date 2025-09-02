package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CarRaceCollision.
*/
class CarRaceCollisionTest {
    @Test
    void testCarRaceCollision_WhenPositiveInput_ReturnsSquareOfInput() {
        // Arrange & Act
        int result = CarRaceCollision.carRaceCollision(5);
        
        // Assert
        assertEquals(25, result);
    }
    
    @Test
        void testNothing(){
            CarRaceCollision s = new CarRaceCollision();
            }
    @Test
    public void testCarRaceCollisionWithPositiveNumber() {
        int n = 2;
        int expected = 4;
        int result = CarRaceCollision.carRaceCollision(n);
        assertEquals(expected, result);
    }
    @Test
    public void testCarRaceCollisionWithZeroCars() {
        int n = 0;
        int expected = 0;
        int result = CarRaceCollision.carRaceCollision(n);
        assertEquals(expected, result);
    }
    @Test
    public void testCarRaceCollisionWithLargeNumber() {
        int n = 1000;
        long expected = 1000000;
        long result = CarRaceCollision.carRaceCollision(n);
        assertEquals(expected, result);
    }
    @Test
    public void testCarRaceCollisionWithNonNegativeNumber() {
        int n = 2;
        assertEquals(4, CarRaceCollision.carRaceCollision(n));
    }
    @Test
    public void testCarRaceCollisionWithEdgeCase_1() {
        int n = 1;
        int expected = 1;
        int result = CarRaceCollision.carRaceCollision(n);
        assertEquals(expected, result);
    }
                                    
}