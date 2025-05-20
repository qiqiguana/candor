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

    public boolean maxCapacityExceeded() {
        return (numItems > (maxCapacity - 1)) && (maxCapacity != NO_MAXIMUM);
    }
}
