/* $Id: IRCProxyModule.java,v 1.3 2004/04/27 19:26:21 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.3 $
 *
 */

package module;

import java.util.*;
import java.util.regex.*;

import java.net.*;
import java.io.*;

import state.*;

@cvs(file     = "$RCSfile: IRCProxyModule.java,v $",
     revision = "$Revision: 1.3 $",
     date     = "$Date: 2004/04/27 19:26:21 $",
     author   = "$Author: bja $",
     tag      = "$Name:  $",
     build    = "")

@mod(name     = "IRCProxyModule",
     topics   = "IRC IRCC IRCQ", 
     cmds     = "",
     desc     = "Simple IRC proxy for debugging.")

/**
 * The IRCProxyModle class provides methods for communicating using the
 * IRC network.
 *
 */
class IRCProxyModule extends AbstractModule {

    /** The session used for communicating with the IRC server. */
    private IRCSession session;

    /** Pattern used by this module to filter input. */
    private Pattern[] pattern;

    /** Contains the users that automatically shall receive op. */
    private HashSet<String> oplist;

    /** Localized strings. */
    protected ResourceBundle locale;

    /** Contains module specific information. */
    protected ModuleInfo modinfo;

    /**
     * Create a new instance of the IRCProxyModule.
     */
    public IRCProxyModule() {}

    /**
     * Create a new instance of the IRCProxyModule with the specified
     * argument.
     *
     * @param krn The Kernel associated with this instance.
     * @throws ModuleRegisterException If the registration fails.
     */
    public IRCProxyModule(Kernel krn)
	throws ModuleRegisterException {
	super(krn);
    }


    /**
     * Connects to a IRC network using default values for nick, channel,
     * server, and port from the configuration file.
     *
     * @return If succesful, the IRCSession associated with the server.
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    public IRCSession ircConnect() {
	try {

	    String[] val;
	    Message r;

	    r = request("CONFIG","GETS IRCProxy nick,channel,server,port");
	    val = ((String) r.body).split("\n");

	    session = ircConnect(val[0], val[2], Integer.parseInt(val[3]));
	    session.join(val[1]);

	} catch (UnknownHostException e) {
	    e.printStackTrace(System.err);
	} catch (IOException e) {
	    e.printStackTrace(System.err);
	} catch (MessageTimeoutException e) {
	    e.printStackTrace(System.err);
	} catch (NullPointerException e) {
	    e.printStackTrace(System.err);
	}

	return session;
    }

    /**
     * Connects to a IRC server on the specified host and port.
     *
     * @param nick The nickname to use in this session.
     * @param host The hostname of the server.
     * @param port The port to connect to.
     *
     * @return If succesful, the IRCSession associated with the server.
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    public IRCSession ircConnect(String nick, String host, int port)
	throws UnknownHostException, IOException {
	return new IRCSession(nick, host, port);
    }

    /**
     * Disconnects from the current IRC server.
     *
     */
    public void ircDisconnect() {
	session.close();
	session = null;
    }

    /**
     * Sends the specified message to the specified channel.
     *
     * @param msg The message that is to be sent.
     * @param chan The channel to which the message should be sent.
     */
    public void ircSendMessage(String msg, String chan) {
	session.send(msg, chan);
    }
    
    /**
     *
     *
     */
    protected void init() {

	/* Get the locale specific resource file. */

	locale = ResourceBundle.getBundle("gangup", Locale.getDefault());

	/* Initialize this class' ModuleInfo structure. Values are
	   retrieved from the cvs and mod annotations made above. */

	modinfo = new ModuleInfo(this);	// see bugs!

	oplist = new HashSet<String>();

	oplist.add("bja");
	oplist.add("istari");
	oplist.add("takashi");
	oplist.add("njursten");
	oplist.add("bartek");

	pattern = new Pattern[10];

	pattern[0] = Pattern.compile("PING :(.+)");
	pattern[1] = Pattern.compile(".*IRCProxy: ([A-Za-z0-9]+) (.+)");
	pattern[2] = Pattern.compile(".* (PRIVMSG #[A-Za-z0-9]+) .*");

    }

    /**
     *
     *
     */
    protected void step() {

	Matcher m = null;

	if (session == null) { return; }	/* nothing to do */

	try {

	    String[] tmp = null;
	    String str = session.recv(false);

	    if (str.length() == 0) {
		return;
	    }

	    System.out.println(str);

	    /* Check for the server ping request. */

	    m = pattern[0].matcher(str);

	    if (m.matches()) {
		session.quote("PONG " + m.group(1));
		System.out.println("PONG " + m.group(1));
	    }
	    
	    tmp = str.split(" ");

	    /* Check for user specified commands. */

	    if (tmp.length > 3
		&& tmp[1].contains("PRIVMSG")
		&& tmp[3].startsWith(":!")) {
		
		String cmd = tmp[3].substring(2);
		String arg = "";

		session.channel(tmp[2]);

		if (cmd.equals("help")) {
		    session.send("There is no help yet!");
		}

		if (cmd.equals("quote")) {
		    if (tmp.length < 5) {
			session.send("usage: quote MESSAGE");
		    } else {
			for (int i=4; i<tmp.length; arg += tmp[i++] + " ");
			session.quote(arg);
		    }
		}

		if (cmd.equals("send")) {
		    if (tmp.length < 6) {
			session.send("usage: send HEAD BODY");
		    } else {
			for (int i=5; i<tmp.length; arg += tmp[i++] + " ");
			sendMessage(new Message(tmp[4], arg));
		    }
		}

		if (cmd.equals("action")) {
		    if (tmp.length < 8) {
			session.send("usage: action HEAD action actor target");
		    } else {
			try {
			    sendMessage(new Message(tmp[4], 
					new Action(Integer.parseInt(tmp[5]), 
						   Integer.parseInt(tmp[6]),
						   Integer.parseInt(tmp[7]))));
			}
			catch (NumberFormatException nfe) {
			    session.send("error: action/actor/target "
					 + "isn't an int.");
			}
		    }
		}

		if (cmd.equals("event")) {
		    if (tmp.length < 8) {
			session.send("usage: event HEAD event actor target");
		    } else {
			try {
			    sendMessage(new Message(tmp[4],
				     new NetworkData(null,
				     new GameEvent(Integer.parseInt(tmp[6]), 
						   Integer.parseInt(tmp[7]),
						   Integer.parseInt(tmp[5])))));
			}
			catch (NumberFormatException nfe) {
			    session.send("error: event/actor/target "
					 + "isn't an int.");
			}
		    }
		}

		if (cmd.equals("textmessage")) {
		    if (tmp.length < 9) {
			session.send("usage: textmessage HEAD messagetype actor target text");
		    } else {
			try {
			    sendMessage(new Message(tmp[4],
				 new TextMessage(Integer.parseInt(tmp[6]),
						 Integer.parseInt(tmp[7]),
						 Integer.parseInt(tmp[5]),
						 tmp[8])));
			} catch (NumberFormatException nfe) {
			    session.send("error: action/actor/target "
					 + "isn't an int.");
			}
		    }
		}
		
		if (cmd.equals("join")) {
		    if (tmp.length < 5) {
			session.send("usage: join CHANNEL");
		    } else {
			session.join(tmp[4]);
		    }
		}

		if (cmd.equals("part")) {
		    if (tmp.length < 5) {
			session.send("usage: part CHANNEL");
		    } else {
			session.part(tmp[4]);
		    }
		}

		if (cmd.equals("chan")) {
		    if (tmp.length < 5) {
			session.send("usage: chan CHANNEL");
		    } else {
			session.channel(tmp[4]);
		    }
		}

		if (cmd.equals("quit")) {
		    session.close();
		    exit();
		}

	    }

	    /* Check for new Messages to deliver. */

	    m = pattern[1].matcher(str);

	    if (m.matches()) {
		Message msg = new Message(m.group(1), m.group(2));
		sendMessage(msg);
	    }

	} catch (IOException e) {
	    e.printStackTrace(System.err);
	}
    }

    /**
     * Processes the specified message.
     * @param m The message to process.
     */
    protected void processMessage(Message m) {

	String[] data = ((String) m.body).split(" ");

	if (m.header.equals("IRCQ")) {
	    session.quote(m.body.toString());
	} else if (m.header.equals("IRC")) {
	    session.send(m.body.toString());
	}
    }
}

/**
 * This class provides a simple wrapper around the IRC protocol. A class
 * that wishes to use IRC simply has to create a new instance of this
 * class and then use the send and recv methods.
 */
class IRCSession {

    private static final String CRLF  = "\r\n";
    private static final String EMPTY = "";

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    String chan = null;
    String nick = null;

    /**
     *
     * @param nick
     * @param host
     * @param port
     */
    public IRCSession(String nick, String host, int port) 
	throws UnknownHostException, IOException {
	socket = open(nick, host, port);
    }
 
    /**
     * Send the specified message to the current channel or user.
     * @param msg The message that is to be sent.
     */
    public void send(String msg) {
	quote("PRIVMSG " + chan + " :" + msg);
    }

    /**
     * Send the specified message to the specified channel or user.
     * @param msg The message that is to be sent.
     * @param dst The channel or user to send message to.
     */
    public void send(String msg, String dst) {
	quote("PRIVMSG " + dst + " :" + msg);
    }

    /**
     *
     * @param msg The message that is to be sent.
     */
    public void quote(String msg) {
	out.print(msg + CRLF);
	out.flush();
    }

    /**
     *
     * @param block True if this method should block.
     * @return The received message.
     */
    public String recv(boolean block) throws IOException {
	if (block || in.ready()) {
	    return in.readLine();
	} else {
	    return EMPTY;
	}
    }

    /**
     * Joins the specified channel.
     *
     * @param chan The channel to join.
     */
    public void join(String chan) {
	this.chan = chan;
	quote("JOIN " + chan);
    }

    /**
     * Parts with the specified channel.
     *
     * @param chan The channel to part with.
     */
    public void part(String chan) {
	quote("PART " + chan);
    }

    /**
     * Sets the currently active channel.
     *
     * @param chan The channel to part with.
     */
    public void channel(String chan) {
	this.chan = chan;
    }

    /**
     * Sends the pong reply to the ping request from server.
     * @param ping The ping request made by the server.
     */
    public void pong(String ping) {
	quote(ping.replace('I','O'));
    }

    /**
     *
     * @param nick
     * @param host
     * @param port
     */
    public Socket open(String nick, String host, int port) 
	throws UnknownHostException, IOException {

	Socket sock = new Socket(host, port);
	String str = null;
	boolean done = false;

	OutputStreamWriter osw;
	InputStreamReader isr;
	    
	osw = new OutputStreamWriter(sock.getOutputStream());
	isr = new InputStreamReader(sock.getInputStream());

	this.out = new PrintWriter(new BufferedWriter(osw));
	this.in  = new BufferedReader(isr);

	this.nick = nick;

	quote("NICK " + nick);
	quote("USER jvm arachnid " + host + " :IRCProxyModule\r\n");

	// fixme - not all servers use this!

	do {
	    str = in.readLine(); 
	    System.err.println(str);
	} while (!str.contains("PING") && 
		 !str.contains("Welcome"));

	if (str.contains("PING")) {
	    pong(str);
	}

	return sock;
    }

    /**
     *
     * @throws IOException
     */
    public void close() /* throws IOException */ {
	quote("QUIT");
	try {
	    out.flush();
	    socket.shutdownInput();
	    socket.shutdownOutput();
	    in.close();
	    out.close();
	    socket.close();
	} catch (SocketException e) {
	} catch (IOException e) {
	}
    }
}


