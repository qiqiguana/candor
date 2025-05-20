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
     * Field storing a stringified version of the classname.
     * @since 0.1
     */
    private static final java.lang.String NAME = "com.objectmentors.state.EndState";

    /**
     * Variable used to store the singleton instance of the EndState class.
     *
     * Uses the private constructor to create the instance on the loading this
     * class.
     * @since 0.1
     */
    private static final EndState SINGLETON = new EndState();

    /**
     * The actual method a client should call to get a
     * reference to the single instance of the EndState object.
     * Creation date: (2/20/01 11:05:10 AM)
     * @return com.objectmentors.state.EndState
     */
    public static EndState getSingleton();

    /**
     *  Method to compare two IState objects.
     * <p>
     *  Returns true if both IState objects are instances of
     *  <code>EndState</code>, otherwise returns false.
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
     * Creation date: (20/02/01 10:55:55 AM)
     * @return java.lang.String
     */
    public java.lang.String getName();

    /**
     * Returns this EndState object as a java.lang.Object.
     * Used during reflection.
     * Creation date: (2/26/01 10:49:49 AM)
     * @return java.lang.Object
     */
    public java.lang.Object toObject();
}
