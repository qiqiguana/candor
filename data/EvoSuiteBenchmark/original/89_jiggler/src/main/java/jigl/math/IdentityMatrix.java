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
 * Last Modified: 22 October 1997
 * <P>
 * This class allows a programmer to create a (square) identity matrix of arbitrary order. See
 * {@link <a href="http://mathworld.wolfram.com/IdentityMatrix.html">Identity Matrix</a>} for more
 * information about it.
 */
public class IdentityMatrix extends Matrix {
	/**
	 * This constructor creates an identity matrix of order <CODE>n</CODE>.
	 * <P>
	 */
	public IdentityMatrix(int n) {
		super(n, n);

		for (int i = 0; i < n; ++i)
			mem[i][i] = 1.0;
	}

	/**
	 * Tests IdentityMatrix class: creating an IdentityMatrix object of order 4, then multiplying it
	 * by 3 and getting a new matrix, finally displaying the two matrices.
	 */
	public static void main(String[] args) {
		IdentityMatrix I = new IdentityMatrix(4);

		Matrix A = I.mult(3);

		System.out.println(I);
		System.out.println(A);
	}
}
