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



package ioproject.server.network;



import org.apache.mina.common.IoSession;
import org.junit.Before;
import org.junit.Test;
import static org.easymock.classextension.EasyMock.*;



/**
 * Tests ClientGroupHandlerAdapter.
 */

public class ClientGroupHandlerAdapterTest {
	
	private IoSession session;
	private Client client;
	private Object message;
	private GlobalClientGroup group;
	private ClientGroupHandlerAdapter adapter;
	
	
	
	/**
	 * Sets up the test environment.
	 */
	
	@Before
	public void setUpTestEnvironment() {
		session = createMock(IoSession.class);
		client = new Client(session);
		expect(session.getAttachment()).andReturn(client);
		replay(session);
		
		message = new Object();
		group = createMock(GlobalClientGroup.class);
		adapter = new ClientGroupHandlerAdapter(group);
	}
	
	
	
	/**
	 * Notifies the adapter of a new connection, expect the notification to be passed on to the handler.
	 */
	
	@Test
	public void notifyClientConnectedExpectHandlerNotification() {
		group.notifyClientConnected(isA(Client.class));
		replay(group);

		session = createMock(IoSession.class);
		adapter.sessionOpened(session);
		
		verify(group);
	}
	
	
	
	/**
	 * Notifies the adapter of a sent message, expect the notification to be passed on to the handler.
	 */
	
	@Test
	public void notifySentMessageExpectHandlerNotification() {
		expect(group.notifyMessageSent(client, message)).andReturn(true);
		replay(group);
		
		adapter.messageSent(session, message);
		
		verify(group);
	}
	
	
	
	/**
	 * Notifies the adapter of a received message, expect the notification to be passed on to the handler.
	 */
	
	@Test
	public void notifyReceivedMessageExpectHandlerNotification() {
		expect(group.notifyMessageReceived(client, message)).andReturn(true);
		replay(group);
		
		adapter.messageReceived(session, message);
		
		verify(group);
	}
	
	
	
	/**
	 * Notifies the adapter of a caught exception, expect the notification to be passed on to the handler.
	 */
	
	@Test
	public void notifyCaughtExceptionExpectHandlerNotification() {
		Throwable cause = new Throwable();
		
		expect(group.notifyExceptionCaught(client, cause)).andReturn(true);
		replay(group);
		
		adapter.exceptionCaught(session, cause);
		
		verify(group);
	}
	
	
	
	/**
	 * Notifies the adapter of a closed session, expect the notification to be passed on to the handler.
	 */
	
	@Test
	public void notifySessionClosedExpectHandlerNotification() {
		group.notifyClientDisconnected(client);
		replay(group);
		
		adapter.sessionClosed(session);
		
		verify(group);
	}
}
