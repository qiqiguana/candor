/* $Id: GameEvent.java,v 1.6 2004/04/30 23:35:12 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.6 $
 *
 */

package state;

import java.io.UnsupportedEncodingException;

/**
 * Class for game events to be sent to the clients.
 *
 * Possible events include:
 *
 * GameEvent.FIGHT
 * <i>actor</i>'s group defeated <i>target</i>'s group in a fight.
 * GameEvent.JOIN
 * <i>actor</i> joined <i>target</i>'s group.
 * GameEvent.APPLY
 * <i>actor</i> wants to join <i>target</i>'s group.
 * GameEvent.INVITE
 * <i>actor</i> invites <i>target</i> to join.
 * GameEvent.WARMUP
 * The warmup time has started. <i>actor</i> and <i>target</i> are undefined.
 * GameEvent.START
 * The game is now started. <i>actor</i> and <i>target</i> are undefined.
 * GameEvent.END
 * The game has now ended. <i>actor</i> and <i>target</i> are undefined.
 * GameEvent.DROP
 * <i>actor</i> was dropped from the game.
 */

public class GameEvent implements Packable {

    /** Actor */
    private int actor;

    /** Target of the action. @see GameEvent */
    private int target;

    /**
     * Event type! Can be of values:
     * FIGHT, JOIN, APPLY, INVITE, START, END, DROP, PART.
     */
    private int eventtype;
    
    /** Relative time to gamestart. */
    private long time;

    public final int type = Packable.GAME_EVENT;

    public final static int FIGHT  = 0;
    public final static int JOIN   = 1;
    public final static int APPLY  = 2;
    public final static int INVITE = 3;
    public final static int START  = 4;
    public final static int END    = 5;
    public final static int DROP   = 6;
    public final static int PART   = 7;
    public final static int MOVE   = 8;
    public final static int KICK   = 9;
    public final static int WARMUP = 10;
    
    /**
     * Creates a new GameEvent.
     */
    public GameEvent(int actor, int target, int eventtype) {
	this.actor = actor;
	this.target = target;
	this.eventtype = eventtype;
    }

    /**
     * Creates a new GameEvent.
     */
    public GameEvent(int actor, int target, int eventtype, int time) {
	this(actor, target, eventtype);
	this.time = time;
    }

    /**
     * Creates a new empty GameEvent. Empty events cannot be sent over
     * the network, and will throw a NullPointerException if done.
     */
    public GameEvent() {
	this(-1,-1,-1);
    }

    /**
     * Creates a new empty GameEvent. Empty events cannot be sent over
     * the network, and will throw a NullPointerException if done.
     */
    public GameEvent(long time) {
	this(-1,-1,-1);
	this.time = time;
    }

    public int getActor() {
	return actor;
    }

    public int getTarget() {
	return target;
    }

    public int getEventType() {
	return eventtype;
    }

    public long getTime() {
	return time;
    }

    public void setTime(long l) {
	time = l;
    }

    /**
     * Sets the actor, target, and eventType of this Action to the
     * specified values;
     *
     * @param actor the Actor performing this action.
     * @param target the Target receiving this action.
     * @param etype the type of action.
     */
    public void setAction(int actor, int target, int etype) {
	this.actor = actor;
	this.target = target;
	this.eventtype = etype;
    }

    public int type() {
	return Packable.GAME_EVENT;
    }
    
    /**
     * Converts a GameEvent into a byte array representation.
     *
     * @param msg The message to be converted.
     * @return The resulting byte array.
     */
    public byte[] pack() {
	
	byte[] data = new byte[3];
	
	data[0] = (byte) actor;
	data[1] = (byte) target;
	data[2] = (byte) eventtype;

	return data;
    }
    
    /**
     * Converts a byte array back into a GameEvent.
     *
     * @param data The byte array to be converted.
     * @return The resultning message.
     */
    public void unpack(byte[] data) {

	this.actor     = (int) data[0];
	this.target    = (int) data[1];
	this.eventtype = (int) data[2];

    }

    public String toString() {
	return "GameEvent[" +eventtype+ "," +actor+ "," +target+ "]";
    }
}
