package uk.me.jockmacmad.jstate.state;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Class representing a state machines staring state.
 * There is no instance data associated with such a class therefore
 *   there only needs to be a single instance of the start state.
 * Creation date: (19/02/01 4:22:22 PM)
 *
 * @stereotype singleton
 * @author :Don Stewart
 * @version 0.1
 * @since 0.1
 */
public final class StartState extends java.lang.Object implements IState, uk.me.jockmacmad.jstate.patterns.ISingleton {

    public java.lang.Object toObject() {
        return (java.lang.Object) this;
    }
}
