package original;

import java.util.Map;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CheckDictCaseTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.CheckDictCase.checkDictCase(Map.of("p", "pineapple", "b", "banana")));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.CheckDictCase.checkDictCase(Map.of("p", "pineapple", "A", "banana", "B", "banana")));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(false, original.CheckDictCase.checkDictCase(Map.of("p", "pineapple", 5, "banana", "a", "apple")));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(false, original.CheckDictCase.checkDictCase(Map.of("Name", "John", "Age", "36", "City", "Houston")));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.CheckDictCase.checkDictCase(Map.of("STATE", "NC", "ZIP", "12345")));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(true, original.CheckDictCase.checkDictCase(Map.of("fruit", "Orange", "taste", "Sweet")));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(false, original.CheckDictCase.checkDictCase(Map.of()));
	}

	
}