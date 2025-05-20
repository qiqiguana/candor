/*
    This code is (c) Don Stewart 2001.

This file is part of OMJState.

    OMJState is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    OMJState is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with OMJState; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA */

package uk.me.jockmacmad.jstate.state;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Class representing a state machines staring state.
 * There is no instance data associated with such a class therefore
 *   there only needs to be a single instance of the start state.
 * Creation date: (19/02/01 4:22:22 PM)
 * @stereotype singleton
 * @author :Don Stewart
 * @version 0.1
 * @since 0.1
 */
public final class StartState
extends java.lang.Object
implements IState, uk.me.jockmacmad.jstate.patterns.ISingleton {
    /**
     * Field storing a stringified version of the classname.
     * @since 0.1
     */
    public static final java.lang.String NAME =
        "com.objectmentors.state.StartState";
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
        super();
    }

    /**
     * Method to compare two IState objects.
     *<p>
     * Returns true if both IState objects are instances of
     * <code>StartState</code>, otherwise returns false.
     * <p>
     * Uses the Apache Commons Lang
     *  <code>EqualsBuilder.reflectionEquals(this, pIState);</code> function.
     * @since 0.1
     * @return boolean
     * @param pIState the State to compare against
     * the State to compare against
     */
    @Override
    public boolean equals(final Object pIState) {
        return EqualsBuilder.reflectionEquals(this, pIState);
    }

    /**
     *  Builds the <code>hashCode</code> of this <code>Object</code>
     *  using the Apache Commons Lang
     *  <code>HashCodeBuilder.reflectionHashCode(this);</code> function.
     * @return int the HashCode of this <code>Object</code>
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    /**
     * Field storing a stringified version of the classname.
     * Creation date: (2/20/01 10:55:55 AM)
     * @return java.lang.String
     */
    @Override
    public java.lang.String getName() {
        return NAME;
    }

    /**
     * The actual method a client should call to get a
     * reference to the single instance of the StartState object.
     * Creation date: (2/20/01 11:05:10 AM)
     * @return com.objectmentors.state.StartState
     */
    public static StartState getSingleton() {
        return SINGLETON;
    }

    /**
     * Returns this StartState object as a java.lang.Object.
     * Used during reflection.
     * Creation date: (2/26/01 10:49:49 AM)
     * @return java.lang.Object
     */
    public java.lang.Object toObject() {
        return (java.lang.Object) this;
    }
}
