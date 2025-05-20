package bible.util;

import java.util.*;

/**
 * Queue is a storage class to link together <b>Node</b> objects which are
 * stored in a first in first out storage system. 
 *
 * @see Node
 * @version   1.0
 * @author    LaMoine Zielieke, James Stauffer
 */
public class Queue  {
  public final static int NO_MAXIMUM = -1;
  /**
   * number of items in this list
   */
  private int  numItems;
  private int  maxNumItems;
  private int  maxCapacity;
  /**
   * first Node in list
   */
  private Node first;
  /**
   * last Node in list
   */
  private Node last;

  /**
   * Constructor creating an empty Queue.  All variables are set to zero
   * or null.
   */
  public Queue() {
    this(NO_MAXIMUM);
  }

  /**
   * Constructor creating an empty Queue.  All variables are set to zero
   * or null.
   */
  public Queue(int capacity) {
    first = null;
    last = null;
    numItems = 0;
    maxNumItems = 0;
    maxCapacity = capacity;
  }

  /**
   * See if the queue is empty.
   * @return     <code>true</code> if Queue is empty;
   *             <code>false</code> otherwise.         
   */
  public boolean isEmpty() {
    return (first == null);
  }

  /**
   * Insert an object into the end of the queue
   * 
   * @param someObject  the Object to store 
   */
  public void enqueue(Object someObject) {
    if(someObject != null) {
      Node newNode = new Node(someObject);

      synchronized(this) {
//System.out.println("+++" + someObject);

          if(isEmpty())   {
             first = newNode;
             last = newNode;
          } else {//Add at end
            last.next = newNode;
            last = newNode;
          }
          numItems++;
          if(numItems > maxNumItems) {
            maxNumItems = numItems;
          }
      }
    }
  }

  /**
   * Remove an object from the front of the Queue
   *
   * @return       Object if Queue is not empty
   *               otherwise print message to dos window
   * @see GameQuestion
   */
  public synchronized Object dequeue() {
    Object obj = null;

    if(isEmpty()) {
      System.out.println("Cannot remove when queue is empty");

    } else if(first == last) { // first see if we only have one item in the queue
      obj = first.value;
      first = null;
      last = null;
    } else {
      obj = first.value;
      first = first.next;
    }
    numItems--;
    return obj;
  }

  /**
   *  Removes all nodes where Node.value.equals(object).
   */
  public synchronized int remove(Object object) {
//System.out.println("---" + object);
    int originalNumItems = numItems;
    while((first != null) && (first.value.equals(object))) {// found it
      first = first.next;
      numItems--;
    }

    if(first == null) {//All elements removed
      last = null;
    } else {
      //Now it isn't in the first node
      for(Node current = first; current.next != null; ) {
        if(current.next.value.equals(object)) {// found it in the next Node
          current.next = current.next.next;
          numItems--;
          if(current.next == null) {// we are removing the last element
            last = current;
            // the for loop will be exited
          }
        } else {
          current = current.next;
        }
      }// of for
    }

    return originalNumItems - numItems;
  }

  /**
   *  Moves element to back of que.
   */
  public synchronized void refreshElement(Object object) {
      remove(object);
      enqueue(object);
  }

  /**
   * See how many items are in the queue.
   *
   * @return integer value of number of items in queue 
   */
  public int getNumberItems() {
    return numItems;
  }

  /**
   * The peak number of items in the Queue.
   *
   * @return peak number of items in queue 
   */
  public int getPeakNumberItems() {
    return maxNumItems;
  }

  /**
   * The Queue is stepped through one node at a time and the 
   * QuestionNumber is inserted into a Vector.This action copies
   * unused question numbers from queue into temp vector.
   * 
   * @return   Vector of unused question numbers. In this way when 
   *           a new vector of randomized numbers is created the nunbers
   *           already in the queue will not be duplicated.  If number 
   *           396 is in the queue we do not want 396 to appear in the 
   *           new randomized vector.  This method is only called in 
   *           <b>DataBase</b>
   * @see      Database#load
   * @see      GameQuestion
   */
  public Vector getObjects() {
    Node i;
    Vector tempVector = new Vector(numItems,10);
     
    for(i = first; i != null; i = i.next) {
      tempVector.addElement(i.value);
    } // end for
    return tempVector;
  } // end getUnused

  /**
   *  Returns true if the first should be removed.
   */
  public boolean maxCapacityExceeded() {
      return (numItems > (maxCapacity - 1)) && (maxCapacity != NO_MAXIMUM);
  }

  public String toString() {
    return getClass().getName() + ":[numItems=" + numItems
        + ", maxNumItems=" + maxNumItems
        + ", maxCapacity=" + maxCapacity
        + ", getObjects()=" + getObjects()
        + "\r\n]";
  }

  /**
   * Node objects hold the Object to be stored and the next node in our linked list. 
   * In this way we can access the next node in our <b>Queue</b>.
   *
   * @version   2.0
   * @author    LaMoine Zielieke
   */
  class Node {   
    /**
     * The next Node in the chain.
     */
    Node  next;
    /**
     * The Object this node stores.
     */
    Object value;
      
    /** 
     * @param  obj   the Object to be inserted into the node. 
     */
    public Node(Object obj) {
      value = obj;
    }
  }

}

