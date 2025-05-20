/* DirtyNode.java v0.1 (06/16/2002)
 *
 * Copyright (C) 2002, Joel Andersson <bja@kth.se>
 * 
 * This software is hereby expressly placed in the public domain.
 * Share and enjoy.
 */

package map;

public final class DirtyNode implements Node {
	
    /** The parent of this node. */
    public DirtyNode parent;

    /** The child nodes of this node. */
    public DirtyNode[] children;

    /** The cost associated with this node. */
    public float cost;

    /** The total cost associate with this node. */
    public float total;

    /** The score associated with this node. */
    public float score;

    /** The current state of this node. */
    public int state;

    /** The bitmask associated with this node. */
    public long bits;

    /** The horizontal component of this node. */
    public float x;

    /** The vertical component of this node. */
    public float y;

    /** The z coordinate of this node. */
    public float z;

    /** Specifies whether this node is visible. */
    public float visibility;

    /** The number of children of this node. */
    private int numOfChildren;

    /**
     * Creates a new DirtyNode with the specified tile.
     * @param tile The tile to be associated with the node.
     */
    public DirtyNode(long bits) {
	this(bits, 0, 0);
    }

    /**
     * Creates a new DirtyNode with the specified attributes.
     * @param tile The tile to be associated with the node.
     * @param bits The bits to be associated with the node.
     * @param x The horizontal component of this node.
     * @param y The vertical component of this node.
     */
    public DirtyNode(long bits, int x, int y) {
	this.bits = bits;
	this.x = x;
	this.y = y;
	this.numOfChildren = 0;
	this.children = new DirtyNode[8];
    }

    /**
     * Adds the specified node as a child to this node.
     * @param n The node to add.
     */
    public void addChildNode(Node n) {
	children[numOfChildren++] = (DirtyNode) n;
    }

    /**
     * Sets the child of this node given by index to the 
     * specified Node.
     *
     * @param index The index of the child to change.
     * @param n The node to set in the place of index.
     */
    public void setChildNode(int index, Node n) {
	children[index] = (DirtyNode) n;
    }

    /**
     * Returns the child at the specified location <code>index</code>.
     * @return The child at the specified location <code>index</code>.
     */
    public Node getChildNode(int index) {
	return children[index];
    }

    /**
     * Returns the children of this <code>Node</code>. The order
     * of the child nodes returned is unspecified.
     *
     * @return The children of this <code>Node</code>.
     */
    public Node[] getAllChildren() {
	return children;
    }

    /**
     * Sets the parent of this node to the specified node.
     * @param parent The new parent node.
     */
    public void setParent(Node parent) {
	this.parent = (DirtyNode) parent;
    }

    /**
     * Returns this nodes parent.
     * @return this nodes parent.
     */
    public Node getParent() {
	return parent;
    }

    /**
     * Sets the positional coordinates of this node to the
     * specified values.
     *
     * @param x The horizontal component.
     * @param y The vertical component.
     */
    public void setLocation(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public void setLocation(float x, float y, float z) {
	this.x = x;
	this.y = y;
	this.z = z;
    }

    /**
     * Returns the horizontal position of this node.
     * @return the horizontal position of this node.
     */
    public float getX() {
	return x;
    }

    /**
     * Returns the vertical position of this node.
     * @return the vettical position of this node.
     */
    public float getY() {
	return y;
    }

    /**
     * Returns the z coordinate of this node.
     * @return the z coordinate of this node.
     */
    public float getZ() {
	return z;
    }

    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
    public void setZ(float z) {this.z = z;}

    /**
     * Sets the cost associated with entering this node.
     * @param cost the cost associated with entering this node.
     */
    public void setCost(float cost) {
	this.cost = cost;
    }

    /**
     * Returns the cost associated with entering this node.
     * @return The cost associated with entering this node.
     */
    public float getCost() {
	return cost;
    }

    /**
     * Sets the total cost associated with this node. The
     * total cost is the cost of travelling from the start
     * node to this node.
     *
     * @param total The total cost to be associated with
     *              this node.
     */
    public void setTotal(float total) {
	this.total = total;
    }

    /**
     * Returns the total cost associated with this node.
     * @return The total cost associated with this node.
     */
    public float getTotal() {
	return total;
    }

    /**
     * Sets the score associated with this node. The score 
     * determines how good this node is in relation to other
     * nodes and is used for ordering them amongst each other.
     *
     * @param score The score to be associated with this node.
     */
    public void setScore(float score) {
	this.score = score;
    }

    /**
     * Returns the score associated with this node.
     * @return the score associated with this node.
     */
    public float getScore() {
	return score;
    }

    /**
     * Specifies whether this node has already been processed 
     * and no longer is considered for further processing.
     *
     * @param b A boolean value that specifies whether
     *          this node has been processed.
     */
    public void setClosed(boolean b) {
	state = b ? state | CLOSED : state & ~CLOSED;
    }

    /**
     * Returns true if this node has been processed.
     * @return True if this node has been processed.
     */
    public boolean isClosed() {
	return (state & CLOSED) > 0;
    }

    /**
     * Specifies whether this node is currently being
     * considered for further processing.
     *
     * @param b A boolean value that specifies whether
     *          this node is currently open.
     */
    public void setOpen(boolean b) {
	state = b ? state | OPEN : state & ~OPEN;
    }

    /**
     * Returns true if this node is currently being 
     * considered for further processing.
     *
     * @return True if this node is open.
     */
    public boolean isOpen() {
	return (state & OPEN) > 0;
    }

    /**
     * Sets the data associated with this node. The handles
     * the way the node is displayed.
     *
     * @param data The data to be associated with this node.
     */
    public void setData(long data) {
	this.bits = data;
    }

    /**
     * Retrieves the data associated with this node.
     * @return the data associated with this node.
     */
    public long getData() {
	return bits;
    }

    /**
     * Specifies whether this node is solid (ie. the
     * cost of entering the node is infinite.)
     *
     * @param b A boolean value that specifies whether
     *          this node is solid.
     */
    public void setSolid(boolean b) {
	throw new RuntimeException("Method not implemented!");
    }


    /**
     * Returns whether this node is considered solid (ie. the
     * cost of entering the node is infinite.)
     *
     * @return True if this node is solid.
     */
    public boolean isSolid() {
	throw new RuntimeException("Method not implemented!");
    }

    /**
     * Specifies whether this node is currently visible.
     *
     * @param b A boolean value that specifies whether
     *          this node is currently visible.
     */
    public void setVisible(boolean b) {
	this.visibility = b ? 1.0f : 0.0f;
    }

    /**
     * Returns whether this node is considered visible.
     * @return True if this node is visible.
     */
    public boolean isVisible() {
	return visibility > 0.0f;
    }

    /**
     * Sets the degree of visibility of this node. 
     * @param value the degree of visibility.
     */
    public void setVisibility(float value) {
	this.visibility = value;
    }

    /**
     * Returns the current degree of visibility of this node.
     * @return the current degree of visibility of this node.
     */
    public float getVisibility() {
	return visibility;
    }

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
    public int compareTo(Object obj) {
	return compareTo((DirtyNode) obj);
    }

    public int compareTo(DirtyNode n) {
	return score < n.score ? -1 : (score > n.score ? 1 : 0);
    }

    /**
     * Compares this node with the specified object for equality.
     * Two nodes are considered equal if they have the same map
     * coordinates.
     *
     * @param obj The node which is to be compared.
     * @return True if obj is of type node and has the same map
     *         coordinate as this node. Otherwise return false.
     */
    public boolean equals(Object obj) {
	return equals((DirtyNode) obj);
    }

    public boolean equals(DirtyNode n) {
	return x == n.x && y == n.y;
    }

    /**
     * Returns a string representation of this <code>Node</code>.
     * @return a string representation of this <code>Node</code>.
     */
    public String toString() {
	return "DirtyNode[x=" + x + ",y=" + y + ",score=" + score + "]";
    }
}




