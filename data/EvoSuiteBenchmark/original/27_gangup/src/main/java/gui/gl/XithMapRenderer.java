/* $Id: XithMapRenderer.java,v 1.21 2004/05/05 11:55:12 emill Exp $
 *
 * Based on Jens Lehmann's excellent tutorial found at 
 * http://xith.org/tutes/GettingStarted/html/simple_header.html
 *
 * @author Joel Andersson <bja@kth.se>
 * @version $Revision: 1.21 $
 */

package gui.gl;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.round;

import static gui.gl.Util3D.*;

import state.*;
import gui.GUIGLState;

import javax.vecmath.*;
import javax.swing.*;

import java.util.Stack;

import com.xith3d.scenegraph.*;
import com.xith3d.render.*;
import com.xith3d.render.jogl.*;
import com.xith3d.datatypes.*;
import com.xith3d.loaders.texture.*;
import com.xith3d.picking.PickRenderResult;
//import com.xith3d.text.*;

import net.java.games.jogl.GLCanvas;
import net.java.games.jogl.GL;


import org.newdawn.xith3d.obj.OBJLoader;
//import org.newdawn.xith3d.md2.MD2Loader;

import java.awt.event.*;
import java.awt.*;

import java.util.Enumeration;

/**
 * This class provides a three dimensional view of the world. Methods are
 * provided for rotatating, zooming in and out, and moving the map.
 *
 */
public class XithMapRenderer extends Canvas3D implements Runnable,
							 MouseListener {

    private BranchGroup scene;

    private Container parent;
    private View view;

    //private Transform3D camera;
    private Transform3D worldTransform;

    private TransformGroup model;		// Model node.
    private TransformGroup terrain;		// Terrain node.

    private Shape3D marker;

    private ParticleManager particleManager = new ParticleManager();
    private ActorManager actorManager = new ActorManager();
    private World grid;

    private float cameraSpeed = 0.02f;

    /** Contains the current key state. */
    private byte keys[] = new byte[256];

    private int pickX;
    private int pickY;
    private Player selected;
    
    private boolean isPickScheduled = false;
    private boolean isMovementScheduled = false;
    private boolean isActorAddScheduled = false;
    private boolean isActorRemoveAllScheduled = false;
    private boolean isFightScheduled = false;
    private boolean isSelectScheduled = false;

    private boolean running = true;
    private boolean stopped = false;

    private Camera camera;
    private CameraControl cameraControl;

    /** Head Up Display */
    private HeadUpDisplay hud;
    private SwingTextOverlay hudTextArea;

    /** Frame statistics (frame rate, triangle count, etc). */
    private FrameStats frameStats;
    private long time = 0;
    private long nextTime = 0;

    private Stack<Actor> actorAdd     = new Stack<Actor>();
    private Stack<Actor> fightWinners = new Stack<Actor>();
    private Stack<Actor> fightLosers  = new Stack<Actor>();

    private GUIGLState guiState;

    private Actor player;
    private NodeTextOverlay playerText;

    
    /** */
    GLCanvas glCanvas;

    /** Mutex used to synchronize mouse events. */
    private final Integer pickParamsMutex = new Integer(0);
    /** Mutex used to make sure we're not adding and removing fights
        at the same time. */
    private final Integer fightMutex = new Integer(0);

    /**
     * Creates a new XithMapRenderer instance.
     *
     * @param owner the parent container, may be null, in which case
     *              a new toplevel window will be created.
     */
    public XithMapRenderer(Container owner, int width, int height) {

	this.parent = owner;

	// create a canvas for our graphics

	RenderPeer rp = new RenderPeerImpl();
	CanvasPeer cp = rp.makeCanvas(owner, width, height, 32, false);
        RenderOptions options = new RenderOptions();
	// glCanvas = (GLCanvas) (((CanvasPeerImpl) cp).getComponent());


	// rendering options

        options.setOption(Option.USE_LIGHTING,false);
        //options.setOption(Option.USE_SHADOWS,true);
	//options.setOption(Option.ENABLE_WIREFRAME_MODE,true);
	//options.setOption(Option.USE_TEXTURES,false);
        cp.setRenderOptions(options);

	// attach event listeners

	cp.getComponent().addMouseListener(this);
	//cp.getComponent().addMouseMotionListener(this);

	set3DPeer(cp);
    }

    /**
     * Initializing function.
     */
    public void init() {
	createScene();
    }

    public ActorManager getActorManager() {
	return actorManager;
    }

    public ParticleManager getParticleManager() {
	return particleManager;
    }

    public void setGUIState(GUIGLState state) {
	guiState = state;
    }

    /**
     * Main rendering loop.
     */
    public void run() {
	
	while (running) {

	    // update actors
	    actorManager.updateAll();
	    // update particles
	    particleManager.updateSystems(view.getTransform());

	    /* Render scene. */

	    grid.render();
	    view.renderOnce();


	    /* Update frame statistics. */
	    /*
	    frameStats.nextFrame();

	    time = System.currentTimeMillis();
	    if (time > nextTime) {
		Vector3f p = new Vector3f();
		camera.getTranslation(p);
		hudTextArea.setText(
		    " tris: " + frameStats.getTriangleCount()+ 
		    "\n fps: " + frameStats.getFrameRate() +
		    "\n cam: x="+(int)p.x+" y="+(int)p.y+
		    " z="+(int)p.z);

		if (playerText != null && player != null) {
		  Point3f q = player.getPosition();
		  playerText.setText(player.getName() +
				     " (x="+(int)q.x+",z="+(int)q.z+")");
		}

		nextTime = time + 1000;
	    }
	    */
	    synchronized (pickParamsMutex) {
		if (isPickScheduled) {
		    performPicking();
		}
	    }

	    if (isMovementScheduled) {
                performMovement();
            }

	    if (isActorRemoveAllScheduled) {
		performActorRemoveAll();
	    }

	    if (isActorAddScheduled) {
		performActorAdd();
	    }

	    if (isFightScheduled) {
		performFight();
	    }

	    if (isSelectScheduled) {
		performSelect();
	    }

	    camera.performCameraMovement();
        }

	stopped = true;
    }
    
  
    /**
     * Sets up the scene.
     */
    public void createScene() {

	// Create universe

	VirtualUniverse universe = new VirtualUniverse();
	Locale locale = new Locale();
	scene = new BranchGroup();

	// Enable picking of nodes in this scene. All nodes from the root
	// down to the node that should be pickable must be made pickable.
	scene.setPickable(true);

	view = new View();
	view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
	//view.setProjectionPolicy(View.PARALLEL_PROJECTION);
	view.setBackClipPolicy(View.VIRTUAL_EYE);
	view.setFieldOfView(60.0f);
	view.setFrontClipDistance(0.1f);
	view.addCanvas3D(this);

	universe.addView(view);
	universe.addLocale(locale);
	locale.addBranchGraph(scene);

	frameStats = FrameStats.newInstance(view);
	frameStats.attachScene(scene);
	
	// Lighting

        Color3f ambientColor = new Color3f(0.1f, 0.1f, 0.1f);
	AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        DirectionalLight light = 
	    new DirectionalLight(true, 
				 new Color3f(1f,1f,1f), 
				 new Vector3f(-1,-1,-1));

	scene.addChild(ambientLightNode);
	scene.addChild(light);

	// The Grid

	grid = new World(20,20,.5f);
	grid.setTexture("grass2.png");
	try {
	    grid.open("dat/maps/test.map");
	} catch (java.io.IOException e) {
	    e.printStackTrace(System.err);
	}
	
	scene.addChild(grid);
	//grid.addChild(new Shape3D(createCoordAxes(1)));

	grid.setPickable(true);	


	// create the heads up display

	hud = new HeadUpDisplay(this);
	hudTextArea = new SwingTextOverlay(127,80);
	hud.addOverlay(hudTextArea);
	hudTextArea.setPosition(10, 5);
	hudTextArea.setVisible(true);

	/*
	try {
	    
	    BranchGroup mdl0 = new OBJLoader().load("./dat/mdl/house01.obj");
	    BranchGroup mdl2 = new OBJLoader().load("./dat/mdl/flatbuilding02.obj");
	    Node g = mdl0.getChild(0);
	    Node m = mdl2.getChild(0);

	    TransformGroup tg0 = new TransformGroup();
	    Transform3D tr0 = new Transform3D();

	    tr0.setTranslation(new Vector3f(2.0f,0.0f,2.0f));
	    tg0.addChild(g);
	    tg0.setTransform(tr0);

	    hud.addOverlay(new NodeTextOverlay(tg0,"Crocker's Place"));

	    TransformGroup tg1 = new TransformGroup();
	    Transform3D tr1 = new Transform3D();

	    tr1.setTranslation(new Vector3f(4.0f,0.0f,4.0f));
	    tg1.addChild(g.sharedCopy(g));
	    tg1.setTransform(tr1);
	    hud.addOverlay(new NodeTextOverlay(tg1,"Home Sweet Home"));

	    TransformGroup tg3 = new TransformGroup();
	    Transform3D tr3 = new Transform3D();

	    tr3.setTranslation(new Vector3f(5, 0.0f ,5));
	    //tg3.addChild(m);
	    tg3.setTransform(tr3);

	    grid.addChild(tg0);
	    grid.addChild(tg1);
	    grid.addChild(tg3);

	    BranchGroup mdl3 = new OBJLoader().load("./dat/mdl/woman02.obj");
	    Node mdl = mdl3.getChild(0);


	    ImageOverlay imgOverlay = new ImageOverlay(
		getBufferedImage("dat/gfx/player_empty.png"));
	    hud.addOverlay(imgOverlay);
	    
	} catch (java.io.IOException e) {
	    e.printStackTrace(System.err);
	    }*/

	// World Coordinate Axes
	//scene.addChild(new Shape3D(createCoordAxes(3)));

	// PARTICLE TEST
	particleManager.createParticleSystem(ParticleSystem.TYPE_SMOKE,
				new Coord3f(4.2f,1.0f,4.2f),
				30,0);
	

	grid.addChild(particleManager.getParticles());
	
	// turn the scene into a render friendly format
	// not implemented in Xith3D
	// scene.compile();

	/* set default camera/view transform */
	view.addCanvas3D(this);
	view.getTransform().lookAt(new Vector3f(0, 12f, 0),
				   new Vector3f(0, 0, 0),
				   new Vector3f(0, 0, 1));

	// camera stuff 
	camera = new Camera(view.getTransform());
	cameraControl = new CameraControl(camera,this,grid);

	get3DPeer().getComponent().addMouseMotionListener(cameraControl);
	get3DPeer().getComponent().addMouseListener(cameraControl);
    }

    /**
     * Makes run thread exit, waits until this has been acknowledged,
     * and then returns. Timeout is defined to 3 seconds, if thread
     * hasn't been exited by then, return anyway. (Might cause
     * exceptions, but could also happen if window is closed before
     * xmr is started.).
     */
    public void dispose() {
	try {
	    running = false;
	    long waitUntil = System.currentTimeMillis() + 3000;
	    while (!stopped) {
		if (System.currentTimeMillis() > waitUntil)
		    break;
		Thread.sleep(10);
	    }
	} catch (Exception e) {
	    e.printStackTrace(System.err);
	    // doesn't really matter, program should close now.
	}
    }
 
    /**
     * Picking players in the world view.
     */
    public void performPicking() {
	PickRenderResult[] selection =
	    view.pick(this, pickX, pickY, 15, 15);

	if (selection != null) {

	    System.out.println("hits: " + selection.length);

	    for (PickRenderResult r : selection) {
		Node node = r.getNodes()[0];
		// this is beautiful code, i know
		while (!(node instanceof Actor) && node != null) {
		    node = node.getParent();
		}
		if (node instanceof Actor) {
		    guiState.setSelected(((Actor)node).getPlayer());
		}
	    }
	}
	isPickScheduled = false;
    }

    public void performSelect() {
	if (selected != null)
	    actorManager.select(selected);
	else
	    actorManager.deselect();
	isSelectScheduled = false;	
    }


     /**
     *
     *
     */
    public void performMovement() {

	PickRay r = castRay(this, pickX, pickY);
	Point3f p = grid.intersect(r);
	Point q = grid.gridCoordToCell(p);
	Actor a = actorManager.getSelected();

	try {

	    Point3f p1 = a.getPosition();
	    Point p2 = grid.gridCoordToCell(p1);
	    map.Path path = grid.findPath(p2.x, p2.y, q.x, q.y);
	    a.clearWaypoints();
	    java.util.Iterator it = path.iterator();

	    while (it.hasNext()) {
		map.Node n = (map.Node) it.next();
		a.addWaypoint(new Point3f(n.getX()/2+.25f, 0,n.getY()/2+.25f));
		//System.err.println(new Point3f(n.getX()/2, 0, n.getY()/2));
	    }

	    //a.setWaypointsVisible(true);
	    a.gotoWaypoint(0);

	} catch (Exception e) {
	    e.printStackTrace();
	}
	isMovementScheduled = false;
    }


    public void performActorAdd() {
	Actor actor;
	while (!actorAdd.empty()) {
	    actor = actorAdd.pop();
	    actorManager.addActor(actor);
	    grid.addActor(actor);
	    hud.addOverlay(actor.getPlayerText());
	}
	isActorAddScheduled = false;
    }


    public void performActorRemoveAll() {
	for (Actor a : actorManager.getActors()) {
	    grid.removeActor(a);
	    hud.removeOverlay(a.getPlayerText());
	}
	actorManager.removeAllActors();
	isActorRemoveAllScheduled = false;
    }


    /**
     * Returns a position between actor a and b.
     * 
     * @param p Where the result is stored.
     * @param a First actor.
     * @param b Second actor.
     * @param scale distance from actor a
     */ 
    private void getPosBetween(Vector3f p, Actor a, Actor b, float scale) {
	p.set(b.getPosition());
	p.sub(a.getPosition());
	p.normalize();
	p.scale(scale);
	p.add(a.getPosition());
    }
    
    public void performFight() {
	synchronized (fightMutex) {
	    try {
		Actor winner = null;
		Actor loser = null;
		Coord3f p = new Coord3f();
		Coord3f bloodheight  = new Coord3f(0.0f,0.2f,0.0f);
		Coord3f muzzleheight = new Coord3f(0.0f,0.2f,0.0f);
		Transform3D t = new Transform3D();

		while (!fightWinners.empty() && !fightLosers.empty()) {
		    winner = fightWinners.pop();
		    loser  = fightLosers.pop();
		    winner.shoot(loser);
		    loser.die(winner);

		    
		    getPosBetween(p,winner,loser,0.06f);
		    p.add(muzzleheight);
		    particleManager.createParticleSystem
			(ParticleSystem.TYPE_MUZZLE, p, 20, 50);

		    getPosBetween(p,loser,winner,0.03f);
		    p.add(bloodheight);
		    particleManager.createParticleSystem
			(ParticleSystem.TYPE_BLOOD, p, 20, 50);
		}
		
		while (!fightWinners.empty() && loser != null) {
		    // getting here means losers is empty
		    winner = fightWinners.pop();
		    // shoot latest target - should work ok
		    winner.shoot(loser);

		    getPosBetween(p,winner,loser,0.06f);
		    p.add(muzzleheight);
		    particleManager.createParticleSystem
			(ParticleSystem.TYPE_MUZZLE, p, 20, 50);
		}

		while (!fightLosers.empty() && winner != null) {
		    // getting here means winners is empty
		    loser = fightLosers.pop();
		    // get shot from latest target - should work ok
		    loser.die(winner);

		    getPosBetween(p,loser,winner,0.03f);
		    p.add(bloodheight);
		    particleManager.createParticleSystem
			(ParticleSystem.TYPE_BLOOD, p, 20, 50);
		}
		
	    } catch (Exception e) {
		e.printStackTrace(System.err);
	    }
	    
	    isFightScheduled = false;
	}
    }

    /**
     * Schedules an actor for movement.
     *
     * @param player The player object to be moved.
     * @param target The target point.
     */
    public void moveActor(Player player, Point3f target) {
	System.err.println("moveActor() start");
	try {
	    Actor a = actorManager.getActor(player);
	    if (a == null) {
		System.err.println("moveActor(): Actor not found!");
		return;
	    }
	    System.err.println(player + " -> " + a);
	    Point p1 = grid.gridCoordToCell(a.getPosition());
	    Point p2 = grid.gridCoordToCell(target);
	    map.Path path = grid.findPath(p1.x,p1.y,p2.x,p2.y);
	    a.clearWaypoints();
	    java.util.Iterator it = path.iterator();
	    while (it.hasNext()) {
		map.Node n = (map.Node) it.next();
		a.addWaypoint(new Point3f(n.getX()/2+.25f, 0,n.getY()/2+.25f));
	    }
	    a.gotoWaypoint(0);
	} catch (Exception e) {
	    e.printStackTrace(System.err);
	}
	System.err.println("moveActor() end");
    }

    public void selectActor(Player p) {
	selected = p;
	isSelectScheduled = true;
    }

    public void deselectActor() {
	selected = null;
	isSelectScheduled = true;
    }

    /**
     * Adds a new actor to the actor manager and to the world.
     * 
     * @param actor The actor to be added.
     */
    public void addActor(Actor actor) {
	actorAdd.push(actor);
	isActorAddScheduled = true;
    }

    public void removeAllActors() {
	isActorRemoveAllScheduled = true;
    }

    public void startFight(Player winner, Player loser) {
	synchronized (fightMutex) {
	    fightAddWinner(winner);
	    fightAddLoser(loser);
	}
	isFightScheduled = true;
    }

    public void fightAddWinner(Player winner) {
	fightWinners.push(actorManager.getActor(winner));
	if (winner.next != null)
	    fightAddWinner((Player)winner.next);
	if (winner.head != null)
	    fightAddWinner((Player)winner.head);
    }

    public void fightAddLoser(Player loser) {
	fightLosers.push(actorManager.getActor(loser));
	if (loser.next != null)
	    fightAddLoser((Player)loser.next);
	if (loser.head != null)
	    fightAddLoser((Player)loser.head);
    }
    
    

   


    /* Events */

    public void mouseClicked(MouseEvent ev) {
        switch(ev.getModifiers()) {
            case MouseEvent.BUTTON1_MASK: leftClick(ev);   break;
            case MouseEvent.BUTTON2_MASK: middleClick(ev); break;
            case MouseEvent.BUTTON3_MASK: rightClick(ev);  break;
        }
    }	

    public void leftClick(MouseEvent ev) {
	pickX = ev.getX();
	pickY = ev.getY();
	isPickScheduled = true;
    }

    public void middleClick(MouseEvent ev) {}
    public void rightClick(MouseEvent ev) {}

    
    public void mousePressed(MouseEvent ev) {}

    public void mouseReleased(MouseEvent ev) {}

    public void mouseMoved(MouseEvent ev) {}

    public void mouseDragged(MouseEvent ev) {
        switch(ev.getModifiers()) {
            case MouseEvent.BUTTON1_MASK: leftDrag(ev);   break;
            case MouseEvent.BUTTON2_MASK: middleDrag(ev); break;
            case MouseEvent.BUTTON3_MASK: rightDrag(ev);  break;
        }
    }

    public void mouseEntered(MouseEvent ev) {}
    public void mouseExited(MouseEvent ev) {}

    private void leftDrag(MouseEvent ev) {}

    private void middleDrag(MouseEvent ev) {}

    private void rightDrag(MouseEvent ev) {}
   
    /* main */

    public static final void main(String[] argv) {

	JFrame f = new JFrame("XithMapRenderer");
	JPanel p = new JPanel();
	XithMapRenderer app = new XithMapRenderer(p,640,480);

	f.getContentPane().add(p);
	f.setBounds(100,100,640,480);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setVisible(true);

	app.init();
	app.run();
    }
}

