package original;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringToMd5Test {
    @Test
	public void test1() throws Exception {
		assertEquals("3e25960a79dbc69b674cd4ec67a72c62", original.StringToMd5.stringToMd5("Hello world"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(null, original.StringToMd5.stringToMd5(""));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("0ef78513b0cb8cef12743f5aeb35f888", original.StringToMd5.stringToMd5("A B C"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", original.StringToMd5.stringToMd5("password"));
	}

	
}