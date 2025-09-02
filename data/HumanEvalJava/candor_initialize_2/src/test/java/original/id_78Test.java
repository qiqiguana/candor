package original;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HexKey.
*/
class HexKeyTest {
    @Test
    void testHexKey_1() {
        assertEquals(1, HexKey.hexKey("AB"));
    }
    
    @Test
        public void testNothing(){
            HexKey s = new HexKey();
            }
    @Test
    public void testHexKeyWithPrimeDigits() {
        assertEquals(1, HexKey.hexKey("AB"));
    }
    @Test
    public void testHexKeyWithMultiplePrimeDigits() {
        assertEquals(2, HexKey.hexKey("1077E"));
    }
    @Test
    public void testHexKeyWithEmptyString() {
        assertEquals(0, HexKey.hexKey(""));
    }
    @Test
    void testHexKeyWithPrimeDigits1() {
        String input = "AB";
        int expected_result = 1;
        assertEquals(expected_result, HexKey.hexKey(input));
    }
    @Test
    void testHexKeyWithMultiplePrimeDigits1() {
        String input = "1077E";
        int expected_result = 2;
        assertEquals(expected_result, HexKey.hexKey(input));
    }
    @Test
    void testHexKeyWithEmptyStringFixed() {
        Object[] input = new Object[]{ "A" };
        int expected_result = 0;
        assertEquals(expected_result, HexKey.hexKey(input[0].toString()));
    }
    @Test
    void testHexKeyWithNullInput() {
        Object[] input = new Object[]{ null };
        assertThrows(NullPointerException.class, () -> HexKey.hexKey(input[0]));
    }
    @Test
    public void testHexKeyWithPrimeDigits2() {
        Object[] inputs = new Object[] { "2", "3", "5", "7", "B", "D" };
        int[] expectedResults = new int[] {1, 1, 1, 1, 1, 1};
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(expectedResults[i], HexKey.hexKey((String)inputs[i]));
        }
    }
                                    
}