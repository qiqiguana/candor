/* $Id: Logger.java,v 1.7 2004/05/05 20:07:00 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.7 $
 *
 */

package module;

import java.util.Calendar;
import java.text.DateFormat;

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

import java.util.ResourceBundle;
import java.util.Locale;

import state.*;
import static module.MessageFactory.*;

@cvs(file     = "$RCSfile: Logger.java,v $",
     revision = "$Revision: 1.7 $",
     date     = "$Date: 2004/05/05 20:07:00 $",
     author   = "$Author: emill $",
     tag      = "$Name:  $",
     build    = "")

@mod(name     = "Logger",
     topics   = "*", 
     cmds     = "",
     desc     = "This module dumps every message to a log file.")

/**
 * Dumps a CSV.
 */
class Logger extends AbstractModule {

    private static final String DEFAULT_LOGNAME = "gangup-%d.log";
    private static final String DIRSEP = System.getProperty("file.separator");

    private PrintWriter out;
    private boolean gamestarted = false;
    private boolean writtenstate = false;
    private int count = 0;

    /**
     * Creates a new instance of the Logger.
     *
     * @throws ModuleRegisterException If the registration fails.
     */
    public Logger() 
	throws ModuleRegisterException {
    }

    /**
     * Module initialization.
     */
    protected void init() { startlog(); }
    
    /**
     * Called by the kernel to process a message this module
     * subscribes to. 
     *
     * @param msg The message to be processed.
     */
    protected void processMessage(Message msg) {

	if (msg.getHeader().equals("LOGCMD")) {

	} else if (msg.getHeader().equals("NETWORK")) {
	    log(((NetworkData)msg.getBody()).getData());
	}
    }    

    /**
     *
     *
     */
    void startlog() {

	try {

	    String pwd = System.getProperty("user.dir");
	    String date = String.format("%1$tF %1$tT", Calendar.getInstance());

	    File logfile = null; 
	    String logdir = pwd;

	    // try getting logdir from configuration file.

	    try {
		
		Message reply = request("CONFIG", "GET Logger logdir");
		logdir = (String) reply.getBody();
		
	    } catch (MessageTimeoutException e) {
		try { 
		    Message m;
		    m = createWarningMessage("_LOG_GET_FILENAME_FAIL", logdir);
		    m.send(this);
		} catch (MessageDeliveryException x) {
		    x.printStackTrace();
		}
	    }
	    
	    // try create directories if they do not exist.

	    File dir = new File(logdir);

	    if (!dir.exists() && !dir.mkdirs()) {
		try {
		    createWarningMessage("_LOG_CANNOT_CREATE_DIR",
					 logdir, pwd).send(this);
		} catch (MessageDeliveryException e) {
		    e.printStackTrace(System.err);
		}
		logdir = pwd;
	    }

	    String logname = String.format(
		"%s%sgangup-%3$tF", logdir, DIRSEP, Calendar.getInstance());

	    // make sure the log file does not exist.
	    
	    String unique = "";

	    do {
		logfile = new File(logname + unique + ".log");
		unique = "-" + ++count;
	    } while (logfile.exists());

	    // create the output stream.

	    out = new PrintWriter(new FileOutputStream(logfile),true);
	    out.println("log start: " + date);

	} catch (FileNotFoundException e) {
	    sendMessage(
		new Message("WARNING", 
			    locale.getString("_CANNOT_CREATE_FILE")));
	} catch (SecurityException e) {
	    sendMessage(
		new Message("WARNING", 
			    locale.getString("_FILE_PERMISSION_DENIED")));
	} catch (IOException e) {
	    sendMessage(
		new Message("WARNING", 
			    locale.getString("_UNKNOWN_EXCEPTION")
			    + e.toString()));
	}	
    }

    /**
     * Adds this message to the log.
     *
     * @param p The packable.
     */
    void log(Packable p) {
	
	switch (p.type()) {
	    
	case Packable.GAME_STATE:
	    if (gamestarted && !writtenstate) {
		writtenstate = true;
		GameState s = (GameState) p;
		
		for (int i = 0; i < GameState.MAX_PLAYER_LIMIT; i++) {
		    Player pl = s.player(i);
		    if (pl != null) 
			log("player," + pl.getId() + "," + pl.getName() +
			    "," + pl.getStrength() + "," + pl.getPictureId());
		}
	    }
	    break;
	    
	case Packable.GAME_EVENT:
	    GameEvent e = (GameEvent) p;
	    if (e.getEventType() == GameEvent.WARMUP) {
		gamestarted = true;
		log(e.getTime()+",warmup start");
	    }
	    else if (e.getEventType() == GameEvent.START) {
		gamestarted = true;
		log(e.getTime()+",game start");
	    }
	    else if (e.getEventType() == GameEvent.END) {
		gamestarted = false;
		writtenstate = false;
		log(e.getTime()+",game over");
	    }
		
	    else if (e.getEventType() == GameEvent.JOIN)
		log(e.getTime()+",join,"+e.getActor()+","+e.getTarget());
	    else if (e.getEventType() == GameEvent.PART)
		log(e.getTime()+",part,"+e.getActor()+","+e.getTarget());
	    else if (e.getEventType() == GameEvent.KICK)
		log(e.getTime()+",kick,"+e.getActor()+","+e.getTarget());
	    else if (e.getEventType() == GameEvent.APPLY)
		log(e.getTime()+",apply,"+e.getActor()+","+e.getTarget());
	    else if (e.getEventType() == GameEvent.INVITE)
		log(e.getTime()+",invite,"+e.getActor()+","+e.getTarget());
	    else if (e.getEventType() == GameEvent.FIGHT)
		log(e.getTime()+",fight,"+e.getActor()+","+e.getTarget());
	    else if (e.getEventType() == GameEvent.DROP)
		log(e.getTime()+",drop,"+e.getActor());
	    break;

	case Packable.TEXT_MESSAGE:
	    TextMessage t = (TextMessage) p;
	    if (t.getMessageType() == TextMessage.GENERAL) 
		log(t.getTime()+",message,"+t.getActor()+",all,"+t.getText());
	    else if (t.getMessageType() == TextMessage.GROUP)
		log(t.getTime()+",message,"+t.getActor()+",gang,"+t.getText());
	    else if (t.getMessageType() == TextMessage.PRIVATE)
		log(t.getTime()+",message,"+t.getActor()+","+t.getTarget()+
		    ","+t.getText());
	    break;
	}
    }

    /**
     * Appends a string to the logfile.
     *
     * @param s The string to be appended.
     */
    void log(String s) {
	if (out != null) {
	    out.println(s);
	} else {
	    System.err.println("no logfile specified!");
	}
    }
}

