/* $Id: GameModule.java,v 1.34 2004/05/05 23:45:54 njursten Exp $ 
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Rasmus Ahlberg <ahlberg@kth.se>
 * @version: $Revision: 1.34 $
 *
 */

package module;

import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.System.currentTimeMillis;
import static module.MessageFactory.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.lang.annotation.*;
import java.util.*;

import state.*;
import gui.StatsWindow;

@cvs(file     = "$RCSfile: GameModule.java,v $",
     revision = "$Revision: 1.34 $",
     date     = "$Date: 2004/05/05 23:45:54 $",
     author   = "$Author: njursten $",
     tag      = "$Name:  $",
     build    = "")

@mod(name     = "GameModule",
     topics   = "GAME GAMEINFO FROM_NETWORK CONNECTED DROPPED", 
     cmds     = "DUMP INFO",
     desc     = "This module provides the logic of the game.")

/**
 * This class provides the game logic.
 *
 */
public class GameModule extends AbstractModule {

    private GameState state;
    private RuleSet brules;
    private MessageRuleSet mrules;

//    private Context cx;
//    private Scriptable scope;

    /**
     * Strength exponent to use when calculating results of fights.
     */
    private float strengthpow;

    /**
     * Length of warmup in ms.
     */
    private long warmuplength;

    private PriorityQueue<Name> names;

    private int maxpictureid;

    private int minstrength;
    private int maxstrength;

    private int moneyval;

    private String moneytype;

    Random random = new Random();

    /**
     * Creates a new instance of the GameModule class.
     * @throws ModuleRegisterException if registration failed (can't happen.)
     */
    public GameModule()
	throws ModuleRegisterException {
    }

    private static class Name implements Comparable {
	public float p;
	public String s;
	public Name(String s, float p) {
	    this.s = s;
	    this.p = p;
	}

	public int compareTo(Object anothername) {
	    return Float.compare(p,((Name)anothername).p);
	}
    }

    /**
     * Initialization method for modules. Using init() is recommended
     * instead of overloading the constructor.
     */
    protected void init() {
	Player p;
	Player t;
	String datafile = null;

	try {

	    datafile = (String)request("CONFIG",
			     "GET PlayerData playerdatafile").body;

	    BufferedReader br =
		new BufferedReader(new FileReader(datafile));

	    int c = Integer.parseInt(br.readLine());

	    names = new PriorityQueue<Name>();

	    for (int i=0; i < c; i++) {
		names.add(new Name(br.readLine(),random.nextFloat()));
	    }

	    maxpictureid = Integer.parseInt(br.readLine());

	} catch (FileNotFoundException e) {
	    Message m = null;
	    try {
		m = createErrorMessage("_GAME_PLAYERDATA_NOT_FOUND", datafile);
		m.send(this);
	    } catch (MessageDeliveryException x) {
		System.err.println(m.getBody());
	    }
	} catch (Exception e) {
	    e.printStackTrace(System.err);
	}

	brules = new BasicRules();
	mrules = new BasicMessageRules();

	try {
	    String[] configval = ((String) 
		request("CONFIG","GETS GameModule mintimemove,warmuptime,"+
			"strengthpow,moneyval,moneytype,minstrength,"+
			"maxstrength,rounds").getBody()).split("\n");
	    if (configval[0].length() == 0) {
		System.out.println("Couldn't find mintimemove in config.");
		state = new GameState(0);
	    }
	    else {
		state = new GameState(Integer.parseInt(configval[0]));
	    }

	    if (configval[1].length() == 0) {
		System.out.println("Couldn't find strengthpow in config.");
		warmuplength = 0;
	    }
	    else {
		warmuplength = Integer.parseInt(configval[1]);
	    }

	    if (configval[2].length() == 0) {
		System.out.println("Couldn't find strengthpow in config.");
		strengthpow = 1;
	    }
	    else {
		strengthpow = Integer.parseInt(configval[2]);
	    }

	    if (configval[3].length() == 0) {
		System.out.println("Couldn't find moneyval in config.");
		moneyval = 100;
	    }
	    else {
		moneyval = Integer.parseInt(configval[3]);
	    }

	    if (configval[4].length() == 0) {
		System.out.println("Couldn't find moneytype in config.");
		moneytype = "firstrank";
	    }
	    else {
		moneytype = configval[4];
	    }

	    if (configval[5].length() == 0) {
		System.out.println("Couldn't find minstrength in config.");
		minstrength = 1;
	    }
	    else {
		minstrength = Integer.parseInt(configval[5]);
	    }

	    if (configval[6].length() == 0) {
		System.out.println("Couldn't find maxstrength in config.");
		maxstrength = 5;
	    }
	    else {
		maxstrength = Integer.parseInt(configval[6]);
	    }

	    if (configval[7].length() == 0) {
		System.out.println("Couldn't find rounds in config.");
		state.setTotalRounds(2);
	    }
	    else {
		state.setTotalRounds(Integer.parseInt(configval[7]));
	    }
	}
	catch (MessageTimeoutException mte) {
	    strengthpow = 1;
	    warmuplength = 15000;
	    state = new GameState(15000);
	    moneytype = "firstrank";
	    moneyval = 100;
	    minstrength = 1;
	    maxstrength = 5;
	    state.setTotalRounds(2);
	}
    }

    protected void step() {
	if (state.getGameState() == GameState.STATE_WARMUP) {
	    if (System.currentTimeMillis() > state.getGamestart() 
		+ warmuplength) {
		sendMessage(new Message("GAME", "START"));
	    }
	}
    }

    /**
     * This method is invoked once for every Message in the input queue.
     * Module specific message handling is performed here.
     *
     * @param m the Message that is to be processed.
     */
    protected void processMessage(Message m) {

//	System.err.println("GameModule.processMessage(): " + m);

	/* New and improved Action messages */

	if (m.header.equals("GAME")) {
	    handleGameMessage(m);
	    return;
	}

	/* Handle Messages coming off the network */
	
	if (m.header.equals("FROM_NETWORK")) {

	    /* Everything coming off the network implements Packable. */

	    switch (((Packable) m.body).type()) {

	    case Packable.ACTION:
		handleActionMessage(m);
		break;

	    case Packable.TEXT_MESSAGE:
		handleTextMessage(m);
		break;

	    case Packable.PLAYER:
		handlePlayerMessage(m);
		break;

	    default: 
		System.err.println("GameModule.processMessage(): " +
				   "Unknown packable type ignored: " + 
				   ((Packable) m.body).type());
		break;
	    }

	    return;
	}

	/* Connection messages */

	if (m.header.equals("CONNECTED")
		|| m.header.equals("DROPPED")) {
	    handleConnectionMessage(m);
	    return;
	}

	String[] cmd = null; 

	try {

	    cmd = ((String) m.body).split(" ");

	    /* Info messages */

	    if (cmd[0].equals("INFO")) {

		int pid = Integer.parseInt(cmd[1]);
		Player p = state.player(pid);

		if (p != null) {
		    sendMessage(m.reply(p.toString()));
		}

//		System.err.println("GameModule.processMessage(): GOT REQUEST");
//		System.err.println("GameModule.processMessage(): " 
//				   + state.player(pid).toString());

	    } else if (cmd[0].equals("DUMP")) {
		sendMessage(m.reply(toString()));
	    }

	} catch (IndexOutOfBoundsException e) {
	    e.printStackTrace(System.err);
	} catch (NumberFormatException e) {
	    e.printStackTrace(System.err);
	} catch (NullPointerException e) {
	    e.printStackTrace(System.err);
	}

    }

    /**
     *
     * @param action
     */
    public void handleActionMessage(Message m) {

	Action action = null;
	Player actor  = null;
	Player target = null;

	System.err.println(m);

	if (state.getGamestart() == 0) return;
	
	/* Verify that this really is an ActionMessage. */

	try {
	    action = (Action) m.body;
	} catch (ClassCastException e) {
	    System.err.println("FROM_NETWORK message doesn't have" +
			       " a correct Packable as body! isn't" +
			       " an Action");
	    return;
	}

	if (!brules.checkRules(action, state)) {
	    return;
	}

	/* Retrieve the actor and target fields. */

	try {
	    actor  = state.player(action.getActor());
	    target = state.player(action.getTarget());
	} catch (NullPointerException e) {
	    System.err.println("GameModule.handleActionMessage(): " + 
			       "Action not performed due to errors!");
	    e.printStackTrace(System.err);
	    return;
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.println("GameModule.handleActionMessage(): " + 
			       "Action not performed due to errors!");
	    e.printStackTrace(System.err);
	    return;
	}

	GameEvent ev = new GameEvent(currentTimeMillis() -
				     state.getGamestart());
	NetworkData data = new NetworkData(ev);

	switch (action.getAction()) {

	case Action.ACTION_JOIN:
	    state.join(target, actor);
	    ev.setAction(actor.getId(), target.getId(), GameEvent.JOIN);
	    break;

	case Action.ACTION_JOIN_AGREE:
	    state.join(target, actor);
	    ev.setAction(actor.getId(), target.getId(), GameEvent.JOIN);
	    break;

	case Action.ACTION_JOIN_ALLOW:
	    state.join(actor, target);
	    ev.setAction(target.getId(), actor.getId(), GameEvent.JOIN);
	    break;

	case Action.ACTION_JOIN_INVITE:
	    actor.setJoinOK(target, true);
	    ev.setAction(actor.getId(), target.getId(), GameEvent.INVITE);
	    break;

	case Action.ACTION_JOIN_APPLY:
	    actor.setJoinOK(target, false);
	    ev.setAction(actor.getId(), target.getId(), GameEvent.APPLY);
	    break;

	case Action.ACTION_PART:
	    Player boss = (Player) actor.boss;
	    state.part(boss, actor);
	    ev.setAction(actor.getId(), boss.getId(), GameEvent.PART);
	    break;

	case Action.ACTION_KICK:
	    state.part(actor, target);
	    ev.setAction(actor.getId(), target.getId(), GameEvent.KICK);
	    break;

	case Action.ACTION_ATTACK:
	    if (!state.isMoveTimeOK(actor)) {
		return; //disabled for now
	    }
	    state.updateLastMove(actor);

	    float astr = actor.gangStrength();
	    float tstr = target.gangStrength();

	    double actStr = random() * pow(astr, strengthpow);
	    double trgStr = random() * pow(tstr, strengthpow);

	    LinkedList<Party> loser;

	    if (actStr > trgStr) {
		ev.setAction(actor.getId(), target.getId(), GameEvent.FIGHT);
		loser = target.getSubparty();
	    } else {
		ev.setAction(target.getId(), actor.getId(), GameEvent.FIGHT);
		loser = actor.getSubparty();
	    }

	    long tmptime = System.currentTimeMillis();
	    for (Party t : loser) {
		state.setDead((Player) t, tmptime);
	    }

	    checkFinished();
	    break;

	case Action.ACTION_MOVE: /* A player has moved. */
	    ev.setAction(actor.getId(), action.getTarget(), GameEvent.MOVE);
	    break;
	}

	sendMessage(new Message("NETWORK", data));
    }

    /**
     *
     * @param m
     */
    public void handleTextMessage(Message m) {

	TextMessage textm = null;

	/* Verify that really is a TextMessage */

	try {
	    textm = (TextMessage) m.body;
	} catch (ClassCastException e) {
	    System.err.println("FROM_NETWORK message doesn't have"+
			       " a correct Packable as body! isn't"+
			       " a TextMessage");
	    return;
	}

	if (!mrules.checkRules(textm, state)) {
	    return;
	}

	int act = textm.getActor();
	int[] ids = null;
	textm.setTime(currentTimeMillis() - state.getGamestart());

	switch(textm.getMessageType()) {

	case TextMessage.GENERAL:

	    // player is alive, send to all (targ null)

	    if (state.player(act).isDead()) {
		ids = new int[state.getNumDead()];
		int j = 0;
		for (Party t : state.players()) {
		    if (t != null && ((Player) t).isDead()) {
			ids[j++] = t.getId();
		    }
		}
	    }
	    break;

	case TextMessage.GROUP:
	    LinkedList<Party> tmp = 
		state.player(act).gangBoss().getSubparty();
	    ids = new int[tmp.size()];
	    int i = 0;
	    for (Party t : tmp) {
		ids[i++] = t.getId();
	    }
	    break;

	case TextMessage.PRIVATE:
	    ids = new int[2];
	    ids[0] = textm.getActor();
	    ids[1] = textm.getTarget();
	    break;
	}

	sendMessage(new Message("NETWORK", new NetworkData(ids, textm)));
    }

    /**
     *
     * @param m
     */
    public void handleGameMessage(Message m) {

	if (m.body.equals("START")) {

	    /* send every client the start event. */

	    GameEvent ev = new GameEvent(0, 0, GameEvent.START);
	    Object stateData = new NetworkData(null, state);
	    Object eventData = new NetworkData(null, ev);

	    if (state.getGameState() != GameState.STATE_WARMUP) {
		state.setGamestart(currentTimeMillis());
		state.setGameState(GameState.STATE_PLAYING);
		ev.setTime(currentTimeMillis() - state.getGamestart());
		sendMessage(new Message("SETALLOWCONNECT", false));
		sendMessage(new Message("NETWORK", eventData));
		sendMessage(new Message("NETWORK", stateData));
	    } else {
		state.setGameState(GameState.STATE_PLAYING);
		ev.setTime(currentTimeMillis() - state.getGamestart());
		sendMessage(new Message("NETWORK", eventData));
	    }

	} else if (m.body.equals("WARMUP")) {

	    /* Start of warmup time. send every client the warmup event. */

	    GameEvent ev = new GameEvent(0, 0, GameEvent.WARMUP);

	    Object stateData = new NetworkData(null, state);
	    Object eventData = new NetworkData(null, ev);

	    state.setGamestart(currentTimeMillis());
	    state.setGameState(GameState.STATE_WARMUP);
	    ev.setTime(currentTimeMillis() - state.getGamestart());
	    sendMessage(new Message("SETALLOWCONNECT", false));
	    sendMessage(new Message("NETWORK", eventData));
	    sendMessage(new Message("NETWORK", stateData));

	} else if (m.body.equals("STOP") || m.body.equals("FINISHED")) {

	    /* send every client the end event. */

	    GameEvent ev = new GameEvent(0, 0, GameEvent.END);
	    Object eventData = new NetworkData(null, ev);

	    state.setGameState(GameState.STATE_WAITING);
	    ev.setTime(currentTimeMillis() - state.getGamestart());
	    sendMessage(new Message("SETALLOWCONNECT", true));
	    sendMessage(new Message("NETWORK", eventData));

	    giveMoney();
	    if (state.getCurrentRound() >= state.getTotalRounds()) {
		StatsWindow stats = new StatsWindow(state);
		state.reset(true);
	    }
	    else {
		state.reset(false);
		state.setCurrentRound(state.getCurrentRound() + 1);
		sendMessage(new Message("GAME", "WARMUP"));
	    }
	}
    }

    /**
     *
     * @param m
     */
    public void handleConnectionMessage(Message m) {

	String[] cmd = ((String) m.body).split(" ");

	if (m.header.equals("CONNECTED")) {

	    // A new player has joined the game.
	    if (cmd.length == 4 && cmd[0].equals("ID:") &&
		cmd[2].equals("IP:")) {
		try {
		    int id = Integer.parseInt(cmd[1]);
		    int rstrength = minstrength +
			random.nextInt(maxstrength - minstrength + 1);
		    state.addPlayer(new Player(id, cmd[3],
					       names.poll().s,
					       random.nextInt(maxpictureid),
					       rstrength));
		    sendMessage(new Message("NETWORK",new NetworkData
			(new int[]{id}, state)));
		    sendMessage(new Message("NETWORK",new NetworkData
			(null,state.player(id))));
		} catch (NullPointerException e) {
		    e.printStackTrace();
		    System.err.println("Error adding player. Run out of names!");
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		    System.err.println("Error adding player. ID isn't a "+
				       "number! "+m.header+" "+m.body);
		}
	    }
	}

	if (m.header.equals("DROPPED")) {
	    // A player has dropped from the game.
	    if (cmd.length == 4 && cmd[0].equals("ID:")) {
		try {
		    int id = Integer.parseInt(cmd[1]);
		    state.removePlayer(state.player(id));

		    GameEvent ev = new GameEvent(id, -1, GameEvent.DROP);
		    ev.setTime(currentTimeMillis() - state.getGamestart());
		    Object eventData = new NetworkData(null, ev);
		    sendMessage(new Message("NETWORK", eventData));

		    checkFinished();
		} catch (Exception e) {
		    System.err.println("Error removing dropped player. ID"+
				       "isn't a number! "+m.header+" "+m.body);
		}
	    }
	}
    }

    /**
     * Handle player update messages.
     * @param m 
     */
    public void handlePlayerMessage(Message m) {

	Player p = null;
	Player c = null;

	System.err.println("GameModule.processMessage(): " +
			   "Got new player update message.");
	try {
	    p = (Player) m.body;
	} catch (ClassCastException e) {
	    System.err.println("GameModule.handlePlayerMessage(): " +
			       "Message body is not of Player type.");
	    return;
	}

	c = state.player(p.getId());

	c.setX(p.getX());
	c.setY(p.getY());
	c.setZ(p.getZ());

	sendMessage(new Message("NETWORK", new NetworkData(null, p)));
    }

    public String toString() {
	return "GameModule[" + state + "]";
    }

    private void checkFinished() {
	if (state.getGameState() == GameState.STATE_WAITING)
	    return;

	if (state.getNumOfPlayers() - state.getNumDead() <= 1) {
	    state.setGameState(GameState.STATE_WAITING);
	    sendMessage(new Message("GAME", "FINISHED"));
	}
    }

    private void giveMoney() {
	Player[] players = new Player[GameState.MAX_PLAYER_LIMIT];
	for (int i=0; i<players.length; i++) {
	    players[i] = state.player(i);
	}

	java.util.Arrays.sort(players, new PlayerDeathComparator());

	int[] rank = new int[players.length];
	int i = 0;
	long prevtime = -1;
	int currcount = 0;
	while (players[i] != null && i < players.length) {
	    if (players[i].getTimeOfDeath() != prevtime) {
		currcount = i+1;
		prevtime = players[i].getTimeOfDeath();
	    }
	    rank[i] = currcount;
	    i++;
	}

	int maxrank = i;
	float total = 0;
	i = 0;
	float[] moneyarr = new float[maxrank];
	while (i < maxrank) {
	    moneyarr[i] = (float) (1+maxrank-rank[i]) / maxrank;
	    total += moneyarr[i];
	    i++;
	}

        float money = (float) moneyval;
	if (moneytype.equals("total")) {
	    money = (money / total);
	}

	i = 0;
	while (players[i] != null && i < players.length) {
	    Player p = players[i];
	    p.setMoney(p.getMoney() + moneyarr[i]*money);
	    i++;
	}
    }

    /*
     * Class for comparing players' time of death.
     */
    private class PlayerDeathComparator implements Comparator<Player> {
	public int compare(Player a, Player b) {
	    if (b != null && !b.isConnected())
		return -1;
	    if (a != null && !a.isConnected())
		return 1;
	    if (b == null)
		return -1;
	    if (a == null)
		return 1;
	    if (!a.isDead())
		return -1;
	    if (!b.isDead())
		return 1;

	    return (int) (b.getTimeOfDeath() - a.getTimeOfDeath());
	}

	public boolean equals(Object o) {
	    return this == o;
	}
    }
}
