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



import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;



/**
 * Passes the appropriate network events on to a ServerHandler.
 */

class ServerHandlerAdapter extends IoHandlerAdapter {
	
	private ServerHandler handler;
	
	
	
	/**
	 * Creates a new ServerHandlerAdapter instance (surprise, surprise).
	 * 
	 * @param handler The handler that network events will be passed to.
	 */
	
	public ServerHandlerAdapter(ServerHandler aHandler) {
		handler = aHandler;
	}
	
	
	
	/**
	 * Returns the wrapped handler.
	 */
	
	ServerHandler wrappedHandler() {
		return handler;
	}
	
	
	
	/**
	 * Forwards a message-sent event to the wrapped handler.
	 */
	
	@Override
	public void messageSent(IoSession session, Object message) {
		handler.messageSent((Server)session.getAttachment(), message);
	}
	
	
	
	/**
	 * Forwards a message-received event to the wrapped handler.
	 */
	
	@Override
	public void messageReceived(IoSession session, Object message) {
		handler.messageReceived((Server)session.getAttachment(), message);
	}
	
	
	
	/**
	 * Forwards a caught exception to the wrapped handler.
	 */
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		handler.exceptionCaught((Server)session.getAttachment(), cause);
	}
	
	
	
	/**
	 * Forwards a session-closed event to the wrapped handler.
	 */
	
	@Override
	public void sessionClosed(IoSession session) {
		handler.connectionClosed((Server)session.getAttachment());
	}
}
