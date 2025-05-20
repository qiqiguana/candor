package uk.me.jockmacmad.jstate.state;

/**
 * Insert the type's description here.
 * Creation date: (2/26/01 5:09:09 PM)
 *
 * @author: Administrator
 */
public class StringMatchesGuardCondition implements uk.me.jockmacmad.jstate.state.IGuardCondition {

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
