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



import org.junit.Before;
import org.junit.Test;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.*;



/**
 * Tests GlobalClientGroup.
 */

public class GlobalClientGroupTest {
	
	private GlobalClientGroup group;
	
	
	
	/**
	 * Prepares the test environment.
	 */
	
	@Before
	public void setUpTestEnvironment() {
		group = new GlobalClientGroup();
	}
	
	
	
	/**
	 * Try to add a client to the global client group, expect an exception to be thrown.
	 */
	
	@Test(expected = UnsupportedOperationException.class)
	public void addClientExpectException() {
		group.add(createMock(Client.class));
	}
	
	
	
	/**
	 * Try to remove a client from the global client group, expect an exception to be thrown.
	 */
	
	@Test(expected = UnsupportedOperationException.class)
	public void removeClientExpectException() {
		group.remove(createMock(Client.class));
	}
	
	
	
	/**
	 * Notify the group of a new client. Expect it to be added.
	 */
	
	@Test
	public void notifyNewClient() {
		Client client = createMock(Client.class);
		group.notifyClientConnected(client);
		assertTrue("Notified the global client group of a connected client, but client isn't in group.",
				group.contains(client));
	}
	
	
	
	/**
	 * Notify the group that a client has been disconnected. Expect it to be added.
	 */
	
	@Test
	public void notifyDisconnectedClient() {
		Client client = createMock(Client.class);
		group.notifyClientConnected(client);
		group.notifyClientDisconnected(client);
		assertFalse("Notified the global client group that a client has been disconnected, but client is"
				+ " still in the group.", group.contains(client));
	}
}
