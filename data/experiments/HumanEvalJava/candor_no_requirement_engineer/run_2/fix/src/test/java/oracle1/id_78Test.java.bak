package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HexKey.
*/
class HexKeyTest {

@Test
void testHexKey() {
	String hexNum = "AB";
	int expected = 1;
	int actual = HexKey.hexKey(hexNum);
	assertEquals(expected, actual);
}

@Test
    void testNothing(){
        HexKey s = new HexKey();
        }
@Test
void testEmptyString() {
    assertEquals(0, HexKey.hexKey(""));
}
@Test
void testSinglePrimeDigit() {
    assertEquals(1, HexKey.hexKey("2"));
}
@Test
void testNonPrimeDigit() {
    assertEquals(0, HexKey.hexKey("1"));
}
@Test
void testMixedPrimeAndNonPrimeDigits() {
    assertEquals(6, HexKey.hexKey("123456789ABCD"));
}
@Test
void testNullInput() {
    assertThrows(NullPointerException.class, () -> HexKey.hexKey(null));
}
@Test
void testMultiplePrimeDigitsFixed() {
    assertEquals(6, HexKey.hexKey("2357BDF"));
}
                                
}