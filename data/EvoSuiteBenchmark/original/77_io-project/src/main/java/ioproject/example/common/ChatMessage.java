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



package ioproject.example.common;



import java.io.Serializable;



/**
 * A chat message that the client sends. The server will relay this message to all other clients.
 */

public class ChatMessage implements Serializable {
	
	public String message;
	
	
	
	/**
	 * Nullary constructor. If such a constructor isn't there, the class cannot function as a message type.
	 * Please note that this wouldn't be needed if we didn't have the other constructor, as every class
	 * without a declared constructor has an implicit default constructor without arguments. 
	 */
	
	public ChatMessage() {}
	
	
	/**
	 * The real constructor.
	 */
	
	public ChatMessage(String theMessage) {
		message = theMessage;
	}
}
