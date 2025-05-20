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



import ioproject.common.network.Node;

import org.apache.mina.common.IoSession;



/**
 * Represents a client connection.
 */

public class Client implements Node {
	
	private IoSession session;
	
	
	
	/**
	 * Creates a new client which wraps the given session.
	 */
	
	Client(IoSession theSession) {
		session = theSession;
	}
	
	
	
	/**
	 * Implements Node.send(). Sends a message to the client.
	 * If the client has been disconnected before, this method will just do nothing and the message will not
	 * be sent. If it is important for you to know if a message has been sent or not, please use
	 * ClientHandler.messageSent().
	 * 
	 * @param message An arbitrary object. Must implement java.io.Serializable.
	 */
	
	public void send(Object message) {
		session.write(message);
	}
	
	
	
	/**
	 * Implements Node.disconncet(). Disconnects the client.
	 */
	
	public void disconnect() {
		session.close();
	}
	
	
	
	/**
	 * Stores an attribute using the given key.
	 */
	
	public Object attribute(String key, Object attribute) {
		return session.setAttribute(key, attribute);
	}
	
	
	
	/**
	 * Retrieves the attribute that was stored using the given key.
	 */
	
	public Object attribute(String key) {
		return session.getAttribute(key);
	}
}
