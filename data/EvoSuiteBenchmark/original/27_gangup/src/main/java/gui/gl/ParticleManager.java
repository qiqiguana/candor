/* $Id: ParticleManager.java,v 1.4 2004/05/04 19:42:07 emill Exp $
 *
 *
 * @author Emil Lundström <emill@kth.se>
 */

package gui.gl;

import com.xith3d.scenegraph.*;
import com.xith3d.datatypes.*;
import java.util.LinkedList;
import java.util.Stack;

/**
 * A particle manages all particle systems.
 */
public class ParticleManager {

    // milliseconds between updates
    public static final int UPDATE_FREQUENCY = 10;

    private long nextTime = 0;

    private LinkedList<ParticleSystem> stock;
    //private LinkedList<ParticleSystem> systemList =
    //	new LinkedList<ParticleSystem>();
    
    private TransformGroup systemGroup = new TransformGroup();

    public ParticleManager() {
	stock = new LinkedList<ParticleSystem>();
    }

    /**
     * Update all systems this manager manages.
     *
     * @param c The transformation used for particle
     * billboarding. Translation is omitted.
     */
    public void updateSystems(Transform3D c) {
	long currentTime = System.currentTimeMillis();
	if (currentTime > nextTime) {
	    LinkedList tmp = new LinkedList(systemGroup.getChildren());
	    for (Object p : tmp) {
		ParticleSystem s = (ParticleSystem)p;
		if (!s.updateSystem(c)) {
		    systemGroup.removeChild(s);
		    systemGroup.updateBounds(false);
		    stock.add(s);
		}
	    }
	    nextTime = currentTime + UPDATE_FREQUENCY;
	}
    }

    /**
     * Returns a TransformGroup containing all particles of this
     * particle system.
     */
    public TransformGroup getParticles() {
	return systemGroup;
    }

    /**
     * Adds a new particle system.
     *
     * DEPRECATED: Preferred way to create a particle system is by
     * using built in methods of ParticleManager, which incidentally
     * haven't been written yet. This function will soon be private.
     */
    public void addParticleSystem(ParticleSystem s) {
	systemGroup.addChild(s);
	systemGroup.updateBounds(false);
    }

    /**
     * Creates a new particle system.
     *
     * @param type The type of the new system, as specified in
     * ParticleSystem constants.
     * @param l The coordinate of this the new ParticleSystem in this
     * ParticleManager's coordinate system, which typically should be
     * equal to the world system.
     * @param pc The desired particle count. If a unused particle
     * system with the correct particle count is found, it will be
     * used. Otherwise, a new one is allocated.
     * @param lt The lifetime of the particle system.
     * */
    public ParticleSystem createParticleSystem(int type,
					       Coord3f l,
					       int pc, int lt) {
	ParticleSystem found = null;

	for (ParticleSystem s : stock) {
	    if (s.getParticleCount() == pc) {
		found = s;
		break;
	    }
	}

	if (found == null) {
	    found = new ParticleSystem(this,type,l,pc,lt);
	} else {
	    stock.remove(found);
	    found.newSystem(type,l,lt);
	}
	addParticleSystem(found);
	return found;
    }
}


