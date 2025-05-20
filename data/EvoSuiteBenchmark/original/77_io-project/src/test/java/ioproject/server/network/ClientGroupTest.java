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



import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.*;



/**
 * Tests ClientGroup.
 */

public class ClientGroupTest {
	
	private Client client;
	private ClientGroup parentGroup;
	private ClientGroup group;
	
	
	
	/**
	 * Sets up the test environment.
	 */
	
	@Before
	public void setUpTestEnvironment() {
		client = createMock(Client.class);
		parentGroup = createMock(ClientGroup.class);
		group = new ClientGroup(parentGroup);
	}
	
	
	
	/**
	 * Cast the group to Iterable<Client> and expect it to work.
	 */
	
	@Test
	public void createClientGroupWithMockParentCastToIterableExpectItToWork() {
		Iterable<Client> clientIterable = (Iterable<Client>)group;
	}
	
	
	
	/**
	 * Add a client and expect the group to call contains() on the parent group with the client as parameter.
	 */
	
	@Test
	public void addClientExpectGroupToCheckIfParentGroupContains() {
		expect(parentGroup.contains(eq(client))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		
		verify(parentGroup);
	}
	
	
	
	/**
	 * Add a client that is contained in the mock parent group. Expect the add() method to return true.
	 */
	
	@Test
	public void addClientThatIsMemberOfParentExpectReturnTrue() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		assertTrue("Client should have been successfully added but method didn't return true.",
				group.add(client));
	}
	
	
	
	/**
	 * Add a client that is not a member of the parent group. Expect add() to return false.
	 */
	
	@Test
	public void addClientThatIsNotMemberOfParentExpectReturnFalse() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(false);
		replay(parentGroup);
		
		assertFalse("Client should not have been added but method didn't return false.",
				group.add(client));
	}
	
	
	
	/**
	 * Add a client that is a member of the parent group. Expect the group to contain the client afterwards.
	 */
	
	@Test
	public void addClientThatIsMemberOfParentExpectGroupToContainClient() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		
		assertTrue("Client should have been sucessfully added but is not contained in the group.",
				group.contains(client));
	}
	
	
	
	/**
	 * Add a client that is not a member of the parent group. Expect the group to not contain the client
	 * afterwards.
	 */
	
	@Test
	public void addClientThatIsNotMemberOfParentExpectGroupNotToContainClient() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(false);
		replay(parentGroup);
		
		group.add(client);
		
		assertFalse("Client should not have been successfully added but is contained in the group.",
				group.contains(client));
	}
	
	
	
	/**
	 * Add two clients to the group. Expect the size of the group to be 2.
	 */
	
	@Test
	public void addTwoClientsExpectSizeToBeTwo() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		group.add(createMock(Client.class));
		
		assertEquals("Added two clients but size of group is not two.", 2, group.size());
	}
	
	
	
	/**
	 * Add two clients and iterate over the group. Expect to iterate over two clients.
	 */
	
	@Test
	public void addTwoClientsExpectToIterateOverTwo() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		group.add(createMock(Client.class));
		
		int counter = 0;
		for (Client c : group) {
			counter++;
		}
		
		assertEquals("Added two clients to the group, but iteration didn't return two clients.", 2, counter);
	}
	
	
	
	/**
	 * Add two clients to the group that are both member of the parent group. Iterate over the group and
	 * expect the clients to show up.
	 */
	
	@Test
	public void addTwoClientsAndIterateExpectClientsToShowUp() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		Client client2 = createMock(Client.class);
		
		group.add(client);
		group.add(client2);
		
		List<Client> clients = new LinkedList<Client>();
		for (Client c : group) {
			clients.add(c);
		}
		
		assertTrue("Client was added to the list but wasn't returned by iteration.",
				clients.contains(client));
		assertTrue("Client was added to the list but wasn't returned by iteration.",
				clients.contains(client2));
	}
	
	
	
	/**
	 * Add clients to the group, iterate over the group and try to remove a client using the iterator. Expect
	 * an exception.
	 */
	
	@Test(expected = UnsupportedOperationException.class)
	public void tryToRemoveClientsUsingIteratorExpectException() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		
		Iterator<Client> iterator = group.iterator();
		while (iterator.hasNext()) {
			Client c = iterator.next();
			iterator.remove();
		}
	}
	
	
	
	/**
	 * Create a sub-group from a group and check that the sub-group has the original group as parent.
	 */
	
	@Test
	public void createSubGroupCheckAncestry() {
		ClientGroup subGroup = group.createSubGroup();
		assertEquals("Sub-group doesn't have the group it was created from as parent.", group,
				subGroup.parentGroup());
	}
	
	
	
	/**
	 * Remove a client from an emtpy group, expect the call to return false.
	 */
	
	@Test
	public void removeClientFromEmtpyGroupExpectFalse() {
		assertFalse("Removed a client that wasn't member of the group, but didn't return false.",
				group.remove(client));
	}
	
	
	
	/**
	 * Remove a client from a group. Expect the call to return true.
	 */
	
	@Test
	public void removeClientFromGroupExpectTrue() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		
		assertTrue("Removed a client from a group but the remove call didn't return true.",
				group.remove(client));
	}
	
	
	
	/**
	 * Remove a client that is a member of the group. Expect it to be removed.
	 */
	
	@Test
	public void removeClientFromGroupExpectRemoval() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		group.remove(client);
		
		assertFalse("Client was removed from group but the group still contains the client.",
				group.contains(client));
	}
	
	
	
	/**
	 * Remove a client from a group, expect it to be removed from its sub-group.
	 */
	
	@Test
	public void removeClientFromGroupExpectRemovalFromSubGroup() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		ClientGroup subGroup = group.createSubGroup();
		subGroup.add(client);
		
		group.remove(client);
		
		assertFalse("Client was removed from parent group but is still present in sub-group.",
				subGroup.contains(client));
	}
	
	
	
	/**
	 * Remove a client from a group with several sub-groups. Expect the client to be removed from all
	 * sub-groups.
	 */
	
	@Test
	public void removeClientExpectRemovalFromAllSubGroups() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		ClientGroup subGroup1 = group.createSubGroup();
		ClientGroup subGroup2 = group.createSubGroup();
		subGroup1.add(client);
		subGroup2.add(client);
		
		group.remove(client);
		
		assertFalse("Client was removed from parent group but is still present in sub-group.",
				subGroup1.contains(client));
		assertFalse("Client was removed from parent group but is still present in sub-group.",
				subGroup2.contains(client));
	}
	
	
	
	/**
	 * Add a client handler to a group and add a client. Expect the handler to be notified.
	 */
	
	@Test
	public void addHandlerAddClientExpectNotification() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		ClientHandler handler = createMock(ClientHandler.class);
		handler.clientAdded(eq(client));
		replay(handler);
		
		group.addClientHandler(handler);
		group.add(client);
		
		verify(handler);
	}
	
	
	
	/**
	 * Add a client handler and remove a client. Expect the handler to be notified.
	 */
	
	@Test
	public void addHandlerRemoveClientExpectNotification() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
				
		ClientHandler handler = createMock(ClientHandler.class);
		handler.clientRemoved(eq(client));
		replay(handler);
		
		group.add(client);
		group.addClientHandler(handler);
		
		group.remove(client);
		
		verify(handler);
	}
	
	
	
	/**
	 * Notify a group of a sent message, expect the notify method to return true.
	 */
	
	@Test
	public void notifyGroupOfSentMessageExpectReturnTrue() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		
		assertTrue("Client was in group, but notify method returned false.",
				group.notifyMessageSent(client, new Object()));
	}
	
	
	
	/**
	 * Notify a group of a sent message with the client not being in the group, expect the notify method to
	 * return false.
	 */
	
	@Test
	public void notifyGroupOfSentMessageExpectReturnFalse() {
		assertFalse("Client is not in group, but notify method returned true.",
				group.notifyMessageSent(client, new Object()));
	}
	
	
	
	/**
	 * Notify a group of a sent message, expect it to notify the handler.
	 */
	
	@Test
	public void notifyGroupOfSentMessageExpectHandlerNotification() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		Object message = new Object();
		
		ClientHandler handler = createMock(ClientHandler.class);
		handler.messageSent(eq(client), eq(message));
		replay(handler);
		
		group.add(client);
		group.addClientHandler(handler);
		
		group.notifyMessageSent(client, message);
		
		verify(handler);
	}
	
	
	
	/**
	 * Notify a group of a sent message with the client not being in the group. Expect the handler to not be
	 * notified.
	 */
	
	@Test
	public void notifyGroupOfSentMessageExpectNoHandlerNotification() {
		ClientHandler handler = createMock(ClientHandler.class);
		replay(handler);
		
		group.addClientHandler(handler);
		
		group.notifyMessageSent(client, new Object());
		
		verify(handler);
	}
	
	
	
	/**
	 * Notify a group of a sent message, with the client being in a sub-group. Expect the handler not to be
	 * notified.
	 */
	
	@Test
	public void notifyGroupOfSentMessageWithClientInSubGroupExpectNoHandlerNotification() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		ClientHandler handler = createMock(ClientHandler.class);
		replay(handler);
		
		ClientGroup group2;
		
		group.add(client);
		group2 = group.createSubGroup();
		group2.add(client);
		group.addClientHandler(handler);
		
		group.notifyMessageSent(client, new Object());
		
		verify(handler);
	}
	
	
	
	/**
	 * Notify a group of a received message, expect the notify method to return true.
	 */
	
	@Test
	public void notifyGroupOfReceivedMessageExpectReturnTrue() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		
		assertTrue("Client was in group, but notify method returned false.",
				group.notifyMessageReceived(client, new Object()));
	}
	
	
	
	/**
	 * Notify a group of a received message with the client not being in the group, expect the notify method
	 * to return false.
	 */
	
	@Test
	public void notifyGroupOfReceivedMessageExpectReturnFalse() {
		assertFalse("Client is not in group, but notify method returned true.",
				group.notifyMessageReceived(client, new Object()));
	}
	
	
	
	/**
	 * Notify a group of a received message, expect it to notify the handler.
	 */
	
	@Test
	public void notifyGroupOfReceivedMessageExpectHandlerNotification() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		Object message = new Object();
		
		ClientHandler handler = createMock(ClientHandler.class);
		handler.messageReceived(eq(client), eq(message));
		replay(handler);
		
		group.add(client);
		group.addClientHandler(handler);
		
		group.notifyMessageReceived(client, message);
		
		verify(handler);
	}
	
	
	
	/**
	 * Notify a group of a received message with the client not being in the group. Expect the handler to not
	 * be notified.
	 */
	
	@Test
	public void notifyGroupOfReceivedMessageExpectNoHandlerNotification() {
		ClientHandler handler = createMock(ClientHandler.class);
		replay(handler);
		
		group.addClientHandler(handler);
		
		group.notifyMessageReceived(client, new Object());
		
		verify(handler);
	}
	
	
	
	/**
	 * Notify a group of a received message, with the client being in a sub-group. Expect the handler not to
	 * be notified.
	 */
	
	@Test
	public void notifyGroupOfReceivedMessageWithClientInSubGroupExpectNoHandlerNotification() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		ClientHandler handler = createMock(ClientHandler.class);
		replay(handler);
		
		ClientGroup group2;
		
		group.add(client);
		group2 = group.createSubGroup();
		group2.add(client);
		group.addClientHandler(handler);
		
		group.notifyMessageReceived(client, new Object());
		
		verify(handler);
	}
	
	
	
	/**
	 * Notify a group of a caught exception, expect the notify method to return true.
	 */
	
	@Test
	public void notifyGroupOfCaughtExceptionExpectReturnTrue() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		group.add(client);
		
		assertTrue("Client was in group, but notify method returned false.",
				group.notifyExceptionCaught(client, new Throwable()));
	}
	
	
	
	/**
	 * Notify a group of a caught exception with the client not being in the group, expect the notify method
	 * to return false.
	 */
	
	@Test
	public void notifyGroupOfCaughtExceptionExpectReturnFalse() {
		assertFalse("Client is not in group, but notify method returned true.",
				group.notifyExceptionCaught(client, new Throwable()));
	}
	
	
	
	/**
	 * Notify a group of a caught exception, expect it to notify the handler.
	 */
	
	@Test
	public void notifyGroupOfCaughtExceptionExpectHandlerNotification() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		Throwable cause = new Throwable();
		
		ClientHandler handler = createMock(ClientHandler.class);
		handler.exceptionCaught(eq(client), eq(cause));
		replay(handler);
		
		group.add(client);
		group.addClientHandler(handler);
		
		group.notifyExceptionCaught(client, cause);
		
		verify(handler);
	}
	
	
	
	/**
	 * Notify a group of a caught exception with the client not being in the group. Expect the handler to not
	 * be notified.
	 */
	
	@Test
	public void notifyGroupOfCaughtExceptionExpectNoHandlerNotification() {
		ClientHandler handler = createMock(ClientHandler.class);
		replay(handler);
		
		group.addClientHandler(handler);
		
		group.notifyExceptionCaught(client, new Throwable());
		
		verify(handler);
	}
	
	
	
	/**
	 * Notify a group of a caught exception, with the client being in a sub-group. Expect the handler not to
	 * be notified.
	 */
	
	@Test
	public void notifyGroupOfCaughtExceptionWithClientInSubGroupExpectNoHandlerNotification() {
		expect(parentGroup.contains(isA(Client.class))).andReturn(true);
		replay(parentGroup);
		
		ClientHandler handler = createMock(ClientHandler.class);
		replay(handler);
		
		ClientGroup group2;
		
		group.add(client);
		group2 = group.createSubGroup();
		group2.add(client);
		group.addClientHandler(handler);
		
		group.notifyExceptionCaught(client, new Throwable());
		
		verify(handler);
	}
	
	
	
	/**
	 * Create a group without parent and add a client. Expect it to succeed.
	 */
	
	@Test
	public void createGroupWithoutParentAddClient() {
		group = new ClientGroup(null);
		group.add(client);
		assertTrue("Added client to group without parent, but didn't succeed.", group.contains(client));
	}
}
