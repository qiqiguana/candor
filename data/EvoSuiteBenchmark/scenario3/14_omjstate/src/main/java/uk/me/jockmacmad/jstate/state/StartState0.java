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

    /**
     * The actual method a client should call to get a
     * reference to the single instance of the StartState object.
     * Creation date: (2/20/01 11:05:10 AM)
     *
     * @return com.objectmentors.state.StartState
     */
    public static StartState getSingleton();
}
