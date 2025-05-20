/* AbstractMap.java v0.1 (06/17/2002)
 *
 * Copyright (C) 2002, Joel Andersson <bja@kth.se>
 * 
 * This software is hereby expressly placed in the public domain.
 * Share and enjoy.
 */

package map;

import java.util.Vector;
import java.util.PriorityQueue;
//import bja.util.PriorityQueue;

public abstract class AbstractMap implements Map {

    /** The square root of 2.0f. */
    protected static final float MATH_SQRT_2 = 1.41421356f;

    /** The minimum cost associated with accessing a node in this map. */
    protected static float minCost;
    
    /** The maximum cost associated with accessing a node in this map. */
    protected static float maxCost;

    /**
     * Creates a new instance of this class.
     */
    protected AbstractMap() {
	minCost = 0.1f;
	maxCost = 1.0f;
    }

    /**
     * Creates a new instance of this class with the specified
     * attributes.
     *
     * @param min the minimum cost of accessing a node.
     * @param max the maximum cost of accessing a node.
     */
    protected AbstractMap(float min, float max) {
	minCost = min;
	maxCost = max;
    }

    /**
     * Search for the shortest path connecting the source node with 
     * the target node. This algorithm is guaranteed to find the
     * optimal shortest path, if it exist, provided that the heuristic
     * function is admissible.
     *
     * @param source The node from which to start.
     * @param target The node to which a path is to be found.
     *
     * @return A <code>Path</code> object representing the path between
     *         between the source and destination nodes or <code>null/<code>
     *         if no path was found.
     *
     * @throws NullPointerException if either source or target is null.
     */
    public final Path aStarSearch(Node source, Node target) 
	throws PathNotFoundException {

	PriorityQueue open = new PriorityQueue();

	reset();

	source.setTotal(0.0f);
	source.setScore(minCost * heuristic(source, target));

	open.add(source);

	while (open.size() != 0) {

	    Node current = (Node) open.poll();
	    current.setOpen(false);

	    if (current.equals(target)) {
		return new DefaultPath(current);
	    }

	    Node[] child =  current.getAllChildren();

	    for (int i=0; i<child.length; i++) {

		// ignore the edges on bad maps and nodes that
		// cannot be entered.

		if (child[i] == null || child[i].getCost() >= maxCost) {
		    continue; 
		}

		// calculate the accumulated total cost of moving
		// from the source to this child.

		float total = current.getTotal() + cost(current, child[i]);

		// skip nodes that are currently being processed and
		// which have a lower total cost from the source.

		if (child[i].isOpen() && child[i].getTotal() <= total) {
		    continue;
		}
		
		// skip nodes that have already been processed and
		// which have a lower total cost from the source.

		if (child[i].isClosed() && child[i].getTotal() <= total) {
		    continue;
		}

		// a new shorter path to this child was found. update
		// the node and add it to the list of open nodes if
		// it isn't already open.

		float score = total + minCost * heuristic(child[i], target);

		child[i].setParent(current);
		child[i].setTotal(total);
		child[i].setScore(score);

		if (child[i].isClosed()) {
		    child[i].setClosed(false);
		}

		if (!child[i].isOpen()) {
		    open.add(child[i]);
		    child[i].setOpen(true);
		}
	    }

	    current.setClosed(true);
	}

	throw new PathNotFoundException("s=" + source + ", f=" +  target);
    }

    /**
     * Search for the shortest path connecting the source node with 
     * the target node. This algorithm is guaranteed to find the
     * optimal shortest path, if it exist, provided that the heuristic
     * function is admissible.
     *
     * This is a slightly faster implementation of the A* algorithm than
     * the above using a sorted array to store the open nodes instead of
     * a PriorityQueue.
     *
     * @param source The node from which to start.
     * @param target The node to which a path is to be found.
     *
     * @return A <code>Path</code> object representing the path between
     *         between the source and destination nodes or <code>null/<code>
     *         if no path was found.
     *
     * @throws NullPointerException if either source or target is null.
     */
    public final Path aStarSearch2(Node source, Node target) 
	throws PathNotFoundException {

	int n = 0;

	Node[] open = new Node[16];

	reset();

	source.setTotal(0.0f);
	source.setScore(minCost * heuristic(source, target));

	open[n++] = source;

	while (n > 0) {

	    Node current = open[--n]; 
	    current.setOpen(false);

	    if (current.equals(target)) {
		return new DefaultPath(current);
	    }

	    Node[] child =  current.getAllChildren();

	    for (int i=0; i<child.length; i++) {

		// ignore the edges on bad maps and nodes that
		// cannot be entered.

		if (child[i] == null || child[i].getCost() >= maxCost) {
		    continue; 
		}

		// calculate the accumulated total cost of moving
		// from source to this child.

		float total = current.getTotal() + cost(current, child[i]);

		// skip nodes that are currently being processed and
		// which have a lower total cost from source.

		if (child[i].isOpen() && child[i].getTotal() <= total) {
		    continue;
		}
		
		// skip nodes that have already been processed and
		// which have a lower total cost from source.

		if (child[i].isClosed() && child[i].getTotal() <= total) {
		    continue;
		}

		// a new shorter path to this child was found. update
		// the node and add it to the list of open nodes if
		// it isn't already open.

		float score = total + minCost * heuristic(child[i], target);

		child[i].setParent(current);
		child[i].setTotal(total);
		child[i].setScore(score);

		if (child[i].isClosed()) {
		    child[i].setClosed(false);
		}

		if (!child[i].isOpen()) {

		    int l = 0;
		    int r = n - 1;
		    int m = 0;
		    int t = 0;

		    // search for place to insert new node.

		    while (l <= r) {
			m = (l + r) / 2;
			t = -child[i].compareTo(open[m]);
			if (t < 0) {
			    r = m - 1;
			} else if (t > 0) {
			    l = m + 1;
			} else {
			    break;
			}
		    }

		    m = m < 0 ? 0 : m;

		    // if the buffer is full allocate space for some 
		    // more nodes. make it twice as large as it was.

		    if (n > open.length - 2) {

			Node[] temp = open;
			open = new Node[temp.length << 1];

			System.arraycopy(temp, 0, open, 0, temp.length);

		    } 

		    System.arraycopy(open, m, open, m + 1, n - m);

		    child[i].setOpen(true);
		    open[m] = child[i];

		    n = n + 1;
		}
	    }

	    current.setClosed(true);
	}

	throw new PathNotFoundException("s=" + source + ", f=" +  target);
    }

    /**
     * Search for the shortest path connecting the source node with 
     * the target node. This algorithm is guaranteed to find the
     * optimal shortest path, if it exist, provided that the heuristic
     * function is admissible.
     *
     * This is a slightly faster implementation of the A* algorithm than
     * the above using a sorted array to store the open nodes instead of
     * a PriorityQueue and using DirtyNodes instead of the DefaultNodes.
     *
     * @param source The node from which to start.
     * @param target The node to which a path is to be found.
     *
     * @return A <code>Path</code> object representing the path between
     *         between the source and destination nodes or <code>null/<code>
     *         if no path was found.
     *
     * @throws NullPointerException if either source or target is null.
     */
    public Path aStarSearch3(DirtyNode source, DirtyNode target)
	throws PathNotFoundException {

	DirtyNode[] open = new DirtyNode[32];

	int n = 0;

	reset();

	//float dx = Math.abs(target.x - source.x);
	//float dy = Math.abs(target.y - source.y); 
	//float h  = Math.min(dx, dy) + MATH_SQRT_2 * Math.abs(dx - dy);

	float dx = target.x - source.x;
	float dy = target.y - source.y; 
	float h  = (float) Math.sqrt(dx*dx + dy*dy);

	source.total = 0.0f;
	source.score = minCost * h;
	//source.score = minCost * heuristic(source, target);

	open[n++] = source;

	while (n > 0) {

	    DirtyNode current = open[--n]; 
	    current.state ^= Node.OPEN;

	    if (current.equals(target)) {
		return new DefaultPath(current);
	    }

	    DirtyNode[] child = current.children;

	    for (int i=0; i<child.length; i++) {

		// ignore the edges on bad maps and nodes that
		// cannot be entered.

		if (child[i] == null || child[i].cost >= maxCost) {
		    continue; 
		}

		// calculate the accumulated total cost of moving
		// from source to this child.

		float weight = (i % 2 == 0) ? 1 : MATH_SQRT_2;
		float total  = current.total + weight * child[i].cost;
		//float total = current.total + cost(current, child[i]);

		// skip nodes that are currently being processed and
		// which have a lower total cost from source.

		if ((child[i].state & Node.OPEN) > 0
		        && child[i].total <= total) {
		    continue;
		}
		
		// skip nodes that have already been processed and
		// which have a lower total cost from source.

		if ((child[i].state & Node.CLOSED) > 0 
		        && child[i].total <= total) {
		    continue;
		}

		// a new shorter path to this child was found. update
		// the node and add it to the list of open nodes if
		// it isn't already open.

		//dx = Math.abs(child[i].x - source.x);
		//dy = Math.abs(child[i].y - source.y); 
		//h  = Math.min(dx, dy) + MATH_SQRT_2 * Math.abs(dx - dy);

		dx = child[i].x - target.x;
		dy = child[i].y - target.y; 
		h  = (float) Math.sqrt(dx*dx + dy*dy);

		float score = total + minCost * h;
		//float score = total + minCost * heuristic(current, child[i]);

		child[i].parent = current;
		child[i].total = total;
		child[i].score = score;

		if ((child[i].state & Node.CLOSED) > 0) {
		    child[i].state ^= Node.CLOSED;
		}

		if ((child[i].state & Node.OPEN) == 0 ) {

		    int l = 0;
		    int r = n - 1;
		    int m = 0;
		    int t = 0;

		    // search the open array for a place to insert new 
		    // node. the array is ordered in reverse natural order.

		    while (l <= r) {

			m = (l + r) / 2;

			t = child[i].score < open[m].score ?  1 :
			   (child[i].score > open[m].score ? -1 : 0);

			if (t < 0) {
			    r = m - 1;
			} else if (t > 0) {
			    l = m + 1;
			} else {
			    break;
			}
		    }

		    m = m < 0 ? 0 : m;

		    // if the buffer is full allocate space for some 
		    // more nodes. make it twice as large as it was.

		    if (n > open.length - 2) {

			DirtyNode[] temp = open;
			open = new DirtyNode[temp.length << 1];

			System.arraycopy(temp, 0, open, 0, temp.length);

		    } 

		    System.arraycopy(open, m, open, m + 1, n - m);

		    child[i].state ^= Node.OPEN;
		    open[m] = child[i];

		    n = n + 1;
		}
	    }

	    current.state ^= Node.CLOSED;
	}

	throw new PathNotFoundException("s=" + source + ", t=" +  target);
    }

    /**
     * Search for the path connecting the source node with the target node.
     * This algorithm does not take into account the cost of accessing each 
     * node, only the estimated remaining distance to target is considered.
     *
     * @param source The node from which to start.
     * @param target The node to which a path is to be found.
     *
     * @return A <code>Path</code> object representing the path between
     *         between the source and destination nodes or <code>null/<code>
     *         if no path was found.
     *
     * @throws NullPointerException if either source or target is null.
     */
    public final Path bestFirstSearch(Node source, Node target) 
	throws PathNotFoundException {

	Vector open = new Vector();

	reset();

	source.setScore(0.0f);

	open.add(source);

	while (!open.isEmpty()) {

	    Node current = (Node) open.remove(0);

	    if (current.equals(target)) {
		return new DefaultPath(current);
	    }

	    Node[] child =  current.getAllChildren();

	    for (int i=0; i<child.length; i++) {

		// ignore the edges on bad maps and nodes that
		// cannot be entered.

		if (child[i] == null || child[i].getCost() >= maxCost) {
		    continue; 
		}

		// skip nodes that are currently being processed and
		// which have a lower total cost from source.

		if (child[i].isOpen() || child[i].isClosed()) {
		    continue;
		}
		
		// estimate the remaining distance to target.

		float score  = minCost * heuristic(child[i], target);

		child[i].setParent(current);
		child[i].setScore(score);

		int j = java.util.Collections.binarySearch(open, child[i]);

		if (j < 0) {

		    j = -j > open.size() ? open.size() : -j;
		    open.insertElementAt(child[i], j);

		    child[i].setOpen(true);
		    child[i].setClosed(false);
		}
	    }

	    current.setClosed(true);
	    current.setOpen(false);
	}

	throw new PathNotFoundException("s=" + source + ", f=" +  target);
    }

    /**
     * Search for the shortest path connecting the source node with 
     * the target node. This algorithm is guaranteed to find the
     * optimal shortest path, if it exist.
     *
     * @param source The node from which to start.
     * @param target The node to which a path is to be found.
     *
     * @return A <code>Path</code> object representing the path between
     *         between the source and destination nodes or <code>null/<code>
     *         if no path was found.
     *
     * @throws NullPointerException if either source or target is null.
     */
    public final Path dijkstraSearch(Node source, Node target) 
	throws PathNotFoundException {

	Vector open = new Vector();

	reset();

	source.setTotal(0.0f);
	source.setScore(0.0f);

	open.add(source);

	while (!open.isEmpty()) {

	    Node current = (Node) open.remove(0);
	    current.setOpen(false);

	    if (current.equals(target)) {
		return new DefaultPath(current);
	    }

	    Node[] child =  current.getAllChildren();

	    for (int i=0; i<child.length; i++) {

		// ignore the edges on bad maps and nodes that
		// cannot be entered.

		if (child[i] == null || child[i].getCost() >= maxCost) {
		    continue; 
		}

		// calculate the accumulated total cost of moving
		// from source to this child.

		float total = current.getTotal() + cost(current, child[i]);

		// skip nodes that are currently being processed and
		// which have a lower total cost from source.

		if (child[i].isOpen() && child[i].getTotal() <= total) {
		    continue;
		}
		
		// skip nodes that have already been processed and
		// which have a lower total cost from source.

		if (child[i].isClosed() && child[i].getTotal() <= total) {
		    continue;
		}

		// a new shorter path to this child was found. update
		// the node and add it to the list of open nodes if
		// it isn't already open.

		child[i].setParent(current);
		child[i].setTotal(total);
		child[i].setScore(total);

		if (child[i].isClosed()) {
		    child[i].setClosed(false);
		}

		if (!child[i].isOpen()) {
		    open.add(child[i]);
		    child[i].setOpen(true);
		}
	    }

	    current.setClosed(true);
	}

	throw new PathNotFoundException("s=" + source + ", f=" +  target);
    }

    /**
     * Search for the path connecting the source node with the target node.
     * This algorithm does not take into account the cost of accessing each
     * node.
     *
     * @param source The node from which to start.
     * @param target The node to which a path is to be found.
     *
     * @return A <code>Path</code> object representing the path between
     *         between the source and destination nodes or <code>null/<code>
     *         if no path was found.
     *
     * @throws NullPointerException if either source or target is null.
     */
    public final Path breadthFirstSearch(Node source, Node target) 
	throws PathNotFoundException {

	Vector open = new Vector();

	reset();

	open.add(source);

	while (!open.isEmpty()) {

	    Node current = (Node) open.remove(0);
	    current.setOpen(false);

	    if (current.equals(target)) {
		return new DefaultPath(current);
	    }

	    Node[] child =  current.getAllChildren();

	    for (int i=0; i<child.length; i++) {

		// ignore the edges on bad maps and nodes that
		// cannot be entered.

		if (child[i] == null || child[i].getCost() >= maxCost) {
		    continue; 
		}

		// skip nodes that are currently being processed and
		// nodes that have already been processed.

		if (child[i].isOpen() || child[i].isClosed()) {
		    continue;
		}

		child[i].setParent(current);

		if (!child[i].isOpen()) {
		    child[i].setOpen(true);
		    open.add(child[i]);
		}
	    }

	    current.setClosed(true);
	}

	throw new PathNotFoundException("s=" + source + ", f=" +  target);
    }

    /**
     * Search for the path connecting the source node with the target node.
     * This algorithm does not take into account the cost of accessing each
     * node.
     *
     * @param source The node from which to start.
     * @param target The node to which a path is to be found.
     *
     * @return A <code>Path</code> object representing the path between
     *         between the source and destination nodes or <code>null/<code>
     *         if no path was found.
     *
     * @throws NullPointerException if either source or target is null.
     */
    public final Path depthFirstSearch(Node source, Node target) 
	throws PathNotFoundException {

	Vector open = new Vector();

	reset();

	open.add(source);

	while (!open.isEmpty()) {

	    Node current = (Node) open.remove(open.size() - 1);
	    current.setOpen(false);

	    if (current.equals(target)) {
		return new DefaultPath(current);
	    }

	    Node[] child =  current.getAllChildren();

	    for (int i=0; i<child.length; i++) {

		// ignore the edges on bad maps and nodes that
		// cannot be entered.

		if (child[i] == null || child[i].getCost() >= maxCost) {
		    continue; 
		}

		// skip nodes that are currently being processed and
		// nodes that have already been processed.

		if (child[i].isOpen() || child[i].isClosed()) {
		    continue;
		}

		child[i].setParent(current);

		if (!child[i].isOpen()) {
		    open.add(child[i]);
		    child[i].setOpen(true);
		}
	    }

	    current.setClosed(true);
	}

	throw new PathNotFoundException("s=" + source + ", f=" +  target);
    }

    /**
     * The associated heuristic function for this map. Estimates
     * the remaining distance from node a to node b.
     *
     * @param a The first node.
     * @param b The second node.
     */
    protected abstract float heuristic(Node a, Node b);

    /**
     * Determines the actual cost of crossing the edge between
     * the given adjectant nodes. If the nodes are not adjectant
     * the returned cost is unspecified.
     *
     * @param source The source node.
     * @param target The target node.
     */
    protected abstract float cost(Node source, Node target);
}
