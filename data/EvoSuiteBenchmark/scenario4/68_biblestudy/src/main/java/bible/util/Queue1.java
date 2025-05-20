package bible.util;

import java.util.*;

/**
 * Queue is a storage class to link together <b>Node</b> objects which are
 * stored in a first in first out storage system.
 *
 * @see Node
 * @version 1.0
 * @author LaMoine Zielieke, James Stauffer
 */
public class Queue {

    public final static int NO_MAXIMUM = -1;

    /**
     * number of items in this list
     */
    private int numItems;

    private int maxNumItems;

    private int maxCapacity;

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
    }

    /**
     * Constructor creating an empty Queue.  All variables are set to zero
     * or null.
     */
    public Queue(int capacity) {
    }

    /**
     * See if the queue is empty.
     * @return     <code>true</code> if Queue is empty;
     *             <code>false</code> otherwise.
     */
    public boolean isEmpty();

    /**
     * Insert an object into the end of the queue
     *
     * @param someObject  the Object to store
     */
    public void enqueue(Object someObject);

    /**
     * Remove an object from the front of the Queue
     *
     * @return       Object if Queue is not empty
     *               otherwise print message to dos window
     * @see GameQuestion
     */
    public synchronized Object dequeue();

    /**
     *  Removes all nodes where Node.value.equals(object).
     */
    public synchronized int remove(Object object);

    /**
     *  Moves element to back of que.
     */
    public synchronized void refreshElement(Object object);

    /**
     * See how many items are in the queue.
     *
     * @return integer value of number of items in queue
     */
    public int getNumberItems();

    /**
     * The peak number of items in the Queue.
     *
     * @return peak number of items in queue
     */
    public int getPeakNumberItems();

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
    public Vector getObjects();

    /**
     *  Returns true if the first should be removed.
     */
    public boolean maxCapacityExceeded();

    public String toString();

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
        Node next;

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
