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

package uk.me.jockmacmad.jstate.patterns;

/**
 * An exception that is thrown when a client calls the constructor for for a singleton class.
 *
 * Should be thrown from within the class constructor of the singleton class.
 * Creation date: (2/20/01 11:11:07 AM)
 * @author :Don Stewart
 * @version 0.1
 * @since 0.1
 * @stereotype exception*/
public class SingletonConstructionException extends Exception {
    /**
     * SingletonConstructionException constructor comment.
     */
    public SingletonConstructionException() {
        super();
    }

    /**
     * SingletonConstructionException constructor comment.
     * @param exceptionMessage java.lang.String
     */
    public SingletonConstructionException(final String exceptionMessage) {
        super(exceptionMessage);
    }
}