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

import java.util.HashMap;
import java.util.Map;



/**
 * A ServerHandler that relays events to different server handlers, depending on the current state.
 */

public class StateMultiplexer implements ServerHandler {
	
	private Map<Object, ServerHandler> handlers;
	private Object state;
	
	
	
	/**
	 * Constructs a new StateMultiplexer.
	 */
	
	public StateMultiplexer() {
		handlers = new HashMap<Object, ServerHandler>();
		state = null;
	}
	
	
	
	/**
	 * Adds a new state and a handler to handle all events in this state.
	 */
	
	public synchronized void addStateAndHandler(Object state, ServerHandler handler) {
		handlers.put(state, handler);
	}
	
	
	
	/**
	 * Sets the current state.
	 * 
	 * @param newState The new state. This state must have been registered previously, using the
	 *                 addStateAndHandler() method.
	 * 
	 * @throws IllegalArgumentException if the state has not previously been registered.
	 */
	
	public synchronized void setState(Object newState) {
		if (!handlers.containsKey(state)) {
			throw new IllegalArgumentException("Unknown state: " + state);
		}
		
		state = newState;
	}
	
	
	
	/**
	 * Implements ServerHandler.messageSent(). Relays the event.
	 * This method is called by the framework internally. Please don't call it yourself.
	 */
	
	public synchronized void messageSent(Node server, Object message) {
		ServerHandler handler;
		
		synchronized (this) {
			if (state == null) {
				throw new IllegalStateException("You must set a state before events can be handled.");
			}
			
			handler = handlers.get(state);
		}
		
		handler.messageSent(server, message);
	}
	
	
	
	/**
	 * Implements ServerHandler.messageReceived(). Relays the event.
	 * This method is called by the framework internally. Please don't call it yourself.
	 */
	
	public synchronized void messageReceived(Node server, Object message) {
		ServerHandler handler;
		
		synchronized (this) {
			if (state == null) {
				throw new IllegalStateException("You must set a state before events can be handled.");
			}
			
			handler = handlers.get(state);
		}
		
		handler.messageReceived(server, message);
	}
	
	
	
	/**
	 * Implements ServerHandler.exceptionCaught(). Relays the event.
	 * This method is called by the framework internally. Please don't call it yourself.
	 */
	
	public synchronized void exceptionCaught(Server server, Throwable cause) {
		ServerHandler handler;
		
		synchronized (this) {
			if (state == null) {
				throw new IllegalStateException("You must set a state before events can be handled.");
			}
			
			handler = handlers.get(state);
		}
		
		handler.exceptionCaught(server, cause);
	}
	
	
	
	/**
	 * Implements ServerHandler.connectionClosed(). Relays the event.
	 * This method is called by the framework internally. Please don't call it yourself.
	 */
	
	public synchronized void connectionClosed(Server server) {
		ServerHandler handler;
		
		synchronized (this) {
			if (state == null) {
				throw new IllegalStateException("You must set a state before events can be handled.");
			}
			
			handler = handlers.get(state);
		}
		
		handler.connectionClosed(server);
	}
}
