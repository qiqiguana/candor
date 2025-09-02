package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringXorTest {
    @Test
	public void test1() throws Exception {
		assertEquals("100", original.StringXor.stringXor("010", "110"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("010010", original.StringXor.stringXor("111000", "101010"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("0", original.StringXor.stringXor("1", "1"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("0101", original.StringXor.stringXor("0101", "0000"));
	}

	
}