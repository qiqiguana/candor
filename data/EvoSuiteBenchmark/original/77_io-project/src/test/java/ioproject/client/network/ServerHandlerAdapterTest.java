/*
	Copyright (c) 2007, 2008 Hanno Braun <hannob@users.sourceforge.net>
	
	Permission to use, copy, modify, and/or distribute this software for any
	purpose with or without fee is hereby granted, provided that the above
	copyright notice and this permission notice appear in all copies.

	THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
	WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
	MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
	ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
	WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
	ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
	OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
*/



package ioproject.client.network;



import org.apache.mina.common.IoSession;
import org.junit.Before;
import org.junit.Test;
import static org.easymock.classextension.EasyMock.*;



/**
 * Tests ServerHandlerAdapter.
 */

public class ServerHandlerAdapterTest {
	
	private Object message;
	private ServerHandler handler;
	private ServerHandlerAdapter adapter;
	private IoSession session;
	private Server server;
	
	
	
	/**
	 * Sets up the test environment.
	 */
	
	@Before
	public void setUpTestEnvironment() {
		session = createMock(IoSession.class);
		server = createMock(Server.class);
		expect(session.getAttachment()).andReturn(server);
		replay(session);
		
		message = new Object();
		handler = createMock(ServerHandler.class);
		adapter = new ServerHandlerAdapter(handler);
	}
	
	
	
	/**
	 * Notifies the adapter of a sent message, expects it to notify the wrapped ServerHandler.
	 */
	
	@Test
	public void notifyMessageSentExpectHandlerNotification() throws Exception {
		handler.messageSent(server, message);
		replay(handler);
		
		adapter.messageSent(session, message);
		
		verify(handler);
	}
	
	
	
	/**
	 * Notifies the adapter of a received message, expects it to notify the wrapped ServerHandler.
	 */
	
	@Test
	public void notifyMessageReceivedExpectHandlerNotification() throws Exception {
		handler.messageReceived(server, message);
		replay(handler);
		
		adapter.messageReceived(session, message);
		
		verify(handler);
	}
	
	
	
	/**
	 * Notifies the adapter of a caught throwable, expects it to notify the wrapped ServerHandler.
	 */
	
	@Test
	public void notifyCaughThrowableExpectHandlerNotification() throws Exception {
		Throwable throwable = new Throwable();
		
		handler.exceptionCaught(server, throwable);
		replay(handler);
		
		adapter.exceptionCaught(session, throwable);
		
		verify(handler);
	}
	
	
	
	/**
	 * Notifies the adapter of a closed session, expects it to notify the wrapped ServerHandler.
	 */
	
	@Test
	public void notifiyClosedSessionExpectHandlerNotification() throws Exception {
		handler.connectionClosed(server);
		replay(handler);
		
		adapter.sessionClosed(session);
		
		verify(handler);
	}
}
