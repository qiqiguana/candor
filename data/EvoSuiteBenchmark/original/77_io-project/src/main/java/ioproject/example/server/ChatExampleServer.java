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



package ioproject.example.server;



import ioproject.example.common.ChatMessage;

import java.io.IOException;
import java.net.InetSocketAddress;

import ioproject.common.network.Node;
import ioproject.server.network.Client;
import ioproject.server.network.ClientGroup;
import ioproject.server.network.ClientHandler;
import ioproject.server.network.NetworkService;




/**
 * A simple chat application, demonstrating the capabilities of the Io framework.
 * This class implements the Game interface and some listener interfaces. Implementing game is mandatory if
 * we want to use the Io framework to host our application. It brings us the run() and shutDown() methods.
 * The listener method enable us to use this class to receive feedback from the services, like new client
 * connections and received messages.
 */

public class ChatExampleServer implements Runnable, ClientHandler {
	
	/**
	 * Main method. Instantiates the class and starts the main thread.
	 */
	
	public static void main(String[] args) {
		new Thread(new ChatExampleServer()).start();
	}
	
	

	// All services we depend on.
	private NetworkService net;
	
	// Management of clients and the chat room.
	private ClientGroup clients;
	
	
	
	/**
	 * This constructor doesn't seem to do much on the first look, but it really important for this example.
	 * Kernel will examine the parameter list of the constructor of the class implementing Game, and
	 * instantiate only those services the game needs (in this case the network service).
	 */
	
	public ChatExampleServer() {
		// Initialize services.
		net = new NetworkService();
		
		// Retrieve the global client group and add ourselves as listener.
		clients = net.globalClientGroup();
		clients.addClientHandler(this);
	}
	
	
	
	/**
	 * This could be used as the main loop of this application. In this example, however, we don't have a
	 * main loop. Instead we just react to events that are delivered to us by the network service. 
	 */
	
	public void run() {		
		// Before beginning regular operation, we'll connect the network service to the network.
		try {
			net.connect(new InetSocketAddress(3448));
		}
		catch (IOException e) {
			System.out.println("Error: " + e.toString());
			return;
		}
		
		System.out.println("Listening for incoming connections...");
		
		// Create a shutdown hook, that will call notify() at a local object of this method.
		final Object shutdownMarker = new Object();
		Runnable shutdownHook = new Runnable() {
			public void run() {
				synchronized (shutdownMarker) {
					shutdownMarker.notify();
				}
			}
		};
		Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook));
		
		try {
			synchronized (shutdownMarker) {
				shutdownMarker.wait();
			}
		}
		catch (InterruptedException e) {
			throw new AssertionError("Interrupted while waiting for shutdown. This should never happen.");
		}
		
		System.out.println("Shutting down...");
		net.disconnect();
		
		System.out.println();
		System.out.println("Chat application closed.");
	}
	
	
	
	/**
	 * Implements ClientHandler.clientAdded().
	 * Notifies us that a new client has connected and has been added to the global client group.
	 */
	
	public synchronized void clientAdded(Client client) {
		System.out.println("Client accepted.");
	}
	
	
	
	/**
	 * Implements ClientHandler.clientRemoved().
	 * Notifies us that a client has disconnected and thus has been removed from the global client group.
	 */
	
	public synchronized void clientRemoved(Client client) {
		System.out.println("Client disconnected.");
	}	
	
	
	
	/**
	 * Implements ClientHandler.messageSent().
	 */
	
	public void messageSent(Node client, Object message) {
		// We're not interested in outgoing messages, so we'll just ignore them.
	}
	
	
	
	/**
	 * Implements ClientHandler.messageReceived().
	 * Relays the message to all clients.
	 */
	
	public synchronized void messageReceived(Node client, Object message) {
		for (Client recipient : clients) {
			recipient.send(message);
		}
		System.out.println("Relayed message: " + ((ChatMessage)message).message);
	}
	
	
	
	/**
	 * Implements ClientHandler.exceptionCaught().
	 * Disconnects the client that caused the error and prints out an error message. 
	 */
	
	public synchronized void exceptionCaught(Client client, Throwable cause) {
		client.disconnect();
		
		System.out.println("Error: " + cause.toString());
		while ((cause = cause.getCause()) != null) {
			System.out.println("Caused by: " + cause.toString());
		}
	}
}

