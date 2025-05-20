/* $Id: GameState.java,v 1.30 2004/05/05 23:45:19 njursten Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Rasmus Ahlberg <ahlbgerg@kth.se>
 * @version: $Revision: 1.30 $
 *
 */

package state;

import java.util.*;
import java.io.*;


/**
 * The state of the game. Includes the gang tree, all players stats
 * and more. Implements Observable for whenever a change in the state
 * is detected.
 */

public class GameState extends Observable implements Packable {
    
    /** Maximum number of players. */
    public static final int MAX_PLAYER_LIMIT = 128;

    /** Number to verify against when unpacking. */
    private static final short MAGIC_NO = 0x3BBA;

    /** List of known players. */
    private Player[] players;

    /** Total number of known players. */
    private int numOfPlayers;

    /** Number of dead players */
    private int numDead;

    /** Save when a game is started. */
    private long gamestart;

    /** Current state of the game */
    private int gamestate;
    /** Waiting for players to join */
    public static final int STATE_WAITING = 1;
    /** Warmup time. No attacks possible. */
    public static final int STATE_WARMUP = 2;
    /** A game is in progress. */
    public static final int STATE_PLAYING = 3;
    /** Game has ended. */
    public static final int STATE_ENDED = 4;

    /** The current player for this client. null for the server. */
    private Player me;

    /** Minimum time to wait before making next move */
    private long mintimemove;

    /** Number of rounds to play */
    private int totalRounds;
    /** Current round number */
    private int currentRound;

    /** Keep track of when last move of a user was done */
    private Hashtable<Integer,Long> lastmove;

    /**
     * Creates a new instance of the GameState class.
     */
    public GameState() {
	this(15000);
    }

    /**
     * Creates a new instance of the GameState class.
     * @param mintimemove How long a user must wait between attack moves.
     */
    public GameState(long mintimemove) {
	players = new Player[MAX_PLAYER_LIMIT];
	numOfPlayers = 0;
	numDead = 0;
	gamestart = 0;
	gamestate = STATE_WAITING;
	currentRound = 1;
	totalRounds = 1;
	this.mintimemove = mintimemove;
	lastmove = new Hashtable<Integer,Long>();
    }

    public void reset(boolean newGame) {
	numDead = 0;
	for (int i=0; i<players.length; i++) {
	    if (players[i] == null)
		continue;

	    if (!players[i].isConnected()) {
		if (newGame) {
		    players[i] = null;
		    numOfPlayers--;
		}
		else {
		    players[i].reset(newGame);
		    setDead(players[i]);
		}
	    }
	    else {
		players[i].reset(newGame);
	    }
	}

	if (newGame)
	    currentRound = 1;
	lastmove = new Hashtable<Integer,Long>();
    }

    public void setTotalRounds(int totalRounds) {
	this.totalRounds = totalRounds;
    }
    public int getTotalRounds() {
	return totalRounds;
    }
    public void setCurrentRound(int currentRound) {
	this.currentRound = currentRound;
    }
    public int getCurrentRound() {
	return currentRound;
    }

    /**
     * Returns the state of the game. Ie, warmup, running, waiting for users.
     * Use the predefined values defined as STATE_* in this class. 
     */
    public int getGameState() {
	return gamestate;
    }

    /**
     * Sets the state of the game. Ie, warmup, running, waiting for users.
     * Use the predefined values defined as STATE_* in this class. 
     */
    public void setGameState(int gamestate) {
	this.gamestate = gamestate;
	System.err.println("Notifying observers...:" + this.gamestate);
	setChanged();
	notifyObservers(new Integer(gamestate));
    }

    /**
     * Check if enough time has passed since last move.
     */
    public boolean isMoveTimeOK(Player player) {
	if (lastmove.containsKey(player.getId())) {
	    long time = lastmove.get(new Integer(player.getId()));
	    if (time + mintimemove > System.currentTimeMillis())
		return false;
	}
	return true;
    }

    /**
     * Update the time when player last made an attack move.
     */
    public void updateLastMove(Player player) {
	lastmove.put(player.getId(), System.currentTimeMillis());
    }

    /**
     * Adds the specified player to the list of known players.
     *
     * @param p The player that is to be added.
     */
    public void addPlayer(Player p) {
	try {
	    if (players[p.getId()] != null) {
		/* throw new GameStateException("PlayerID exists!"); */
		System.err.println("GameState.addPlayer(): PlayerID exists!");
		return;
	    }
	       
	    players[p.getId()] = p;
	    numOfPlayers++;
	    updateLastMove(p);
	    setChanged();
	    notifyObservers();

	} catch (IndexOutOfBoundsException e) {
	    System.err.println("GameState.addPlayer(): " + 
			       "PlayerID '" + p.id + "' out of the allowed " +
			       "range 0 - " + MAX_PLAYER_LIMIT);
	} catch (NullPointerException e) {
	    e.printStackTrace(System.err);
	}
    }


    /**
     * Removes the specified player from the list of known players.
     *
     * @param p The player that is to be removed.
     */
    public void removePlayer(Player p) {
	try {
	    if (players[p.getId()] == null) {
		/* throw new GameStateException("PlayerID doesn't exist!"); */
		System.err.println("GameState.removePlayer(): " +
				   "PlayerID doesn't exist!");
	    }

	    if (!p.isBoss()) {
		part(p.boss, p);
	    }

	    while(p.head != null) {
		part(p, p.head);
	    }

	    if (gamestate == STATE_WAITING) {
		players[p.getId()] = null;
		numOfPlayers--;
	    }
	    else {
		setDead(p);
		p.setConnected(false);
	    }

	    setChanged();
	    notifyObservers(p);
	} catch (IndexOutOfBoundsException e) {
	    System.err.println("GameState.removePlayer(): " + 
			       "PlayerID '" + p.getId() + "' out of the allowed " +
			       "range 0 - " + MAX_PLAYER_LIMIT);
	} catch (NullPointerException e) {
	    e.printStackTrace(System.err);
	}
    }


    /**
     * Adds a party to another party's underling list. This
     * automatically merges the two parties' gangs.
     *
     * @param parent The Party whose gang the child joined.
     * @param child The Party which joined the parent's gang.
     */
    public void join(Party parent, Party child) {
	try {
	    parent.add(child);
	    setChanged();
	    notifyObservers(child);
	} catch (NullPointerException e) {
	    System.err.println("GameState.join(): parent=" + parent + " " +
			       "child=" + child);
	    e.printStackTrace(System.err);
	}
    }


    /**
     * Removes a party from another party's underling list. The
     * parties that were previously member of the parting party's gang
     * before the join will also part with it, provided they are still
     * in the party's underling list.
     * 
     * @param parent The Party whose gang the child parts from.
     * @param child The Party which parted the parent's gang.
     */
    public void part(Party parent, Party child) {
	try {
	    parent.remove(child);
	    setChanged();
	    notifyObservers(child);
	} catch (NullPointerException e) {
	    e.printStackTrace(System.err);
	}
    }


    /**
     * Returns the player at int id. If player id is out of bounds for
     * the player array, or if no player exists with given id, null is
     * returned. This is consequent with pidFor(Player p) which
     * returns -1 when passed a null value.
     *
     * @param id The id of the player that is to be found.
     * @return The Player with the right id. -1 if no player could be
     * found.
     */
    public Player player(int id) {
	if (id < 0 || id >= MAX_PLAYER_LIMIT)  return null;
	return players[id];
    }

    public Player[] players() {
	return players;
    }

    public void setMe(Player p) {
	me = p;
    }

    public Player getMe() {
	return me;
    }
    
    public int getNumOfPlayers() {
	return numOfPlayers;
    }

    public void reload() {
	setChanged();
	notifyObservers(this);
    }


    /**
     * Sets a player's state to dead and increases the dead players
     * counter.
     *
     * @param p The dead Player.
     * @param deathtime The time of death of the player.
     */
    public void setDead(Player p, long deathtime) {
	if (p != null && !p.isDead()) {
	    numDead++;
	    p.setDead(deathtime);
	    setChanged();
	    notifyObservers(p);
	}
    }

    public void setDead(Player p) {
	setDead(p, System.currentTimeMillis());
    }

    /**
     * Updates a player's world coordinates in the specified
     * direction.
     *
     * @param p The player.
     * @param direction The direction. As specified by the
     * ANSI-DIRECTIONAL-STANDARD 8-55-155.
     * @deprecated does nothing in gl gui
     */
    public void move(Player p, int direction) {
	/*
	float dirx = 0;
	float diry = 0;
	
	if ((direction & 0x1) != 0) {
	    diry -= Player.MOVE_DISTANCE;
	}
	if ((direction & 0x2) != 0) {
	    dirx -= Player.MOVE_DISTANCE;
	}
	if ((direction & 0x4) != 0) {
	    diry += Player.MOVE_DISTANCE;
	}
	if ((direction & 0x8) != 0) {
	    dirx += Player.MOVE_DISTANCE;
	}
	
	p.setX(p.getX() + dirx);
	p.setY(p.getY() + diry);
	System.err.println("New player pos:" + p.getX() + "," + p.getY() + " " + dirx + " " + diry);
	setChanged();
	notifyObservers(p);
	*/
    }


    /**
     * Returns number of dead players.
     * 
     * @return the number of dead players.
     */
    public int getNumDead() { return numDead; }

    /**
     * Returns when the game was started.
     *
     * @return The system time when game was started. -1 when game is
     * not yet started.
     */
    public long getGamestart() { return gamestart; }


    public void setGamestart(long l) {
	gamestart = l;

    }
    
    /**
     * Packs the entire GameState structure into a byte array.
     * @return array of bytes representing the packed object.
     */
    public byte[] pack() {

	ByteArrayOutputStream buf = new ByteArrayOutputStream();
	DataOutputStream out = new DataOutputStream(buf);
	Player p = null;

	try {
	    int numplayers = numOfPlayers;
	    for (int i=0; i<players.length; i++) {
		if (players[i] != null && !players[i].isConnected())
		    numplayers--;
	    }

	    out.writeShort(MAGIC_NO);
	    out.writeByte(gamestate);
	    out.writeByte(numplayers);

	    for (int i=0, k=-1; i < numOfPlayers; i++) {
		// fixme - fix this!
		while (players[++k] == null); // exceptions are caught

		p = players[k];

		if (!p.isConnected())
		    continue;

		p.pack(out);

		out.writeByte(pidOf(p.boss));
		out.writeByte(pidOf(p.next));
		out.writeByte(pidOf(p.prev));
		out.writeByte(pidOf(p.head));
	    }

	} catch (IOException e) {
	    System.err.println("GameState.pack(): *** WARNING *** [IO] " +
			       "failed to pack data: " + e.getMessage());
	    return null;

	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.println("GameState.pack(): *** ERROR *** [IDX] " + 
			       "failed to pack data: " + e.getMessage());
	    return null;
	}

	return buf.toByteArray();
    }

    /**
     * Unpacks the current state from the given array.
     * @param b the byte array from which to read.
     */
    public void unpack(byte[] b) {

	try {

	    DataInputStream in = 
		new DataInputStream(new ByteArrayInputStream(b));

	    /* read and verify magic number */

	    if (in.readShort() != MAGIC_NO) {
		/* throw new PackableException(); */
		System.err.println("GameState.unpack(): *** WARNING *** " +
				   "data is not of proper type!");
		return;
	    }

	    gamestate = in.readByte();
	    int n = in.readByte(); // number of players.

	    /* fixme - data should be read to a temporary place and then
	       verified, after which it should be commited. */

	    Player p = null;
	    for (int i=0; i<n; i++) {
		in.mark(3);
		in.readShort(); // magic number is checked in Player.unpack().
		p = playerAt(in.readByte());
		in.reset();
		if (p.unpack(in)) {
		    p.boss = playerAt(in.readByte());
		    p.next = playerAt(in.readByte());
		    p.prev = playerAt(in.readByte());
		    p.head = playerAt(in.readByte());
		} else {
		    System.err.println("GameState.unpack(): *** ERROR *** " +
				       "data is incomplete! current state " +
				       "is inconsistent!");
		}
	    }
	} catch (EOFException e) {
	    System.err.println("GameState.unpack(): *** ERROR *** [EOF] " +
			       "data is incomplete! current state is " + 
			       "inconsistent: " + e.getMessage());
	} catch (IOException e) {
	    System.err.println("GameState.unpack(): *** ERROR *** [IO] " +
			       "unpack data failed! current state is " + 
			       "inconsistent: " + e.getMessage());
	}
    }

    public int type() {
	return Packable.GAME_STATE;
    }

    /**
     * Returns the player whith the specified id. If a player doesn't exist 
     * with that id doesn't exist, a new player is created and returned. If
     * id is outside the allowed range, null is returned.
     *
     * @return the player with the specified id, or null if id is
     *         outside allowed range.
     */
    private Player playerAt(int id) {

	Player p = null;

	try {
	    p = players[id];
	    if (p == null) {
		p = new Player(id);
		addPlayer(p);
	    }
	} catch (ArrayIndexOutOfBoundsException e) {
	    //System.err.println("GameState.playerAt(): " + 
	    //		       "Invalid Player ID specified: " + id);
	}

	return p;
    }

    /**
     * Returns the id of the specified player.
     *
     * @param p The player for which id is returned.
     * @return The 
     */
    private int pidOf(Party p) {
	if (p == null) return -1;
	return p.getId();
    }

    /**
     * Returns a string representation of GameState. This includes a
     * full view of the tree.
     */
    public String toString() {
	StringBuffer str = new StringBuffer("GameState[");
	boolean first = true;
	for (int i=0, t=-1; i<numOfPlayers; i++) {
	    while (++t < players.length &&  players[t] == null);
	    if (t >= players.length) { return "ERR," + numOfPlayers + "]"; }
	    if (first) {
		str.append(players[t]);
		first = false;
	    }
	    else { str.append(" , " + players[t]); }
	}
	return str + "]";
    }
}
