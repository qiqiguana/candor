package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FlipCaseTest {
    @Test
	public void test1() throws Exception {
		assertEquals("hELLO", original.FlipCase.flipCase("Hello"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("", original.FlipCase.flipCase(""));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("hELLO!", original.FlipCase.flipCase("Hello!"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("tHESE VIOLENT DELIGHTS HAVE VIOLENT ENDS", original.FlipCase.flipCase("These violent delights have violent ends"));
	}

	
}