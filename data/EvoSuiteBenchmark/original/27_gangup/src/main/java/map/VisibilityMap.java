/* VisibilityMap.java v0.1 (06/17/2002)
 *
 * Copyright (C) 2002, Joel Andersson <bja@kth.se>
 * 
 * This software is hereby expressly placed in the public domain.
 * Share and enjoy.
 */

package map;
import java.util.Vector;

public abstract class VisibilityMap extends AbstractMap {

    /**
     * Creates a new instance of this class with the specified
     * attributes.
     *
     * @param min the minimum cost of accessing a node.
     * @param max the maximum cost of accessing a node.
     */
    protected VisibilityMap(float min, float max) {
	super(min, max);
    }

    /**
     * Determines the line of sight, if any, between the source and
     * target nodes. 
     *
     * @param source The node from which to start.
     * @param target The node to which a line of sight is to be found.
     *
     * @return A <code>Path</code> object representing the path between
     *         between the start and destination nodes or <code>null/<code>
     *         if no path was found.
     *
     * @throws PathNotFoundException if no such path could be found.
     * @throws NullPointerException if either start or finish is null.
     */
    public Path lineOfSight(Node source, Node target) 
	throws PathNotFoundException {

	float dx = target.getX() - source.getX();
	float dy = target.getY() - source.getY();

	int signx = dx < 0 ? -1 : 1;
	int signy = dy < 0 ? -1 : 1;

	// Absolute value of dx and dy.

	int ax = (int) (signx * dx);
	int ay = (int) (signy * dy);

	// Determine the slope of the straight line from source to
	// target. The slope is given as a function of the variable
	// that has the least angle.

	float kx = (ax > ay) ? signx : ((float) dx / (float) dy) * signy;
	float ky = (ax > ay) ? ((float) dy / (float) dx) * signx : signy;

	int maxlen = (int) (ax > ay ? ax : ay);

	Node p = source;

	reset();

	for (int i=1; i < maxlen; i++) {

	    Node n = getNodeAt((source.getX() + i * kx),
			       (source.getY() + i * ky), source.getZ());

	    if (n.getCost() >= maxCost) {
		throw new PathNotFoundException();
	    }

	    n.setParent(p);
	    p = n;
	}

	return new DefaultPath(p);
    }

    /**
     * Determines the field of vision from the source in the given
     * direction and with the given spread, perf, and distance.
     *
     * @param source The node from which to start.
     * @param direction The direction in degrees of the line of sight.
     * @param spread The spread in degrees of the field.
     * @param perf The perf of the field.
     * @param distance The maximum distance.
     *
     * @throws NullPointerException if source is null.
     */
    public void fieldOfVision(Node source, float direction, float spread,
			      float perf, float distance) {

	float alpha = (float) (direction * Math.PI / 180);
	float delta = (float) (spread * Math.PI / 180);

	Vector open = new Vector();

	reset();

	source.setVisibility(1.0f);

	open.add(source);

	while (!open.isEmpty()) {

	    Node current = (Node) open.remove(0);
	    Node[] child = current.getAllChildren();

	    current.setOpen(true);

	    for (int i=0; i<child.length; i++) {

		float dx = child[i].getX() - source.getX();
		float dy = child[i].getY() - source.getY();

		// XXX - this is not working! Cells are not correctly
		// vis:ed if dx > 0. Not sure why.

		float beta = (float) Math.atan2(-dy, dx);
		beta = beta < 0 ? (float)(2 * Math.PI + beta) : beta;

		if (Math.sqrt(dx*dx+dy*dy) > distance) {
		    continue;
		}

		if (beta < Math.min(alpha + delta, alpha - delta)) {
		    continue;
		}

		if (beta > Math.max(alpha + delta, alpha - delta)) {
		    continue;
		}

		if (child[i].isOpen() || child[i].isClosed()) {
		    continue;
		}

		if (isVisible(child[i], source)) {

		    child[i].setOpen(true);
		    child[i].setVisibility(1.0f);

		    open.add(child[i]);
		}
	    }

	    current.setClosed(true);
	}
    }

    /**
     * Determines all nodes that are visible from source and are
     * within the given limit. A Node is considered visible if
     * there exist straight line path leading to it from the source.
     * 
     * @param source the source node.
     * @param limit the limit of the search.
     */
    public void fieldOfVision(Node source, float limit) {

	Vector open = new Vector();

	reset();

	source.setTotal(0);
	source.setVisibility(1.0f);

	open.add(source);

	while (!open.isEmpty()) {

	    Node current = (Node) open.remove(0);
	    Node[] child =  current.getAllChildren();

	    if (current.getTotal() > limit) {
		continue;
	    }

	    for (int i=0; i<child.length; i++) {

		if (child[i] == null || child[i].isClosed()) {
		    continue;
		}

		if (!child[i].isOpen() && isVisible(child[i], source)) {

		    child[i].setTotal(heuristic(child[i], source));
		    child[i].setVisibility((limit-child[i].getTotal())/limit);
		    child[i].setOpen(true);

		    open.add(child[i]);
		}
	    }

	    current.setClosed(true);
	}
    }

    /**
     * Determines whether the target node is visible from the
     * source node (ie. there is a straight line connecting the
     * nodes which does not fall on any obstacles.)
     * 
     * @param source the source node.
     * @param target the target node.
     *
     * @return true if the target node is visible from the source.
     */
    public boolean isVisible(Node source, Node target) {

	float dx = target.getX() - source.getX();
	float dy = target.getY() - source.getY();

	int signx = dx < 0 ? -1 : 1;
	int signy = dy < 0 ? -1 : 1;

	float ax = signx * dx;
	float ay = signy * dy;

	// As we are using tiles with an extension in the plane to 
	// display points in the grid there is a certain error in 
	// the calculated line of sight that must be considered.
	//
	//    dx  dy  (dx < dy) | cx cy
	//   -------------------+-------
	//     -   -    false   |  0  1
	//     -   -     true   |  1  0
	//     -   +    false   |  0  0
	//     -   +     true   |  1  1
	//     +   -    false   |  1  1
	//     +   -     true   |  0  1
	//     +   +    false   |  1  0
	//     +   +     true   |  0  1

	float kx = (ax > ay) ? signx : ((float) dx / (float) dy) * signy;
	float ky = (ax > ay) ? ((float) dy / (float) dx) * signx : signy;

	int maxlen = (int) (ax > ay ? ax : ay);

	for (int i=1; i < maxlen; i++) {

	    Node n = getNodeAt((source.getX() + i * kx +.5f),
	    		       (source.getY() + i * ky +.5f), 
			       (source.getZ()));

	    if (n.getCost() >= maxCost) {
	    	return false;
	    }
	}

	return true;
    }

    /**
     * Straightens the given path by repeatedly removing all
     * intermediet nodes that exist between two nodes that are
     * visible from each other.
     *
     * @param path the path to be straighten out.
     */
    public Path coalescePath(Path path) {

	if (path.isEmpty()) {
	    return path;
	}

	// fixme - remove unnecessary code.

	Node[] p1 = path.asNodeArray();
	Node[] p2 = p1;//smoothPath(p1, 0, p1.length-1, 4);
	Node p = p2[p2.length-1];
	Node q = p.getParent();


	// Coalesce neighboring nodes that are visible from each other.
	// Greedy algorithm to coalesce a chain of visible nodes.

	while (q != null) {
	    Node t = q;
	    while (q != null && isVisible(p, q)) {
		p.setParent(q);
		t = q;
		q = q.getParent();
	    }
	    if (q != null) {
		p = t;
		q = q.getParent();
	    }
	}
	return new DefaultPath(p2[p2.length-1]);
    }

    /**
     * Straightens a subinterval of the given path by repeatedly 
     * removing all intermediet nodes that exist between two nodes
     * that are visible from each other.
     *
     * @param path the path to be straighten out.
     * @param low the low end of the subinterval.
     * @param high the high end of subinterval.
     * @param strength the number of iterations.
     */
    public Node[] smoothPath(Node[] path, int low, int high, int strength) {

	if ((low < 0 || high > path.length) && low < high || strength < 0) {
	    return path;
	}

	if (isVisible(path[low], path[high])) {
	    
	    if (path[high] != path[low]) {
		path[high].setParent(path[low]);
	    }

	} else {

	    smoothPath(path, low, ((low + high) / 2) - 1, strength - 1);
	    smoothPath(path, ((low + high) / 2), high, strength - 1);

	}

	return path;
    }
}




