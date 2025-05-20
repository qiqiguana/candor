/* $Id: Actor.java,v 1.11 2004/05/04 21:33:58 emill Exp $ */

package gui.gl;

import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.atan2;
import static java.lang.Math.PI;

import static com.xith3d.image.DirectBufferedImage.getDirectImageRGBA;

import java.io.FileInputStream;
import java.awt.geom.*;
import java.util.Enumeration;
import java.util.Vector;

import javax.vecmath.*;

import state.Player;

import com.xith3d.image.DirectBufferedImage;
import com.xith3d.scenegraph.*;
import com.xith3d.datatypes.Coord3f;
import com.xith3d.utility.interpolate.*;
import com.xith3d.loaders.texture.*;

import org.newdawn.xith3d.obj.OBJLoader;
import org.newdawn.xith3d.loaders.md2.MD2Loader;
import org.newdawn.xith3d.loaders.md2.MD2Model;
import org.newdawn.xith3d.loaders.md2.MD2ModelInstance;
import org.newdawn.xith3d.loaders.ModelLoadingException;

/**
 * This class represents an actor, i.e. moving object, in the world.
 *
 */
public class Actor extends TransformGroup /* implements GeometryUpdater */ {

    /** The shape representing this actor. */
    private Shape3D shape;

    /** The model associated with this actor. */
    private MD2ModelInstance modelInstance;

    /** The associated transform. */
    private Transform3D transform;

    /** The scale of the associated model. */
    private float scale;
    
    /** The position of this actor. */
    private Coord3f pos;

    private static final float PLAYER_SPEED = 0.8f;

    /** The heading of this actor. */
    private Vector3f heading;

    /** The destination of this actor. */
    private Point3f destination;

    /** Specifies how close to the destination actor must get. */
    private float epsilon;

    /** The speed of this actor. */
    private float speed;

    /** The name of this actor. */
    private String name;

    /** The player object corresponding to this actor. */
    private Player player;

    /** A reference to the world this actor belongs to. */
    private World world;

    private long removeTime = 0;

    public boolean dead = false;


    /** The current waypoint this actor is moving towards. */
    private int currentWaypointIndex;

    /** The list of waypoints associated with this actor. */
    private Vector<Point3f> waypoints;

    /** Debug waypoints. */
    private Shape3D waypointsShape;
    private boolean waypointsVisible;

    private Point3f targetPosition;
    private Transform3D targetTransform;

    private NodeTextOverlay playerText;

    private static MD2Model sharedModel = null;

    /**
     * Creates a new instance of the Actor class. An actor is any object
     * that may change its appearance or transform during it's life time.
     *
     * @param player the player associated with this actor.
     * @param modelname the model used by this actor.
     * @param skin the skin to use with the model.
     */
    public Actor(Player player, String modelname, String skin)
	throws Exception {
	this(player.getName(),modelname,skin);
	this.player = player;
    }

    
    /**
     * Creates a new instance of the Actor class. An actor is any object
     * that may change its appearance or transform during it's life time.
     *
     * @param name the name associated with this actor.
     * @param modelname the model used by this actor.
     * @param skin the skin to use with the model.
     */
    public Actor(String name, String modelname, String skin) 
	throws Exception {

	this(name);

	addChild(loadMD2Model(modelname, skin));
	updateBounds(true);

	setPickable(name);
    }

    /**
     * load obj model
     * @deprecated cleanup this design!
     */
    public Actor(String name, String modelname) 
	throws Exception {
	this(name,new OBJLoader().load(name));
    }

    /**
     * load obj model
     * @deprecated cleanup this design!
     */
    public Actor(String name, Node model) {
	this(name);
	addChild(model);
	setPickable(name);
    }

    /**
     * Creates a new instance of the Actor class. An actor is any object
     * that may change its appearance or transform during it's life time.
     */
    public Actor(String name) {

	transform = getTransform();
	
	this.name = name;
	this.world = null;

	scale = transform.getScale();

	waypoints = new Vector<Point3f>();
	pos = new Coord3f();
	heading = new Vector3f();
	destination = new Point3f();

	targetTransform = new Transform3D();
	targetPosition = new Point3f();

	speed = 0.0f;
	epsilon = 0.1f;

	playerText = new NodeTextOverlay(this);
	//addChild(new Shape3D(Util3D.createCoordAxes(0.1f)));
    }


    private BranchGroup loadMD2Model(String name, String skin) 
	throws Exception {

	BranchGroup group;

	// TextureLoader.registerPath("./dat/mdl");
	// TextureLoader.registerPath("./dat/tex");

	if (sharedModel == null) {
	    System.err.println("CREATE MD2 LOADER");
	    MD2Loader loader = new MD2Loader();
	    System.err.println("BEGIN MD2 LOAD");
	    sharedModel = loader.load(new FileInputStream(name),skin);
	    System.err.println("END MD2 LOAD");
	}
          
	System.err.println("GET MD2 INSTANCE");
	modelInstance = sharedModel.getInstance();
	// modelInstance.setAnimation(anim);
          
	// fix for test model (marvin) 

	TransformGroup tg = new TransformGroup();
	Transform3D tf = new Transform3D();
	tf.rotXYZ((float)PI/2,0.0f,0.0f);
	//tf.rotY(-90.0f);
	//tf.setScale(0.15f);
	tf.setTranslation(new Vector3f(0f,0.15f,0f));
	tg.setTransform(tf);
	System.err.println("ADD MD2 INSTANCE TO TRANSFORMGROUP");
	tg.addChild(modelInstance);

	System.err.println("ADD MD2 INSTANCE TO SCENE");
	group = new BranchGroup();
	group.addChild(tg);
          
	return group;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public NodeTextOverlay getPlayerText() {
	return playerText;
    }

    public Player getPlayer() {
	return player;
    }

    public void setPlayer(Player player) {
	this.player = player;
    }

    public void setX(float x) {
	pos.x = x;
    }

    public void setY(float y) {
	pos.y = y;
    }

    public void setZ(float z) {
	pos.z = z;
    }

    public void die(Actor attacker) {
	heading.y = getBearing(attacker.pos);
	if (modelInstance != null) {
	    modelInstance.setAnimation("death");
	    removeTime = System.currentTimeMillis() + 1000;
	}
    }

    public void die() {
	if (modelInstance != null) {
	    modelInstance.setAnimation("death");
	    removeTime = System.currentTimeMillis() + 1000;
	}
    }

    public void shoot(Actor target) {
	heading.y = getBearing(target.pos);
	if (modelInstance != null) {
	    modelInstance.setAnimation("attack");
	}
    }

    public Point3f getPosition() {
	return new Point3f(pos);
    }

    public void getPosition(Tuple3f res) {
	res.set(pos);
    }

    public void setPosition(Point3f pos) {
	this.pos.set(pos);
    }

    public Vector3f getHeading() {
	return new Vector3f(heading);
    }

    public void setHeading(Vector3f heading) {
	this.heading.set(heading);
    }

    public float getSpeed() {
	return speed;
    }

    public void setSpeed(float speed) {
	this.speed = speed;
    }
    
    public World getWorld() {
	return world;
    }

    /**
     * Sets the world this player belong to. A player can belong to at most
     * one world at any given time.
     *
     * @param w the world this player belong to.
     */
    public void setWorld(World w) {
	if (w != null) {
	    w.removeActor(this);
	}
	this.world = w;
    }

    /**
     * Calculates the bearing of the specified point from the actor's
     * point of view.
     *
     * @param target the point to which bearing is to be calculated.
     */
    public float getBearing(Tuple3f target) {

	targetTransform.invert(getTransform());
	targetPosition.set(target);
	targetTransform.transform(targetPosition);

	return (float) atan2(targetPosition.x, targetPosition.z);
    }

    public void move(float dt) {

	targetPosition.set(pos);
	targetPosition.y = 0;

	float distance = targetPosition.distance(destination);
	float bearing = getBearing(destination);	
	
	if (waypoints.isEmpty()) {

	    if (distance < 10.0f * epsilon) {
		if (distance < epsilon) {
		    if (modelInstance != null && speed != 0.0f) {
			modelInstance.setAnimation("stand");
		    }
		    setSpeed(0.0f);
		} else {
		    setSpeed(distance);
		    if (modelInstance != null) {
			modelInstance.setAnimation("walk");
		    }
		}
	    }

	} else if (distance < epsilon) {
	    moveTo(waypoints.remove(0));
	}

	if (modelInstance != null)
	    modelInstance.interpolateAnim(0.05f);

	if (Math.abs(bearing - heading.y) > 0.001 && speed > 0.0f) {
	    heading.y += (float) (bearing/PI * 0.1);
	}

	if (heading.y > 2*PI) {
	    heading.y -= 2*PI;
	} else if (heading.y < 0) {
	    heading.y += 2*PI;
	}

	float speedScale = 1f - (float) (abs(bearing) / PI);

	pos.x += dt * speedScale * speed * sin(heading.y);
	pos.z += dt * speedScale * speed * cos(heading.y);
    }

    public void clearWaypoints() {
	waypoints.removeAllElements();
	//removeChild(waypointsShape);
    }

    public void gotoWaypoint(int index) {
	try {
	    moveTo(waypoints.get(index));
	    System.err.println("Actor.gotoWaypoint() ");
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.printf("Actor.gotoWaypoint(): index out of range: %d",
			      index);
	}
    }

    public void addWaypoint(Point3f p) {
	System.err.println("adding waypoint: " +p.x+ " " +p.z);
	System.err.println("(start pos: " +pos.x+ " " +pos.z + ")");
	waypoints.add(new Point3f(p));
    }

    public void updateWaypointsShape() {

	if (waypointsShape != null) {
	    getWorld().removeChild(waypointsShape);
	}

	LineStripArray lines = 
	    new LineStripArray(waypoints.size() + 1,
			       GeometryArray.COORDINATES,
			       new int[] {waypoints.size() + 1});


	Point3f[] coords = new Point3f[waypoints.size()];
	waypoints.toArray(coords);
	Point3f start = new Point3f(pos);
	start.y = 0;
	lines.setCoordinate(0, start);
	lines.setCoordinates(1, coords);

	Appearance appearance = new Appearance();
	appearance.setRenderingAttributes(
	    new RenderingAttributes(false, true, 0, 0));
	
	waypointsShape = new Shape3D(lines);

	getWorld().addChild(waypointsShape);

    }

    public void setWaypointsVisible(boolean visible) {
	waypointsVisible = visible;
	if (visible) {
	    updateWaypointsShape();
	}
    }

    /**
     * Sets the destination of this actor to the given coordinates.
     * @param x the x coordinate of the destination point.
     * @param y the y coordinate of the destination point.
     * @param z the z coordinate of the destination point.
     */
    public void moveTo(float x, float y, float z) {
	moveTo(new Point3f(x,y,z));
    }

    /**
     * Sets the destination of this actor.
     * @param dst the destination point.
     */
    public void moveTo(Tuple3f dst) {
	destination.set(dst);
	setSpeed(pos.distance(destination));
	if (modelInstance != null)
	    modelInstance.setAnimation("run");
	System.err.println("distance: " + pos.distance(destination));
	if (getSpeed() > PLAYER_SPEED) {
	    setSpeed(PLAYER_SPEED); 
	}
    }

    /**
     * Returns the shape associated with this actor.
     * @return the shape associated with this actor.
     */
    public Shape3D getShape() {
	return shape;
    }

    /**
     * Changes are performed here so that nothing breaks if methods are
     * invoked from another thread. This method has to be called from the
     * main rendering thread.
     */
    public void update(float dt) {
	if (dead) return;
	transform.setEuler(heading);
	transform.setTranslation(pos);
	move(dt);
	setTransform(transform);
	if (removeTime != 0 && System.currentTimeMillis() > removeTime) {
	    System.err.println("BALEETED");
	    ((Group)getParent()).removeChild(this);
	    dead = true;
	}
    }

    /**
     * Enable picking of this node.
     * @param name the name used to identify this node.
     */
    public void setPickable(String name) {
	Node g = getParent();
	while (g != null) {
	    g.setPickable(true);
	    g = g.getParent();
	}
	setPickableRecursive(this, name);
    }

    /**
     * Set all children of the specified group to be pickable.
     *
     * @param group the node at which to start.
     * @param name the name used to identify the nodes.
     */
    public static void setPickableRecursive(Group group, String name) {

	if (group == null) {
	    return;
	}

	group.setPickable(true);

	Enumeration e = group.getAllChildren();

	while (e.hasMoreElements()) {

	    Node node = (Node) e.nextElement();

	    // if it's a group rename all children
	    if(node instanceof Group) {
		setPickableRecursive((Group)node, name);
	    } else {
		node.setPickable(true); 
		if (node instanceof Shape3D) {
		    node.setName(name);
		}
	    }
	}
    }
}
