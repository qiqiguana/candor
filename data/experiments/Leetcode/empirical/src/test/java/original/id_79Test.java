package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DecimalToBinaryTest {
    @Test
	public void test1() throws Exception {
		assertEquals("db0db", original.DecimalToBinary.decimalToBinary(0));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("db100000db", original.DecimalToBinary.decimalToBinary(32));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("db1100111db", original.DecimalToBinary.decimalToBinary(103));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("db1111db", original.DecimalToBinary.decimalToBinary(15));
	}

	
}