/* Path.java v0.1 (06/16/2002)
 *
 * Copyright (C) 2002, Joel Andersson <bja@kth.se>
 * 
 * This software is hereby expressly placed in the public domain.
 * Share and enjoy.
 */

package map;
import java.util.Iterator;

public interface Path {
    public void construct(Node n);
    public int length();
    public Node next();
    public boolean isEmpty();
    public Iterator iterator();
    public Node[] asNodeArray();
}
