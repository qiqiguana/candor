package original;

import java.util.Arrays;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileNameCheckTest {
    @Test
	public void test1() throws Exception {
		assertEquals("Yes", original.FileNameCheck.fileNameCheck("example.txt"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("1example.dll"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("s1sdf3.asd"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("Yes", original.FileNameCheck.fileNameCheck("K.dll"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("Yes", original.FileNameCheck.fileNameCheck("MY16FILE3.exe"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("His12FILE94.exe"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("_Y.txt"));
	}

	@Test
	public void test8() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("?aREYA.exe"));
	}

	@Test
	public void test9() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("/this_is_valid.dll"));
	}

	@Test
	public void test10() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("this_is_valid.wow"));
	}

	@Test
	public void test11() throws Exception {
		assertEquals("Yes", original.FileNameCheck.fileNameCheck("this_is_valid.txt"));
	}

	@Test
	public void test12() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("this_is_valid.txtexe"));
	}

	@Test
	public void test13() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("#this2_i4s_5valid.ten"));
	}

	@Test
	public void test14() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("@this1_is6_valid.exe"));
	}

	@Test
	public void test15() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("this_is_12valid.6exe4.txt"));
	}

	@Test
	public void test16() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("all.exe.txt"));
	}

	@Test
	public void test17() throws Exception {
		assertEquals("Yes", original.FileNameCheck.fileNameCheck("I563_No.exe"));
	}

	@Test
	public void test18() throws Exception {
		assertEquals("Yes", original.FileNameCheck.fileNameCheck("Is3youfault.txt"));
	}

	@Test
	public void test19() throws Exception {
		assertEquals("Yes", original.FileNameCheck.fileNameCheck("no_one#knows.dll"));
	}

	@Test
	public void test20() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("1I563_Yes3.exe"));
	}

	@Test
	public void test21() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("I563_Yes3.txtt"));
	}

	@Test
	public void test22() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("final..txt"));
	}

	@Test
	public void test23() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("final132"));
	}

	@Test
	public void test24() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("_f4indsartal132."));
	}

	@Test
	public void test25() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck(".txt"));
	}

	@Test
	public void test26() throws Exception {
		assertEquals("No", original.FileNameCheck.fileNameCheck("s."));
	}

	
}