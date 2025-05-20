package uk.me.jockmacmad.jstate.state;

/**
 * An event that takes place upon which a statemachine has a dependance.
 * Creation date: (2/14/01 6:27:09 PM)
 *
 * @author :Don Stewart
 * @version 0.1
 * @since 0.1
 */
public class Event extends java.util.EventObject {

    /**
     * Insert the method's description here.
     * Creation date: (2/14/01 7:11:25 PM)
     *
     * @return java.util.Vector
     */
    public final java.util.Vector getParameters();
}

/**
 * Insert the type's description here.
 * Creation date: (2/26/01 5:09:09 PM)
 *
 * @author: Administrator
 */
public class StringMatchesGuardCondition implements uk.me.jockmacmad.jstate.state.IGuardCondition {

    /**
     * Insert the method's description here.
     * Creation date: (2/26/01 5:09:09 PM)
     *
     * @return boolean
     * @param o java.lang.Object
     */
    public boolean evaluate(Object o) {
        boolean rc = false;
        try {
            java.util.Vector params = ((uk.me.jockmacmad.jstate.state.Event) o).getParameters();
            String str = (String) params.elementAt(0);
            if (Value.equals(str)) {
                rc = true;
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
        return rc;
    }
}
