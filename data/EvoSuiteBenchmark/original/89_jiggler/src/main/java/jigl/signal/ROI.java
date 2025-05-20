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
package jigl.signal;

/**
 * The ROI class is the Region of Interest of a JIGL Signal. Unlike {@link jigl.image.ROI}, this ROI
 * class is of one-dimension.
 */
public class ROI {
	/** Lower bound */
	protected int m_lbound = 0;
	/** Upper bound */
	protected int m_ubound = 0;

	/** Creates an empty Region of Interest. */
	public ROI() {
		m_lbound = 0;
		m_ubound = 0;
	}

	/**
	 * Creates a Region of Interest initialized to [lower .. upper]. upper and lower are sorted so
	 * that lower <= upper.
	 * 
	 * @param lower lower bound of the Region of Interest
	 * @param upper upper bound of the Region of Interest
	 */
	public ROI(int lower, int upper) {
		if (lower <= upper) {
			m_lbound = lower;
			m_ubound = upper;
		} else {
			m_lbound = upper;
			m_ubound = lower;
		}
	}

	/** Returns the lower bound of the Region of Interest */
	public int lbound() {
		return m_lbound;
	}

	/** Returns the upper bound of the Region of Interest */
	public int ubound() {
		return m_ubound;
	}

	/** Returns the length of the Region of Interest */
	public int length() {
		return m_ubound - m_lbound + 1;
	}

	/**
	 * Sets the bounds of the Region of Interest
	 * 
	 * @param lower lower bound of the Region of Interest
	 * @param upper upper bound of the Region of Interest
	 */
	public void setROI(int lower, int upper) {
		if (lower <= upper) {
			m_lbound = lower;
			m_ubound = upper;
		} else {
			m_lbound = upper;
			m_ubound = lower;
		}
	}
}
