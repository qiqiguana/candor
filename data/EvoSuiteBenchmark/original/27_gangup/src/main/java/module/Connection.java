/* $Id: Connection.java,v 1.3 2004/04/27 19:26:21 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emillkth.se>
 * @version: $Revision: 1.3 $
 *
 */

package module;

import java.io.IOException;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.Locale;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import static module.MessageFactory.createWarningMessage;

/**
 * An instance of the Connection class corresponds to one connection,
 * at either the server ot the client. With n clients, the server will
 * have n Connections instances and each client will have one
 * Connection instance.
 */

class Connection extends Thread {

    /** Localized strings. */
    protected ResourceBundle locale;
    
    /** The ServerNetworkModule to which this connection belongs. */
    private NetworkModule parent;
    
    /** Data socket for this connection. */
    private Socket socket;

     /** When done is true, this thread should be terminated. */
    private boolean done = false;

    /** Input stream for this connection. */
    private DataInputStream in;

    /** Output stream for this connection. */
    private DataOutputStream out;

    /** Identification number for this connection. For client
        connections, this is likely to be -1. */
    private int id;
    
    /**
     * Creates a new instance of the Connection.
     *
     * @param parent The parent NetworkModule.
     * @param socket The data socket.
     * @param id The id.
     */
    public Connection(NetworkModule parent, Socket socket, int id) {

	this.id = id;
	this.parent = parent;
	this.socket = socket;
	locale = ResourceBundle.getBundle("gangup", Locale.getDefault());
	
	try {
	    out = new DataOutputStream(socket.getOutputStream());
	    in = new DataInputStream(socket.getInputStream());
	    out.write(id);
	} catch (IOException e) {
	    parent.deleteConnection(this);
	}
    }

    /**
     * Main loop for this thread. Constantly tries to read data off
     * the input buffer.
     */      
    public void run() {
	
	int msg_len = 0;
	byte[] data;
	byte type;

	try {
	    int readid = in.readByte();
	    System.out.println("ID from network: " + readid);
	    if (readid != -1) id = readid;
	
	    while (!done) {
		msg_len = in.readShort();
		data = new byte[msg_len];
		type = in.readByte();
		in.readFully(data);
		parent.receiveNetworkMessage(type,data,id);
	    }
	} catch (IOException e) {
	    /* FIXME: This seemse to happen naturally when a
	       client disconnects caused by a crash. In such a
	       case, tell parent to send a disconnect-message and
	       terminate this thread.*/
	    parent.deleteConnection(this);
	} catch (NegativeArraySizeException e) {
	    /* Happens for example when hitting Ctrl-C in a telnet 
	       session. */
	    parent.deleteConnection(this);
	} catch (NetworkException e) {
	    System.err.println("Connection.run(): *** ERROR *** [NET] " + 
			       "caught network exception: " + e.getMessage());
	    
	    // parent.deleteConnection(this);
	}
    }

    /**
     * Sends to data through this connection.
     */
    public synchronized void send(int type, byte[] data) {
	try {
	    out.writeShort(data.length);
	    out.write(type);
	    out.write(data,0,data.length);
	    out.flush();
	} catch (IOException e) {

	    Message m = null;
	    try {
		m = createWarningMessage("_CON_SEND_FAILED");
		parent.sendMessage(m);
	    } catch (MessageDeliveryException x) {
		if (m != null) {
		    System.err.println(m.getBody());
		} else {
		    x.printStackTrace(System.err);
		}

	    }
	}
    }

    /**
     * Terminates this thread.
     */
    public void exit() {
	done = true;
	try {
	    socket.close();
	} catch (IOException e) {
	    Message m = null;
	    try {
		m = createWarningMessage("_SRVCON_CLOSE_FAILED");
		parent.sendMessage(m);
	    } catch (MessageDeliveryException x) {
		if (m != null) {
		    System.err.println(m.getBody());
		} else {
		    x.printStackTrace(System.err);
		}
	    }
	}
    }

    public Socket getSocket() {
	return socket;
    }

    public int getClientID() {
	return id;
    }

}
