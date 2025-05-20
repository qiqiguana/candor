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



import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;



/**
 * A group of clients.
 * 
 * Each client group, except the global client group, has a parent group. Each group can have several
 * sub-groups. The semantics of groups are as follows:
 * * Only clients can added to a group that are in this group's parent group.
 * * Clients removed from a group are also removed from all of this group's sub-groups.
 * 
 * The global client group contains all connected clients and all groups are, directly or indirectly,
 * sub-groups of the global client group.
 */

public class ClientGroup implements Iterable<Client> {
	
	private ClientGroup parentGroup;
	private Set<WeakReference<ClientGroup>> subGroups;
	private Set<Client> clients;
	
	private ClientHandler handler;
	
	
	
	/**
	 * Creates a new client group. This is a package-scoped constructor. Users are supposed to get client
	 * groups by creating children of existing groups.
	 */
	
	ClientGroup(ClientGroup theParentGroup) {
		parentGroup = theParentGroup;
		subGroups = new LinkedHashSet<WeakReference<ClientGroup>>();
		clients = new LinkedHashSet<Client>();
		
		handler = null;
	}
	
	
	
	/**
	 * Returns this groups parent group.
	 */
	
	ClientGroup parentGroup() {
		return parentGroup;
	}
	
	
	
	/**
	 * Notifies the client group of a sent message. This method is package-scoped and is only called by the
	 * parent group.
	 * 
	 * @return True, if the client is member of this group, false otherwise.
	 */
	
	synchronized boolean notifyMessageSent(Client client, Object message) {
		if (!clients.contains(client)) {
			return false;
		}
		
		boolean inSubGroup = false;
		for (WeakReference<ClientGroup> groupReference : subGroups) {
			inSubGroup = groupReference.get().notifyMessageSent(client, message) || inSubGroup;
		}
		
		if (!inSubGroup && handler != null) {
			handler.messageSent(client, message);
		}
		
		return true;
	}
	
	
	
	/**
	 * Notifies the client group of a received message. This method is package-scoped and is only called by
	 * the parent group.
	 * 
	 * @return True, if the client is member of this group, false otherwise.
	 */
	
	synchronized boolean notifyMessageReceived(Client client, Object message) {
		if (!clients.contains(client)) {
			return false;
		}
		
		boolean inSubGroup = false;
		for (WeakReference<ClientGroup> groupReference : subGroups) {
			inSubGroup = groupReference.get().notifyMessageReceived(client, message) || inSubGroup;
		}
		
		if (!inSubGroup && handler != null) {
			handler.messageReceived(client, message);
		}
		
		return true;
	}
	
	
	
	/**
	 * Notifies the client group of a caught exception. This method is package-scoped and is only called by
	 * the parent group.
	 * 
	 * @return True, if the client is member of this group, false otherwise.
	 */
	
	synchronized boolean notifyExceptionCaught(Client client, Throwable cause) {
		if (!clients.contains(client)) {
			return false;
		}
		
		boolean inSubGroup = false;
		for (WeakReference<ClientGroup> groupReference : subGroups) {
			inSubGroup = groupReference.get().notifyExceptionCaught(client, cause) || inSubGroup;
		}
		
		if (!inSubGroup && handler != null) {
			handler.exceptionCaught(client, cause);
		}
		
		return true;
	}
	
	
	
	/**
	 * Implements Iterable.iterator(). Returns the iterator of the wrapped Set.
	 */
	
	public Iterator<Client> iterator() {
		return Collections.unmodifiableSet(clients).iterator();
	}
	
	
	
	/**
	 * Returns true if the group contains the client, false otherwise.
	 * 
	 * @param client The client that is checked for group membership.
	 * 
	 * @return True if the client is a member of this group, false otherwise.
	 */
	
	public synchronized boolean contains(Client client) {
		return clients.contains(client);
	}
	
	
	
	/**
	 * Returns the number of clients that are member of this group.
	 */
	
	public synchronized int size() {
		return clients.size();
	}
	
	
	
	/**
	 * Attempts to add a client to this group. This will only succeed if the client is a member of this
	 * group's parent group.
	 * 
	 * @param client The client to add to this group.
	 * 
	 * @return True if the client was added to the group, false otherwise.
	 */
	
	public synchronized boolean add(Client client) {
		boolean parentContainsClient = parentGroup == null || parentGroup.contains(client);
		if (parentContainsClient) {
			clients.add(client);
		}
		
		if (handler != null) {
			handler.clientAdded(client);
		}
		
		return parentContainsClient;
	}
	
	
	
	/**
	 * Removes a client from this group. The client will also be removed from all sub-groups that this client
	 * is a member of.
	 * 
	 * @return True, if the client was a member of this group and was removed. False if the client was not a
	 *         member of this group and nothing happened.
	 */
	
	public synchronized boolean remove(Client client) {
		boolean clientIsMember = clients.contains(client);
		if (clientIsMember) {
			clients.remove(client);
			for (WeakReference<ClientGroup> subGroupReference : subGroups) {
				subGroupReference.get().remove(client);
			}
		}
		
		if (handler != null) {
			handler.clientRemoved(client);
		}
		
		return clientIsMember;
	}
	
	
	
	/**
	 * Creates a sub-group of this group.
	 * Please note that sub-groups are weakly referenced by their parent group and will automatically be
	 * garbage-collected once no other reference outside of their parent group exist.
	 * 
	 * @return The newly created sub-group.
	 */
	
	public synchronized ClientGroup createSubGroup() {
		ClientGroup subGroup = new ClientGroup(this);
		subGroups.add(new WeakReference<ClientGroup>(subGroup));
		return subGroup;
	}
	
	
	
	/**
	 * Adds a client handler.
	 * The handler will be notified if a client is added to or removed from the group.
	 * If a client causes another event (message sent, message received or exception caught), the handler is
	 * only notified if the client is not a member of one or several sub-groups. If the client is a member of
	 * one or several sub-groups, only the sub-groups are notified of the event.
	 * 
	 * @param aHandler The client handler to add.
	 */
	
	public synchronized void addClientHandler(ClientHandler aHandler) {
		handler = aHandler;
	}
}
