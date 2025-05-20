package messages.global.test;

import static org.junit.Assert.*;

import messages.global.JoinAckMsg;

import org.junit.Test;

import client.BomberClient;

public class JoinAckMsgTest {
	JoinAckMsg joinAckMsg = new JoinAckMsg(true , 3);

	@Test
	public void testJoinAckMsg() {
		JoinAckMsg joinAckMsg2 = new JoinAckMsg(false , 5);
		assertNotNull(joinAckMsg);
		assertNotNull(joinAckMsg2);
		assertNotSame(joinAckMsg, joinAckMsg2);
		
	}

	@Test
	public void testExecute() {
		BomberClient bomberClient =BomberClient.getInstance();
		joinAckMsg.execute(bomberClient);
		
	}

}
