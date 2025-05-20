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



import org.junit.Before;
import org.junit.Test;
import static org.easymock.classextension.EasyMock.*;



/**
 * Tests MessageMultiplexer.
 */

public class MessageMultiplexerTest {
	
	private MessageMultiplexer multiplexer;
	private MessageHandler defaultHandler;
	private MessageHandler handler;
	private Node node;
	private Object message;
	
	
	
	/**
	 * Sets up the test environment.
	 */
	
	@Before
	public void setUpTestEnvironment() {
		defaultHandler = createMock(MessageHandler.class);
		handler = createMock(MessageHandler.class);
		multiplexer = new MessageMultiplexer(defaultHandler);
		node = createMock(Node.class);
		message = new Object();
	}
	
	
	
	/**
	 * Don't register any message handlers, notify the handler of a sent message and expect it to be passed
	 * through to the default handler.
	 */
	
	@Test
	public void notifyOfSentMessageExpectItToBePassedThrough() {
		defaultHandler.messageSent(node, message);
		replay(defaultHandler);
		
		multiplexer.messageSent(node, message);
		
		verify(defaultHandler);
	}
	
	
	
	/**
	 * Don't register any message handlers, notify the handler of a received message and expect it to be
	 * passed through to the default handler.
	 */
	
	@Test
	public void notifyOfReceivedMessageExpectItToBePassedThrough() {
		defaultHandler.messageReceived(node, message);
		replay(defaultHandler);
		
		multiplexer.messageReceived(node, message);
		
		verify(defaultHandler);
	}
	
	
	
	/**
	 * Registers a handler for a message type. Notify the handler of a sent message and expect it to relay it
	 * to the handler.
	 */
	
	@Test
	public void notifyOfSentMessageExpectItToBeRelayedToHandler() {
		handler.messageSent(node, message);
		replay(handler);
		
		multiplexer.addMessageHandler(Object.class, handler);
		multiplexer.messageSent(node, message);
		
		verify(handler);
	}
	
	
	
	/**
	 * Registers a handler for a message type. Notify the handler of a received message and expect it to
	 * relay it to the handler.
	 */
	
	@Test
	public void notifyOfReceivedMessageExpectItToBeRelayedToHandler() {
		handler.messageReceived(node, message);
		replay(handler);
		
		multiplexer.addMessageHandler(Object.class, handler);
		multiplexer.messageReceived(node, message);
		
		verify(handler);
	}
}
