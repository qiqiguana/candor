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
 * An <code>Exception</code> that is thrown when a user attempts
 * to call an operation that is not available in the current state.
 * Creation date: (16/02/01 2:24:17 PM)
 * @author: Don Stewart
 */
public class InvalidMethodInStateException extends Exception {
    /**
     * Compiler generated serialVersionUID.
     */
    private static final long serialVersionUID = -7644347905865689949L;

    /**
     * InvalidMethodInStateException constructor comment.
     */
    public InvalidMethodInStateException() {
        super();
    }

    /**
     * InvalidMethodInStateException constructor comment.
     * @param exceptionMessage java.lang.String
     */
    public InvalidMethodInStateException(final String exceptionMessage) {
        super(exceptionMessage);
    }
}
