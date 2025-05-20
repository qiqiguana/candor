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

    /**
     * Remove an object from the front of the Queue
     *
     * @return Object if Queue is not empty
     *               otherwise print message to dos window
     * @see GameQuestion
     */
    public synchronized Object dequeue() {
        Object obj = null;
        if (isEmpty()) {
            System.out.println("Cannot remove when queue is empty");
        } else if (first == last) {
            // first see if we only have one item in the queue
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
}
