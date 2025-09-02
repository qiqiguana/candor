package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriangleAreaTest {
    @Test
	public void test1() throws Exception {
		assertEquals(7.5, original.TriangleArea.triangleArea(5, 3));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(2.0, original.TriangleArea.triangleArea(2, 2));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(40.0, original.TriangleArea.triangleArea(10, 8));
	}

	
}