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



package ioproject;



import ioproject.client.network.ClientSuite;
import ioproject.common.network.MessageMultiplexerTest;
import ioproject.server.ServerSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



/**
 * Bundles all tests from this package.
 */

@RunWith(Suite.class)
@SuiteClasses({ClientSuite.class, MessageMultiplexerTest.class, ServerSuite.class})
public class TestSuite {
	// The class itself does nothing. The annotations define everything that needs to defined here, the rest
	// of the work is done by junit automatically.
}

