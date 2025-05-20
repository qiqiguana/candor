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



import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoServiceConfig;
import org.easymock.IArgumentMatcher;
import org.junit.Before;
import org.junit.Test;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.*;



/**
 * Tests DefaultCommService.
 */

public class NetworkServiceTest {
	
	class WrappedGroupMatcher implements IArgumentMatcher {
		
		private ClientGroup wrappedGroup;
		
		WrappedGroupMatcher(ClientGroup expectedGroup) {
			wrappedGroup = expectedGroup;
		}
		
		public boolean matches(Object actual) {
			return ((ClientGroupHandlerAdapter)actual).getGroup() == wrappedGroup;
		}
		
		public void appendTo(StringBuffer buffer) {
			buffer.append("wrappedGroup(" + wrappedGroup + ")");
		}
	}
	
	
	
	/**
	 * Helper method for the EasyMock WrappedHelper matcher.
	 */
	
	private IoHandler wrappedGroup(ClientGroup wrappedGroup) {
		reportMatcher(new WrappedGroupMatcher(wrappedGroup));
		return null;
	}
	
	
	
	private IoAcceptor mockAcceptor;
	private IoAcceptor niceMockAcceptor;
	
	private InetSocketAddress address;
	



	/**
	 * Creates a mock IoAcceptor.
	 */
	
	@Before
	public void prepareMockAcceptor() {
		mockAcceptor = createMock(IoAcceptor.class);
		niceMockAcceptor = createNiceMock(IoAcceptor.class);
		
		address = new InetSocketAddress(3448);
	}



	/**
	 * Constructs the default comm service using a mock IoAcceptor. Do nothing and expect that nothing is
	 * done to the acceptor.
	 */
	
	@Test
	public void createServiceDoNothingExpectNothingToBeDone() {
		replay(mockAcceptor);
		
		new NetworkService(mockAcceptor);
		verify(mockAcceptor);
	}
	
	
	
	/**
	 * Constructs the default comm service using a mock IoAcceptor, connects it and makes sure the comm
	 * service binds the acceptor. Makes sure the passed InetSocketAddress object is used.
	 */
	
	@Test
	public void connectCommServiceMakeSureItBindsAcceptorWithCorrectAddress() throws IOException {
		mockAcceptor.bind(eq(address), isA(IoHandler.class), isA(IoServiceConfig.class));
		replay(mockAcceptor);
		
		NetworkService net = new NetworkService(mockAcceptor);
		net.connect(address);
		
		verify(mockAcceptor);
	}
	
	
	
	/**
	 * Constructs the network service using a mock IoAcceptor and connects it. Makes sure that an instance
	 * of ClientGroupHandlerAdapter is used.
	 */
	
	@Test
	public void connectNetworkServicweMakeSureItUsesThePassedHandler() throws IOException {
		mockAcceptor.bind(isA(InetSocketAddress.class), isA(ClientGroupHandlerAdapter.class),
				isA(IoServiceConfig.class));
		replay(mockAcceptor);
		
		NetworkService net = new NetworkService(mockAcceptor);
		net.connect(address);
		
		verify(mockAcceptor);
	}
	
	
	
	/**
	 * Constructs the default comm service using a mock IoAcceptor, connect and disconnects it, then makes
	 * sure the comm service unbinds the acceptor.
	 */
	
	@Test
	public void connectAndDisconnectMakeSureAcceptorIsUnbound() throws IOException {
		niceMockAcceptor.unbindAll();
		replay(niceMockAcceptor);
		
		NetworkService comm = new NetworkService(niceMockAcceptor);
		comm.connect(address);
		comm.disconnect();
		
		verify(niceMockAcceptor);
	}
	
	
	
	/**
	 * Connect several times, expect an exception to be thrown.
	 */
	
	@Test(expected = IllegalStateException.class)
	public void connectTwiceExpectException() throws IOException {
		NetworkService comm = new NetworkService(niceMockAcceptor);
		comm.connect(address);
		comm.connect(address);
	}
	
	
	
	/**
	 * Disconnect without having connected, expect an exception to be thrown.
	 */
	
	@Test(expected = IllegalStateException.class)
	public void disconnectFirstExpectException() throws IOException {
		NetworkService comm = new NetworkService(niceMockAcceptor);
		comm.disconnect();
	}
	
	
	
	/**
	 * Let the mock object throw an I/O exception and expect the net service to wrap it into a
	 * NetworkException and pass it on.
	 */
	
	@Test(expected = IOException.class)
	public void letMockCauseExceptionExpectException() throws IOException {
		mockAcceptor.bind(isA(InetSocketAddress.class), isA(IoHandler.class), isA(IoServiceConfig.class));
		expectLastCall().andThrow(new IOException());
		replay(mockAcceptor);
		
		NetworkService comm = new NetworkService(mockAcceptor);
		comm.connect(address);
	}
	
	
	/**
	 * Connect the service, pass null instead of an address. Expect an exception to be thrown.
	 */
	
	@Test(expected = NullPointerException.class)
	public void connectServicePassNullForAddressExpectException() throws IOException {
		NetworkService comm = new NetworkService(mockAcceptor);
		comm.connect(null);
	}
	
	
	
	/**
	 * Make sure the network service returns the global client group if asked for.
	 */
	
	@Test
	public void makeSureGlobalClientGroupIsReturned() {
		NetworkService comm = new NetworkService(mockAcceptor);
		assertTrue("Network service didn't return global client group.",
				comm.globalClientGroup() instanceof GlobalClientGroup);
	}
	
	
	
	/**
	 * Make sure that the global client group returned by the network service is the same one that is wrapped
	 * by the ClientGroupHandlerAdapter.
	 */
	
	@Test
	public void makeSureGlobalClientGroupIsTheSameAsWrappedByHandlerAdapter() throws IOException {
		NetworkService comm = new NetworkService(mockAcceptor);
		ClientGroup group = comm.globalClientGroup();
				
		mockAcceptor.bind(isA(InetSocketAddress.class), wrappedGroup(group), isA(IoServiceConfig.class));
		replay(mockAcceptor);
		
		comm.connect(address);
		
		verify(mockAcceptor);
	}
}

