package messages.global.test;

import static org.junit.Assert.*;
import messages.global.JoinSessionMsg;

import org.junit.Test;

public class JoinSessionMsgTest {
	
	
	JoinSessionMsg joinSessionMsg = new JoinSessionMsg("SessionName" , "playerName");

	@Test
	public void testJoinSessionMsg() {
		assertNotNull(joinSessionMsg);
	}

	@Test
	public void testExecute() {
		//joinSessionMsg.execute(bomberSrv, sender);
	}

}
