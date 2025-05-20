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
public class ProjectionMatrix extends Matrix {
	/**
	 * This construction creates a projection matrix with <CODE>n</CODE> columns and the same number
	 * of rows as the length of <CODE>p</CODE>. Should satisfy <code>0 <= p[k] < n</code>.
	 */
	public ProjectionMatrix(int n, int[] p) {
		super(p.length, n);

		for (int i = 0; i < p.length; ++i)
			mem[i][p[i]] = 1.0;
	}
}
