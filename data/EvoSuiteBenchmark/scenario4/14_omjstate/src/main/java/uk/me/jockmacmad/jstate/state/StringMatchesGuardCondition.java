package uk.me.jockmacmad.jstate.state;

/**
 * Insert the type's description here.
 * Creation date: (2/26/01 5:09:09 PM)
 *
 * @author: Administrator
 */
public class StringMatchesGuardCondition implements uk.me.jockmacmad.jstate.state.IGuardCondition {

    private final java.lang.String Value;

    /**
     * StringMatchesGuardCondition constructor comment.
     */
    public StringMatchesGuardCondition(java.lang.String newStr) {
    }

    /**
     * Insert the method's description here.
     * Creation date: (2/26/01 5:09:09 PM)
     * @return boolean
     * @param o java.lang.Object
     */
    public boolean evaluate(Object o);

    /**
     * Insert the method's description here.
     * Creation date: (2/26/01 5:11:09 PM)
     * @return java.lang.String
     */
    public final java.lang.String getValue();

    /**
     * Returns a String that represents the value of this object.
     * @return a string representation of the receiver
     */
    @Override
    public String toString();
}
