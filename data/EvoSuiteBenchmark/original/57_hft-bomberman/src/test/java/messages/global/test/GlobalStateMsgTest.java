package messages.global.test;

import static org.junit.Assert.*;
import messages.global.GlobalStateMsg;

import org.junit.Test;

import client.BomberClient;
import client.gui.LoginDialog;
import client.gui.StartFrame;

public class GlobalStateMsgTest {

	GlobalStateMsg 	globalStateMsg = new GlobalStateMsg(3);  
	
	@Test
	public void testGlobalStateMsg() {
		assertNotNull(globalStateMsg);
	}
	
	@Test
	public void testExecute3() {
		BomberClient bomberClient = BomberClient.getInstance();
		globalStateMsg.execute(bomberClient);
		
		
	}

	@Test
	public void testExecute4() {
		GlobalStateMsg 	globalStateMsg4 = new GlobalStateMsg(4);
		BomberClient bomberClient = BomberClient.getInstance();
		globalStateMsg4.execute(bomberClient);
	//	assertTrue(LoginDialog.init().loginUsernameTaken());
		
	}
	@Test
	public void testExecute5() {
		GlobalStateMsg 	globalStateMsg5 = new GlobalStateMsg(5);
		BomberClient bomberClient = BomberClient.getInstance();
		globalStateMsg5.execute(bomberClient);
		LoginDialog.init().loginWrongPassword();
		
	}
	@Test
	public void testExecute6() {
		GlobalStateMsg 	globalStateMsg6 = new GlobalStateMsg(6);
		BomberClient bomberClient = BomberClient.getInstance();
		globalStateMsg6.execute(bomberClient);
		LoginDialog.init().loginWrongUsername();
		
	}
	@Test
	public void testExecute7() {
		GlobalStateMsg 	globalStateMsg7 = new GlobalStateMsg(7);
		BomberClient bomberClient = BomberClient.getInstance();
		globalStateMsg7.execute(bomberClient);
		LoginDialog.init().loginUserStillLogedIn();
		
	}

}
