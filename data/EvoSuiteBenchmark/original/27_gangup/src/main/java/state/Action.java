/* $Id: Action.java,v 1.4 2004/04/27 19:26:22 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.4 $
 *
 */

package state;

import java.io.UnsupportedEncodingException;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import util.TaintedByteArrayOutputStream;
import util.TaintedByteArrayInputStream;
import util.ManagedObject;
import util.ObjectManager;

/**
 * Interface for all objects that are packable, i.e. objects that are
 * to be sent over the network.
 */
public class Action extends ManagedObject implements Packable {

    public final static int ACTION_JOIN        = 0;
    public final static int ACTION_PART        = 1;
    public final static int ACTION_ATTACK      = 2;
    public final static int ACTION_JOIN_APPLY  = 3;
    public final static int ACTION_JOIN_INVITE = 4;
    public final static int ACTION_JOIN_ALLOW  = 5;
    public final static int ACTION_JOIN_AGREE  = 6;
    public final static int ACTION_MOVE	       = 7;
    public final static int ACTION_KICK        = 8;

    /** 
     * Number used by the network module to identify
     * a piece of data as being an Action.
     */
    private static final int PACK_TYPE = Packable.ACTION;

    /** The actor (i.e. the Player that invkoked the action). */
    private int actor;

    /** The target (i.e. the Player the action is invoked on). */
    private int target;

    /** The type of action this is. */
    private int action;

    /** The destination associated with a ACTION_MOVE. */
    private byte destX, destY, destZ;

    /** Utility classes, not part of the Action. */

    private TaintedByteArrayOutputStream byteOutputStream;
    private TaintedByteArrayInputStream byteInputStream;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    /** The manager associated with this Action, if any. */
    private ObjectManager manager = null;

    /**
     * Create a new instance of the Action class. Fields are set to
     * default values. The action, actor, and target are set to -1.
     */
    public Action() {
	this(-1,-1,-1);
    }
    
    /**
     *
     * @param action
     * @param actor
     * @param target
     */
    public Action(int action, int actor, int target) {

	this.actor = actor;
	this.action = action;
	this.target = target;

	byteOutputStream = new TaintedByteArrayOutputStream();
	outputStream = new DataOutputStream(byteOutputStream);
	byteInputStream = new TaintedByteArrayInputStream();
	inputStream = new DataInputStream(byteInputStream);
    }

    /**
     *
     *
     */
    public Action(int action, int actor, byte x, byte y, byte z) { 
	this(action, actor, -1);
	this.destX = x;
	this.destY = y;
	this.destZ = z;
    }

    /**
     *
     *
     */
    public void set(int action, int actor, int target, 
		    byte x, byte y, byte z) {
	this.actor = actor;
	this.action = action;
	this.target = target;
	this.destX = x;
	this.destY = y;
	this.destZ = z;
    }

    /**
     * Sets the actor of this Action object.
     */
    public void setActor(int actor) { this.actor = actor; }
    
    /**
     * Returns the action of the Action object.
     *
     * @return The action byte.
     */
    public int getAction() { return action; }

    /**
     * Returns the actor trying to perform this Action.
     *
     * @return The actor byte.
     */
    public int getActor() { return actor; }

    /**
     * Returns the target of the action.
     *
     * @return The target byte.
     */
    public int getTarget() { return target; }

    /**
     * Returns the type of Packable this is. This number is used by the
     * network module to identify a piece of data coming from the network
     * as being an Action.
     *
     * @return the type of Packable this is.
     */
    public int type() { return PACK_TYPE; }

   /**
    * Packs this Action into the specified stream.
    * @param out the stream to write this player to.
    * @throws PackingException if the packing failed.
    */
    public void pack(DataOutputStream out) throws PackingException {

	try {

	    //out.writeByte(PACK_TYPE);
	    out.writeByte(actor);
	    out.writeByte(target);
	    out.writeByte(action);

	    /* Pack the waypoints associated with this action if any. */

	    /* fixme - this should really be subclassed, but the Action design 
	       has not been finalized, so until then we do it here, *sigh*. */

	    if (action == ACTION_MOVE) {
		out.writeByte(destX);
		out.writeByte(destY);
		out.writeByte(destZ);
		//out.writeByte(this.numOfWaypoints);
		//out.writeFloat(wp[0]);
		//out.writeFloat(wp[1]);
		//out.writeFloat(wp[2]);
		// waypoints are stored as offsets
		//for (byte[] wp : waypoints) {
		//}
	    }

	} catch (Exception e) {
	    throw new PackingException(e);
	}
    }

    /**
     * Unpack and set action data from the specified stream.
     * @param in the stream to read data from.
     * @throws PackingException if the unpacking failed.
     */
    public void unpack(DataInputStream in) throws PackingException {
	try {
	    /*
	    byte packType = in.readByte();

	    if (PACK_TYPE != packType) {
		throw new PackingException(
		    String.format(
			"Action.unpack(): *** ERROR *** " +
			"Data is not of correct packable type: " +
			"got %d should be %d", packType, PACK_TYPE));
	    }
	    */
	    actor  = in.readByte();
	    target = in.readByte();
	    action = in.readByte();

	    if (action == ACTION_MOVE) {
		destX = in.readByte();
		destY = in.readByte();
		destZ = in.readByte();
	    }
	} catch (Exception e) {
	    //if (e instanceof PackingException) throw (PackingException) e;
	    throw new PackingException(e);
	}
    }

    /**
     * Returns a byte array representation of this Action.
     *
     * @return The resulting byte array.
     */
    public byte[] pack() /* throws PackingException */{
	try {
	    byteOutputStream.reset();
	    pack(outputStream);
	    return byteOutputStream.toByteArray();
	} catch (PackingException e) {
	    System.err.println(e.getMessage());
	}
	return null;
    }

    /**
     * Unpacks an array previously created with pack() and puts in
     * this object.
     *
     * @deprecated
     * @param b the byte array to be unpacked.
     */
    public void unpack(byte[] b) /* throws PackingException */ {
	try {
	    byteInputStream.setByteArray(b);
	    unpack(inputStream);
	} catch (PackingException e) {
	    // caught here since letting it propagate would break
	    // things in other modules.
	    System.err.println("Action.unpack(): *** ERROR *** [IO] " +
			       "unpack failed! current state is " +
			       "inconsistent: " + e.getMessage());
	}
    }

    public Action clone() {
	Action a = new Action();
	a.setManager(manager);
	return a;
    }

    public String toString() {
	return String.format(
	    "Action[type=%d,action=%s,actor=%s,target=%s," +
	    "destX=%d,destY=%d,destZ=%d]",
	    PACK_TYPE, action, actor, target, destX, destY, destZ);
    }
}
