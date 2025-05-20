/* DefaultPath.java v0.1 (06/16/2002)
 *
 * Copyright (C) 2002, Joel Andersson <bja@kth.se>
 * 
 * This software is hereby expressly placed in the public domain.
 * Share and enjoy.
 */

package map;

import java.util.Vector;
import java.util.Iterator;

public class DefaultPath implements Path {

    private final Vector path;

    public DefaultPath() {
	path = new Vector();
    }

    public DefaultPath(Node n) {
	path = new Vector();
	construct(n);
    }

    public DefaultPath(Path p) {
	path = new Vector();
	Iterator it = p.iterator();
	while (it.hasNext()) {
	    path.add(it.next());
	}
    }

    public DefaultPath(Node[] array) {
	this(array[array.length-1]);
    }

    public void construct(Node n) {

	path.removeAllElements();

	while (n != null && n.getParent() != null) {
	    path.insertElementAt(n, 0);
	    n = n.getParent();
	}
    }
    
    public void reconstruct() {
	if (path.size() > 2) {
	    construct((Node) path.remove(path.size() - 1));
	}
    }

    public int length() {
	return path.size();
    }

    public boolean isEmpty() {
	return path.isEmpty();
    }

    public Node next() {
	return (Node) path.remove(0);
    }

    public Iterator iterator() {
	return path.iterator();
    }

    public Node[] asNodeArray() {
	return (Node[]) path.toArray(new Node[path.size()]);
    }

    public String toString() {
	Iterator it = iterator();
	String ret = "";
	while (it.hasNext()) {
	    Node n = (Node) it.next();
	    ret += n.toString() + "\n";
	}
	return ret;
    }
}
