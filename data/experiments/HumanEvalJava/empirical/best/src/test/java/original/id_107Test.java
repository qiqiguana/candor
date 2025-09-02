package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvenOddPalindromeTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(8, 13), original.EvenOddPalindrome.evenOddPalindrome(123));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(4, 6), original.EvenOddPalindrome.evenOddPalindrome(12));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(1, 2), original.EvenOddPalindrome.evenOddPalindrome(3));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(6, 8), original.EvenOddPalindrome.evenOddPalindrome(63));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(5, 6), original.EvenOddPalindrome.evenOddPalindrome(25));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(4, 6), original.EvenOddPalindrome.evenOddPalindrome(19));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(4, 5), original.EvenOddPalindrome.evenOddPalindrome(9));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(Arrays.asList(0, 1), original.EvenOddPalindrome.evenOddPalindrome(1));
	}

	
}