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



import ioproject.common.network.Node;

import java.io.IOException;
import java.net.SocketAddress;

import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.IoConnector;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.RuntimeIOException;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationDecoder;
import org.apache.mina.filter.codec.serialization.ObjectSerializationEncoder;
import org.apache.mina.transport.socket.nio.SocketConnector;
import org.apache.mina.transport.socket.nio.SocketConnectorConfig;



/**
 * Represents a connection to a server.
 */

public class Server implements Node {
	
	private IoConnector connector;
	private IoSession session;
	
	private boolean connected;
	
	
	
	/**
	 * Creates a new Server instance. You need to actually connect to a server first using the connect()
	 * method before you can send and receive messages.
	 */
	
	public Server() {
		this(new SocketConnector());
	}
	
	
	
	/**
	 * Constructs a new Server with a pre-defined IoConnector instance. Used only for testing.
	 */
	
	Server(IoConnector aConnector) {
		connector = aConnector;
		connected = false;
	}
	
	
	
	/**
	 * Opens a connection to a server.
	 * 
	 * @param address The address of the server.
	 * @param handler The handler that will handle all network events.
	 * 
	 * @throws IOException if something goes wrong with the network connection.
	 * @throws IllegalStateException if the connector has already been connected.
	 */
	
	public synchronized void connect(SocketAddress address, ServerHandler handler)
			throws IOException {
		if (connected) {
			throw new IllegalStateException("Already connected.");
		}
		
		try {
			// Create a new socket connector configuration. Add the protocol codec to this configuration's
			// filter chain.
			SocketConnectorConfig config = new SocketConnectorConfig();
			config.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationEncoder(),
				new ObjectSerializationDecoder()));
			
			ConnectFuture future = connector.connect(address, new ServerHandlerAdapter(handler), config);
			future.join();
			session = future.getSession();
		}
		catch (RuntimeIOException e) {
			throw new IOException("Exception while connecting.", e);
		}
		
		connected = true;
	}
	
	
	
	/**
	 * Implements Node.send(). Sends a message to the server.
	 * 
	 * @param message The message. Must implement serializable.
	 */
	
	public void send(Object message) {
		synchronized (this) {
			if (!connected) {
				throw new IllegalStateException("Not connected.");
			}
		}
		
		session.write(message);
	}
	
	
	
	/**
	 * Implements Node.disconnect(). Disconnects from the server.
	 * 
	 * @throws IllegalStateException if there is no connection to the server.
	 */
	
	public void disconnect() {
		synchronized (this) {
			if (!connected) {
				throw new IllegalStateException("Not connected.");
			}
		}
		
		session.close();
	}
}
