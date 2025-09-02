package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HexKey.
*/
class HexKeyTest {
    @Test
    void testHexKey() {
        String num = "AB";
        int expected = 1;
        int actual = HexKey.hexKey(num);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            HexKey s = new HexKey();
            }
    @Test
    public void test_hex_key_with_mixed_prime_and_non_prime_digits() {
        Object input = "1077E";
        int expected_result = 2;
        assertEquals(expected_result, HexKey.hexKey(input));
    }
    @Test
    public void testHexKeySinglePrimeDigit() {
    	Object[] input = new Object[] { "2" };
    	int expected_result = 1;
    	assertEquals(expected_result, HexKey.hexKey(input[0]));
    }
    @Test
    public void testHexKeyMultiplePrimeDigits() {
    	Object[] input = new Object[] { "237B" };
    	int expected_result = 4;
    	assertEquals(expected_result, HexKey.hexKey(input[0]));
    }
                                    
}