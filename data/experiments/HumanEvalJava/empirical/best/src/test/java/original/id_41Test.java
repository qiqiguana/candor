package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarRaceCollisionTest {
    @Test
	public void test1() throws Exception {
		assertEquals(4, original.CarRaceCollision.carRaceCollision(2));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(9, original.CarRaceCollision.carRaceCollision(3));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(16, original.CarRaceCollision.carRaceCollision(4));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(64, original.CarRaceCollision.carRaceCollision(8));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(100, original.CarRaceCollision.carRaceCollision(10));
	}

	
}