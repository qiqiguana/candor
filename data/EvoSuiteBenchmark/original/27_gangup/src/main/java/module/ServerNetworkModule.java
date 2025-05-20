/* $Id: ServerNetworkModule.java,v 1.7 2004/05/05 17:15:30 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emillkth.se>
 * @version: $Revision: 1.7 $
 *
 */

package module;

import java.io.IOException;

import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Locale;

import java.net.Socket;
import java.net.SocketException;
import java.net.ServerSocket;

import state.*;
import static util.Unpacker.unpack;
import static module.MessageFactory.*;


@cvs(file     = "$RCSfile: ServerNetworkModule.java,v $",
     revision = "$Revision: 1.7 $",
     date     = "$Date: 2004/05/05 17:15:30 $",
     author   = "$Author: bja $",
     tag      = "$Name:  $",
     build    = "")

@mod(name     = "ServerNetworkModule",
     topics   = "NETWORK SETALLOWCONNECT",
     cmds     = "DUMP",
     desc     = "")
/**
 * Provides the network part of the server. For n clients, this module
 * will consist of a total of n+2 threads.
 *
 */
class ServerNetworkModule extends AbstractModule implements NetworkModule {

    /** Default port to listen for connections on. */
    static final int DEFAULT_PORT = 1119;

    /** The thread listening for new tcp/ip connections. */
    ServerConnectionListener theListener;

    /** List of active connections. */
    Connection[] connections;

    /* Number of active connections. */
    int numconns = 0;

    /* Do we allow connections to this server? */
    private boolean allowConnections = false;

    /**
     * Creates a new instance of this module. This constructor is usually
     * called from the kernel when it wishes to load a module dynamically.
     *
     * @throws ModuleRegisterException If you have a broken Java interpreter.
     */
    public ServerNetworkModule()
	throws ModuleRegisterException { }

    /**
     * Initialization method for modules. Using init() is recommended
     * instead of overloading the constructor.
     */
    protected void init() {

	int port = DEFAULT_PORT;
	Message reply = null;
	Message error = null;

	connections = new Connection[GameState.MAX_PLAYER_LIMIT];

	try {

	    reply = request("CONFIG", "GET ServerNetwork port");
	    port = Integer.parseInt((String)reply.body);
	    
	} catch (MessageTimeoutException e) {
	    error = createWarningMessage("_SRV_GET_PORT_FAILED");
	} catch (NumberFormatException e) {
	    error = createWarningMessage("_SRV_INVALID_PORT",DEFAULT_PORT);
	} catch (NullPointerException e) {
	    error = createWarningMessage("_UNKNOWN_EXCEPTION"+e);
	} finally {
	    
	    try {
		error.send(this);
	    } catch (MessageDeliveryException e) {
		System.err.println("ServerNetworkModule.init(): " + 
		    error.getHeader() + " " + error.getBody());
	    } catch (NullPointerException e) {
		// meaning there were no errors.
	    }

	}

	try {

	    theListener = new ServerConnectionListener(this, port);
	    theListener.start();

	} catch (IOException e) {
	    Message m = null;
	    try {

		m = createErrorMessage("_SRV_BIND_PORT_FAILED",port);
		m.send(this);

		// unload module here!! correct way of doing it?

		m = createUnloadMessage(modinfo.name);
		m.send(this);

	    } catch (MessageDeliveryException x) {
		System.err.println("ServerNetworkModule.init(): " +
				   m.getHeader() + " " + m.getBody());
	    }
	}

	allowConnections = true;

    }

    /**
     * This method is invoked once for every Message in the input queue.
     * Normally this is where module specific message handling is performed.
     * 
     * @param msg The Message that is to be processed.
     */
    protected void processMessage(Message msg) {
	
 	/* Handle network messages. */
	//System.err.println("ServerNetworkModule.processMessage()");

	if (msg.header.equals("NETWORK")) {
	    //System.err.println("sending NetworkMessage");
	    sendNetworkMessage((NetworkData) msg.body);
	}
	else if (msg.header.equals("SETALLOWCONNECT")) {
	    allowConnections = (Boolean)msg.body;
	}
    }
    
    /**
     * Makes sure to terminate all connections.
     */
    protected void free() {
	// stop listener
	try {
	    theListener.exit();
	    // stop connections
	    for (Connection c : connections) {
		if (c != null) {
		    c.exit();
		}
	    }
	} catch (NullPointerException e) {
	    e.printStackTrace(System.err);
	} catch (Exception e) {
	    e.printStackTrace(System.err);
	}
    }
    
    /**
     * Adds a new connection and gives it an id. Also sends a
     * CONNECTED Message.
     */
    protected synchronized void addConnection(Socket sock) {
	if (numconns == GameState.MAX_PLAYER_LIMIT || !allowConnections) {
	    // server full. simply close connection for now.
	    try {
		sock.close();
	    } catch (IOException e) {
		// doesn't matter
	    }
	    
	} else {
	    numconns++;
	    int t = -1;
	    // grab the first free id
	    while (connections[++t] != null);
	    connections[t] = new Connection(this,sock,t);
	    connections[t].start();
	    sendMessage(new Message("CONNECTED",
				    "ID: " + t + " " +
				    "IP: " + sock.getInetAddress().
				    getHostAddress()));
	}
    }

    
    /**
     * Removes a connection. Also sends a DROPPED Message.
     */
    public synchronized void deleteConnection(Connection c) {
	Message m = null;
	for (int i=0; i < GameState.MAX_PLAYER_LIMIT; i++) {
	    if (c == connections[i]) {
		numconns--;
		connections[i] = null;
	    	c.exit();
		try {
		    String host = c.getSocket().
			getInetAddress().getHostAddress();
		    m = createConnectionDroppedMessage(i,host);
		    m.send(this);
		} catch (MessageDeliveryException e) {
		    if (m != null) { 
			System.err.println(
			    "ServerNetworkModule.deleteConnection(): " + m); 
		    }
		}

		break;
	    }
	}
    }

    
    public void receiveNetworkMessage(int type, byte[] data, int id) 
	throws NetworkException {
	try {
	    sendMessage(new Message("FROM_NETWORK", unpack(type,data,id)));
	} catch (PackingException e) {
	    throw new NetworkException(e);
	}
    }

    public boolean allowConnections() {
	return allowConnections;
    }


    protected void sendNetworkMessage(NetworkData d) {
	
	Packable p = d.getData();
	byte[] data = p.pack();

	/* Send only to provided recipients. */
	if (d.getRecipients() != null) {
	    for (int id : d.getRecipients()) {
		if (connections[id] != null) {
		    connections[id].send(p.type(),data);
		}
	    }
	}
	/* Send to everyone. */
	else {
	    for (Connection c : connections) {
		if (c != null) {
		    c.send(p.type(),data);
		}
	    }
	}
    }
}



/**
 * ServerConnnectionListener is a thread that is constantly waiting
 * for new clients to connect.
 */

class ServerConnectionListener extends Thread {

    /** The ServerNetworkModule to which this listener belongs. */
    private ServerNetworkModule parent;
    
    /** The ServerSocket that listens for connections. */
    private ServerSocket listenSocket;
    
    /** When done is true, this thread should be terminated. */
    private boolean done = false;
    
    /**
     * Creates a new instance of the ServerConnectionListener.
     *
     * @throws IOException at failure of binding requested port.
     */
    
    public ServerConnectionListener(ServerNetworkModule par, int port)
	throws IOException {
	parent = par;
	listenSocket = new ServerSocket(port);
    }

    /**
     * Loop function for this thread. Constantly listens for new
     * connection, adds them as a new ServerConnection and sends them
     * back to the parent ServerNetworkModule
     */
    public void run() {
	while (!done) {
	    try {
		Socket newsock = listenSocket.accept();
		parent.addConnection(newsock);
	    } catch (SocketException e) {
		/* A SocketException is thrown when exit() is called
                   and the listenSocket needs to be closed. Do
                   nothing. */
		System.err.println("ServerConnectionListener.run(): " +
				   "SocketException: " + e.getMessage());
	    } catch (IOException e) {
		parent.sendMessage(new Message("WARNING",
		  parent.locale.getString("_SRVCON_CONNECTION_FAILED")));
	    }
	}
    }

    /**
     * Terminates this thread.
     */
    public void exit() {
	done = true;
	try {
	    listenSocket.close();
	} catch (IOException e) {
	    parent.sendMessage(new Message("WARNING",
			   parent.locale.getString("_SRVCON_CLOSE_FAILED")));
	}
    }
}




    
