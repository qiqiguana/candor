package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FruitDistribution.
*/
class FruitDistributionTest {
    @Test
    void testFruitDistribution() {
        String s = "5 apples and 6 oranges";
        int n = 19;
        int expectedMangoes = 8;
        assertEquals(expectedMangoes, FruitDistribution.fruitDistribution(s, n));
    }
}