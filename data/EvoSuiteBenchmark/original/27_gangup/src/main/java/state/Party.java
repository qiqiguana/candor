/* $Id: Party.java,v 1.2 2004/03/31 21:12:03 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Rasmus Ahlberg <ahlbgerg@kth.se>
 * @version: $Revision: 1.2 $
 *
 */

package state;

import java.io.*;
import java.util.*;


/**
 * A party in the tree view. Generally, all parties used will be of
 * class Player (which extends Party).
 */

public class Party {

    /** The party id */
    int id;

    /** The supergroup. */
    public Party boss;
    
    /** The first subgroup. */
    public Party head;
    
    /** The next group at the same level. */
    public Party next;
    
    /** The previous group at the same level. */
    public Party prev;


    /**
     * Creates a new instance of the Party class. All fields are set 
     * to null, except id which is set to -1.
     */
    public Party() {
	this(-1);
    }

    public Party(int id) {
	this.id = id;
	this.boss = null;
	this.head = null;
	this.next = null;
	this.prev = null;
    }

    /**
     * Used to determine whether this player is the current boss of
     * his gang.
     *
     * @return <i>true</i> if this player currently has no
     * boss. Otherwise <i>false</i>.
     */
    public boolean isBoss() {
	return boss == null;
    }

    /**
     * Returns the boss of current gang.
     */
    public Party gangBoss() {
	if (isBoss()) {
	    return this;
	} else {
	    return boss.gangBoss();
	}
    }

    /**
     * Add a group as a subgroup to this group. Does not check whether
     * a join is allowed or not.
     *
     * @param p The party to be added.
     */
    public void add(Party p) {
	try {
	    p.boss = this;
	    p.next = head;
	    p.prev = null;
	    if (head != null) {
		head.prev = p;
	    }
	    head = p;
	} catch (NullPointerException e) {
	    e.printStackTrace(System.err);
	}
    }

    /**
     * Remove a group as a subgroup to this group. Does not check
     * whether given group is a subgroup.
     *
     * @param p The party to be removed.
     */
    public void remove(Party p) {

	if (p != null) {

	    if (p.prev != null) {
		p.prev.next = p.next;
	    } else {
		head = p.next;
	    }
	
	    if (p.next != null) {
		p.next.prev = p.prev;
	    }

	    p.next = null;
	    p.prev = null;
	    p.boss = null;
	} 
    }

    /**
     * Returns a LinkedList representation of the current subparty. A
     * subparty includes the current Party plus all its childrens
     * subparties.
     */
    public LinkedList<Party> getSubparty() {
	
	LinkedList<Party> plist = new LinkedList<Party>();
	Party tmp;

	plist.add(this);
	for (tmp = head; tmp != null; tmp = tmp.next) {
	    plist.addAll(tmp.getSubparty());
	}

	return plist;
    }

    public int getId() {
	return id;
    }

    public String toString() {
	return ""; 
    }
}
