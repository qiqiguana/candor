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



/**
 * This is a message handler that throws an AssertionError if one of its methods is called.
 * You should use this handler as a default handler for a multiplexer, if you don't want any events to be
 * passed to the default handler.
 */

public class AlwaysFailMessageHandler implements MessageHandler {
	
	/**
	 * Implements MessageHandler.messageSent().
	 * Throws an AssertionError.
	 */
	
	public void messageSent(Node node, Object message) {
		throw new AssertionError("Method called with the following arguments: " + node + " (node), "
				+ message + " (message).");
	}
	
	
	
	/**
	 * Implements MessageHandler.messageReceived().
	 * Throws an AssertionError.
	 */
	
	public void messageReceived(Node node, Object message) {
		throw new AssertionError("Method called with the following arguments: " + node + " (node), "
				+ message + " (message).");
	}
}
