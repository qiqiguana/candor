package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidDateTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.ValidDate.validDate("03-11-2000"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.ValidDate.validDate("15-01-2012"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(false, original.ValidDate.validDate("04-0-2040"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(true, original.ValidDate.validDate("06-04-2020"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.ValidDate.validDate("01-01-2007"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.ValidDate.validDate("03-32-2011"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(false, original.ValidDate.validDate(""));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(false, original.ValidDate.validDate("04-31-3000"));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(true, original.ValidDate.validDate("06-06-2005"));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(false, original.ValidDate.validDate("21-31-2000"));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(true, original.ValidDate.validDate("04-12-2003"));
	}

	@Test
	public void test12() throws Exception {
		assertEquals(false, original.ValidDate.validDate("04122003"));
	}

	@Test
	public void test13() throws Exception {
		assertEquals(false, original.ValidDate.validDate("20030412"));
	}

	@Test
	public void test14() throws Exception {
		assertEquals(false, original.ValidDate.validDate("2003-04"));
	}

	@Test
	public void test15() throws Exception {
		assertEquals(false, original.ValidDate.validDate("2003-04-12"));
	}

	@Test
	public void test16() throws Exception {
		assertEquals(false, original.ValidDate.validDate("04-2003"));
	}

	
}