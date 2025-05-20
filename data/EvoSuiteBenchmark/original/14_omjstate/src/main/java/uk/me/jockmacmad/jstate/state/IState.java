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

/**
 * Interface representing a state that is contained within a state machine.
 * Creation date: (2/14/01 6:18:59 PM)
 * @author:
 */
public interface IState {
    /**
     * Compares two states for type equality.
     * Creation date: (15/02/01 9:31:52 AM)
     * @return boolean
     * @param pIState com.objectmentors.state.IState
     */
    @Override
    boolean equals(Object pIState);

    /**
     * Insert the method's description here.
     * Creation date: (2/19/01 4:28:13 PM)
     */
    java.lang.String getName();

    /**
     * Insert the method's description here.
     * Creation date: (2/15/01 4:24:15 PM)
     * @return java.lang.String
     */
    String toString();

    /**
     * Insert the method's description here.
     * Creation date: (2/26/01 10:48:28 AM)
     * @return java.lang.Object
     */
    Object toObject();
}