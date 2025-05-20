/* $Id: AbstractFactory.java,v 1.2 2004/04/27 19:26:22 bja Exp $ 
 *
 * @author Joel Andersson <bja@kth.se>
 * @version $Revision: 1.2 $
 */

package util;

import java.util.Stack;
//import java.util.LinkedList;

/**
 * This class provides a generic interface for creating and managing
 * objects. The factory produces and manages objects derived from
 * {@link util.ManagedObject}. The ref and unref methods are used to
 * keep track of the number of referencing objects, when this number
 * reaches zero (no referencing object) the object calls the unrefObject
 * method of its associated manager.
 *
 * Note: currently a produced object is never freed, which means that if
 * you create many new objects under some time, but normally only use a
 * fraction of that, then there will be a large number of vasted objects.
 * A possible solution to this is to trim the stoch from time to time.
 */
public abstract class AbstractFactory<E extends ManagedObject> 
    implements ObjectManager<E> {

    /** The prototype associated with this Factory. */
    protected E prototype = null;

    /** The collection of unused (available) Messages. */
    protected Stack<E> stock = null;
    //protected LinkedList<E> stock = null;

    /** The total number of objects produced by this factory. */
    protected int totalObjectsProduced = 0;

    /**
     * Creates a new instance of the MessageFactory class.
     * @param prototype The prototype of this factory.
     */
    protected AbstractFactory(E prototype) {
	this.stock = new Stack<E>();
	//this.stock = new LinkedList<E>();
	this.prototype = prototype;
	this.prototype.setManager(this);
    }

    /**
     * Get a reference to an unused ManagedObject instance. The object's
     * reference count is increased to one.
     *
     * @return a reference to an unused object instance.
     */
    public synchronized E createObject() {
	E obj = stock.size() == 0 ? 
	    (E) produceObject() : stock.pop(); obj.ref();
	System.err.println("+" + obj.hashCode());
	return obj;
    }

    /**
     * Adds the specified object to the list of unused objects.
     * @param obj the object that is to be added.
     */
    public synchronized void unrefObject(E obj) {
	stock.push(obj);
	System.err.println("-" + obj.hashCode());
    }

    /**
     * Returns the current number of unused (i.e. available) objects.
     * @return the current number of unused (i.e. available) objects.
     */
    public synchronized int getStockCount() {
	return stock.size();
    }

    /**
     * Returns the total number of produced objects, i.e. the number of
     * new instances of the prototype that created.
     *
     * @return the total number of prototype instances created.
     */
    public int getTotalProduced() {
	return totalObjectsProduced;
    }

    /**
     * Produces and returns a new object based on this factory's prototype.
     * @return a new instance of the prototype object.
     */
    protected E produceObject() {
	totalObjectsProduced++;
	return (E) prototype.clone();
    }

    /**
     * Produces the given number of objects of this factory's prototype
     * and adds them to the stock.
     *
     * @param count the number of objects to produce.
     */
    protected synchronized void produceObjects(int count) {
	for (int i=0; i<count; i++) {
	    stock.push((E)prototype.clone());
	}
	totalObjectsProduced += count;
    }
}
