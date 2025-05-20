/* $Id: World.java,v 1.7 2004/05/02 23:01:54 emill Exp $ */

package gui.gl;

import static java.lang.Math.round;
import static gui.gl.Util3D.*;

import java.awt.geom.*;
import java.awt.Point;
import javax.vecmath.*;
import com.xith3d.scenegraph.*;
import com.xith3d.loaders.texture.*;
import java.util.Iterator;

import map.TileMap;
import map.Path;
import map.PathNotFoundException;
import org.newdawn.xith3d.obj.OBJLoader;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StreamTokenizer;

/**
 * This class represents the world. All objects in the world are children
 * of this class.
 */
public class World extends TransformGroup {

    /** The transform associated with this grid. */
    // private Transform3D transform;

    /** The map associated with this world object. */
    private TileMap map;

    /** The cells of the grid. */
    private TransformGroup[][] cells;

    /** The geometry that make up the grid. */
    private Shape3D grid;

    private int rows;
    private int cols;
    private float size;

    private Node[] models;

    /** The active cell marker. */
    private TransformGroup selected;
    private TransformGroup actors;
    private TransformGroup objects;

    /** This is the offset vector used to center the grid. */
    private Vector3f offset;
    private Transform3D center;

    /** The physical bounds of this grid. */
    private Rectangle2D.Float bounds;

    /** */
    private float roll  = 0;
    private float pitch = 0;
    private float yaw   = 0;

    /** */
    //private float posX = 0;
    //private float posY = 0;
    //private float posZ = 0;


    /**
     * Create a new instance of the Grid class. The grid lies in the xz-plane,
     * with origo at the bottom left corner.
     *
     * @param rows
     * @param cols
     * @param size
     */
    public World(int rows, int cols, float size) {

	this.rows = rows;
	this.cols = cols;
	this.size = size;

	cells = new TransformGroup[rows][cols];

	actors = new TransformGroup();

	objects = new TransformGroup();

	/* Create the a visible grid system. */

	grid = createGrid(rows, cols, size);

	/* Compute grid bounds and use them to center the grid. */

	bounds = new Rectangle2D.Float(0,0,(cols)*size,(rows)*size);
	offset = new Vector3f(-.5f*bounds.width, 0, -.5f*bounds.height);

	center = new Transform3D();
	center.setTranslation(offset);

	setGridVisible(false);
	addChild(grid);
	addChild(actors);
	addChild(objects);
	actors.setPickable(true);

	try {
	    map = new TileMap(0,2,"./dat/maps/test.map", 1);
	    for (int i=0; i<rows; i++) {
		for (int j=0; j<cols; j++) {
		    map.Node n = map.getNodeAt(j,i);
		    // System.err.print(n.getCost() + " ");
		    if (n.getCost() > 1.0f) {
		      //System.err.println("x=" + n.getX() + " y="+ n.getY());
			//setCellTexture((int)(n.getX()+0.5f),
			//	       (int)(n.getY()+0.5f),"dirt.png");
		    }
		}
		// System.err.println();
	    }
	} catch (java.io.IOException e) {
	    e.printStackTrace(System.err);
	}

    }

    /**
     * Adds an actor to the this world object.
     * @param a the actor to add.
     */
    public void addActor(Actor a) {
	a.setWorld(this);
	actors.addChild(a);
    }

    //public void addChild(Actor actor) {
    //actor.setWorld(this);
    //actors.addChild(actor);
    //}

    /**
     * Removes the specified actor from this world.
     * @param a the actor to remove.
     */
    public void removeActor(Actor a) {
	actors.removeChild(a);
	a.setWorld(null);
    }

    public void removeAllActors() {
	removeChild(actors);
	actors = new TransformGroup();
	addChild(actors);
	actors.setPickable(true);
    }

    /**
     * Loads the scene from the specified file.
     * @param path the file from which to load the scene.
     */
    public void load(String path) { //throws java.io.IOException {
    }

    public void save(String path) { //throws java.io.IOException {
    }

    public Path findPath(int srcX, int srcY, int dstX, int dstY) 
	throws PathNotFoundException {

	map.Node src = map.getNodeAt(srcX, srcY);
	map.Node dst = map.getNodeAt(dstX, dstY);
	map.reset();

	return map.coalescePath(map.search(src, dst));
    }

    /**
     * Rotates the grid the specified number of degrees around 
     * the given axes.
     *
     * @param rotx
     * @param roty
     * @param rotz
     */
    public void rotXYZ(float rotx, float roty, float rotz) {
	roll  = rotx;
	yaw   = roty;
	pitch = rotz;
    }

    public void panX(float dist) {
	offset.x += dist;
    }

    public void panY(float dist) {
	offset.y += dist;
    }

    public void panZ(float dist) {
	offset.z += dist;
    }

    /**
     * Returns the position of this grid.
     * @return the position of this grid.
     */
    public Point3f getPosition() {
	return new Point3f(offset);
    }

    /**
     * Returns the orientation of this grid.
     * @return the orientation of this grid.
     */
    public Vector3f getOrientation() {
	return new Vector3f(roll, yaw, pitch);
    }

    /**
     * Sets the orienation of this grid to the specified vector.
     * @param v the new orientation of this grid.
     */
    public void setOrientation(Vector3f v) {
	roll = v.x;
	yaw = v.y;
	pitch = v.z;
    }

    /**
     * Calculate the intersection point of the specified ray with 
     * this Grid.
     *
     * @param r the intersecting ray.
     * @return the intersection point of the ray and this grid or null
     *         if the ray is paralell to the grid.
     */
    public Point3f intersect(PickRay r) {

	Vector3f v = r.getDirection();	// ray direction in world space.
	Point3f p = r.getOrigin();	// ray origin in world space.
	Point3f q = null;		// intersection point in grid space.
	Transform3D tf = null;		// world to grid transform.

	// Get a copy of the grid transform.

	tf = new Transform3D(getTransform());

	// Transform the ray by the inverse grid transform. This way we
	// get the ray in grid coordinate space.
	
	tf.invert();
	tf.transform(p);
	tf.transform(v);

	// Check that the ray is not parallell to the grid.

	if (v.y != 0) {
	    
	    // Assume that the grid lies in the xz-plane. It will always
	    // lie in the xz-plane!

	    float t = (offset.y - p.y) / v.y;
	    q = new Point3f(p.x + t * v.x, 0.0f, p.z + t * v.z);
	}

	return q;
    }

    /**
     * Returns the grid cell corresponding to the given grid coordinate.
     * @return the grid cell corresponding to the given grid coordinate.
     */
    public Point gridCoordToCell(Point3f p) {
	return new Point(round(p.x/size-0.5f), round(p.z/size-0.5f));
    }

    /**
     * Sets the background texture of this grid.
     * @param tex the texture to set as background.
     */
    public void setTexture(String tex) {

	TextureAttributes[] textureAttrs = new TextureAttributes[2];
	TextureUnitState[] textureUnitStates = new TextureUnitState[2];
	Texture2D[] textures = new Texture2D[2];

	try {
	    
	    TextureLoader tl = TextureLoader.getInstance();
	    
	    // these paths should be registered elsewhere.

	    tl.registerPath("./");
	    tl.registerPath("./dat/mdl");
	    tl.registerPath("./dat/tex");
	    tl.registerPath("./dat/gfx");

	    textures[0] = (Texture2D) tl.getMinMapTexture("grass2.png");
	    textures[1] = (Texture2D) tl.getMinMapTexture("stone.png");

	    textureAttrs[0] = new TextureAttributes();
	    textureAttrs[0].setTextureMode(TextureAttributes.DECAL);
	    textureAttrs[1] = new TextureAttributes();
	    textureAttrs[1].setTextureMode(TextureAttributes.MODULATE);

	    textureUnitStates[0] = 
		new TextureUnitState(textures[0], textureAttrs[0], null);

	    textureUnitStates[1] = 
		new TextureUnitState(textures[1], textureAttrs[1], null);

	    // Set polygon offset so that we can see objects lying in the
	    // same plane as the grid.

	    PolygonAttributes polyAttr = new PolygonAttributes();
	    polyAttr.setPolygonOffset(10.0f);
	    polyAttr.setPolygonOffsetFactor(20.0f);

	    Appearance a = new Appearance();
	    a.setTextureUnitState(textureUnitStates);
	    a.setPolygonAttributes(polyAttr);

	    addChild(new Shape3D(createQuad(0f,0f,cols * size, 1f, 2*cols), a));

	} catch (Exception e) {
	    e.printStackTrace(System.err);
	}
    }

    /**
     * Sets the texture of the cell at the specified coordinates
     * given in grid space. If name is null or empty string, then
     * the specified cell's texture will be removed.
     *
     * @param row the cell row coordinate.
     * @param col the cell column coordinate.
     * @param name the texture to set to the cell. If it is null then
     *             the current texture will be removed from the cell.
     */
    public void setCellTexture(int row, int col, String name, boolean blend) {

	TransformGroup tg = null;
	Shape3D sh = null;

	try {
	    tg = getCellNode(row, col);
	} catch (ArrayIndexOutOfBoundsException e) {
	    return; // craps!
	}

	if (name == null || name.length() == 0) {
	    removeChild(tg);
	    setCellNode(row, col, null);
	    return;
	}

	if (tg == null) {
	    tg = new TransformGroup();
	    //sh = new Shape3D(createPlane(size, 1));
	    //setTexture2D(sh, "./dat/tex/" + name,blend,blend,true);
	    sh = createTexturedPlane(name, size, 1);
	    tg.addChild(sh);
	    setCellNode(row, col, tg);
	    addChild(tg);
	} else {
	    sh = (Shape3D) tg.getChild(0);
	    TextureLoader tl = TextureLoader.getInstance();
	    Texture2D tex = (Texture2D) tl.getMinMapTexture(name);
	    //setTexture2D(sh,"./dat/tex/" + name,blend,blend,true);
	}

	Vector3f pos = new Vector3f(row * size, 0.01f, col * size);
	Transform3D tf = tg.getTransform();

	tf.setTranslation(pos);
	tg.setTransform(tf);
    }

    public void setCellTexture(int row, int col, String name) {
	setCellTexture(row, col, name, false);
    }

    public void setCellNode(int row, int col, TransformGroup tg) {
	cells[row][col] = tg;
    }

    public TransformGroup getCellNode(int row, int col) {
	return cells[row][col];
    }

    /**
     * Sets the active cell given by the row and col coordinates.
     *
     * @param row the active cell row coordinate.
     * @param col the active cell column coordinate.
     */
    public void setActiveCell(int row, int col) {

	if (selected != null) {
	    
	    Transform3D tf = selected.getTransform();
	    tf.setTranslation(new Vector3f(row*size, 0.01f, col*size));

	} else { // create the active cell marker 

	    selected = new TransformGroup();
	    Vector3f pos = new Vector3f(row*size, 0.01f, col*size);

	    Appearance ap = new Appearance();
	    setTexture2D("dat/tex/active.png", ap);

	    selected.getTransform().setTranslation(pos);
	    selected.addChild(new Shape3D(createPlane(size, 1.0f), ap));
	    addChild(selected);

            /*

	    // enable transparency.
	    TransparencyAttributes tcattr = new TransparencyAttributes();
	    tcattr.setTransparency(0.5f);
	    tcattr.setMode(TransparencyAttributes.BLENDED);
	    a.setTransparencyAttributes(tcattr);
	    sh.setAppearance(a);

	    // set the line width
	    LineAttributes attr = new LineAttributes();
	    attr.setLineWidth(1f);
	    attr.setLineAntialiasingEnable(true);
	    //a.setLineAttributes(attr);

	    // disable depth buffer test for this shape
	    RenderingAttributes attr1 = new RenderingAttributes();
	    attr1.setDepthBufferEnable(false);
	    a.setRenderingAttributes(attr1);

	    Color3f lineColor = new Color3f(1,1,0);
	    Shape3D rect = new Shape3D(createRectangle(size,size,lineColor)));
	    selected.addChild(rect);
				          
            */
	}
    }

    /**
     * Updates and renders the grid. Since scene graph operations must
     * be carried out from the same thread, we keep all such operations 
     * in this method and call it once from the main rendering loop.
     */
    public void render() {

	Transform3D tf = getTransform();
	Transform3D centering = new Transform3D();

	centering.setTranslation(offset);

	//center.getTranslation(pos);
	//pos.x = posX;
	//pos.y = posY;
	//pos.z = posZ;

	//center.setTranslation(offset);

	tf.rotXYZ(roll, yaw, pitch);
	tf.mul(centering);
	setTransform(tf);
    }

    /**
     * Enables or disables the display of the grid.
     * @param enable true if the grid should be visible otherwise false.
     */
    public void setGridVisible(boolean enable) {

	Appearance ap = null;
	RenderingAttributes attr = null;

	try {
	    ap = grid.getAppearance();
	    attr = ap.getRenderingAttributes();
	    attr.setVisible(enable);
	} catch (NullPointerException e) {
	    if (ap == null) {
		ap = new Appearance();
		grid.setAppearance(ap);
	    }
	    if (attr == null) {
		attr = new RenderingAttributes();
		attr.setVisible(enable);
	    }
	}

	ap.setRenderingAttributes(attr);
    }

    /**
     * Returns true if the grid is visible otherwise false.
     * @return true if the grid is visible otherwise false.
     */
    public boolean getGridVisible() {
	boolean visible = false;
	try {
	    Appearance ap = grid.getAppearance();
	    RenderingAttributes attr = ap.getRenderingAttributes();
	    visible = attr.getVisible();
	} catch (NullPointerException e) {
	    // e.printStackTrace(System.err);
	}
	return visible;
    }


    protected void open(String fileName) throws IOException {


	BufferedReader in = new BufferedReader(new FileReader(fileName));
	StreamTokenizer st = new StreamTokenizer(in);

	int j=0, i=0;
	int[] tmp = new int[3];

	// throw away tile info
	int numOfTiles = Integer.parseInt(in.readLine());
	models = new Node[numOfTiles];
	
	for (i=0; i<numOfTiles; i++) {
	    String modelname = in.readLine();
	    BranchGroup mdl = new OBJLoader().load(modelname);
	    models[i] = mdl.getChild(0);	    
	}

	int numOfObjs = Integer.parseInt(in.readLine());

	i = j = 0;
	while (st.nextToken() != st.TT_EOF && i++ < numOfObjs*tmp.length+1) {
	    if (j >= tmp.length) {

		TransformGroup tg = new TransformGroup();
		Transform3D t = new Transform3D();
		Node model = new Shape3D();

		tg.getTransform(t);

		model = model.sharedCopy(models[tmp[2]]);
		t.setTranslation(new Vector3f((float)tmp[0]*size+0.5f,
					      0.0f,
					      (float)tmp[1]*size+0.5f));

		System.err.println(new Vector3f((float)tmp[0]*size,
						0.0f,
						(float)tmp[1]*size));

		tg.setTransform(t);
		tg.addChild(model);
		objects.addChild(tg);

		j = 0;
	    }

	    tmp[j++] = (int) st.nval; 
	}

	in.close();
	objects.updateBounds(true);
    }
}
