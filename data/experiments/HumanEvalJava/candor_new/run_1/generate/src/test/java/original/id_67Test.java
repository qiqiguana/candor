package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FruitDistribution.
*/
class FruitDistributionTest {
    @Test
    void testFruitDistribution() {
        String input = "5 apples and 6 oranges";
        int totalFruits = 19;
        int expectedMangoes = 8;
        int actualMangoes = FruitDistribution.fruitDistribution(input, totalFruits);
        assertEquals(expectedMangoes, actualMangoes);
    }
    
    @Test
        public void testNothing(){
            FruitDistribution s = new FruitDistribution();
            }
                                    
}