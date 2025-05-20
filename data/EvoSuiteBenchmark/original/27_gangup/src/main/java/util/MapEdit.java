/** $Id: MapEdit.java,v 1.17 2004/04/27 19:26:22 bja Exp $ 
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.17 $
 */

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Comparator;
import java.util.Arrays;

//import javax.media.jai.JAI;
//import javax.media.jai.PlanarImage;

/**
 * The MapEdit is used to create, edit and view GangUp maps.
 *
 */
class MapEdit extends Frame {

    private static final int WIDTH  = 640;
    private static final int HEIGHT = 480;
    private static final Font FONT  = new Font("Helvetica", Font.PLAIN, 10);

    /** The file dialog used when loading and saving maps. */
    private final FileDialog fileDialog = new FileDialog(this);

    private StatusBar statusBar;
    private MapEditPane editPane;
    private MapTilePane tilePane;
    private MenuBar menu;
    private MapManager mgr;
    private CheckboxMenuItem cbx;


    /** A simple statusbar component. */
    private class StatusBar extends Container { 

	private Label label;

	public StatusBar() {

	    GridBagLayout gridbag = new GridBagLayout();
	    GridBagConstraints cons = new GridBagConstraints();

	    setLayout(gridbag);

	    label = new Label();
	    

	    cons.insets.left = 5;
	    cons.fill = GridBagConstraints.BOTH;
	    cons.weightx = 1.0;
	    gridbag.setConstraints(label, cons);
	 
	    add(label);
	}
	/**
	 *
	 *
	 */
	public void print(String text) {
	    label.setText(text);
	}
    }

    /* Listens for menu clicks */

    ItemListener itemListener = new ItemListener() {

	    public void itemStateChanged(ItemEvent ev) {
		
		if (cbx != null) {
		    cbx.setState(false);
		}

		cbx = (CheckboxMenuItem) ev.getSource();
		String label = cbx.getLabel();
		System.err.println(label);

		if (label.equals("Background")) {
		    mgr.setLayer(0);
		    return;
		}

		if (label.equals("Foreground")) {
		    mgr.setLayer(1);
		    return;
		}

		if (label.equals("Sprite")) {
		    mgr.setLayer(2);
		    return;
		}

		if (label.equals("Missile")) {
		    mgr.setLayer(3);
		    return;
		}
	    }
	};

    ActionListener actionListener = new ActionListener() {
	    public void actionPerformed(ActionEvent ev) {
		String cmd = ev.getActionCommand();

		if (cmd.equals("open")) {
		    fileDialog.setMode(FileDialog.LOAD);
		    fileDialog.setTitle("Open Map");
		    fileDialog.setVisible(true);

		    if (fileDialog.getFile() != null) {
			try {
			    mgr.loadMap(fileDialog.getDirectory() + "/" +
					fileDialog.getFile());
			} catch (IOException e) {
			    e.printStackTrace();
			}
		    }
		} else if (cmd.equals("save")) {
		    fileDialog.setMode(FileDialog.SAVE);
		    fileDialog.setTitle("Save Map");
		    fileDialog.setVisible(true);
		    if (fileDialog.getFile() != null) {
			try {
			    mgr.saveMap(fileDialog.getDirectory() + "/" + 
					fileDialog.getFile());
			} catch (IOException e) {
			    e.printStackTrace();
			}
		    }
		} else if (cmd.equals("new")) {
		    try {
			mgr.loadMap("dat/maps/empty.map");
		    } catch (IOException e) {
			e.printStackTrace(System.err);
		    }
		} else if (cmd.equals("quit")) {
		    System.exit(0);
		} else if (cmd.equals("sort")) {
		    mgr.sort();
		} else if (cmd.equals("undo")) {
		    mgr.undo();
		} else if (cmd.equals("merge")) {
		    mgr.merge();
		} else if (cmd.equals("layer")) {
		}
	    }
	};

    /**
     * Constructor responsible for creating the main window.
     *
     */
    private MapEdit(String fileName) {

	setTitle("$Id: MapEdit.java,v 1.17 2004/04/27 19:26:22 bja Exp $");
	setSize(WIDTH, HEIGHT);
	setFont(FONT);

	try {
	    mgr = new MapManager(fileName);
	} catch (IOException e) {
	    e.printStackTrace(System.err);
	}

	setLayout(new BorderLayout());

	statusBar = new StatusBar();
	add(statusBar, BorderLayout.SOUTH);

	editPane = new MapEditPane(mgr);
	add(editPane, BorderLayout.CENTER);

	tilePane = new MapTilePane(mgr);
	add(tilePane, BorderLayout.WEST);

	/* menus */

	Menu fileMenu = new Menu("File");
	MenuItem fileNew  = new MenuItem("New");
	MenuItem fileOpen  = new MenuItem("Open...");
	MenuItem fileSave  = new MenuItem("Save...");
	MenuItem separator = new MenuItem("-");
	MenuItem fileQuit  = new MenuItem("Quit");
	Menu editMenu = new Menu("Edit");
	MenuItem editUndo  = new MenuItem("Undo");
	CheckboxMenuItem editLayer0 = new CheckboxMenuItem("Background", true);
	CheckboxMenuItem editLayer1 = new CheckboxMenuItem("Foreground",false);
	CheckboxMenuItem editLayer2 = new CheckboxMenuItem("Sprite", false);
	CheckboxMenuItem editLayer3 = new CheckboxMenuItem("Missile", false);
	Menu toolMenu = new Menu("Tools");
	MenuItem toolSort = new MenuItem("Sort Cells");
	MenuItem toolMerge = new MenuItem("Merge Cells");

	fileNew.addActionListener(actionListener);
	fileQuit.addActionListener(actionListener);
	fileSave.addActionListener(actionListener);
	fileOpen.addActionListener(actionListener);

	fileQuit.setActionCommand("quit");
	fileNew.setActionCommand("new");
	fileOpen.setActionCommand("open");
	fileSave.setActionCommand("save");

	fileMenu.add(fileNew);
	fileMenu.add(fileOpen);
	fileMenu.add(fileSave);
	fileMenu.add(separator);
	fileMenu.add(fileQuit);

	editUndo.addActionListener(actionListener);
	editUndo.setActionCommand("undo");
	editMenu.add(editUndo);
	editMenu.add(separator);

	cbx = editLayer0;
	editLayer0.addItemListener(itemListener);
	editLayer1.addItemListener(itemListener);
	editLayer2.addItemListener(itemListener);
	editLayer3.addItemListener(itemListener);

	editMenu.add(editLayer0);
	editMenu.add(editLayer1);
	editMenu.add(editLayer2);
	editMenu.add(editLayer3);

	toolSort.addActionListener(actionListener);
	toolMerge.addActionListener(actionListener);
	toolSort.setActionCommand("sort");
	toolMerge.setActionCommand("merge");
	toolMenu.add(toolMerge);
	toolMenu.add(toolSort);

	menu = new MenuBar();
	menu.add(fileMenu);
	menu.add(editMenu);
	menu.add(toolMenu);

	setMenuBar(menu);

	enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    }

    /**
     *
     *
     */
    public void echo(String text) {
	statusBar.print(text);
    }

    /**
     *
     *
     */
    public static final void main(String argv[]) {

	String fileName = "dat/maps/empty.map";

	System.out.println("Creating Frame... ");

	if (argv.length > 0) {
	    fileName = argv[0];
	} 

	Frame w = new MapEdit(fileName);
	w.setVisible(true);
    }

    protected void processWindowEvent(WindowEvent ev) {
	switch (ev.getID()) {
	case WindowEvent.WINDOW_CLOSING:
	    System.exit(0);
	    break;
	}
    }
}

class MapManager extends Observable {

    public Map map;		// current map.
    public int tile;		// current tile for drawing. 
    public int layer;		// current active layer.
    public Point cursor;	// current cursor position in map.
    private MapCell old;	// old tile used for undo.

    /**
     *
     *
     */
    public MapManager(String fileName) throws IOException {
	loadMap(fileName);
	cursor = new Point(0, 0);
	layer = tile = 0;
    }

    /**
     *
     *
     */
    public Map loadMap(String fileName) throws IOException {
	map = new Map(fileName);
	setChanged();
	notifyObservers();
	return map;
    }

    /**
     *
     *
     */
    public void saveMap(String fileName) throws IOException {
	merge();
	map.save(fileName);
    }

    /**
     *
     *
     */    
    public void setActiveTile(int index) {
	tile = index;
	setChanged();
	notifyObservers();
    }

    /**
     *
     *
     */
    public void setCursor(int x, int y) {
	cursor.x = x;
	cursor.y = y;
	setChanged();
	notifyObservers();
    }

    /**
     *
     *
     */
    public void setCursor(Point p) {
	cursor.x = p.x;
	cursor.y = p.y;
	setChanged();
	notifyObservers();
    }

    /**
     *
     *
     */
    public void setLayer(int i) {
	layer = i;
    }

    /**
     *
     *
     */
    public void undo() {
	if (map.numOfCells > 0) {
	    map.cell[--map.numOfCells] = old;
	}
	setChanged();
	notifyObservers();
    }

    /**
     * 
     *
     */
    public void draw() {
	if (map.numOfCells >= map.MAX_NUM_CELLS) {
	    System.err.println("MapManager.draw(): Too many cells!");
	    return;
	}

	if (map.numOfCells > 0) {
	    old = map.cell[map.numOfCells-1];
	}
	map.cell[map.numOfCells++] = new MapCell(cursor.x,cursor.y,tile,layer);
	setChanged();
	notifyObservers();
    }

    /**
     * Deletes the cell at the cursor.
     *
     */
    public void delete() {
	MapCell c = map.lookup(cursor.x, cursor.y);
	if (c != null) {
	    c.x = c.y = 0;
	    c.tile = -1;
	}
	setChanged();
	notifyObservers();
    }

    /**
     *
     *
     */
    public void sort() {
	map.sortCells();
	setChanged();
	notifyObservers();
    }

    /**
     * Remove permenantly those cells marked as deleted.
     *
     */
    public void merge() {
	int numCells=0;
	MapCell[] tmp = new MapCell[map.MAX_NUM_CELLS];
	for (int i=0; i<map.numOfCells; i++) {
	    if (map.cell[i].tile != -1) {
		tmp[numCells++] = map.cell[i];
	    }
	}
	map.numOfCells = numCells;
	map.cell = tmp;
    }

    public int getActiveTile() {
	return tile;
    }

    public Map getActiveMap() {
	return map;
    }
}

class MapTile {

    public String name;
    public Image image;

    private static final Toolkit tk;
    static { tk = Toolkit.getDefaultToolkit(); }

    public MapTile (String fileName) throws IOException {
	name = fileName;
	image = tk.getImage(fileName);
    }
}

class MapCell {
    int x,y,tile,layer;
    public MapCell(int x, int y, int tile, int layer) {
	this.x = x;
	this.y = y;
	this.tile = tile;
	this.layer = layer;
    }
    public MapCell(int x, int y, int tile) {
	this(x,y,tile,0);
    }
}

class Map {

    static final int MAX_NUM_CELLS = 1000;

    MapCell[] cell;
    MapTile[] tileList;
    int numOfTiles;
    int numOfCells;

    /** Compares two MapCells and determine their order. */
    private class MapCellComparator implements Comparator<MapCell> {

	// MapCellComparator() { /* ... */ }

	public int compare(MapCell src, MapCell dst) {

	    if (src.layer < dst.layer)
		return -1;

	    if (src.layer > dst.layer)
		return 1;

	    if (src.x == dst.x) {

		if (src.y == dst.y)
		    return 0;

		if (src.y < dst.y)
		    return -1;

		return 1;
	    }

	    if (src.x < dst.x) {
		return -1;
	    }

	    return 1;
	}

	public boolean equals(Object obj) {
	    return obj == this;
	}
	
    };

    /**
     * Constructor
     *
     */
    public Map(String fileName) throws IOException {
	open(fileName);
    }

    /**
     * Open the specified map fileName.
     *
     */
    public Map open(String fileName) throws IOException {

	System.err.println("Map.open()");

	BufferedReader in = new BufferedReader(new FileReader(fileName));
	StreamTokenizer st = new StreamTokenizer(in);
	MediaTracker tracker = new MediaTracker(new Frame());

	int j=0, i=0;
	int[] tmp = new int[4];

	numOfCells  = Integer.parseInt(in.readLine());
	numOfTiles = Integer.parseInt(in.readLine());

	cell = new MapCell[MAX_NUM_CELLS];
	tileList = new MapTile[numOfTiles];

	for (i=0; i<numOfTiles; i++) {
	    tileList[i] = new MapTile(in.readLine());
	    tracker.addImage(tileList[i].image, 0);
	}

	try {
	    tracker.waitForAll();
	} catch (InterruptedException e) {
	    e.printStackTrace(System.err);
	}

	i = j = 0;
	while (st.nextToken() != st.TT_EOF) {

	    if (j >= tmp.length) {
		cell[i++] = new MapCell(tmp[0],tmp[1],tmp[2],tmp[3]);
		j = 0;
	    }

	    tmp[j++] = (int) st.nval; 
	}

	if (j == tmp.length) {
	    cell[i++] = new MapCell(tmp[0],tmp[1],tmp[2],tmp[3]);
	    System.err.println("Map.open(): Missing end of line!");
	}

	if (i != numOfCells) {
	    System.err.println("Map.open(): Unable to open file!");
	}

	in.close();

	return this;
    }

    /**
     *
     *
     */
    public void save(String fileName) throws IOException {

	BufferedWriter buf = new BufferedWriter(new FileWriter(fileName));
	PrintWriter out = new PrintWriter(buf);

	out.println(numOfCells);
	out.println(numOfTiles);

	for (int i=0; i<numOfTiles; i++) {
	    out.println(tileList[i].name);
	}

	for (int i=0; i<numOfCells; i++) {
	    out.println(cell[i].x + " " + cell[i].y + " " + 
			cell[i].tile + " " + cell[i].layer);
	}

	out.println();
	out.close();
	buf.close();
    }

    public void sortCells() {
	System.err.print("Sorting... ");
	Arrays.sort(cell, 0, numOfCells, new MapCellComparator());
	System.err.println("done!");
    }

    /**
     * Lookup and return the MapCell at the specified x and y coordinates.
     * Returns null if no MapCell is found. This method does a linear 
     * search through all the cells. This is considered a bug!
     *
     * @param x the x-coordinate of the MapCell.
     * @param y the y-coordinate of the MapCell.
     * @return The MapCell at the coordinates, or null if not exists.
     */
    public MapCell lookup(int x, int y) {
	for (int i=numOfCells-1; i >= 0; --i) {
	    if (cell[i].x == x && cell[i].y == y) {
		return cell[i];
	    }
	}
	return null;
    }
    

    public void draw(Graphics g) {
    }

    public String toString() {
	return "";
    }
}

class MapGrid extends Panel implements Observer {

    private final static Color MAJOR_GRID_COLOR = new Color(128,128,128);
    private final static Color MINOR_GRID_COLOR = new Color(192,192,192);
    private final static Color TINY_GRID_COLOR  = new Color(224,224,224);
    private final static Color BACKGROUND_COLOR = Color.WHITE;

    private final static int WORLD_OFFSET_X = 672;
    private final static int WORLD_OFFSET_Y = 32;
    private final static int GRID_OFFSET_X = 8;
    private final static int GRID_OFFSET_Y = 4;
    
    private BufferedImage img;	// off-screen image used to draw grid on.
    private BufferedImage buf;	// off-screen double buffer used for drawing.
    private Graphics2D gfx;	// graphics context associated with the image.
    private int tileSize;	// size of a single grid cell.
    private int ox, oy;		// user selected (screen) coordinates.
    private int dx, dy;		// old pointer (screen) coordinates.
    private Polygon cell;	// shape of a single grid cell.
    private MapManager mgr;
    private Image tile;
    private int width, height;

    /**
     * Constructor creates the grid component. 
     *
     * @param w Width of the grid in pixels (>1). 
     * @param h Height of the grid in pixels (>1).
     * @param s Size of a cell in the grid in pixels.
     * @param c Color of the grid.
     */
    MapGrid(MapManager mgr, int w, int h, int s, Color c) {

	tileSize = s;
	setSize(w, h);
	width = w;
	height = h;

	this.mgr = mgr;
	mgr.addObserver(this);

	/* create the backbuffer */

	buf = new BufferedImage(2048, 2048, BufferedImage.TYPE_INT_ARGB);

	createRGBImage(s);
	createGrid(w, h, s/4, TINY_GRID_COLOR);
	createGrid(w, h, s/2, MINOR_GRID_COLOR);
	createGrid(w, h, s, MAJOR_GRID_COLOR);

	int[] xpts = {-16, 0, 16, 0};
	int[] ypts = {0,-8, 0, 8};

	cell = new Polygon(xpts,ypts,4);

	enableEvents(AWTEvent.MOUSE_EVENT_MASK | 
		     AWTEvent.MOUSE_MOTION_EVENT_MASK);

    }
    
    /**
     * Converts the input coordinats in screen space to their respective
     * coordinates in world space. 
     *
     * @param x
     * @param y
     * @param z
     */
    public static Point pixelTransform(int x, int y, int z) {
	return new Point(x + 2 * (y + z), y - (x + z) / 2);
    }

    /**
     * Converts the input coordinats in world space to their respective
     * coordinates in screen space.
     *
     * @param x
     * @param y
     * @param z
     */
    public static Point worldTransform(int x, int y, int z) {
	return new Point((x - z) / 2 - z - y, 
			 (x + 2 * (y - z) + z) / 4);
    }

    public static Point gridTransform(int x, int y, int z) {

	int j = x - WORLD_OFFSET_X + GRID_OFFSET_X;
	int i = y + WORLD_OFFSET_Y + GRID_OFFSET_Y;

	j = (int) (j < 0 ? j / 32 - 0.5 : j / 32 + 1.5);
	i = (int) (i < 0 ? i / 16 - 0.5 : i / 16 + 1.5);

	return new Point(j, i);
    }

    /**
     * Creates an image to draw the grid on. This method clears any
     * previously drawn grids.
     *
     * @param size Size of the grid in pixels (>1).
     */
    public void createRGBImage(int size) {

	System.out.print("Creating image: ");

	img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
	gfx = (Graphics2D) img.getGraphics();

	System.out.println("done!");

	gfx.setColor(BACKGROUND_COLOR);
	gfx.fillRect(0,0,size,size);
    }

    /**
     * Creates a diagonal (diamond shaped) grid with the specified
     * dimensions and color.
     *
     * @param w Width of the grid in pixels (>1).
     * @param h Height of the grid in pixels (>1).
     * @param s Size of a cell in the grid in pixels.
     * @param c Color of the grid.
     */
    public void createGrid(int w, int h, int s, Color c) {
	
	gfx.setColor(c);

	System.out.print("Drawing " + s+"x"+s + "-grid: ");

	/* have to move y in half steps in order to have the grid
	   look nice near the borders. */

	for (int y=0; y<tileSize; y+=s/2) {

	    System.out.print(".");

	    for (int x=0; x<tileSize; x+=s) {
		gfx.drawLine(x, y, x+tileSize, y+tileSize/2);
		gfx.drawLine(tileSize-x, y, -x, y+tileSize/2);
	    }
	}

	System.out.println();
    }

    public void update(Graphics g){
        paint(g);
    }

    /* Note: It might be possible to get better performance by using
       the copyPixels method in Graphics. This requires knowing what
       region of the grid is currently visible and finding a tilable
       subregion somewhere in there.

       If that fails, try drawing whole grid on to a single buffer 
       (by first drawing one tile and then copy that seamlessly over 
       the entire image.) This may require undesirably large amounts
       of memory, though. */

    public void paint(Graphics g) {

	Point p,p1;
	int x, y, z, w, h;
	MapTile t;

	/* fill the entire canvas with the image. */

	gfx = (Graphics2D) buf.getGraphics();
	gfx.setColor(Color.WHITE);
	gfx.fillRect(0,0,1024,1024);

	gfx.drawImage(img, 0, 0, this);
	for (y=0; y<height; y+=tileSize) {
	    for (x=0; x<width; x+=tileSize) {
		gfx.copyArea(0, 0, tileSize, tileSize, x, y);
		gfx.drawImage(img, x, y, this);
	    }
	}


	/* draw map */

	for (int i=0; i<mgr.map.numOfCells; i++) {

	    try {

	    x = mgr.map.cell[i].x;
	    y = mgr.map.cell[i].y;
	    z = mgr.map.cell[i].tile;

	    if (z != -1) { /* check whether cell has been deleted */

		t = mgr.map.tileList[z];
		w = t.image.getWidth(this);
		h = t.image.getHeight(this);

		p = worldTransform(x*32+672-8-w/2,y*16-32-4+w/4,74);
		gfx.drawImage(t.image, p.x, p.y-h, this);
	    }
	    } catch (Exception e) {
		System.err.println("MapGrid.paint(): i=" + i + " l=" + mgr.map.cell.length);
		e.printStackTrace(System.err);
	    }
	}

	int z1 = 32;
	int z2 = z1/2;

	/* fixme - have to adjust the world to grid alignment. */

	p = pixelTransform(dx, dy+4, 74);	// oy+4 is for alignment.

	if (p.y < 0) p.y -= z2;
	if (p.x < 0) p.x -= z1;

	p.x = (int) (p.x / z1) * z1;
	p.y = (int) (p.y / z2) * z2;

	p1 = gridTransform(p.x, p.y, 74);
	String s = "(" + p1.x + "," + p1.y + ")";

	int offx=0, offy=3; // align the screen and grid coords.

	p = worldTransform((p.x / z1) * z1, 
			   (p.y / z2) * z2, 74);

	/*
	gfx.translate(p.x + offx, p.y + offy);
	gfx.setColor(Color.BLUE);
	gfx.fillPolygon(cell);
	gfx.setColor(Color.RED);
	gfx.drawLine(0,0,0,-16);
	gfx.fillOval(-4,-20,8,8);
	gfx.drawBytes(s.getBytes(), 0, s.length(), 6, -16);
	gfx.translate(-p.x-offx,-p.y-offy);
	*/

	g.drawImage(buf, 0, 0, this);
    }

    /** 
     * Sets the preferred size of this component. This method is has 
     * to be defined when using the ScrollPane component.
     */
    public Dimension getPreferredSize() {
	return new Dimension(getWidth(), getHeight());
    }


    /**
     *
     *
     */    
    protected void processMouseEvent(MouseEvent ev) {

	super.processMouseEvent(ev);

	int mods = ev.getModifiers();
	int nx = ev.getX();
	int ny = ev.getY();

	Point g;

	g = pixelTransform(nx, ny, 74);
	g = gridTransform(g.x, g.y, 74);

	switch (ev.getID()) {

	case MouseEvent.MOUSE_PRESSED:

	    if ((mods & MouseEvent.BUTTON1_MASK) > 0) {

		/* create new cell */

		mgr.setCursor(g);
		mgr.draw();

	    } 

	    if ((mods & MouseEvent.BUTTON2_MASK) > 0) {
	    }

	    if ((mods & MouseEvent.BUTTON3_MASK) > 0) {

		mgr.setCursor(g);
		mgr.delete();
/*		
		Point p =
		    ((ScrollPane) getParent()).getScrollPosition();

		Dimension d =
		    ((ScrollPane) getParent()).getViewportSize();

		System.out.println("scrollpos: " + p);
		System.out.println("viewdim:   " + d);
*/
	    } 

//	    repaint();
//	    repaint(ox - 64, oy - 32, 128, 64);
//	    repaint(nx - 64, ny - 32, 128, 64);

	    ox = nx;
	    oy = ny;
	}
    }


    /**
     *
     *
     */
    protected void processMouseMotionEvent(MouseEvent ev) {

	int mods = ev.getModifiers();
	int x = ev.getX();
	int y = ev.getY();
	int i=0, j=0;

	super.processMouseMotionEvent(ev);

	Point p = pixelTransform(x,y,74);
	Point s = worldTransform(p.x,p.y,74);
	Point g = gridTransform(p.x,p.y,74);

	switch (ev.getID()) {

	case MouseEvent.MOUSE_MOVED:

	    MapEdit w = (MapEdit) (getParent().getParent());

	    w.echo("screen: [x=" + x + ",y=" + y + "] " +
		   "world: [x=" + p.x + ",y=" + p.y + "] " +
		   "inv: [x=" + g.x + ",y=" + g.y + "]");

	    //	    repaint();

	    repaint(dx - 32, dy - 48, 128, 64);
	    repaint(x - 32, y - 48, 128, 64);

	    dx = x;
	    dy = y;

	    break;

	case MouseEvent.MOUSE_DRAGGED:
	    break;
	}
    }

    public void update(Observable obs, Object obj) {
	repaint();
    }
}


/**
 *
 *
 */
class MapEditPane extends ScrollPane {

    private MapGrid grid;

    /**
     * Constructor creates the MapEditPane component.
     *
     */
    public MapEditPane(MapManager mgr) {
	System.out.println("Creating EditPane... ");
	add(new MapGrid(mgr, 512, 512, 128, null /* unused */));
    }
}

/**
 *
 *
 */
class MapTilePane extends Panel implements Observer {

    private Map map;
    private MapManager mgr;
    private List tileList;
    private MapTileView tileView;
    private MediaTracker tracker;
    private GridBagLayout layout;

    /** Listens for change of selection in the List. */
    private class ListItemListener implements ItemListener {
	private List l;
	public void itemStateChanged(ItemEvent ev) {
	    if (ev.getStateChange() == ItemEvent.SELECTED) {
		l = (List) ev.getSource();
		mgr.setActiveTile(l.getSelectedIndex());
	    }
	}
    };

    /**
     *
     *
     */
    MapTilePane(MapManager mgr) {

	GridBagConstraints cons = new GridBagConstraints();
	layout = new GridBagLayout();
	setLayout(layout);

	tracker = new MediaTracker(this);
	tileView = new MapTileView(mgr);

	tileList = new List();
	tileList.addItemListener(new ListItemListener());

	/* listen for changes made on the MapManager */

	this.mgr = mgr;
	mgr.addObserver(this);

	update(mgr, this);

	tileList.select(0);
	tileView.setTile(map.tileList[0]);

	cons.weightx = 0.0;
	cons.weighty = 1.0;
	cons.fill = GridBagConstraints.BOTH;

	cons.gridwidth = GridBagConstraints.REMAINDER;
	layout.setConstraints(tileList,cons);
	add(tileList);

	cons.weighty = 0.0;
	layout.setConstraints(tileView,cons);
	add(tileView);
/*

	Label label = new Label("Layer: ");
	TextField text = new TextField("0");

	cons.weightx = 0.0;
	cons.weighty = 0.0;
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = 1;
	layout.setConstraints(label, cons);
	add(label);

	cons.weightx = 1.0;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	layout.setConstraints(text, cons);
	add(text);
*/
    }

    /**
     * 
     *
     */
    private final void updateLayout() {
	layout.layoutContainer(this);
    }


    public void update(MapManager mgr, Object obj) {

	if (map == mgr.map) {
	    return;
	}

	map = mgr.map;

	tileList.removeAll();

	/* Track and add all loaded images. */

	try {

	    /* filter out directory and file extension from name */

	    Pattern re = Pattern.compile(".*/(.*).png");
	    Matcher m;

	    for (int i=0; i<map.numOfTiles; i++) {
		tracker.addImage(map.tileList[i].image, i);
		m = re.matcher(map.tileList[i].name);
		if (m.matches()) {
		    tileList.add(m.group(1));
		} else {
		    tileList.add(map.tileList[i].name);
		}
	    }

	    tracker.waitForAll();

	} catch (InterruptedException e) {
	    e.printStackTrace(System.err);
	} catch (NullPointerException e) {
	    e.printStackTrace(System.err);
	}

	tileList.select(mgr.getActiveTile());
	tileView.setTile(map.tileList[0]);

	updateLayout();
    }

    /**
     *
     *
     */
    public void update(Observable obs, Object obj) {
	if (obs instanceof MapManager) {
	    update((MapManager) obs, obj);
	}
    }
}

/**
 * This Component is responsible for displaying MapTiles. The currently
 * displayed MapTile can be set through the setTile method. 
 */
class MapTileView extends Component implements Observer {

    /** The currently active tile used for drawing. */
    private MapTile tile;

    /** The preferred dimension of this Component. */
    private Dimension dim;

    /**
     * Creates a new instance of the MapTileView class.
     * @param mgr The MapManager associated with this instance.
     */
    public MapTileView(MapManager mgr) {

	tile = null;

	dim = new Dimension(128, 128);
	setSize(dim);

	if (mgr != null) {
	    mgr.addObserver(this);
	}
    }

    /**
     * Sets the tile that is to be displayed.
     * @param tile The tile that is to be displayed.
     */
    public void setTile(MapTile tile) {
	this.tile = tile;
	//dim.width = tile.image.getWidth(this);
	//dim.height = tile.image.getHeight(this);
	//setSize(dim);
	repaint();
    }

    /**
     *
     *
     */
    public void paint(Graphics g) {
	super.paint(g);
	g.drawImage(tile.image, 
		    (dim.width - tile.image.getWidth(this))/2,
		    (dim.height - tile.image.getHeight(this))/2, this);
    }

    /**
     *
     *
     */
    public Dimension getPreferredSize() {
	return dim;
    }

    /**
     * Receive notification when something has happened to the 
     * MapManager. 
     * 
     */
    public void update(MapManager mgr, Object src) {
	setTile(mgr.map.tileList[mgr.tile]);
    }

    public void update(Observable obs, Object obj) {
	if (obs instanceof MapManager) {
	    update((MapManager) obs, obj);
	}
    }
}

class MapMiniPane extends Panel {

    MapMiniPane() {
    }

    public void paint(Graphics g) {
    }
}
