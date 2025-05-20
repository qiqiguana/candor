/**
 * [ObjectExplorer4J - Tool zur grafischen Darstellung von Objekten und ihren
 * Referenzen]
 * 
 * Copyright (C) [2009] [PARAGON Systemhaus GmbH]
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 **/
package de.paragon.explorer.excp;

/**
 * Exception-Type for use in Framework
 * 
 * (usually subclasses are actually thrown)
 * 
 */
public class FigureException extends Exception {
	private static final long	serialVersionUID	= 25383948709994467L;

	/**
	 * FigureException constructor comment.
	 */
	public FigureException() {
		super();
	}

	/**
	 * @param s
	 *            String
	 */
	public FigureException(String s) {
		super(s);
	}
}
