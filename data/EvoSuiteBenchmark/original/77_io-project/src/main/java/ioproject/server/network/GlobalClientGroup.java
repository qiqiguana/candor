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



/**
 * The global client group that contains all connected clients.
 */

class GlobalClientGroup extends ClientGroup {
	
	/**
	 * Constructor.
	 */
	
	GlobalClientGroup() {
		super(null);
	}
	
	
	
	/**
	 * Overrides ClientGroup.add(). Throws an exception.
	 */
	
	@Override
	public boolean add(Client client) {
		throw new UnsupportedOperationException("Adding clients to the global client group is not allowed.");
	}
	
	
	
	/**
	 * Overrides ClientGroup.remove(). Throws an exception.
	 */
	
	@Override
	public boolean remove(Client client) {
		throw new UnsupportedOperationException("Removing clients from the global client group is not"
				+ " allowed. If you want to close a client connection use Client.disconnect() instead.");
	}
	
	
	
	/**
	 * Notifies the group of a newly connected client.
	 */
	
	void notifyClientConnected(Client client) {
		super.add(client);
	}
	
	
	
	/**
	 * Notifies the group that a client has been disconnected.
	 */
	
	void notifyClientDisconnected(Client client) {
		super.remove(client);
	}
}
