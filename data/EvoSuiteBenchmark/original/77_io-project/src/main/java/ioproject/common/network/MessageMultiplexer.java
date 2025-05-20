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



package ioproject.common.network;



import java.util.HashMap;
import java.util.Map;



/**
 * Takes messages from a single MessageHandler and distributes them to different handlers, depending on
 * message type.
 */

public class MessageMultiplexer implements MessageHandler {
	
	private MessageHandler defaultHandler;
	private Map<Class<?>, MessageHandler> handlers;
	
	
	
	/**
	 * Creates a new MessageMultiplexer instance.
	 * 
	 * @param theDefaultHandler The handler that is called for message types that no other handler has been
	 *                          registered for.
	 */
	
	public MessageMultiplexer(MessageHandler theDefaultHandler) {
		defaultHandler = theDefaultHandler;
		handlers = new HashMap<Class<?>, MessageHandler>();
	}
	
	
	
	/**
	 * Register a handler for a specific message type.
	 * 
	 * @param messageType The type of message that should be relayed to the handler.
	 * @param handler The handler to relay the messages to.
	 */
	
	public void addMessageHandler(Class<?> messageType, MessageHandler handler) {
		handlers.put(messageType, handler);
	}
	
	
	
	/**
	 * Passes the event to a registered handler, if one has been registered. If not, the event is passed to
	 * the default handler.
	 * This method is automatically called by the framework. Please don't call it.
	 */
	
	public void messageSent(Node node, Object message) {
		if (handlers.containsKey(message.getClass())) {
			handlers.get(message.getClass()).messageSent(node, message);
		}
		else {
			defaultHandler.messageSent(node, message);
		}
	}
	
	
	
	/**
	 * Passes the event to a registered handler, if one has been registered. If not, the event is passed to
	 * the default handler.
	 * This method is automatically called by the framework. Please don't call it.
	 */
	
	public void messageReceived(Node node, Object message) {
		if (handlers.containsKey(message.getClass())) {
			handlers.get(message.getClass()).messageReceived(node, message);
		}
		else {
			defaultHandler.messageReceived(node, message);
		}
	}
}
