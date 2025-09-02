package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VowelsCountTest {
    @Test
	public void test1() throws Exception {
		assertEquals(2, original.VowelsCount.vowelsCount("abcde"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(3, original.VowelsCount.vowelsCount("Alone"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(2, original.VowelsCount.vowelsCount("key"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(1, original.VowelsCount.vowelsCount("bye"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(2, original.VowelsCount.vowelsCount("keY"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(1, original.VowelsCount.vowelsCount("bYe"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(3, original.VowelsCount.vowelsCount("ACEDY"));
	}

	
}