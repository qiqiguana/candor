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



import ioproject.common.network.MessageHandler;
import ioproject.common.network.Node;



/**
 * Handles all client-events for a given group of clients.
 */

public interface ClientHandler extends MessageHandler {
	
	/**
	 * Called by a client group when a client has been added to the group.
	 * 
	 * @param client The client that has been added to the group.
	 */
	
	public void clientAdded(Client client);
	
	
	
	/**
	 * Called by a client group when a client has been removed from the group.
	 * 
	 * @param client The client that has been removed from the group.
	 */
	
	public void clientRemoved(Client client);
	
	
	
	/**
	 * Called by a client group when a message has been sent to a client in that group.
	 * 
	 * @param client The client the message has been sent to.
	 * @param message The message.
	 */
	
	public void messageSent(Node client, Object message);
	
	
	
	/**
	 * Called by a client group when a message has been received from a client in that group.
	 * 
	 * @param client The client the message has been received from.
	 * @param message The message.
	 */
	
	public void messageReceived(Node client, Object message);
	
	
	
	/**
	 * Called by a client group when a client in that group caused an exception.
	 * 
	 * @param client The client that caused the exception.
	 * @param cause The exception.
	 */
	
	public void exceptionCaught(Client client, Throwable cause);
}
