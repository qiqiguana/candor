package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FruitDistribution.
*/
class FruitDistributionTest {
    @Test
    void testFruitDistribution_When5ApplesAnd6Oranges_ThenReturns8() {
        String input = "5 apples and 6 oranges";
        int totalFruits = 19;
        int expectedMangoes = 8;
        int actualMangoes = FruitDistribution.fruitDistribution(input, totalFruits);
        assertEquals(expectedMangoes, actualMangoes);
    }
    
    @Test
        void testNothing(){
            FruitDistribution s = new FruitDistribution();
            }
    @Test
    public void testFruitDistribution_HappyPath() {
        String s = "5 apples and 6 oranges";
        int n = 19;
        int expected = 8;
        int actual = FruitDistribution.fruitDistribution(s, n);
        assertEquals(expected, actual);
    }
    @Test
    public void testFruitDistribution_HappyPath_DifferentInputs() {
        String s = "1 apples and 0 oranges";
        int n = 3;
        int expected = 2;
        int actual = FruitDistribution.fruitDistribution(s, n);
        assertEquals(expected, actual);
    }
    @Test
    public void testFruitDistribution_NoOranges() {
        String s = "5 apples and 0 oranges";
        int n = 19;
        int expected = 14;
        int actual = FruitDistribution.fruitDistribution(s, n);
        assertEquals(expected, actual);
    }
    @Test
    public void testFruitDistribution_NoApples() {
        String s = "0 apples and 6 oranges";
        int n = 19;
        int expected = 13;
        int actual = FruitDistribution.fruitDistribution(s, n);
        assertEquals(expected, actual);
    }
    @Test
    public void testFruitDistribution_HappyPath1() {
        String s = "5 apples and 0 oranges";
        int n = 19;
        int expected = 14;
        int actual = FruitDistribution.fruitDistribution(s, n);
        assertEquals(expected, actual);
    }
    @Test
    public void testFruitDistribution_HappyPath2() {
        String s = "0 apples and 6 oranges";
        int n = 19;
        int expected = 13;
        int actual = FruitDistribution.fruitDistribution(s, n);
        assertEquals(expected, actual);
    }
    @Test
    public void testFruitDistribution_SadPath_InvalidInputs2() {
        String s = "5 apples and 0 oranges";
        int n = 19;
        int result = FruitDistribution.fruitDistribution(s, n);
        assertEquals(14, result);
    }
                                    
}