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
package jigl.math;

/**
 * Written By: Michael Reese Bastian
 * <P>
 * Last Modified: 24 October 1997
 * <P>
 */
public class ScaleMatrix extends Matrix {
	public ScaleMatrix() {
	}

	/**
	 * This constructor creates a diagonal matrix with diagonal entries from <CODE>d</CODE>.
	 * <P>
	 */
	public ScaleMatrix(double[] d) {
		super(d.length, d.length);

		for (int i = 0; i < d.length; ++i)
			mem[i][i] = d[i];
	}
}
