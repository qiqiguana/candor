/* $Id: GUIGLState.java,v 1.8 2004/05/05 21:34:07 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.8 $
 */

package gui;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.Observable;
import java.util.Observer;
import javax.vecmath.*;
import com.xith3d.datatypes.*;
import state.*;
import gui.gl.*;

/**
 * The GUI state. Extends observable and fires a notifyObserver with
 * the selected object as arg when a new object is selected.
 */
public class GUIGLState extends GUIState {

    protected GameState gs = null;
    protected Point3f[] spawnPoints = new Point3f[GameState.MAX_PLAYER_LIMIT];
    protected Point3f spawnPointOffset = new Point3f(5.0f,0f,5.0f);
    // fix: load in from cfg
    protected String player_model = "dat/mdl/female.md2";
    protected String player_skin  = "dat/mdl/female-skin01.jpg";

    protected XithMapRenderer xmr;

    public final float SP_RADIUS = 3.0f;

    public void setXMR(XithMapRenderer p) {
	xmr = p;
	xmr.setGUIState(this);
    }

    public void setSelected(Player p) {
	if (p != null) {
	    xmr.selectActor(p);
	} else {
	    xmr.deselectActor();
	}
	super.setSelected(p);
    }

    /**
     * Update the guistate.
     *
     * @param o the observable that triggered the change.
     * @param arg the optional argument passed to notifyObserver().
     */
    public void update(Observable o, Object arg) {

	super.update(o,arg); // checks if selected player is dead

	if (arg instanceof GameState) {


	    gs = (GameState) arg;
	    System.err.println("### GAMESTATE RECEIVED: "+gs);

	    int n = gs.getNumOfPlayers();
	    Player[] players = gs.players();
	    Actor[] actors = new Actor[n];
	    
	    // spawnpoints calculations variables
	    double v = 2*PI/n;
	    double cv = 0;
	    int c = 0;

	    xmr.removeAllActors();

	    
	    // add (or re-add) all players
	    for (int i=0; i < GameState.MAX_PLAYER_LIMIT; i++) {
		
		// new players
		
		if (players[i] != null) {
		    spawnPoints[i] = new
			Point3f((float)(cos(cv)*SP_RADIUS),
				0.0f,
				(float)(sin(cv)*SP_RADIUS));
		    //spawnPoints[i].add(spawnPointOffset);
		    cv += v;
		    try {
			actors[c] = new Actor(players[i],
					      player_model,
					      player_skin);
			actors[c].setPosition(destination(players[i]));
			xmr.addActor(actors[c]);
			c++;
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
	    }
	}

	else if (arg != null && arg instanceof Player) {

	    Player p = (Player)arg;
	    if (gs == null) return;

	    
	    if (p.isDead()) {
		xmr.getActorManager().getActor(p).die();
	    } else {
		sendMoveActor(p);
	    }
	}
    }

    private Point3f destination(Player p) {
	Point3f offset = new Point3f();
	offset.scale(0.05f,spawnPoints[p.getId()]);
	offset.add(spawnPoints[p.gangBoss().getId()]);
	offset.add(spawnPointOffset);
	return offset;
    }
    
    private void sendMoveActor(Player p) {
	xmr.moveActor(p,destination(p));

	for (Player c = (Player)p.head; c != null; c = (Player)c.next) {
	    sendMoveActor(c);
	}
    }
}
