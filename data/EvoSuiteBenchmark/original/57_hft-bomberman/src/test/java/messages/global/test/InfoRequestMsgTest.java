package messages.global.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import messages.global.InfoRequestMsg;
import java.net.Socket;

import org.junit.Test;

import server.BomberServer;
import server.ClientInfo;

public class InfoRequestMsgTest {

	InfoRequestMsg infoRequestMsg1 = new InfoRequestMsg(1);
	InfoRequestMsg infoRequestMsg2 = new InfoRequestMsg(2);
	@Test
	public void testInfoRequestMsg() {
		assertNotNull(infoRequestMsg1);
		assertNotNull(infoRequestMsg2);
	}

	@Test
	public void testGetSessionList() {
		assertEquals(infoRequestMsg1.GET_SESSION_LIST, 1);
		assertEquals(infoRequestMsg2.GET_SESSION_LIST, 2);
	}
	
	/*@Test
	public void testGetMapList() {
		assertEquals(infoRequestMsg.GET_MAP_LIST, 2);
	}

	@Test
	public void testSetTypeInteger() {
		InfoRequestMsg infoRequestMsg1 = new InfoRequestMsg(6);
		InfoRequestMsg infoRequestMsg2 = new InfoRequestMsg(6);
		assertEquals(infoRequestMsg1.getTypeInteger(), 5);
		assertEquals(infoRequestMsg2.getTypeInteger(), 3);
		assertNotSame(infoRequestMsg1.getTypeInteger(), infoRequestMsg2.getTypeInteger());
	}

	@Test
	public void testGetObjectID() {
		infoRequestMsg.setObjectID(1);
		assertEquals(infoRequestMsg.getObjectID(), 1);
	}

	@Test
	public void testSetObjectID() {
		infoRequestMsg.setObjectID(5);
		infoRequestMsg.setObjectID(6);
		assertEquals(infoRequestMsg.getObjectID(), 6);
		
	}*/

	
	@Test
	public void testExecute() {
		Socket socket = new Socket();
		ClientInfo clientInfo =new ClientInfo(socket);
		BomberServer bomberServer = BomberServer.getInstance();
		infoRequestMsg1.execute(bomberServer, clientInfo);
		//assertNotNull();
	}

}

