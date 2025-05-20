/* Node.java v0.2 (06/16/2002)
 *
 * Copyright (C) 2002, Joel Andersson <bja@kth.se>
 * 
 * This software is hereby expressly placed in the public domain.
 * Share and enjoy.
 */

package map;

/**
 * The <code>Node</code> class represents an internal map node.
 * Each cell in the map consists of a <code>Node</code>.
 */
public interface Node extends Comparable {
	
    public static final int NORTH      = 0;
    public static final int NORTH_EAST = 1;
    public static final int EAST       = 2;
    public static final int SOUTH_EAST = 3;
    public static final int SOUTH      = 4;
    public static final int SOUTH_WEST = 5;
    public static final int WEST       = 6;
    public static final int NORTH_WEST = 7;

    /** This is the default state of the node. */
    public static final int UNSET  = 0;

    /** This state is occurs when the node is being processed. */
    public static final int OPEN   = 1;

    /** This state is reached when the node has been processed. */
    public static final int CLOSED = 2;

    /**
     * Adds the specified node as a child to this node.
     * @param n The node to add.
     */
    public void addChildNode(Node n);

    /**
     * Sets the child of this node given by index to the 
     * specified Node.
     *
     * @param index The index of the child to change.
     * @param n The node to set in the place of index.
     */
    public void setChildNode(int index, Node n);

    /**
     * Returns the child at the specified location <code>index</code>.
     * @return The child at the specified location <code>index</code>.
     */
    public Node getChildNode(int index);

    /**
     * Returns the children of this <code>Node</code>. The order
     * of the child nodes returned is unspecified.
     *
     * @return The children of this <code>Node</code>.
     */
    public Node[] getAllChildren();

    /**
     * Sets the parent of this node to the specified node.
     * @param n The new parent node.
     */
    public void setParent(Node n);

    /**
     * Returns this nodes parent.
     * @return this nodes parent.
     */
    public Node getParent();

    /**
     * Sets the positional coordinates of this node to the
     * specified values.
     *
     * @param x The horizontal component.
     * @param y The vertical component.
     */
    public void setLocation(int x, int y);
    public void setLocation(float x, float y, float z);

    /**
     * Returns the horizontal component of this node.
     * @return the horizontal component of this node.
     */
    public float getX();

    /**
     * Returns the vertical component of this node.
     * @return the vettical component of this node.
     */
    public float getY();

    /**
     * Returns the z coordinate of this node.
     * @return the z coordinate of this node.
     */
    public float getZ();

    public void setX(float x);
    public void setY(float y);
    public void setZ(float z);

    /**
     * Sets the cost associated with entering this node.
     * @param cost the cost associated with entering this node.
     */
    public void setCost(float cost);

    /**
     * Returns the cost associated with entering this node.
     * @return The cost associated with entering this node.
     */
    public float getCost();

    /**
     * Sets the total cost associated with this node. The
     * total cost is the cost of travelling from the start
     * node to this node.
     *
     * @param total The total cost to be associated with
     *              this node.
     */
    public void setTotal(float total);

    /**
     * Returns the total cost associated with this node.
     * @return The total cost associated with this node.
     */
    public float getTotal();

    /**
     * Sets the score associated with this node. The score 
     * determines how good this node is in relation to other
     * nodes and is used for ordering them amongst each other.
     *
     * @param score The score to be associated with this node.
     */
    public void setScore(float score);

    /**
     * Returns the score associated with this node.
     * @return the score associated with this node.
     */
    public float getScore();

    /**
     * Sets the data associated with this node. The handles
     * the way the node is displayed.
     *
     * @param data The data to be associated with this node.
     */
    public void setData(long data);

    /**
     * Retrieves the data associated with this node.
     * @return the data associated with this node.
     */
    public long getData();

    /**
     * Specifies whether this node has already been processed 
     * and no longer is considered for further processing.
     *
     * @param b A boolean value that specifies whether
     *          this node has been processed.
     */
    public void setClosed(boolean b);

    /**
     * Returns true if this node has been processed.
     * @return True if this node has been processed.
     */
    public boolean isClosed();

    /**
     * Specifies whether this node is currently being
     * considered for further processing.
     *
     * @param b A boolean value that specifies whether
     *          this node is currently open.
     */
    public void setOpen(boolean b);

    /**
     * Returns true if this node is currently being 
     * considered for further processing.
     *
     * @return True if this node is open.
     */
    public boolean isOpen();

    /**
     * Specifies whether this node is solid (ie. the
     * cost of entering the node is infinite.)
     *
     * @param b A boolean value that specifies whether
     *          this node is solid.
     */
    public void setSolid(boolean b);

    /**
     * Returns whether this node is considered solid (ie. the
     * cost of entering the node is infinite.)
     *
     * @return True if this node is solid.
     */
    public boolean isSolid();

    /**
     * Specifies whether this node is currently visible.
     *
     * @param b A boolean value that specifies whether
     *          this node is currently visible.
     */
    public void setVisible(boolean b);

    /**
     * Returns whether this node is considered visible.
     *
     * @return True if this node is visible.
     */
    public boolean isVisible();

    /**
     * Sets the degree of visibility of this node. 
     * @param value the degree of visibility.
     */
    public void setVisibility(float value);

    /**
     * Returns the current degree of visibility of this node.
     * @return the current degree of visibility of this node.
     */
    public float getVisibility();

    /**
     * Compares this Node with the specified Object for order.
     * <b>Note</b>: This method imposes ordering which is 
     * inconsistant with equals.
     *
     * @param obj The object which is to be compared.
     * @return An integer value of -1, 0, or 1, as this node
     *         is less than, equal to, or greater than obj.
     *
     * @throws ClassCastException if obj is not of type Node.
     */
    public int compareTo(Object obj);

    /**
     * Compares this node with the specified object for equality.
     * Two nodes are considered equal if they have the same map
     * coordinates.
     *
     * @param obj The node which is to be compared.
     * @return True if obj is of type node and has the same map
     *         coordinate as this node. Otherwise return false.
     */
    public boolean equals(Object obj);
}





