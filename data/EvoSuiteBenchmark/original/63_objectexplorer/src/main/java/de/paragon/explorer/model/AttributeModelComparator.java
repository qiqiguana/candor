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
package de.paragon.explorer.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Diese Klasse stellt einen Vergleichsalgorihmus zur Sortierung der Attribute
 * zur Verfuegung
 */
public class AttributeModelComparator implements Comparator<AttributeModel>, Serializable {
	private static final String	A_AS_STRING			= "a";
	private static final long	serialVersionUID	= -8846334934584145381L;

	/**
	 * Comparator constructor comment.
	 */
	public AttributeModelComparator() {
		super();
	}

	/**
	 * Compares its two arguments for order. Returns a negative integer, zero,
	 * or a positive integer as the first argument is less than, equal to, or
	 * greater than the second.
	 * <p>
	 * 
	 * The implementor must ensure that <tt>sgn(compare(x, y)) ==
	 * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>. (This implies
	 * that <tt>compare(x, y)</tt> must throw an exception if and only if
	 * <tt>compare(y, x)</tt> throws an exception.)
	 * <p>
	 * 
	 * The implementor must also ensure that the relation is transitive:
	 * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
	 * <tt>compare(x, z)&gt;0</tt>.
	 * <p>
	 * 
	 * Finally, the implementer must ensure that <tt>compare(x, y)==0</tt>
	 * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
	 * <tt>z</tt>.
	 * <p>
	 * 
	 * It is generally the case, but <i>not </i> strictly required that
	 * <tt>(compare(x, y)==0) == (x.equals(y))</tt>. Generally speaking, any
	 * comparator that violates this condition should clearly indicate this
	 * fact. The recommended language is "Note: this comparator imposes
	 * orderings that are inconsistent with equals."
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 * @throws ClassCastException
	 *             if the arguments' types prevent them from being compared by
	 *             this Comparator.
	 */
	public int compare(AttributeModel o1, AttributeModel o2) {
		int value = -1;
		if ((o1 != null) && (o2 != null)) {
			value = (o1).getName().compareToIgnoreCase((o2).getName());
			if (value < 0) {
				if (((o1).getName().compareToIgnoreCase(AttributeModelComparator.A_AS_STRING) < 0)
						&& ((o2).getName().compareToIgnoreCase(AttributeModelComparator.A_AS_STRING) >= 0)) {
					return 1;
				}
			}
			if (value > 0) {
				if (((o2).getName().compareToIgnoreCase(AttributeModelComparator.A_AS_STRING) < 0)
						&& ((o1).getName().compareToIgnoreCase(AttributeModelComparator.A_AS_STRING) >= 0)) {
					return -1;
				}
			}
		}
		return value;
	}
}
