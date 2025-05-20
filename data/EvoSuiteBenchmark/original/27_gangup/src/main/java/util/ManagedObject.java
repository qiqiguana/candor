/* $Id: ManagedObject.java,v 1.2 2004/04/27 19:26:22 bja Exp $ */

package util;

public abstract class ManagedObject {

    /** The manager associated with this object. */
    protected ObjectManager manager;

    /** 
     * Number of referencing modules. When this equals zero the
     * ActionManager is free to modify the action. 
     */
    protected int refCount = 0;

    /**
     * Create a new unmanaged instance of the ManagedObject class.
     * An unmanaged object does not have an associated manager.
     */
    protected ManagedObject() {
	this(null);
    }

    /**
     * Creates a new managed instance of the ManagedObject class.
     * @param manager the manager of this object.
     */
    protected ManagedObject(ObjectManager manager) {
	this.manager = manager;
    }

    public abstract ManagedObject clone();

    /**
     * Returns true if this object has been consumed, and therefore may
     * be dereferenced by the associated manager (if any.)
     */
    public boolean getIsConsumed() {
	return refCount <= 0;
    }

    /**
     * Returns the number of objects currently referencing this object.
     * @return the number of referencing objects.
     */
    public int getRefCount() {
	return refCount;
    }

    /**
     * Returns true if this object is managed. A managed object is one
     * which have a manager.
     *
     * @return true if this object is managed, otherwise false.
     */
    public boolean getIsManaged() {
	return manager != null;
    }

    public synchronized void unref() {
	if (refCount != 0 && --refCount == 0) {
	    consume();
	}
    }

    public synchronized void ref() {
	refCount++;
    }

    protected synchronized void consume() {
	refCount = 0;
	if (getIsManaged()) {
	    manager.unrefObject(this);
	} else {
	    //    System.err.println("unmanaged: " + this.hashCode());
	}
    }

    /**
     * Sets the ObjectManager of this object. This method is called by the
     * manager itself, and should normally not be invoked manually.
     *
     * @param manager the manager that manages this object.
     */
    public void setManager(ObjectManager manager) {
	if (!getIsManaged()) {
	    this.manager = manager;
	}
    }
}
