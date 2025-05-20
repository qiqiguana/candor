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



import ioproject.common.network.MessageHandler;
import ioproject.common.network.Node;



/**
 * Handles cliend-side network events.
 */

public interface ServerHandler extends MessageHandler {
	
	/**
	 * Called when a message has been sent.
	 * 
	 * @param server The server the message has been sent to.
	 * @param message The message.
	 */
	
	public void messageSent(Node server, Object message);
	
	
	
	/**
	 * Called when a message has been received.
	 * 
	 * @param server The server the message has been received from.
	 * @param message The message.
	 */
	
	public void messageReceived(Node server, Object message);
	
	
	
	/**
	 * Called when an error has occured.
	 * 
	 * @param server The server that caused the error or that the error was caused in connection with.
	 * @param cause The cause of the error.
	 */
	
	public void exceptionCaught(Server server, Throwable cause);
	
	
	
	/**
	 * Called when the connection to a server has been closed.
	 * 
	 * @param server The server that the connection has been closed with.
	 */
	
	public void connectionClosed(Server server);
}
