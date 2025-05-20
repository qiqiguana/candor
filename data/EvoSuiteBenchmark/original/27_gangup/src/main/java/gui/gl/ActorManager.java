/* $Id: ActorManager.java,v 1.6 2004/05/05 20:07:00 emill Exp $
 *
 * @author Joel Andersson <bja@kth.se>
 * @version $Revision: 1.6 $
 */

package gui.gl;

import java.util.Vector;
import java.util.HashMap;
import java.util.Collection;

import javax.vecmath.Vector3f;
import com.xith3d.scenegraph.Shape3D;
import com.xith3d.scenegraph.Group;
import com.xith3d.scenegraph.Node;
import com.xith3d.scenegraph.Appearance;

import state.*;

/**
 *
 *
 */
public class ActorManager {

    /** List of managed actors */
    //private Vector<Actor> actors;

    /** Hashtable of all managed actors*/
    private HashMap<Player,Actor> actors;
    
    /** The marker that is associated with the selected actor. */
    private Shape3D marker;

    /** The currently selected node. */
    private Actor selected;

    private long currentTime = 0;
    private long nextTime = 0;
    private long lastTime = 0;

    /**
     * Creates a new instance of the ActorManager class.
     *
     */
    public ActorManager() {
	//actors = new Vector<Actor>();
	actors = new HashMap<Player,Actor>();
	marker = new Shape3D(Util3D.createQuad(-0.125f,-0.125f,0.25f, 1, 1));
	marker.setAppearance(new Appearance());
	Util3D.setTexture2D("./dat/tex/shadow.png", 
			    marker.getAppearance(), true, true, false);
    }

    /**
     * Adds the specified actor to the list of managed actors.
     * @param a the actor to add.
     */
    public void addActor(Actor a) {
	actors.put(a.getPlayer(), a);
    }

    public void removeActor(Actor a) {
	actors.remove(a.getPlayer());
    }

    public void removeAllActors() {
	actors.clear();
    }

    public Collection<Actor> getActors() {
	return actors.values();
    }

    public Actor getActor(Player player) {
	return actors.get(player);
    }

    public Actor getSelected() {
	return selected;
    }

    /**
     * Sets the marker on the specified actor.
     * @param p the actor to select.
     */
    public void select(Player p) {
	if (actors.containsKey(p))
	    select((Node)actors.get(p));
    }

    /**
     * Sets the marker on the specified actor.
     * @param node the actor to select.
     */
    public void select(Node node) {
	System.err.println("SELECT!!!!!!! " + node.getName());

	if (node == null || !(node instanceof Actor)) return;

	selected = (Actor)node;

	Group g = (Group) marker.getParent();
	if (g != null) {
	    g.removeChild(marker);
	}

	selected.addChild(marker);
    }

    /**
     * Deselects the selected actor and removes the marker
     * shape from it.
     */
    public void deselect() {
	Group g = (Group) marker.getParent();
	if (g != null) {
	    g.removeChild(marker);
	}
    }

    /**
     * Update all managed actors. This method should be called whenever
     * changes made to the actors should be made visible in the scene.
     */
    public void updateAll() {
	currentTime = System.currentTimeMillis();
	if (currentTime > nextTime) {
	    float dt = (currentTime - lastTime) / 1000.0f;
	    for (Actor a : actors.values()) {
		float d = (float) Math.random();
		float s = (float) Math.signum(Math.random()-0.25);
		Vector3f h = a.getHeading();
		h.y += s*d*0.005;
		//a.setHeading(h);
		//a.setSpeed(d*0.0025f);
		
		a.update(dt/2);
	    }
	    nextTime = currentTime + 10;
	    lastTime = currentTime;
	}
    }
}
