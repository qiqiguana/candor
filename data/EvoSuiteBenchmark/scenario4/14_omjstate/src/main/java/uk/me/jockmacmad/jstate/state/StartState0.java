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
     * Field storing a stringified version of the classname.
     * @since 0.1
     */
    public static final java.lang.String NAME = "com.objectmentors.state.StartState";

    /**
     * Variable used to store the singleton instance of the StartState class.
     *
     * Uses the private constructor to create the instance on the loading this
     * class.
     * @since 0.1
     */
    private static final StartState SINGLETON = new StartState();

    /**
     * Private constructor, makes sure that there is no default constructor
     * by which people can create a new instance of the StartState.
     *
     * Creation date: (2/20/01 11:12:56 AM)
     * @since 0.1
     * @see getSingleton()
     */
    private StartState() {
    }

    /**
     *  Method to compare two IState objects.
     * <p>
     *  Returns true if both IState objects are instances of
     *  <code>StartState</code>, otherwise returns false.
     *  <p>
     *  Uses the Apache Commons Lang
     *   <code>EqualsBuilder.reflectionEquals(this, pIState);</code> function.
     *  @since 0.1
     *  @return boolean
     *  @param pIState the State to compare against
     *  the State to compare against
     */
    @Override
    public boolean equals(final Object pIState);

    /**
     *  Builds the <code>hashCode</code> of this <code>Object</code>
     *  using the Apache Commons Lang
     *  <code>HashCodeBuilder.reflectionHashCode(this);</code> function.
     * @return int the HashCode of this <code>Object</code>
     */
    @Override
    public int hashCode();

    /**
     * Field storing a stringified version of the classname.
     * Creation date: (2/20/01 10:55:55 AM)
     * @return java.lang.String
     */
    @Override
    public java.lang.String getName();

    /**
     * The actual method a client should call to get a
     * reference to the single instance of the StartState object.
     * Creation date: (2/20/01 11:05:10 AM)
     * @return com.objectmentors.state.StartState
     */
    public static StartState getSingleton();

    /**
     * Returns this StartState object as a java.lang.Object.
     * Used during reflection.
     * Creation date: (2/26/01 10:49:49 AM)
     * @return java.lang.Object
     */
    public java.lang.Object toObject();
}
