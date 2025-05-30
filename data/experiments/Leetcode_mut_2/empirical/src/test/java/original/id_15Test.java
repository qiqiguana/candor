package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringSequenceTest {
    @Test
	public void test1() throws Exception {
		assertEquals("0", original.StringSequence.stringSequence(0));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("0 1 2 3 4 5", original.StringSequence.stringSequence(5));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("0", original.StringSequence.stringSequence(0));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("0 1 2 3", original.StringSequence.stringSequence(3));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("0 1 2 3 4 5 6 7 8 9 10", original.StringSequence.stringSequence(10));
	}

	
}