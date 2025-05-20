/* $Id: ClientNetworkModule.java,v 1.4 2004/04/27 19:26:21 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emillkth.se>
 * @version: $Revision: 1.4 $
 *
 */

package module;

import java.io.IOException;

import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Locale;

import java.net.Socket;
import java.net.SocketException;


import state.*;
import static util.Unpacker.unpack;

@cvs(file     = "$RCSfile: ClientNetworkModule.java,v $",
     revision = "$Revision: 1.4 $",
     date     = "$Date: 2004/04/27 19:26:21 $",
     author   = "$Author: bja $",
     tag      = "$Name:  $",
     build    = "")

@mod(name     = "ClientNetworkModule",
     topics   = "CONNECT NETWORK CLIENT DISCONNECT",
     cmds     = "DUMP",
     desc     = "")

/**
 * Provides the network part of the client.
 *
 */
class ClientNetworkModule extends AbstractModule implements NetworkModule {

    /** The Connection associated with this client. */
    private Connection connection;
    
    /**
     * Creates a new instance of this module. This constructor is usually
     * called from the kernel when it wishes to load a module dynamically.
     *
     * @throws ModuleRegisterException If you have a broken Java interpreter.
     */
    public ClientNetworkModule() 
	throws ModuleRegisterException { }

    /**
     * Creates a new instance of this module. This constructor is usually
     * called from a loader program to initialize a set of modules at 
     * startup.
     *
     * @param krn the Kernel associated with this Module.
     * @throws ModuleRegisterException If kernel registration failed.
     */
    public ClientNetworkModule(Kernel krn)
	throws ModuleRegisterException {
	super(krn);
    }

    /**
     * Initialization method for modules. Using init() is recommended
     * instead of overloading the constructor.
     */
    protected void init() { }
    
    /**
     * This method is invoked once for every Message in the input queue.
     * Normally this is where module specific message handling is performed.
     * 
     * @param msg The Message that is to be processed.
     */
    protected void processMessage(Message msg) {

	if (msg.header.equals("CLIENT")) {
	    
	    /* If a module has requested id, send it. Will return default
	       id (-1) when not connected. */
	    if (msg.body.equals("ID")) {
		sendMessage(msg.reply(connection.getClientID()));
	    }

	/* Handle network messages. */

	} else if (msg.header.equals("NETWORK")) {
	    
	    sendNetworkMessage((Packable) msg.body);

	} else if (msg.header.equals("CONNECT")) {
	    
	    /* body contains the string: HOSTNAME PORT */
	    String[] addr = ((String) msg.body).split(" ");
	    connect(addr[0], Integer.parseInt(addr[1]));
	    
	} else if (msg.header.equals("DISCONNECT")) {
	    if (connection != null)
		deleteConnection(connection);
	}
	
    }
    
    /**
     * Connects to specified host and port.
     *
     * @param host The address of the host to connect to.
     * @param port The port of the host to connect to.
     */
    private void connect(String host, int port) {

	if (connection == null) { /* Not connected, connect. */
	    try {

		Socket sock = new Socket(host, port);
		connection = new Connection(this, sock, -1);

		connection.start();
		sendMessage(new Message("CONNECTED", "IP: " + host));

	    } catch (IOException e) {
		sendMessage(new Message("ERROR", 
		  String.format(locale.getString("_CLIENT_CON_FAILED"),
				host, port) + " " + e.getMessage()));
	    }
	} else {  /* Connection already made, ignore. */
	    sendMessage(new Message("WARNING",
			    locale.getString("_CLIENT_CON_IGNORED")));
	}
    }
    
    /**
     * Exit method for modules. This method is invoked after a call to
     * the @see exit() method. Makes sure to terminate all connections.
     */
    protected void free() {
	deleteConnection(connection); 
    }

    public void deleteConnection(Connection c)  {
	if (c == connection) {
	    c.exit();
	    connection = null;
	    sendMessage(
		new Message("DROPPED", "IP: " + 
			    c.getSocket().getInetAddress().
			    getHostAddress()));
	}
    }

    public void receiveNetworkMessage(int type, byte[] data, int id) 
	throws NetworkException {
	try {
	    sendMessage(new Message("FROM_NETWORK", unpack(type,data)));
	} catch (Exception e) {
	    throw new NetworkException(e);
	}
    }

    protected void sendNetworkMessage(Packable p) {
	if (connection != null) {
	    byte[] data = p.pack();
	    connection.send(p.type(),data);
	}
    }
}
