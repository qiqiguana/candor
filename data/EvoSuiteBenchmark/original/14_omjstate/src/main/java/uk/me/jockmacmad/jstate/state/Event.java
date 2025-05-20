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

import java.util.Vector;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * An event that takes place upon which a statemachine has a dependance.
 * Creation date: (2/14/01 6:27:09 PM)
 * @author :Don Stewart
 * @version 0.1
 * @since 0.1*/
public class Event extends java.util.EventObject {
    /**
     * Field storing a stringified version of the classname.
     * @since 0.1
     */
    private final java.lang.String Name;
    private final java.util.Vector Parameters;

    private Event() {
        super ( null );
        Name = "";
        Parameters = new Vector();
    }
    /**
     * Public class constructor for the Event class.
     *
     * Three parameters are required:-
     *
     *     pName is the name of this instance of the Event object
     *     pParameters is the list of parameters for this event
     *     pObject is a java.lang.Object, which is passed to our superclass
     * @since 0.1
     */
    public Event(final java.lang.String pName,
                 final java.util.Vector pParameters,
                 final java.lang.Object pObject) {
        // Call the superclasses constructor
        super(pObject);
        // Set the final name field storing a stringified version of the classname.
        Name = pName;
        Parameters = pParameters;
    }

    /**
     * Method to compare two Event objects.
     *
     * Returns true if both Event objects are instances are identical,
     * otherwise returns false.
     * <p>
     * Uses the Apache Commons Lang
     *  <code>EqualsBuilder.reflectionEquals(this, pEvent);</code> function.
     * @since 0.1
     * @return boolean
     * @param pEvent the Event to compare against
     * the State to compare against
     */
    @Override
    public final boolean equals(final Object pEvent) {
        return EqualsBuilder.reflectionEquals(this, pEvent);
    }

    /**
     *  Builds the <code>hashCode</code> of this <code>Object</code>
     *  using the Apache Commons Lang
     *  <code>HashCodeBuilder.reflectionHashCode(this);</code> function.
     * @return int the HashCode of this <code>Object</code>
     */
    @Override
    public final int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Field storing a stringified version of the classname.
     * Creation date: (2/20/01 10:55:55 AM)
     * @return java.lang.String
     */
    public final java.lang.String getName() {
        return Name;
    }

    /**
     * Insert the method's description here.
     * Creation date: (2/14/01 7:11:25 PM)
     * @return java.util.Vector
     */
    public final java.util.Vector getParameters() {
        return Parameters;
    }
}