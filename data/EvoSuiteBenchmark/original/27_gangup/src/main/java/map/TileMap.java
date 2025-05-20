/* TileMap.java v0.2 (06/14/2002)
 *
 * Copyright (C) 2002, Joel Andersson <bja@kth.se>
 * 
 * This software is hereby expressly placed in the public domain.
 * Share and enjoy.
 */

package map;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

/**
 * The <code>Map</code> class represents an W by H grid area where each cell
 * in the grid can be referenced by two integers, x and y, corresponding to
 * the horizontal position and vertical position of the cell respectively.
 */
public class TileMap extends VisibilityMap {

    /** The internal representation of the map. */
    protected Node[][] grid;

    /** The size of the tiles in this map. */
    protected int tileSize;

    protected int numOfCols;
    protected int numOfRows;

    /**
     * Creates a new map from the specified fileName with the number
     * of subdivisions given by tileSize.
     *
     * @param fileName The file from which to create the map.
     * @param tileSize The size of the tiles in this map.
     */
    public TileMap(String fileName, int tileSize) 
	throws IOException {
	this(1.0f/15.0f, 1.0f, fileName, tileSize);
    }

    /**
     * Creates a new map from the specified fileName with the number
     * of subdivisions given by tileSize.
     *
     * @param fileName The file from which to create the map.
     * @param tileSize The size of the tiles in this map.
     */
    public TileMap(float min, float max, String fileName, int tileSize)
	throws IOException {
	super(min, max);
	this.tileSize = tileSize;
	open(fileName);
    }

    /**
     * Returns the <code>Node</code> at the given coordinates.
     * 
     * @param x The horizontal coordinate.
     * @param y The vertical coordinate.
     * @return The <code>Node</code> at the given coordinates.
     */
    public Node getNodeAt(int x, int y) {
	return grid[y][x];
    }
    
    public Node getNodeAt(float x, float y, float z) {
	return grid[(int)(y/tileSize)][(int)(x/tileSize)];
    }

    /**
     * Returns the width of the map.
     * @return The width of the map.
     */
    public int getWidth() {
	return grid[0].length;
    }

    /**
     * Returns the height of the map.
     * @return The height of the map.
     */
    public int getHeight() {
	return grid.length;
    }

    /**
     * Restores the internal state of each processed node to
     * its default state.
     */    
    public final void reset() {
	for (int y=0; y<grid.length; y++) {
	    for (int x=0; x<grid[0].length; x++) {
		grid[y][x].setParent(null);
		grid[y][x].setTotal(0.0f);
		grid[y][x].setScore(0.0f);
		grid[y][x].setVisible(false);
		grid[y][x].setOpen(false);
		grid[y][x].setClosed(false);
	    }
	}
    }

    /**
     * Searches for a path connecting the nodes given by their coordinates.
     *
     * @param x0 The horizontal position of the source node. 
     * @param y0 The vertical position of the source node. 
     * @param x1 The horizontal position of the destination node. 
     * @param y1 The vertical position of the destination node. 
     *
     * @return A <code>Path</code> object representing the path between
     *         between the source and destination nodes or <code>null/<code>
     *         if no path was found.
     */
    public Path search(int x0, int y0, int x1, int y1) 
	throws PathNotFoundException {
	return search(getNodeAt(x0, y0), getNodeAt(x1, y1));
    }

    /**
     * Searches for a path connecting the source node with the target
     * node.
     *
     * @param source The node from which to start.
     * @param target The node to which a path is to be found.
     *
     * @return A <code>Path</code> object representing the path between
     *         between the start and destination nodes or <code>null/<code>
     *         if no path was found.
     *
     * @throws PathNotFoundException if no such path could be found.
     * @throws NullPointerException if either start or finish is null.
     */
    public Path search(Node source, Node target) 
	throws PathNotFoundException {
	return aStarSearch2(source, target);
    }

    /**
     * Calculate the Euclidian distance from <code>source</code>
     * to <code>target</code>.
     *
     * @param source The source node.
     * @param target The target node.
     */
    protected float heuristic(Node source, Node target) {

	float dx = target.getX() - source.getX();
	float dy = target.getY() - source.getY(); 

	return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Determines the actual cost of crossing the edge between
     * the given adjectant nodes. If the nodes are not adjectant
     * the returned cost is unspecified.
     *
     * @param source The source node.
     * @param target The target node.
     */
    protected float cost(Node source, Node target) {
	return target.getCost() * heuristic(source, target);
    }

    /**
     * Open and parse the file given by fileName. The file should be in
     * a supported map format.
     *
     * @param fileName The file to read.
     */
    protected void open(String fileName) throws IOException {

	// System.err.println("TileMap.open()");

	BufferedReader in = new BufferedReader(new FileReader(fileName));
	StreamTokenizer st = new StreamTokenizer(in);

	int j=0, i=0;
	int[] tmp = new int[3];

	// throw away tile info
	int numOfTiles = Integer.parseInt(in.readLine());
	for (i=0; i<numOfTiles; i++) {
	    in.readLine();
	}
	// throw away obj info
	int numOfObjs = Integer.parseInt(in.readLine());
	for (i=0; i<numOfObjs; i++) {
	    in.readLine();
	}

	numOfCols = Integer.parseInt(in.readLine());
	numOfRows = Integer.parseInt(in.readLine());

	grid = new Node[numOfRows][numOfCols];
	for (i=0; i<numOfRows; i++) {
	    for (j=0; j<numOfCols; j++) {
		grid[i][j] = new DefaultNode(null, 0, j, i);
		grid[i][j].setCost(1);
	    }
	}

	i = j = 0;
	while (st.nextToken() != st.TT_EOF) {

	    if (j >= tmp.length) {
		grid[tmp[1]][tmp[0]].setCost(tmp[2]);
//		grid[tmp[1]][tmp[0]].setX(tmp[0]);
//		grid[tmp[1]][tmp[0]].setY(tmp[1]);
		j = 0;
	    }

	    tmp[j++] = (int) st.nval; 
	}

	if (j == tmp.length) {
	    grid[tmp[1]][tmp[0]].setCost(tmp[2]);
//	    grid[tmp[1]][tmp[0]].setX((float)tmp[0]);
//	    grid[tmp[1]][tmp[0]].setY((float)tmp[1]);
//	    System.err.println("TileMap.open(): Missing end of line!");
	}

	in.close();

	buildGraph();
    }

    private void buildGraph() {

	int i = 0;
	int j = 0;

	grid[i][j].addChildNode(grid[i][j+1]);
	grid[i][j].addChildNode(grid[i+1][j]);
	grid[i][j].addChildNode(grid[i+1][j+1]);

	i = 0;
	j = numOfCols-1;

	grid[i][j].addChildNode(grid[i][j-1]);
	grid[i][j].addChildNode(grid[i+1][j]);
	grid[i][j].addChildNode(grid[i+1][j-1]);

	i = numOfRows-1;
	j = 0;

	grid[i][j].addChildNode(grid[i][j+1]);
	grid[i][j].addChildNode(grid[i-1][j]);
	grid[i][j].addChildNode(grid[i-1][j+1]);

	i = numOfRows-1;
	j = numOfCols-1;

	grid[i][j].addChildNode(grid[i][j-1]);
	grid[i][j].addChildNode(grid[i-1][j]);
	grid[i][j].addChildNode(grid[i-1][j-1]);

	for (i=1; i<numOfRows-1; i++) {
	    grid[i][0].addChildNode(grid[i][1]);
	    grid[i][0].addChildNode(grid[i-1][1]);
	    grid[i][0].addChildNode(grid[i+1][1]);

	    grid[i][numOfCols-1].addChildNode(grid[i][numOfCols-2]);
	    grid[i][numOfCols-1].addChildNode(grid[i-1][numOfCols-2]);
	    grid[i][numOfCols-1].addChildNode(grid[i+1][numOfCols-2]);
	}

	
	for (j=1; j<numOfCols-1; j++) {
	    grid[0][j].addChildNode(grid[1][j]);
	    grid[0][j].addChildNode(grid[1][j+1]);
	    grid[0][j].addChildNode(grid[1][j-1]);

	    grid[numOfRows-1][j].addChildNode(grid[numOfRows-2][j]);
	    grid[numOfRows-1][j].addChildNode(grid[numOfRows-2][j+1]);
	    grid[numOfRows-1][j].addChildNode(grid[numOfRows-2][j-1]);
	}

	for (i=1; i<numOfRows-1; i++) {
	    for (j=1; j<numOfCols-1; j++) {
		grid[i][j].addChildNode(grid[i-1][j]);
		grid[i][j].addChildNode(grid[i+1][j]);
		grid[i][j].addChildNode(grid[i-1][j-1]);
		grid[i][j].addChildNode(grid[i-1][j+1]);
		grid[i][j].addChildNode(grid[i][j-1]);
		grid[i][j].addChildNode(grid[i][j+1]);
		grid[i][j].addChildNode(grid[i-1][j-1]);
		grid[i][j].addChildNode(grid[i+1][j+1]);
	    }
	}
    }

}
