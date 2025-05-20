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

import java.util.NoSuchElementException;
import java.util.Vector;

import de.paragon.explorer.figure.Figure;

public class StandardEnumerator implements StandardEnumeration {
	private static final String	NO_SUCH_ELEMENT_EXCEPTION	= "standardenumerator.no_such_element_exception";
	private Vector<Object>		vector;
	private int					count						= 0;

	public StandardEnumerator() {
		this(new Vector<Object>());
	}

	public StandardEnumerator(Vector<?> newVector) {
		super();
		this.setVector(newVector);
	}

	public void addElement(Object object) {
		if (this.getCount() == 0) {
			this.getVector().addElement(object);
		}
	}

	public int getCount() {
		return this.count;
	}

	public Vector<Object> getVector() {
		return this.vector;
	}

	public boolean hasMoreElements() {
		if (this.getCount() < this.getVector().size()) {
			return true;
		}
		this.setCount(0);
		return false;
	}

	public void insertElementAt(Object obj, int index) {
		this.getVector().insertElementAt(obj, index);
	}

	public boolean isInEnumeration(Object object) {
		return this.getVector().contains(object);
	}

	public Object nextElement() {
		synchronized (this.getVector()) {
			if (this.getCount() < this.getVector().size()) {
				this.setCount(this.getCount() + 1);
				return this.getVector().elementAt(this.getCount() - 1);
			}
		}
		throw new NoSuchElementException(ResourceBundlePurchaser.getMessage(StandardEnumerator.NO_SUCH_ELEMENT_EXCEPTION));
	}

	public Figure nextFigure() {
		synchronized (this.getVector()) {
			if (this.getCount() < this.getVector().size()) {
				this.setCount(this.getCount() + 1);
				return (Figure) this.getVector().elementAt(this.getCount() - 1);
			}
		}
		throw new NoSuchElementException(ResourceBundlePurchaser.getMessage(StandardEnumerator.NO_SUCH_ELEMENT_EXCEPTION));
	}

	public void removeElement(Object object) {
		if (this.getCount() == 0) {
			this.getVector().removeElement(object);
		}
	}

	public void setCount(int i) {
		this.count = i;
	}

	public void setVector(Vector<?> newVector) {
		Vector<Object> vector2return = new Vector<Object>();
		for (Object object : newVector) {
			vector2return.add(object);
		}
		this.vector = vector2return;
	}
}
