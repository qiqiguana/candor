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



package ioproject.example.client;



import ioproject.example.common.ChatMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Iterator;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import ioproject.client.network.ServerHandler;
import ioproject.client.network.Server;
import ioproject.common.network.Node;



/**
 * Simple chat client.
 */

public class ChatExampleClient implements ServerHandler {
	
	/**
	 * The main method.
	 */
	
	public static void main(String[] args) throws IOException {
		JSAP parser = new JSAP();
		
		try {
			parser.registerParameter(new FlaggedOption("server")
					.setLongFlag("server")
					.setShortFlag('s')
					.setStringParser(JSAP.STRING_PARSER)
					.setRequired(JSAP.NOT_REQUIRED)
					.setAllowMultipleDeclarations(false)
					.setDefault("localhost")
					.setUsageName("server address")
					.setHelp("Hostname or IP address of the server.")
					);
			
			parser.registerParameter(new FlaggedOption("port")
					.setLongFlag("port")
					.setShortFlag('p')
					.setStringParser(JSAP.INTEGER_PARSER)
					.setRequired(JSAP.NOT_REQUIRED)
					.setAllowMultipleDeclarations(false)
					.setDefault("3448")
					.setUsageName("server port")
					.setHelp("Port the server software runs at.")
					);
			
			parser.registerParameter(new FlaggedOption("name")
					.setLongFlag("name")
					.setShortFlag('n')
					.setStringParser(JSAP.STRING_PARSER)
					.setRequired(JSAP.NOT_REQUIRED)
					.setAllowMultipleDeclarations(false)
					.setDefault("chatclient")
					.setUsageName("name")
					.setHelp("The name of this client in the chat.")
					);
			
			parser.registerParameter(new Switch("help")
					.setLongFlag("help")
					.setShortFlag('h')
					.setHelp("Prints out a help message, then quits.")
					);
			parser.registerParameter(new Switch("version")
					.setLongFlag("version")
					.setShortFlag('v')
					.setHelp("Prints out version information, then quits.")
					);
		}
		catch (JSAPException e) {
			throw new AssertionError("This should never happen.");
		}
		
		JSAPResult result = parser.parse(args);
		
		// Check if errors occured while parsing the parameters. If errors occured, print them out. We don't
		// need to handle the errors any further. This is done below, together with the handling of the
		// help parameter.
		if (!result.success()) {
			System.out.println();
			Iterator<?> iterator = result.getErrorMessageIterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		}
		
		if (!result.success() || result.getBoolean("help")) {
			System.out.println();
			System.out.println("Io chat example (client)");
			System.out.println("http://io-project.org");
			System.out.println();
			System.out.println("Usage: iochat " + parser.getUsage());
			System.out.println();
			System.out.println(parser.getHelp());
						
			if (!result.success()) {
				System.exit(1);
			}
			else {
				System.exit(0);
			}
		}
		else if (result.getBoolean("version")) {
		    // If the user requested version information, we print it (of course) and then exit.
			System.out.println();
			System.out.println("Io chat example (client)");
			System.out.println();
			System.out.println("Copyright (c) 2007, 2008 Hanno Braun.");
			System.out.println("The Io framework is open source software, available under the ISC license.");
			System.out.println("See http://io-project.org for details.");
			System.exit(0);
		}
		
		String address = result.getString("server");
		int port = result.getInt("port");
		String name = result.getString("name");
		
		System.out.println();
		System.out.println("Starting chat example with the following arguments:");
		System.out.println("Server address: " + address + ":" + port);
		System.out.println("Name in chat: " + name);
		System.out.println("Start with \"--help\" to learn how to change these values.");
		System.out.println();
		
		System.out.println("Connecting...");
		Server con = new Server();
		con.connect(new InetSocketAddress(address, port), new ChatExampleClient());
		System.out.println("Connected.");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			String message = in.readLine();
			con.send(new ChatMessage(name + ": " + message));
		}
	}
	
	
	
	/**
	 * Handles received messages.
	 */
	
	public void messageReceived(Node server, Object message) {
		System.out.println("\nReceived: " + ((ChatMessage)message).message);
	}
	
	
	
	/**
	 * Handles sent messages.
	 */
	
	public void messageSent(Node server, Object message) {
		// We don't care for sent messages, so let's just do nothing.
	}
	
	
	
	/**
	 * Handles exceptions.
	 */
	
	public void exceptionCaught(Server server, Throwable cause) {
		System.out.println(cause.toString());
		System.exit(1);
	}
	
	
	
	/**
	 * Handles the case that the connection is closed.
	 */
	
	public void connectionClosed(Server server) {
		System.out.println("Connection has been closed.");
		System.exit(0);
	}
}
