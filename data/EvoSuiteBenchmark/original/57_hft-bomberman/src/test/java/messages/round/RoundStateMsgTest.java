package messages.round;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoundStateMsgTest {
	RoundStateMsg roundStateMsg1 = new RoundStateMsg(1);
	RoundStateMsg roundStateMsg2 = new RoundStateMsg(1);
	
	@Test
	public void testRoundStateMsg() {
		assertNotNull(roundStateMsg1);
		assertNotNull(roundStateMsg2);
		assertNotSame(roundStateMsg1, roundStateMsg2);
	}

	@Test
	public void testExecute() {
		//fail("Not yet implemented");
	}

}
