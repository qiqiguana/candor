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



import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.IoConnector;
import org.apache.mina.common.IoConnectorConfig;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.RuntimeIOException;
import org.easymock.IArgumentMatcher;
import org.junit.Before;
import org.junit.Test;
import static org.easymock.classextension.EasyMock.*;



/**
 * Tests Server.
 */

public class ServerTest {
	
	class WrappedHandlerMatcher implements IArgumentMatcher {
		
		private ServerHandler handler;
		
		WrappedHandlerMatcher(ServerHandler expectedHandler) {
			handler = expectedHandler;
		}
		
		public boolean matches(Object actual) {
			return ((ServerHandlerAdapter)actual).wrappedHandler() == handler;
		}
		
		public void appendTo(StringBuffer buffer) {
			buffer.append("wrappedHandler(" + handler + ")");
		}
	}
	
	
	
	private SocketAddress address;
	private ServerHandler handler;
	private ConnectFuture future;
	private IoConnector ioConnector;
	private Server connector;
	private IoSession session;
	private Object message;
	
	
	
	/**
	 * Helper method for the EasyMock WrappedHelper matcher.
	 */
	
	private IoHandler wrappedHandler(ServerHandler wrappedHandler) {
		reportMatcher(new WrappedHandlerMatcher(wrappedHandler));
		return null;
	}
	
	
	
	/**
	 * Sets up the test environment.
	 */
	
	@Before
	public void setUpTestEnvironment() {
		address = new InetSocketAddress("localhost", 3448);
		handler = createMock(ServerHandler.class);
		future = createNiceMock(ConnectFuture.class);
		ioConnector = createMock(IoConnector.class);
		connector = new Server(ioConnector);
		session = createMock(IoSession.class);
		message = new Object();
	}
	
	
	
	/**
	 * Connect, verify that the correct address is passed to the IoConnector.
	 */
	
	@Test
	public void connectVerifyAddressIsPassed() throws IOException {
		ioConnector.connect(eq(address), isA(IoHandler.class), isA(IoConnectorConfig.class));
		expectLastCall().andReturn(future);
		replay(ioConnector);
		
		connector.connect(address, handler);
		
		verify(ioConnector);
	}
	
	
	
	/**
	 * Connect, verify that the correct handler is passed.
	 */
	
	@Test
	public void connectVerifyHandlerIsPassed() throws IOException {
		ioConnector.connect(isA(SocketAddress.class), wrappedHandler(handler), isA(IoConnectorConfig.class));
		expectLastCall().andReturn(future);
		replay(ioConnector);
		
		connector.connect(address, handler);
		
		verify(ioConnector);
	}
	
	
	
	/**
	 * Connects and disconnects, expects the connector to be disconnected.
	 */
	
	@Test
	public void connectDisconnectVerifyIsDisconnected() throws IOException {
		expect(session.close()).andReturn(null);
		replay(session);
		
		expect(future.getSession()).andReturn(session);
		replay(future);
				
		ioConnector.connect(isA(SocketAddress.class), isA(IoHandler.class),	isA(IoConnectorConfig.class));
		expectLastCall().andReturn(future);
		replay(ioConnector);
		
		connector.connect(address, handler);
		connector.disconnect();
		
		verify(session);
	}
	
	
	
	/**
	 * Let the connect fail, expect an exception to be thrown.
	 */
	
	@Test(expected = IOException.class)
	public void connectFailExpectException() throws IOException {
		expect(future.getSession()).andThrow(new RuntimeIOException());
		replay(future);
		
		ioConnector.connect(isA(SocketAddress.class), isA(IoHandler.class), isA(IoConnectorConfig.class));
		expectLastCall().andReturn(future);
		replay(ioConnector);
		
		connector.connect(address, handler);
	}
	
	
	
	/**
	 * Makes sure the connector joins the connect future before retrieving the session.
	 */
	
	@Test
	public void connectVerifyFutureIsJoined() throws IOException {
		future.join();
		replay(future);
		
		ioConnector.connect(isA(SocketAddress.class), isA(IoHandler.class), isA(IoConnectorConfig.class));
		expectLastCall().andReturn(future);
		replay(ioConnector);
		
		connector.connect(address, handler);
	}
	
	
	
	/**
	 * Connect twice, expect an exception.
	 */
	
	@Test(expected = IllegalStateException.class)
	public void connectTwiceExpectException() throws IOException {
		expect(future.getSession()).andReturn(session);
		replay(future);
				
		ioConnector.connect(isA(SocketAddress.class), isA(IoHandler.class), isA(IoConnectorConfig.class));
		expectLastCall().andReturn(future);
		replay(ioConnector);
		
		connector.connect(address, handler);
		connector.connect(address, handler);
	}
	
	
	
	/**
	 * Disconnect without having connected. Expect exception to be thrown.
	 */
	
	@Test(expected = IllegalStateException.class)
	public void disconnectWithoutConnectExpectException() {
		connector.disconnect();
	}
	
	
	
	/**
	 * Connect and send a message. Verify that the message was written to the session.
	 */
	
	@Test
	public void sendMessageVerifyResult() throws IOException {
		expect(session.write(eq(message))).andReturn(null);
		replay(session);
		
		expect(future.getSession()).andReturn(session);
		replay(future);
				
		ioConnector.connect(isA(SocketAddress.class), isA(IoHandler.class), isA(IoConnectorConfig.class));
		expectLastCall().andReturn(future);
		replay(ioConnector);
		
		connector.connect(address, handler);
		connector.send(message);
		
		verify(session);
	}
	
	
	
	/**
	 * Send without being connected, expect an exception.
	 */
	
	@Test(expected = IllegalStateException.class)
	public void sendDisconnectedExpectException() {
		connector.send(message);
	}
}
