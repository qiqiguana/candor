package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Bitset.
*/
class BitsetTest {
    @Test
    void testAll() {
        // Arrange
        Bitset bitset = new Bitset(2);
        bitset.fix(0);
        bitset.fix(1);
        // Act & Assert
        assertTrue(bitset.all());
    }
    @Test
    public void test_unfixed_bit() {
        Bitset bs = new Bitset(5);
        bs.fix(0);
        bs.unfix(0);
        assertFalse(bs.one());
    }
    @Test
    public void test_flip() {
    Bitset bs = new Bitset(5);
    bs.fix(0);
    bs.flip();
    assertNotEquals('1', bs.toString().charAt(0));
    }
    @Test
    public void test_one() {
    Bitset bs = new Bitset(5);
    bs.fix(0);
    assertTrue(bs.one());
    }
    @Test
    public void test_count_0() {
    	Bitset bs = new Bitset(5);
    	bs.fix(0);
    	bs.fix(1);
    	assertEquals(2, bs.count());
    }
    @Test
    public void all_test_3() {
    Bitset bitset = new Bitset(10);
    assertFalse(bitset.all());
    }
    @Test
    public void testFixAlreadySetBitDoesNotIncreaseCount1() {
        Bitset obj = new Bitset(5);
        obj.fix(3);
        int initialCount = obj.count();
        obj.fix(3);
        assertEquals(initialCount, obj.count());
    }
}