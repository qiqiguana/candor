/* $Id: Player.java,v 1.20 2004/05/05 23:45:20 njursten Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Rasmus Ahlberg <ahlbgerg@kth.se>
 * @version: $Revision: 1.20 $
 *
 */

package state;

import java.io.*;
import java.util.*;


/**
 * Represents a player in the game world.
 */

public class Player extends Party implements Packable {

    /** The tolerance to decide whether a player has reached its
        destination. If x or y distance to target is less than
        TOLERANCE, no move in that direction is triggered. */
    public static final float TOLERANCE = 0.2f;

    /** The distance to move in one step. */
    public static final float MOVE_DISTANCE = 0.1f;
    
    /** Number to verify against when unpacking. */
    public static final short MAGIC_NO = 0x1337;

    /* Strength of this player */
    private int strength;

    /* Picture of this player */
    private int pictureid;

    /* True if this player is alive */
    private boolean alive;

    /* The time when this user died. */
    private long deathtime;

    /* World coordinates of this player. */
    private float x = 10.0f, y = 0.0f, z;

    /** List to keep track of allowed joins */
    private Hashtable<Integer,JoinInfo> joinlist;

    /** Size of player class when packed (not including length of name) */
    private int packsize = 10;

    /** Reference to the packed data. */
    private byte[] data;
    
    /** True if referenced data is _not_ up to date. */
    private boolean dirty;

    /** Name of the player. */
    private String name;

    /** False if this player has dropped from the game. */
    private boolean connected;

    /** The IP of the player. */
    private String ip;

    /** Amount of money the user has earned. */
    private float money;

    /**
     * Class used to keep track of when a user was invited or applied 
     * to join a group.
     */
    private class JoinInfo {
	long time;
	boolean invite;
	JoinInfo(boolean invited) {
	    time = System.currentTimeMillis();
	    invite = invited;
	}
    }

    /** 
     * Creates a new empty instance of the Player class without an
     * id. Note that a call to GameState.player(pidFor(new Player()))
     * will return null.
     */
    public Player() {
	this(-1);
    }


    /** 
     * Creates a new player with the given id.
     * @param id the associated id of this player.
     */
    public Player(int id) {
	super(id);

	this.strength  = 1;
	this.pictureid = 0;
	this.alive     = true;
	this.dirty     = true;
	this.name      = "Player"+id;
	this.deathtime = 0;
	this.connected = true;
	this.ip        = "0.0.0.0";

	this.joinlist = new Hashtable<Integer,JoinInfo>();
    }

    public void reset(boolean newGame) {
	this.joinlist = new Hashtable<Integer,JoinInfo>();
	this.alive     = true;
	this.dirty     = true;
	this.deathtime = 0;

	this.boss = null;
	this.head = null;
	this.next = null;
	this.prev = null;

	if(newGame) {
	    money = 0;
	}
    }

    /**
     *
     */
    public Player(int id, String name, int pictureid) {
	this(id);
	this.name = name;
	this.pictureid = pictureid;
	System.err.println(id + " " + name + " " + pictureid);
    }

    /**
     *
     */
    public Player(int id, String ip, String name, int pictureid) {
	this(id, name, pictureid);
	this.ip = ip;
    }

    public Player(int id, String ip, String name, int pid, int strength) {
	this(id, ip, name, pid);
	this.strength = strength;
    }


    public boolean isConnected() {
	return connected;
    }

    public void setConnected(boolean conned) {
	connected = conned;
    }

    /**
     * Returns the current strength of the gang this player is part of.
     * @return the total strength of this players gang.
     */
    public int gangStrength() {
	int str = 0;
	LinkedList<Party> gang = gangBoss().getSubparty();
	for (Party p : gang) {
	    str += ((Player)p).strength;
	}
	return str;
    }

    public void setDead(long deathtime, boolean alive) {
	this.deathtime = deathtime;
	this.alive = alive;
    }

    public void setDead() {
	setDead(System.currentTimeMillis());
    }

    public void setDead(long deathtime) {
	setDead(deathtime, false);
    }

    public boolean isDead() {
	return !alive;
    }

    public long getTimeOfDeath() {
	return deathtime;
    }

    public int getStrength() {
	return strength;
    }

    /** Sets the amount of money this player has earned */
    public void setMoney(float money) {
	this.money = money;
    }

    /** Returns amount of money this player has earned */
    public float getMoney() {
	return money;
    }

    /** Returns player IP. */
    public String getIP() {
	return ip;
    }

    /** Returns player name. */
    public String getName() {
	return name;
    }

    /** Returns player picture id. */
    public int getPictureId() {
	return pictureid;
    }

    /** Returns player x coordinate. */
    public float getX() {
	return x;
    }

    /** Returns player y coordinate. */
    public float getY() {
	return y;
    }

    /** Returns player z coordinate. */
    public float getZ() {
	return z;
    }

    /** Sets player y coordinate. */
    public void setY(float f) {
	y = f;
    }

    /** Sets player x coordinate. */
    public void setX(float f) {
	x = f;
    }

    /** Sets player z coordinate. */
    public void setZ(float f) {
	z = f;
    }
    
    /**
     * Checks if player <i>joiner</i> is allowed to join the current player.
     * If <i>invited</i> is set it will be checked if <i>joiner</i> was 
     * invited, otherwise it'll be checked if <i>joiner</i> applied.
     */
    public boolean isJoinOK(Player joiner, boolean invited) {
	if (joinlist.containsKey(joiner.getId())) {
	    JoinInfo jinfo = joinlist.get(joiner.getId());
	    // change 15000 to either a constant defined at top of
	    // file or a variable read from config
	    if (jinfo.time + 15000 > System.currentTimeMillis() &&
		jinfo.invite == invited)
		return true;
	}
	return false;
    }

    public void setJoinOK(Player joiner, boolean invited) {
	JoinInfo jinfo = new JoinInfo(invited);
	joinlist.put(joiner.id,jinfo);
    }

   /**
    * Packs this player into the specified stream.
    * @param out the stream to write this player to.
    */
    public void pack(DataOutputStream out) throws IOException {
	out.writeShort(MAGIC_NO);
	out.writeByte(id);
	out.writeShort(strength);
	out.writeShort(pictureid);
	out.writeFloat(x);
	out.writeFloat(y);
	out.writeFloat(z);
	out.writeUTF(name);
    }
 
    /**
     * Unpack and set player data from the specified stream.
     * @param in the stream to read data from.
     * @return true if data was successfully unpacked.
     */
    public boolean unpack(DataInputStream in) {

	Player tmp = null;

	try {

	    /* Verify that stream really contains player data. */

	    if (in.readShort() != MAGIC_NO) {
		System.err.println("Player.unpack(): *** WARNING *** " +
				   "data is not of proper type!");
		return false;
	    }

	    /* Try unpacking the data to a temporary variable before
	       commiting the changes. */

	    tmp = new Player();

	    tmp.id = in.readByte();
	    tmp.strength  = in.readShort();
	    tmp.pictureid = in.readShort();

	    tmp.x = in.readFloat();
	    tmp.y = in.readFloat();
	    tmp.z = in.readFloat();

	    tmp.name = in.readUTF();

	} catch (EOFException e) {
	    System.err.println("Player.unpack(): *** WARNING *** [EOF] " +
			       "data was incomplete: " + e.getMessage());
	    return false;
	} catch (IOException e) {
	    System.err.println("Player.unpack(): *** WARNING *** [IO] " +
			       "failed to unpack data: " + e.getMessage());
	    return false;
	}

	/* Apply the unpacked data. */

	id = tmp.id;
	name = tmp.name;

	strength = tmp.strength;
	pictureid = tmp.pictureid;

	x = tmp.x;
	y = tmp.y;
	z = tmp.z;

	return true;
    }

   /**
     * Returns an unambigious byte-array representation of this
     * object.
     *
     * @deprecated
     * @return The resulting byte array.
     */
    public byte[] pack() {
	try {
	    ByteArrayOutputStream buf = new ByteArrayOutputStream();
	    DataOutputStream out = new DataOutputStream(buf);
	    pack(out);
	    out.close();
	    return buf.toByteArray();
	} catch (IOException e) {
	    System.err.println("Player.pack(): *** ERROR *** [IO] " +
			       "pack failed! current state is " +
			       "inconsistent: " + e.getMessage());
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
    public void unpack(byte[] b) {
	unpack(new DataInputStream(new ByteArrayInputStream(b)));
    }

    /**
     * Returns the type of the packable.
     * @return The resulting byte array.
     */
    public int type() {
	return Packable.PLAYER;
    }

    public String toString() {
	return name;
    }
}
