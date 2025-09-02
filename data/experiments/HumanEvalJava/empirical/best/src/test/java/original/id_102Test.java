package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChooseNumTest {
    @Test
	public void test1() throws Exception {
		assertEquals(14, original.ChooseNum.chooseNum(12, 15));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(-1, original.ChooseNum.chooseNum(13, 12));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(12354, original.ChooseNum.chooseNum(33, 12354));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(-1, original.ChooseNum.chooseNum(5234, 5233));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(28, original.ChooseNum.chooseNum(6, 29));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(-1, original.ChooseNum.chooseNum(27, 10));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(-1, original.ChooseNum.chooseNum(7, 7));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(546, original.ChooseNum.chooseNum(546, 546));
	}

	
}