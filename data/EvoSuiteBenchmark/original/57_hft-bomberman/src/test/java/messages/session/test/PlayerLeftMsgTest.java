package messages.session.test;

import static org.junit.Assert.*;
import messages.session.PlayerLeftMsg;

import org.junit.Test;

public class PlayerLeftMsgTest {
	PlayerLeftMsg playerLeftMsg = new PlayerLeftMsg();

	@Test
	public void testPlayerLeftMsg() {
		assertNotNull(playerLeftMsg);
	}

	@Test
	public void testExecute() {
		//playerLeftMsg.execute(session, sender);
	}

}
