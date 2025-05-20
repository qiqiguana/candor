package messages.global.test;

import static org.junit.Assert.*;

import org.junit.Test;
import messages.global.LoginMsg;
public class LoginMsgTest {
		
	LoginMsg loginMsg = new LoginMsg("userName" , "password" , 5);
	LoginMsg loginMsg2 = new LoginMsg("userName3" , "password3" , 9);

	@Test
	public void testLoginMsg() {
		assertNotNull(loginMsg);
	}

	@Test
	public void testGetUsername() {
		assertEquals(loginMsg.getUsername(), "userName");
		assertNotSame(loginMsg.getUsername(), loginMsg2.getUsername());
	}

	@Test
	public void testSetUsername() {
		loginMsg.setUsername("userName1");
		assertEquals(loginMsg.getUsername(), "userName1");
	}

	@Test
	public void testGetPassword() {
		assertEquals(loginMsg.getPassword(), "password");
		assertNotSame(loginMsg.getPassword(), loginMsg2.getPassword());
	}

	@Test
	public void testSetPassword() {
		loginMsg.setPassword("password1");
		assertEquals(loginMsg.getPassword(), "password1");
	}

	@Test
	public void testGetAction() {
		assertEquals(loginMsg.getAction(), 5);
		assertNotSame(loginMsg.getAction(), loginMsg2.getAction());
	}

	@Test
	public void testSetAction() {
		loginMsg.setAction(8);
		assertEquals(loginMsg.getAction(),8);
	}

	@Test
	public void testExecute() {
		//fail("Not yet implemented");
	}

}
