/* Map.java v0.1 (06/16/2002)
 *
 * Copyright (C) 2002, Joel Andersson <bja@kth.se>
 * 
 * This software is hereby expressly placed in the public domain.
 * Share and enjoy.
 */

package map;

import java.io.IOException;
import java.awt.Graphics;

/**
 * The <code>Map</code> class represents an W by H grid area where each cell
 * in the grid can be referenced by two integers, x and y, corresponding to
 * the horizontal position and vertical position of the cell respectively.
 */
public interface Map {

    /** */
    // public abstract class Node implements MapNodeInterface {};

    /**
     * Returns the <code>Node</code> at the given point.
     * 
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @return The <code>Node</code> at the given point.
     */
    public Node getNodeAt(float x, float y, float z);
    
    /**
     * Returns the width of the map.
     * @return The width of the map.
     */
    public int getWidth();

    /**
     * Returns the height of the map.
     * @return The height of the map.
     */
    public int getHeight();

    /**
     * Draws the map on the specified <code>Graphics</code> object
     * starting at grid location x,y and ending at x+w,y+h. The map
     * is drawn at the top left corner of the <code>Graphics</code> 
     * object.
     *
     * @param g The <code>Graphics</code> object to draw on.
     * @param x The horizontal grid location to start at.
     * @param y The vertical grid location to start at.
     * @param w The number of horizontal grid cells to draw.
     * @param h The number of vertical grid cells to draw.
     */
    // public void draw(Graphics g, int x, int y, int w, int h);

    /**
     * Resets all previously altered nodes to their default state. The
     * method does a depth-first traversal of the nodes. Warning! This 
     * method has the potential of running out of stack space!
     *
     * @param n The node from which to start.
     * @throws NullPointerException if <code>n</code> is null.
     */
    public void reset();

    /**
     * Search for a path connecting the given nodes. The search is 
     * implemented using A* with a straight-line heuristic. 
     *
     * @param source The node from which to start.
     * @param destination The node to which a path is to be found.
     *
     * @return A <code>Path</code> object representing the path between
     *         between the source and destination nodes or <code>null/<code>
     *         if no path was found.
     *
     * @throws NullPointerException if either start or finish is null.
     */
    public Path search(Node source, Node destination) 
	throws PathNotFoundException; 
}
