package jigl.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MathUtilTest {

	@Test
	public void testPowerOf2() {
		assertTrue(MathUtil.powerOf2(1));//because 2^0=1
		assertTrue(MathUtil.powerOf2(2));
		assertTrue(MathUtil.powerOf2(4));
		assertTrue(MathUtil.powerOf2(8));
		assertTrue(MathUtil.powerOf2(16));
		assertTrue(MathUtil.powerOf2(1024));
		assertFalse(MathUtil.powerOf2(0));//because there is no value for which 2^x=0
		assertFalse(MathUtil.powerOf2(3));
		assertFalse(MathUtil.powerOf2(5));
		assertFalse(MathUtil.powerOf2(44));
		assertFalse(MathUtil.powerOf2(847));
		assertFalse(MathUtil.powerOf2(1023));
	}

}
