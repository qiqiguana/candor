package messages.global.test;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.net.Socket;

import messages.global.GlobalChatMsg;

import org.junit.Test;

import client.BomberClient;

import server.BomberServer;
import server.ClientInfo;

public class GlobalChatMsgTest {
	
	GlobalChatMsg globalChatMsg = new GlobalChatMsg("sender1", "msg");
	@Test
	public void testGlobalChatMsg(){
		assertNotNull(globalChatMsg);
	}

	@Test
	public void testExecuteBomberServerClientInfo() {
		BomberServer serv = BomberServer.getInstance();
		ClientInfo clientInfo= new ClientInfo(new Socket());
		clientInfo.setName("Client7");
		globalChatMsg.execute(serv, clientInfo);
		assertEquals("Client7",clientInfo.getName());
		
	}

	@Test
	public void testExecuteBomberClient() {
		BomberClient bomberClient=BomberClient.getInstance();
		globalChatMsg.execute(bomberClient);
		
	}

}
