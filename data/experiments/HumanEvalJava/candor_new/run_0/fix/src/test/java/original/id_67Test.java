package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FruitDistribution.
*/
class FruitDistributionTest {
    @Test
    void test_fruit_distribution_with_five_apples_and_six_oranges() {
        // Arrange
        String s = "5 apples and 6 oranges";
        int n = 19;
        int expectedMangoes = 8;

        // Act
        int actualMangoes = FruitDistribution.fruitDistribution(s, n);

        // Assert
        assertEquals(expectedMangoes, actualMangoes);
    }
    
    @Test
        public void testNothing(){
            FruitDistribution s = new FruitDistribution();
            }
                                    
}