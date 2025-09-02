package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Add1Test {
    @Test
	public void test1() throws Exception {
		assertEquals(88, original.Add1.add(Arrays.asList(4, 88)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(122, original.Add1.add(Arrays.asList(4, 5, 6, 7, 2, 122)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(0, original.Add1.add(Arrays.asList(4, 0, 6, 7)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(12, original.Add1.add(Arrays.asList(4, 4, 6, 8)));
	}

	
}