/* $Id: ObjectManager.java,v 1.2 2004/04/27 19:26:22 bja Exp $ */

package util;

/**
 *
 *
 */
public interface ObjectManager<E extends ManagedObject> {

    /**
     * This method is typically invoked by the managed object to let
     * the manager know that it has changed.
     */
    public void unrefObject(E obj);
}
