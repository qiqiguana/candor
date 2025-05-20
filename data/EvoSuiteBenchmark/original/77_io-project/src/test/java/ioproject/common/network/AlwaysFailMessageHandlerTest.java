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



/**
 * Tests AlwaysFailMessageHandler.
 */

public class AlwaysFailMessageHandlerTest {
	
	private AlwaysFailMessageHandler handler;
	
	
	
	/**
	 * Sets up the test environment.
	 */
	
	@Before
	public void setUpTestEnvironment() {
		handler = new AlwaysFailMessageHandler();
	}
	
	
	
	/**
	 * Notify the handler of a sent message, expect it to throw an AssertionError.
	 */
	
	@Test(expected = AssertionError.class)
	public void notifyOfSentMessageExpectError() {
		handler.messageSent(null, null);
	}
	
	
	
	/**
	 * Notify the handler of a received message, expect it to throw an AssertionError.
	 */
	
	@Test(expected = AssertionError.class)
	public void notifyOfReceivedMessageExpectError() {
		handler.messageReceived(null, null);
	}
}
