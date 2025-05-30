package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IsEqualToSumEvenTest {
    @Test
	public void test1() throws Exception {
		assertEquals(false, original.IsEqualToSumEven.isEqualToSumEven(4));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.IsEqualToSumEven.isEqualToSumEven(6));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.IsEqualToSumEven.isEqualToSumEven(8));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(true, original.IsEqualToSumEven.isEqualToSumEven(10));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(false, original.IsEqualToSumEven.isEqualToSumEven(11));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(true, original.IsEqualToSumEven.isEqualToSumEven(12));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(false, original.IsEqualToSumEven.isEqualToSumEven(13));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(true, original.IsEqualToSumEven.isEqualToSumEven(16));
	}

	
}