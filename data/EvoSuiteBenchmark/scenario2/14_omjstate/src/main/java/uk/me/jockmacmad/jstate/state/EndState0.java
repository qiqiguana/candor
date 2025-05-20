package uk.me.jockmacmad.jstate.state;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Class representing a  state machines ending state.
 * There is no instance data associated with such a class therefore
 *   there only needs to be a single instance of the end state.
 * Creation date: (2/19/01 4:22:51 PM)
 *
 * @since 0.1
 * @author :Don Stewart
 * @version 0.1
 * @stereotype singleton
 */
public final class EndState extends java.lang.Object implements IState, uk.me.jockmacmad.jstate.patterns.ISingleton {

    /**
     * The actual method a client should call to get a
     * reference to the single instance of the EndState object.
     * Creation date: (2/20/01 11:05:10 AM)
     *
     * @return com.objectmentors.state.EndState
     */
    public static EndState getSingleton() {
        return SINGLETON;
    }
}
