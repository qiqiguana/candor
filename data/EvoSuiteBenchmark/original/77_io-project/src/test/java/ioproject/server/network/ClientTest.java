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



import org.apache.mina.common.IoSession;
import org.junit.Before;
import org.junit.Test;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.assertEquals;



/**
 * Tests Client.
 */

public class ClientTest {
	
	private Object message;
	private IoSession session;
	private Client client;
	
	private String key;
	private Object attribute;
	
	
	
	/**
	 * Sets up the test environment.
	 */
	
	@Before
	public void setUpTestEnvironment() {
		message = new Object();
		session = createMock(IoSession.class);
		client = new Client(session);
		
		key = "test";
		attribute = new Object();
	}
	
	
	
	/**
	 * Sends a message, expects it to be passed on to the wrapped IoSession.
	 */
	
	@Test
	public void sendMessageExpectItToBeWrittenToSession() {
		session.write(message);
		expectLastCall().andReturn(null);
		replay(session);
		
		client.send(message);
		
		verify(session);
	}
	
	
	
	/**
	 * Disconnects the client, expect the session to be closed.
	 */
	
	@Test
	public void disconnectClientExpcetSessionToBeClosed() {
		session.close();
		expectLastCall().andReturn(null);
		replay(session);
		
		client.disconnect();
		
		verify(session);
	}
	
	
	
	/**
	 * Store and retrieve attribute, expect it to work.
	 */
	
	@Test
	public void storeAndRetrieveAttributeVerifyResult() {
		expect(session.setAttribute(key, attribute)).andReturn(null);
		expect(session.getAttribute(key)).andReturn(attribute);
		replay(session);
		
		client.attribute(key, attribute);
		
		assertEquals("Retrieved attribute doesn't match stored attribute.", attribute,
				client.attribute(key));
	}
	
	
	
	/**
	 * Store an attribute, then overwrite it. Expect the first attribute to be returned.
	 */
	
	@Test
	public void storeAndOverwriteAttributeExpectFirstAttributeToBeReturned() {
		Object attribute2 = new Object();
		
		expect(session.setAttribute(key, attribute)).andReturn(null);
		expect(session.setAttribute(key, attribute2)).andReturn(attribute);
		replay(session);
		
		client.attribute(key, attribute);
		
		assertEquals("Attribute isn't returned when overwritten.", attribute,
				client.attribute(key, attribute2));
	}
}
