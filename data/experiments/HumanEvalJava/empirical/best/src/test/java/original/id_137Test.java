package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompareOneTest {
    @Test
	public void test1() throws Exception {
		assertEquals(2, original.CompareOne.compareOne(1, 2));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(2.5, original.CompareOne.compareOne(1, 2.5));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(3, original.CompareOne.compareOne(2, 3));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(6, original.CompareOne.compareOne(5, 6));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("2,3", original.CompareOne.compareOne(1, "2,3"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals("6", original.CompareOne.compareOne("5,1", "6"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals("2", original.CompareOne.compareOne("1", "2"));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(null, original.CompareOne.compareOne("1", 1));
	}

	
}