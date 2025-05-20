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
import java.net.SocketAddress;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationDecoder;
import org.apache.mina.filter.codec.serialization.ObjectSerializationEncoder;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;



/**
 * The network service. Responsible for network connectivity.
 */

public class NetworkService {

	private IoAcceptor acceptor;
	private boolean connected;
	
	private GlobalClientGroup globalClientGroup;
	
	

	/**
	 * Public constructor.
	 * Calls the protected constructor, providing it with an IoAcceptor implementation.
	 */
	
	public NetworkService() {
		this(new SocketAcceptor());
	}
	
	
	
	/**
	 * Constructs a new network service using a predefined IoAcceptor. This can be used for testing this
	 * class using a mock object.
	 */
	
	NetworkService(IoAcceptor anAcceptor) {
		acceptor = anAcceptor;
		connected = false;
		
		globalClientGroup = new GlobalClientGroup();
	}
	
	
	
	/**
	 * Returns the global client group. The global client group always contains all currently connected
	 * clients.
	 * 
	 * @return The global client group.
	 */
	
	public ClientGroup globalClientGroup() {
		return globalClientGroup;
	}
	
	
	
	/**
	 * Opens a server connection.
	 * This method must be called before the network service can accept connections.
	 *
	 * @param address Specifies the address and port to listen on.
	 *
	 * @throws IOException if a connection can't be established.
	 * @throws IllegalStateException if the service is already connected.
	 * @throws NullPointerException if one of the parameters is null.
	 */
	
	public synchronized void connect(SocketAddress address) throws IOException {
		// Check the parameter. It must not be null.
		if (address == null) {
			throw new NullPointerException("Parameters must not be null.");
		}
		
		// Check the connection status. If connect has already been called, an exception must be thrown.
		if (connected) {
			throw new IllegalStateException("Already connected.");
		}
		
		// Configure the acceptor and bind it to the specified address. Configuration consists of adding the
		// protocol codec filter with our custom codecs into the filter chain.
		SocketAcceptorConfig config = new SocketAcceptorConfig();
		config.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationEncoder(),
				new ObjectSerializationDecoder()));
		acceptor.bind(address, new ClientGroupHandlerAdapter(globalClientGroup), config);
		
		connected = true;
	}
	
	
	
	/**
	 * Disconnects the network service.
	 *
	 * @throws IllegalStateException if the service is not connected.
	 */
	
	public synchronized void disconnect() {
		if (!connected) {
			throw new IllegalStateException("Not connected.");
		}
		
		acceptor.unbindAll();
		
		connected = false;
	}
}

