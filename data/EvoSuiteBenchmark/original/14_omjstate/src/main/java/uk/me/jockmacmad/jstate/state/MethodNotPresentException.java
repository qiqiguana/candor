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
 * An <code>Exception</code> that gets thrown when a user tries
 * to call an operation that does not exist.
 * Creation date: (15/02/01 10:35:56 AM)
 * @author: Don Stewart
 */
public class MethodNotPresentException extends Exception {

    /**
     * @param message the message displayed to the caller.
     */
    public MethodNotPresentException(final String message) {
        super(message);
    }

    /**
     * Compiler generated serialVersionUID.
     */
    private static final long serialVersionUID = 5188228850394781473L;
}