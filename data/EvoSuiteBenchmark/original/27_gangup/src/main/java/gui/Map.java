/** $Id: Map.java,v 1.7 2004/04/22 11:26:42 emill Exp $ 
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.7 $
 *
 */

package gui;

import static java.lang.Math.max;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

import java.util.Comparator;
import java.util.Arrays;
import java.util.Hashtable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StreamTokenizer;

import java.io.IOException;

import java.awt.*;

import state.*;

/**
 * The MapEdit implementation of Map. Some things may be altered to
 * work better with the WorldView in the GUI.
 */
public class Map {

    private final static int WORLD_OFFSET_X = 672;
    private final static int WORLD_OFFSET_Y = 32;
    private final static int GRID_OFFSET_X = 14;
    private final static int GRID_OFFSET_Y = -2;
    static final int MAX_NUM_CELLS = 1000;
    public final static int N_LAYERS = 4;
    public final static int PLAYER_LAYER = 2;

    MapCell[][] cell;
    MapTile[] tileList;
    int numOfTiles;
    int[] numOfCells;

    Hashtable<Player,MapCell> playercells = new Hashtable<Player,MapCell>();

    /**
     * Each tile represents one different kind of tile that can be placed
     * on the map.
     */
    public static class MapTile {

	public String name;
	public Image image;

	private static final Toolkit tk;
	static { tk = Toolkit.getDefaultToolkit(); }

	public MapTile (String fileName) throws IOException {
	    name = fileName;
	    image = tk.getImage(fileName);
	}

    }

    /**
     * Each cell is the smallest part of the map.
     */
    public static class MapCell {
	float x,y;
	int tile,layer;
	float depthvalue;
	public MapCell(int x, int y, int tile, int layer) {
	    this((float)x,(float)y,tile,layer);
	}
	public MapCell(int x, int y, int tile) {
	    this(x,y,tile,0);
	}
	public MapCell(float x, float y, int tile, int layer) {
	    this.x = x;
	    this.y = y;
	    this.tile = tile;
	    this.layer = layer;
	}
	public String toString() {
	    return String.format("MapCell[x=%f,y=%f,tile=%d,layer=%d]",
				 x, y, tile, layer);
	}
    }

    public static class MapPlayerCell extends MapCell {
	Player p;
	public MapPlayerCell(Player p1) {
	    super(p1.getX(),p1.getY(),8,PLAYER_LAYER);
	    p = p1;
	}
    }

    /** Compares two MapCells and determine their order. */
    private class MapCellComparator implements Comparator<MapCell> {

	// MapCellComparator() { /* ... */ }

	public int compare(MapCell src, MapCell dst) {

	    return ((Float) depthvalue(src)).compareTo(depthvalue(dst)) ;

	}
							
	    /*if (src.y > src.x) {
		if (src.y > dst.y)
		    return 1;
		if (src.y < dst.y)
		    return -1;
		return 0;
	    }

	    if (src.y < src.x) {
		if (src.x > dst.x)
		    return 1;
		if (src.x < dst.x)
		    return -1;
		return 0;
	    }
	    
	    if (src.x+src.y > dst.x+dst.y)
		return 1;
	    if (src.x+src.y < dst.x+dst.y)
		return -1;
		return 0;
	}*/

	private float depthvalue(MapCell c) {
	    return (1000*(float)c.x + 1001*(float)c.y)/1000*32;
	}
	
	public boolean equals(Object obj) {
	    return obj == this;
	}

    };

    /**
     * Creates a new instance of the Map class.
     * @param fileName the name of the map to open.
     */
    public Map(String fileName) throws IOException {
	open(fileName);
    }

    /**
     * Open the specified map fileName.
     * @param fileName the name of the file to open.
     */
    public Map open(String fileName) throws IOException {

	//System.err.println("Map.open()");

	BufferedReader in = new BufferedReader(new FileReader(fileName));
	StreamTokenizer st = new StreamTokenizer(in);

	int j=0, i=0;
	int[] tmp = new int[4];

	//numOfCells = Integer.parseInt(in.readLine());
	numOfCells = new int[N_LAYERS];
	int readNumOfTiles = Integer.parseInt(in.readLine());
	numOfTiles = Integer.parseInt(in.readLine());

	cell = new MapCell[N_LAYERS][];
	for (i=0; i < N_LAYERS; i++) {
	    cell[i] = new MapCell[MAX_NUM_CELLS];
	}
	tileList = new MapTile[numOfTiles];

	for (i=0; i<numOfTiles; i++) {
	    tileList[i] = new MapTile(in.readLine());
	}

	j = 0;
	int c[] = new int[N_LAYERS];
	while (st.nextToken() != st.TT_EOF) {

	    if (j > 3) {
		numOfCells[tmp[3]]++;
		cell[ tmp[3] ][ c[tmp[3]]++ ] =
		    new MapCell(tmp[0],tmp[1],tmp[2],tmp[3]);
		j = 0;
	    }

	    tmp[j++] = (int) st.nval; 
	}

	if (j == 4) {
	    numOfCells[tmp[3]]++;
	    cell[ tmp[3] ][ c[tmp[3]]++ ] =
		    new MapCell(tmp[0],tmp[1],tmp[2],tmp[3]);
	    // System.err.println("Map.open(): Missing end of line!");
	}

	//if ( != numOfCells) {
	//    System.err.println("Map.open(): Unable to open file!");
	//}

	in.close();

	try {
	    MediaTracker tracker = new MediaTracker(new Frame());
	    for (MapTile t : tileList) {
		tracker.addImage(t.image, 0);
	    }
	    //System.err.println("Map.open(): waiting for images!");
	    tracker.waitForAll();
	} catch (InterruptedException e) {
	    e.printStackTrace(System.err);
	}

	return this;
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
	for (int i=numOfCells[PLAYER_LAYER]-1; i >= 0; --i) {
	    if (round(cell[PLAYER_LAYER][i].x) == x &&
		round(cell[PLAYER_LAYER][i].y) == y) {
		return cell[PLAYER_LAYER][i];
	    }
	}
	return null;
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
    public static Point worldTransform(float x, float y) {
	//return new Point( (int) ((x - z) / 2 - z - y), 
	//		  (int) ((x + 2 * (y - z) + z) / 4));
	x += GRID_OFFSET_X;
	y += GRID_OFFSET_Y;
	return new Point( (int) (16*(x-y)) ,
			  (int) (8*(y+x))  ) ;
    }

    /**
     * Converts the input coordinats in world space to their respective
     * coordinates in defined grid.
     *
     * @param x
     * @param y
     * @param z
     */
    public static Point gridTransform(int x, int y) {

	/*int j = x - WORLD_OFFSET_X + GRID_OFFSET_X;
	int i = y + WORLD_OFFSET_Y + GRID_OFFSET_Y;

	j = (int) (j < 0 ? j / 32 - 0.5 : j / 32 + 1.5);
	i = (int) (i < 0 ? i / 16 - 0.5 : i / 16 + 1.5);

	return new Point(j, i);*/

	return new Point( round((float)x/32+(float)y/16) - GRID_OFFSET_X,
			  round((float)y/16-(float)x/32) - GRID_OFFSET_Y);
    }

    public MapCell addPlayerCell(Player p) {
	MapCell c = new MapPlayerCell(p);
	cell[PLAYER_LAYER][numOfCells[PLAYER_LAYER]++] = c;
	sortCells(PLAYER_LAYER);
	playercells.put(p,c);
	return c;
    }


    public void updatePlayerCell(Player p) {
	if (p.isDead()) {
	    MapCell m = playercells.get(p);
	    m.tile = 39;
	}
	else {
	    MapCell m = playercells.get(p);
	    m.x = p.getX();
	    m.y = p.getY();
	}
    }

    public void sortCells(int layer) {
	//System.err.print("Sorting... ");
	Arrays.sort(cell[layer], 0, numOfCells[layer],
		    new MapCellComparator());
	//System.err.println("done!");
    }

    /** Paints a layer of this map on a Graphics object. */
    public void draw(Graphics2D g, int layer) {
	int i,z,w,h;
	float x,y;
	MapTile t;
	Point p;

	i = 0;

	try {
	    for (i=0; i<numOfCells[layer]; i++) {

		x = cell[layer][i].x;
		y = cell[layer][i].y;
		z = cell[layer][i].tile;

		/* check whether this cell should be drawn */
		if (z != -1) { 
		    
		    t = tileList[z];
		    w = t.image.getWidth(null);
		    h = t.image.getHeight(null);
		
		    //p = worldTransform(x*32 + WORLD_OFFSET_X - 8 - w/2,
		    //	       y*16 - WORLD_OFFSET_Y - 4 + w/4, 74);
		    p = worldTransform(x,y);
		    g.drawImage(t.image, p.x-w/2, p.y-h,null);
		}
	    }
	} catch (Exception e) {
	    System.err.printf("MapGrid.paint(): i=%d l=%d\n", i, cell.length);
	    e.printStackTrace(System.err);
	}
    }

    public String toString() {
	return "Map[]";
    }
}

