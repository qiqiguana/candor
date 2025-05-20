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



import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;



/**
 * Wraps a ServerNetworkHandler and passes on all notifications.
 */

class ClientGroupHandlerAdapter extends IoHandlerAdapter {
	
	private GlobalClientGroup group;
	
	
	
	/**
	 * Creates a new adapter that will forward events to the wrapped handler.
	 */
	
	ClientGroupHandlerAdapter(GlobalClientGroup theGroup) {
		group = theGroup;
	}
	
	
	
	/**
	 * Returns the global client group.
	 */
	
	GlobalClientGroup getGroup() {
		return group;
	}
	
	
	
	/**
	 * Forwards a session-opened event to the global client group.
	 */
	
	@Override
	public void sessionOpened(IoSession session) {
		Client client = new Client(session);
		session.setAttachment(client);
		group.notifyClientConnected(client);
	}
	
	
	
	/**
	 * Forwards a message-sent event to the global client group.
	 */
	
	@Override
	public void messageSent(IoSession session, Object message) {
		group.notifyMessageSent((Client)session.getAttachment(), message);
	}
	
	
	
	/**
	 * Forwards a message-received event to the global client group.
	 */
	
	@Override
	public void messageReceived(IoSession session, Object message) {
		group.notifyMessageReceived((Client)session.getAttachment(), message);
	}
	
	
	
	/**
	 * Forwards an exception-caught event to the global client group.
	 */
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		group.notifyExceptionCaught((Client)session.getAttachment(), cause);
	}
	
	
	
	/**
	 * Forwards a session-closed event to the global client group.
	 */
	
	@Override
	public void sessionClosed(IoSession session) {
		group.notifyClientDisconnected((Client)session.getAttachment());
	}
}
