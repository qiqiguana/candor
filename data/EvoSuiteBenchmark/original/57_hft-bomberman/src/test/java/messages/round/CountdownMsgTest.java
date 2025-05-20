package messages.round;

import static org.junit.Assert.*;

import org.junit.Test;

public class CountdownMsgTest {
	CountdownMsg countdownMsg = new CountdownMsg(20);

	@Test
	public void testCountdownMsg() {
		assertNotNull(countdownMsg);

	}

	@Test
	public void testExecute() {
	//	fail("Not yet implemented");
	}

}
