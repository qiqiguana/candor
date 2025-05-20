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
package de.paragon.explorer.util;

import java.util.Vector;

/**
 * Diese Klasse stellt Algorithmen zur Verfuegung um den Wert von Objekten als
 * String darzustellen. Wurde implmentiert, da die Methode toString bei der
 * Klasse Vector im Falle eines Verweises auf sich selbst zu einer Exception
 * fuehrt.
 */
public final class ToStringConverter {
	private static final String	COMMA_BLANK				= ", ";
	private static final String	THIS					= "this";
	private static final String	CLOSE_SQUARE_BRACKET	= "]";
	private static final String	OPEN_SQUARE_BRACKET		= "[";

	public static String convertVectorToString(Vector<Object> vector) {
		StringBuffer buf = new StringBuffer();
		buf.append(ToStringConverter.OPEN_SQUARE_BRACKET);
		int maxIndex = vector.size() - 1;
		for (int i = 0; i <= maxIndex; i++) {
			if (vector.elementAt(i) != vector) {
				buf.append(String.valueOf(vector.elementAt(i)));
			}
			else {
				buf.append(ToStringConverter.THIS);
			}
			if (i < maxIndex) {
				buf.append(ToStringConverter.COMMA_BLANK);
			}
		}
		buf.append(ToStringConverter.CLOSE_SQUARE_BRACKET);
		return buf.toString();
	}

	/**
	 * ToStringConverter constructor comment.
	 */
	private ToStringConverter() {
		super();
	}
}
