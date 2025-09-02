package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StartsOneEndsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(1, original.StartsOneEnds.startsOneEnds(1));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(18, original.StartsOneEnds.startsOneEnds(2));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(180, original.StartsOneEnds.startsOneEnds(3));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(1800, original.StartsOneEnds.startsOneEnds(4));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(18000, original.StartsOneEnds.startsOneEnds(5));
	}

	
}