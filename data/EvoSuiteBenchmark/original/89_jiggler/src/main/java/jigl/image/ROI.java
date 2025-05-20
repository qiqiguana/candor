/* This file is part of the JIGL Java Image and Graphics Library.
 * 
 * Copyright 1999 Brigham Young University.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * A copy of the GNU Lesser General Public License is contained in
 * the 'licenses' directory accompanying this distribution.
 */
package jigl.image;

/** A region of interest */
public class ROI {

	protected int m_ux, m_uy, m_lx, m_ly;

	/** Creates a Region of Interest initialized to (0,0,0,0) */
	public ROI() {
		m_ux = m_uy = m_lx = m_ly = 0;
	}

	/**
	 * Creates a Region of Interest initialized to the box defined by (x1,y1,x2,y2). The parameters
	 * will be sorted so that ux(),uy() and lx(),ly() return the x,y coordinates of the upper left
	 * (smaller x,y values) corner and lower right (larger x,y values) corner of the box,
	 * respectively.
	 * 
	 * @param x1 x coordinate of one corner of the box
	 * @param y1 y coordinate of one corner of the box
	 * @param x2 x coordinate of opposite corner of the box
	 * @param y2 y coordinate of opposite corner of the box
	 */
	public ROI(int x1, int y1, int x2, int y2) {
		setROISorted(x1, y1, x2, y2);
	}

	/** Auxiliary function for constructor. */
	private void setROISorted(int x1, int y1, int x2, int y2) {
		if (x1 > x2) {
			m_ux = x2;
			m_lx = x1;
		} else {
			m_ux = x1;
			m_lx = x2;
		}

		if (y1 > y2) {
			m_uy = y2;
			m_ly = y1;
		} else {
			m_uy = y1;
			m_ly = y2;
		}
	}

	/** Returns the upper y corner of the Region of Interest */
	public int uy() {
		return m_uy;
	}

	/** Returns the upper x corner of the Region of Interest */
	public int ux() {
		return m_ux;
	}

	/** Returns the lower y corner of the Region of Interest */
	public int ly() {
		return m_ly;
	}

	/** Returns the lower x corner of the Region of Interest */
	public int lx() {
		return m_lx;
	}

	/** Returns the width of the Region of Interest */
	public int X() {
		return m_lx - m_ux + 1;
	}

	/** Returns the height of the Region of Interest */
	public int Y() {
		return m_ly - m_uy + 1;
	}

	/**
	 * Sets the Region of Interest. The parameters will be sorted so that ux(),uy() and lx(),ly()
	 * return the x,y coordinates of the upper left (smaller x,y values) corner and lower right
	 * (larger x,y values) corner of the box, respectively.
	 * 
	 * @param x1 x coordinate of one corner of the box
	 * @param y1 y coordinate of one corner of the box
	 * @param x2 x coordinate of opposite corner of the box
	 * @param y2 y coordinate of opposite corner of the box
	 */
	public void setROI(int x1, int y1, int x2, int y2) {
		setROISorted(x1, y1, x2, y2);
	}
}
