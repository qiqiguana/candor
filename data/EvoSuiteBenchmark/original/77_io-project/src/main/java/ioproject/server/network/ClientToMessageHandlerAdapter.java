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
 * A ClientHandler that wraps another ClientHandler and a MessageHandler, relaying all message-events to the
 * MessageHandler, the rest of the events to the ClientHandler.
 */

public class ClientToMessageHandlerAdapter implements ClientHandler {
	
	private MessageHandler messageHandler;
	private ClientHandler clientHandler;
	
	
	
	/**
	 * Constructs a new ClientToMessageHandlerAdapter.
	 * 
	 * @param aMessageHandler A MessageHandler that this handler will relay all message-events to.
	 * @param aClientHandler A ClientHandler that will receive all non-message events.
	 */
	
	public ClientToMessageHandlerAdapter(MessageHandler aMessageHandler, ClientHandler aClientHandler) {
		messageHandler = aMessageHandler;
		clientHandler = aClientHandler;
	}
	
	
	
	/**
	 * Implements ClientHandler.clientAdded() and relays it to the client handler.
	 */
	
	public void clientAdded(Client client) {
		clientHandler.clientAdded(client);
	}
	
	
	
	/**
	 * Implements ClientHandler.clientRemoved() and relays it to the client handler.
	 */
	
	public void clientRemoved(Client client) {
		clientHandler.clientRemoved(client);
	}
	
	
	
	/**
	 * Implements ClientHandler.messageSent() and relays it to the message handler.
	 */
	
	public void messageSent(Node client, Object message) {
		messageHandler.messageSent(client, message);
	}
	
	
	
	/**
	 * Implements ClientHandler.messageReceived() and relays it to the message handler.
	 */
	
	public void messageReceived(Node client, Object message) {
		messageHandler.messageReceived(client, message);
	}
	
	
	
	/**
	 * Implements ClientHandler.exceptionCaught() and relays it to the client handler.
	 */
	
	public void exceptionCaught(Client client, Throwable cause) {
		clientHandler.exceptionCaught(client, cause);
	}
}
