package server.test;

import static org.junit.Assert.*;

import java.net.Socket;

import org.junit.Test;

import server.ClientInfo;
import server.network.ServerMsgReceiver;
import server.network.ServerMsgSender;



public class ClientInfoTest {
	Socket socket = new Socket();
	ClientInfo clientInfo1 = new ClientInfo(socket);
	ClientInfo clientInfo2 = new ClientInfo(socket);
	@Test
	public void testClientInfo() {
		assertNotNull(clientInfo1);
	}

	@Test
	public void testSetName() {
		clientInfo1.setName("clientName1");
		clientInfo2.setName("clientName2");
	}

	@Test
	public void testGetName() {
		assertTrue(clientInfo1.getName() == "clientName1");
		assertEquals(clientInfo2.getName(), "clientName2");
		assertNotSame(clientInfo1.getName(), clientInfo2.getName());
	}

	@Test
	public void testGetSocket() {
		assertNotNull(clientInfo1.getSocket());
		assertNotNull(clientInfo2.getSocket());
		
	}

	@Test
	// liefert null objekt
	public void testGetGameSession() {
		assertNull(clientInfo1.getGameSession());
	}

	@Test
	public void testGetIdOffset() {
		assertNotNull(clientInfo1.getIdOffset());
	}

	@Test
	public void testGetMsgSender() {
		assertNotNull(clientInfo1.getMsgSender());
	}

	@Test
	public void testSendMsg() {
		fail("Not yet implemented");
	}

	@Test
	public void testJoinGameSession() {
		fail("Not yet implemented");
	}

	@Test
	public void testLeaveGameSession() {
		fail("Not yet implemented");
	}

}
