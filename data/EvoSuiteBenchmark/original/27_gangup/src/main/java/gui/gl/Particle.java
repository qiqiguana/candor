/* $Id: Particle.java,v 1.1 2004/04/25 15:05:31 emill Exp $
 *
 *
 * @author Emil Lundström <emill@kth.se>
 */

package gui.gl;

import javax.vecmath.*;
import com.xith3d.scenegraph.*;
import com.xith3d.datatypes.*;

/**
 * A single particle in the particle engine.
 */
public class Particle {

    /** The particle location in the world. */
    Coord3f location = new Coord3f();
    
    /** The particle speed. */
    Vector3f speed = new Vector3f();
    
    /** The particle acceleration. */
    Vector3f acceleration = new Vector3f();
    
    /** The particle color. */
    Color4f color = new Color4f();

    Point3f[] particleCoordinates =
    { new Point3f(), new Point3f(), new Point3f(), new Point3f() };
    
    /** The total lifetime of this particle. */
    private int lifetime = 1;

    /** The speed that this particle fades away with. A high value
        makes it fade away slow at first, then quickly in the end. A
        value of one makes it fade away linearly. */
    private float fadeSpeed = 1f;
    
    /** The age of this particle. */
    private int age = 0;

    private ParticleSystem system;
    
    private static Coord3f randomPoint = new Coord3f();

    /**
     * Creates a new particle. Its parameters are all set to zero.
     */
    public Particle() {}
    
    /**
     * Creates a new particle and sets its parameters to the passed
     * arguments.
     *
     * @see newParticle()
     */
    public Particle(ParticleSystem m, Coord3f l, Vector3f s,
		    Vector3f a, Color4f c, int lt, float fs) {
	newParticle(m,l,s,a,c,lt,fs);
    }
     
    /**
     * Sets this particle to a new particle, for re-usability.
     *
     * @param m The parent particle system for this particle.
     * @param l The location of the particle.
     * @param s The speed vector of the particle.
     * @param a The acceleration vector of the particle.
     * @param c The color of the particle.
     * @param lt The lifetime, in update ticks, of the particle.
     */
    public void newParticle(ParticleSystem m, Coord3f l, Vector3f s,
			    Vector3f a, Color4f c, int lt, float fs) {
	system = m;
	location.set(l);
	speed.set(s);
	acceleration.set(a);
	color.set(c);
	lifetime = lt + (int)(system.particleLifetimeRandomness *
	    (system.random.nextFloat()-0.5f));
	fadeSpeed = fs;
	age = 0;
	updateParticleLocation();

	addRandomness(speed,system.particleSpeedRandomness);
	addRandomness(acceleration,system.particleAccelerationRandomness);

    }

    private void addRandomness(Tuple3f point, float scale) {
	randomPoint.set(system.random.nextFloat()-0.5f,
			system.random.nextFloat()-0.5f,
			system.random.nextFloat()-0.5f);
	randomPoint.scale(scale);
	point.add(randomPoint);
    }
    
    /**
     * Executes one update on this particle by updating its speed,
     * location and color. To be called once every update tick from
     * the state loop.
     *
     * @return true if particle is still alive. false otherwise.
     */
    public boolean updateParticle() {

	speed.add(acceleration);
	location.add(speed);
	
	addRandomness(location,system.particleLocationRandomness);
	
	updateParticleLocation();
	
	age++;
	color.w = 1f - (float)Math.pow(age/(float)lifetime,fadeSpeed);
	if (age < lifetime)
	    return true;
	return false;
    }

    public void updateParticleLocation(){
      for(int i = 0; i < 4; i++)
	  particleCoordinates[i].scaleAdd
	      (system.particleSize, system.sharedGeometryData[i], location);
    }
}
