package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EncryptTest {
    @Test
	public void test1() throws Exception {
		assertEquals("lm", original.Encrypt.encrypt("hi"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("ewhjklnop", original.Encrypt.encrypt("asdfghjkl"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("kj", original.Encrypt.encrypt("gf"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("ix", original.Encrypt.encrypt("et"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("jeiajeaijeiak", original.Encrypt.encrypt("faewfawefaewg"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals("lippsqcjvmirh", original.Encrypt.encrypt("hellomyfriend"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals("hbdhpqrmpjylqmpyjlpmlyjrqpmqryjlpmqryjljygyjl", original.Encrypt.encrypt("dxzdlmnilfuhmilufhlihufnmlimnufhlimnufhfucufh"));
	}

	@Test
	public void test8() throws Exception {
		assertEquals("e", original.Encrypt.encrypt("a"));
	}

	
}