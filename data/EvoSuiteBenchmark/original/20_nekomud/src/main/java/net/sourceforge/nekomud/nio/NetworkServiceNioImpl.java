/*
 * NetworkServiceNioImpl.java
 * Copyright 2008-2009 Patrick Meade
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sourceforge.nekomud.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sourceforge.nekomud.Configuration;
import net.sourceforge.nekomud.service.NetworkService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkServiceNioImpl implements NetworkService
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public NetworkServiceNioImpl() {
		connections = Collections.synchronizedList(new ArrayList<Connection>());
	}

	public List<Connection> getConnections() {
		return connections;
	}
	
	public void start() {
		// in case of IOException; seek medical attention
		try {
			// create the selector
			selector = Selector.open();
			// create the server socket
			serverSocketChannel = ServerSocketChannel.open();
			// ensure that it doesn't block
			serverSocketChannel.configureBlocking(false);
			// bind the server socket to the local host and port
			InetAddress localHost = InetAddress.getLocalHost();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(localHost, configuration.getPort());
			serverSocketChannel.socket().bind(inetSocketAddress);
			// register the server socket with the selector
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			// create a worker thread to handle I/O
			selectionThread = new SelectionThread(selector, connections);
			// start the worker thread running
			new Thread(selectionThread).start();
		} catch(IOException e) {
			logger.warn("IOException: " + e.getMessage());
		}
	}
	
	public void stop() {
		selectionThread.stop();
		while(selectionThread.hasStopped() == false) {
			// spin wait
		}
		for(Connection c : connections) {
			try { c.getChannel().close(); } catch(Exception e) { }
		}
		connections.clear();
		try { serverSocketChannel.close(); } catch(Exception e) { }
		try { selector.close(); } catch(Exception e) { }
	}
	
	private List<Connection> connections;

	private SelectionThread selectionThread;

	private Selector selector;
	
	private ServerSocketChannel serverSocketChannel;

	public void setConfiguration(Configuration configuration) { 
		this.configuration = configuration;
	}
	private Configuration configuration;
}

class SelectionThread implements Runnable
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public SelectionThread(Selector selector, Collection<Connection> connections) {
		this.connections = connections;
		this.selector = selector;
	}
	
	public void run() {
		finished = false;
		while(finished == false) {
			// in case of IOException; seek medical attention
			try {
				// block until a channel is ready for I/O
				int numKeys = selector.select(10000);
				// obtain the keys ready for I/O operations
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				// for each key in the set of ready keys 
				for(Iterator<SelectionKey> i = selectedKeys.iterator(); i.hasNext();) {
					// obtain the next key to be processed
					SelectionKey selectionKey = i.next();
					// remove it from the set of keys to be processed
					i.remove();
					// if this key refers to a channel accepting connections
					if(selectionKey.isAcceptable()) {
						// obtain the ServerSocketChannel ready to accept a connection
						ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
						// obtain the SocketChannel servicing the connection
						SocketChannel socketChannel = serverSocketChannel.accept();
						// set the blocking mode on the channel
						socketChannel.configureBlocking(false);
						// create a connection object for that channel
						Connection connection = new Connection(socketChannel);
						// add the connection to the set of connections
						connections.add(connection);
						// register the new channel with the selector,
						// and the connection object with the key
						socketChannel.register(selector, SelectionKey.OP_READ, connection);
					}
					// if this key refers to a channel ready to write
					if(selectionKey.isWritable()) {
						// obtain the connection object this key is registered with
						Connection connection = (Connection)selectionKey.attachment();
						// tell the connection to handle it's channel
						connection.handleWrite(selectionKey);
					}
					// if this key refers to a channel ready to read
					if(selectionKey.isReadable()) {
						// obtain the connection object this key is registered with
						Connection connection = (Connection)selectionKey.attachment();
						// tell the connection to handle it's channel
						connection.handleRead(selectionKey);
					}
				}
			} catch(IOException e) {
				logger.warn("IOException: " + e.getMessage());
			}
		}
		stopped = true;
	}

	public void stop() {
		finished = true;
		selector.wakeup();
	}
	
	public boolean hasStopped() {
		return stopped;
	}
	
	private Collection<Connection> connections;

	private boolean finished;
	
	private boolean stopped;

	private Selector selector;
}
