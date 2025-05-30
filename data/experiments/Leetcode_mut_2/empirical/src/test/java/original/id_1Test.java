package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SeparateParenGroupsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList("(()())", "((()))", "()", "((())()())"), original.SeparateParenGroups.separateParenGroups("(()()) ((())) () ((())()())"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList("()", "(())", "((()))", "(((())))"), original.SeparateParenGroups.separateParenGroups("() (()) ((())) (((())))"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList("(()(())((())))"), original.SeparateParenGroups.separateParenGroups("(()(())((())))"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList("()", "(())", "(()())"), original.SeparateParenGroups.separateParenGroups("( ) (( )) (( )( ))"));
	}

	
}