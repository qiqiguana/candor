package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IscubeTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.Iscube.iscube(1));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.Iscube.iscube(2));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.Iscube.iscube(-1));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(true, original.Iscube.iscube(64));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(false, original.Iscube.iscube(180));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(true, original.Iscube.iscube(1000));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(true, original.Iscube.iscube(0));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(false, original.Iscube.iscube(1729));
	}

	
}