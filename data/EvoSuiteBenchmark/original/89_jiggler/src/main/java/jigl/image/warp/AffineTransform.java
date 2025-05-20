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
package jigl.image.warp;

import jigl.math.IdentityMatrix;
import jigl.math.Matrix;

/** Performs any affine warp on a mapped image. The <code>matrix</code> keeps constant. */
public class AffineTransform implements PointMapper {
	/** Transfrom matrix */
	protected Matrix matrix;
	/** Reverse transform matrix */
	protected Matrix inverse = null;
	/** Flag for whether the reverse matrix has been calculated. True: having calculated. */
	protected boolean applied_once = false;

	/** Creates an Affine Transform from a two-dimensional array (transform matrix) */
	public AffineTransform(double[][] data) {
		matrix = new Matrix(data);
	}

	/** Creates an Affine Transform from a matrix */
	public AffineTransform(Matrix m) {
		matrix = m;
	}

	/**
	 * Performs the Affine Transformation. Should satisfy <code>x.length = y.length</code>
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 */
	public void transform(float[] x, float[] y) throws IllegalArgumentException {
		transform(x, y, 0, x.length);
	}

	/**
	 * Performs the Affine Transformation
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 * @param offset the starting point of the array
	 * @param count the number of points from the <i>offset</i> to apply the transformation
	 */
	public void transform(float[] x, float[] y, int offset, int count)
			throws IllegalArgumentException {
		if (x.length != y.length)
			throw new IllegalArgumentException();

		final int lastIndex = Math.min(x.length, offset + count);

		for (int i = offset; i < lastIndex; ++i) {
			double u, v;
			//matrix multiplication
			u = matrix.get(0, 0) * x[i] + matrix.get(1, 0) * y[i] + matrix.get(2, 0);
			v = matrix.get(0, 1) * x[i] + matrix.get(1, 1) * y[i] + matrix.get(2, 1);

			x[i] = (float) u;
			y[i] = (float) v;
		}
	}

	/**
	 * Performs the inverse of the Affine Transformation
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 */
	public void inverseTransform(float[] x, float[] y) {
		inverseTransform(x, y, 0, x.length);
	}

	/**
	 * Performs the inverse of the Affine Transformation.
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 * @param offset the starting point of the array
	 * @param count the number of points from the <i>offset</i> to apply the transformation
	 */
	public void inverseTransform(float[] x, float[] y, int offset, int count) {
		if (x.length != y.length)
			throw new IllegalArgumentException();

		if (applied_once == false) {
			IdentityMatrix id = new IdentityMatrix(matrix.nColumns());
			Matrix tempMatrix = new Matrix(matrix);
			inverse = tempMatrix.gaussj(id);//find the reverse matrix
			applied_once = true;
		}

		final int lastIndex = Math.min(x.length, offset + count);

		for (int i = offset; i < lastIndex; ++i) {
			double u, v;

			u = inverse.get(0, 0) * x[i] * inverse.get(1, 0) * y[i] + inverse.get(2, 0);
			v = inverse.get(0, 1) * x[i] * inverse.get(1, 1) * y[i] + inverse.get(2, 1);

			x[i] = (float) u;
			y[i] = (float) v;
		}

	}
}
