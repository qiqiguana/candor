package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OddCountTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList("the number of odd elements 4n the str4ng 4 of the 4nput."), original.OddCount.oddCount(Arrays.asList("1234567")));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList("the number of odd elements 1n the str1ng 1 of the 1nput.", "the number of odd elements 8n the str8ng 8 of the 8nput."), original.OddCount.oddCount(Arrays.asList("3", "11111111")));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList("the number of odd elements 2n the str2ng 2 of the 2nput.", "the number of odd elements 3n the str3ng 3 of the 3nput.", "the number of odd elements 2n the str2ng 2 of the 2nput."), original.OddCount.oddCount(Arrays.asList("271", "137", "314")));
	}

	
}