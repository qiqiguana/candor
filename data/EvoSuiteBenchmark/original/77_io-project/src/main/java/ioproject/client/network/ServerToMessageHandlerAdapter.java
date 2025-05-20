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
 * A ServerHandler that wraps another ServerHandler and a MessageHandler, relaying all message-events to the
 * MessageHandler, the rest of the events to the ServerHandler.
 */

public class ServerToMessageHandlerAdapter implements ServerHandler {
	
	private MessageHandler messageHandler;
	private ServerHandler serverHandler;
	
	
	
	/**
	 * Constructs a new ServerToMessageHandlerAdapter.
	 * 
	 * @param aMessageHandler A MessageHandler that this handler will relay all message-events to.
	 * @param aServerHandler A ServerHandler that will receive all non-message events.
	 */
	
	public ServerToMessageHandlerAdapter(MessageHandler aMessageHandler, ServerHandler aServerHandler) {
		messageHandler = aMessageHandler;
		serverHandler = aServerHandler;
	}
	
	
	
	/**
	 * Implements ServerHandler.messageSent() and relays it to the message handler.
	 */
	
	public void messageSent(Node server, Object message) {
		messageHandler.messageSent(server, message);
	}
	
	
	
	/**
	 * Implements ServerHandler.messageReceived() and relays it to the message handler.
	 */
	
	public void messageReceived(Node server, Object message) {
		messageHandler.messageReceived(server, message);
	}
	
	
	
	/**
	 * Implements ServerHandler.exceptionCaught() and relays it to the server handler.
	 */
	
	public void exceptionCaught(Server server, Throwable cause) {
		serverHandler.exceptionCaught(server, cause);
	}
	
	
	
	/**
	 * Implements ServerHandler.connectionClosed() and relays it to the server handler.
	 */
	
	public void connectionClosed(Server server) {
		serverHandler.connectionClosed(server);
	}
}
