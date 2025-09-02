package original;

import java.util.Arrays;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AntiShuffleTest {
    @Test
	public void test1() throws Exception {
		assertEquals("Hi", original.AntiShuffle.antiShuffle("Hi"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("ehllo", original.AntiShuffle.antiShuffle("hello"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("bemnru", original.AntiShuffle.antiShuffle("number"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("abcd", original.AntiShuffle.antiShuffle("abcd"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("Hello !!!Wdlor", original.AntiShuffle.antiShuffle("Hello World!!!"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals("", original.AntiShuffle.antiShuffle(""));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(".Hi My aemn is Meirst .Rboot How aer ?ouy", original.AntiShuffle.antiShuffle("Hi. My name is Mister Robot. How are you?"));
	}

	
}