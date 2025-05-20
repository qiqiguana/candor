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
 * Insert the type's description here.
 * Creation date: (2/26/01 5:09:09 PM)
 * @author: Administrator
 */
public class StringMatchesGuardCondition implements uk.me.jockmacmad.jstate.state.IGuardCondition {
    private final java.lang.String Value;

    /**
     * StringMatchesGuardCondition constructor comment.
     */
    public StringMatchesGuardCondition(java.lang.String newStr) {
        super();
        Value = newStr;
    }

    /**
     * Insert the method's description here.
     * Creation date: (2/26/01 5:09:09 PM)
     * @return boolean
     * @param o java.lang.Object
     */
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

    /**
     * Insert the method's description here.
     * Creation date: (2/26/01 5:11:09 PM)
     * @return java.lang.String
     */
    public final java.lang.String getValue() {
        return Value;
    }

    /**
     * Returns a String that represents the value of this object.
     * @return a string representation of the receiver
     */
    @Override
    public String toString() {
        // Insert code to print the receiver here.
        // This implementation forwards the message to super. You may replace or supplement this.
        return super.toString();
    }
}
