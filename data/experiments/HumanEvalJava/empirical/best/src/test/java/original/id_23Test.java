package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StrlenTest {
    @Test
	public void test1() throws Exception {
		assertEquals(0, original.Strlen.strlen(""));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(3, original.Strlen.strlen("abc"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(0, original.Strlen.strlen(""));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(1, original.Strlen.strlen("x"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(9, original.Strlen.strlen("asdasnakj"));
	}

	
}