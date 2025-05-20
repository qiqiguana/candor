import java.io.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.*;

public class Xent {

    public double process(EventSet e1, EventSet e2, int windowSize) {

	double me = meanEntropy(e1,e2,windowSize);
	double hhat = (Math.log(1.0*windowSize) / Math.log(2.0)) / me;

	return hhat;
    }

    private double meanEntropy(EventSet e1, EventSet e2, int windowSize) {
	
	double totalEntropy = 0;
	int trials = 0;
	
	if (windowSize > e1.size() - 1)
	    windowSize = e1.size();
	
	XEDictionary xed = new XEDictionary();
	EventSet dictionary = new EventSet();
	dictionary = window(e1,0,windowSize);
	xed.build(dictionary);

	for (int i = 0; i < e2.size(); i++) {
	    totalEntropy += xed.find(e2.subset(i,e2.size()));
	    trials++;
	}
					      
	return totalEntropy / (double)trials;
    }

    private EventSet window(EventSet e1, int offset, int WindowSize) {

	return e1.subset(offset, offset + WindowSize);
    }

   
    private double calculateEntropy(EventSet e1, EventSet e2) {
	int currentEntropy = 0;
	int maxEntropy = 0;
	int finger = 0;
	while (finger < e1.size() && finger < e2.size()) {
	    if (e1.eventAt(finger) == e2.eventAt(0)) {
		currentEntropy = matchLength(e1.subset(finger, e1.size()), e2);
		if (currentEntropy > maxEntropy)
		    maxEntropy = currentEntropy;
	    }
	    finger++;
	}

	return maxEntropy;
    }

    private int matchLength(EventSet e1, EventSet e2) {
	int i = 0;
	while (i < e1.size() && i < e2.size() && e1.eventAt(i) == e2.eventAt(i)) {
	    i++;
	}
	return i;
    }
    
}

/**Cross Entropy Dictionary Node
 * Each node contains a single event and a hashtable containing Events as keys and
 * Cross Entropy Dictionary Nodes as values to the keys.  The hashtable can be of 
 * any size.  When this node is placed in a tree structure, a generalized Trie is 
 * created
 **/
class XEDictionaryNode {
    Event key;
    Hashtable< Event, XEDictionaryNode > child = new Hashtable< Event, XEDictionaryNode >();

    XEDictionaryNode() {
	key = null;
    }

    XEDictionaryNode (Event key) {
	this.key = key;
    }
	
    void setKey(Event key) {
	this.key = key;
    }

    void addEventToLevel(Event e) {
	XEDictionaryNode node = new XEDictionaryNode();
	node.key = e;
	child.put(e, node);
    }

    boolean isEventInLevel(Event e) {
	return child.containsKey(e);
    }

    XEDictionaryNode get(Event e) {
	return child.get(e);
    }

    String printKey(XEDictionaryNode key) {
	return child.get(key).toString();
    }

    /**Shows the events at this level of the tree.  Used mainly
     * for debugging purposes
     **/
    String eventsAtThisLevel() {
	String t = new String();
	for(Enumeration en = child.keys(); en.hasMoreElements();)
	    t += en.nextElement();
	return t;	
    }

    public String toString() {
	String t = new String();
	if (key != null)
	    t = key.toString();
	if (child != null) {
	    t += eventsAtThisLevel();
	    t += child;
	}
	return t;
    }

} 


/**Methods for building and manipulating a Cross Entropy Dictionary.
 * A Cross Entropy Dictionary is a hashtable where the keys are Events
 * and the values are Cross Entropy Dictionary Nodes
 **/
class XEDictionary {
    XEDictionaryNode root;

    XEDictionary() {
	root = new XEDictionaryNode();
    }

    public void build(EventSet e) {
	for (int i = 0; i < e.size(); i++) {
	    Event start = e.eventAt(i);
	    if (!root.isEventInLevel(start)) 
	        insertAtRoot(start,e,i);
	    else 
		insertBelowRoot(start,e,i);
	    
	}
	root.key = null;
    }

    public int find(EventSet e) {
	int matchlength = 0;
	boolean matched = false;
	XEDictionaryNode node = root;
	while (matchlength < e.size() && !matched) {
	    if (node.isEventInLevel(e.eventAt(matchlength))) {
		node = node.get(e.eventAt(matchlength));
		matchlength++;
	    }
	    else 
		matched = true;
	}
	return matchlength;
    }
		

    private void insertAtRoot(Event start,EventSet e,int offset) {
	root.addEventToLevel(start);
	XEDictionaryNode node = new XEDictionaryNode();
	node = root;
	int j = offset;
	while(j < e.size() - 1) {
	    node = node.get(e.eventAt(j));
	    j++;
	    //  System.out.println("Adding Event: " + e.eventAt(j));
	    node.addEventToLevel(e.eventAt(j));
	}
    }

    private void insertBelowRoot(Event start, EventSet e, int offset) {
	XEDictionaryNode node = new XEDictionaryNode();
	node = root;
	//	System.out.println("Event at offset: " + e.eventAt(offset));
	node = node.get(e.eventAt(offset));
	int j = offset;
	boolean matches = true;   //match the events up to a given level
	while(matches && j < e.size() - 1) {
	    j++;
	    if (node.isEventInLevel(e.eventAt(j))) {
		//	System.out.println("Match at level: " + e.eventAt(j));
		node = node.get(e.eventAt(j));
	    }
	    else 
		matches = false;
	}
	for (int i = j; i < e.size(); i++) {    
	    //	    System.out.println("Adding Event: " + e.eventAt(i));
	    node.addEventToLevel(e.eventAt(i));
	    node = node.get(e.eventAt(i));
	}
    }

    public String toString() {
	return root.toString();
    }
}
