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

/**Performs perspective warp on a mapped image. It can be seen as a special
case of class AffineTransform.*/
public class PerspectiveTransform implements PointMapper {
  /**Perspective matrix.*/
  protected Matrix matrix;
  /**Reverse perspective matrix.*/
  protected Matrix inverse=null;
  /**Flag*/
  protected boolean applied_once=false;

  /** Creates a Perspective Transform from a two dimensional array (transform matrix).*/
  public PerspectiveTransform(double[][] data) {
    matrix = new Matrix(data);
  }

	/** Creates a Perspective Transform from a Matrix*/
  public PerspectiveTransform(Matrix m) {
    matrix = m;
  }

	/** Performs the Perspective Transformation
	@param x an array containing the x coordinates
	@param y an array containing the y coordinates
	*/
  public void transform(float[] x, float[] y) throws IllegalArgumentException {
		transform(x,y,0,x.length);
  }

	/** Performs the Perspective Transformation
	@param x an array containing the x coordinates
	@param y an array containing the y coordinates
	@param offset the starting point of the array
	@param count the number of points from the <i>offset</i> to apply the transformation*/
  public void transform(float[] x, float[] y, int offset, int count) throws IllegalArgumentException {

		float u, v, w;

		if (x.length != y.length)
			throw new IllegalArgumentException();

		final int lastIndex = Math.min(x.length, offset + count);

		for (int i = offset; i < lastIndex; ++i) {

			u = (float)(matrix.get(0, 0) * x[i] + matrix.get(1, 0) * y[i] + matrix.get(2, 0));
			v = (float)(matrix.get(0, 1) * x[i] + matrix.get(1, 1) * y[i] + matrix.get(2, 1));
			w = (float)(matrix.get(0, 2) * x[i] + matrix.get(1, 2) * y[i] + matrix.get(2, 2));

			x[i] = u / w;
			y[i] = v / w;
		}
  }

	/** Performs the inverse of the Perspective Transformation
	@param x an array containing the x coordinates
	@param y an array containing the y coordinates
	*/
	public void inverseTransform(float[] x, float[] y) throws IllegalArgumentException {
		inverseTransform(x,y,0,x.length);
  }

	/** Performs the inverse of the Perspective Transformation
	@param x an array containing the x coordinates
	@param y an array containing the y coordinates
	@param offset the starting point of the array
	@param count the number of points from the <i>offset</i> to apply the transformation*/
	public void inverseTransform(float[] x, float[] y, int offset, int count) throws IllegalArgumentException {
		if (x.length != y.length)
			throw new IllegalArgumentException();

		float u, v, w;

		if (applied_once==false){
			IdentityMatrix id=new IdentityMatrix(matrix.nColumns());
			Matrix tempMatrix=new Matrix(matrix);
			inverse=tempMatrix.gaussj(id);
			applied_once=true;
		}

    		final int lastIndex = Math.min(x.length, offset + count);

		for (int i = offset; i < lastIndex; ++i) {

			u = (float)(matrix.get(0, 0) * x[i] + matrix.get(1, 0) * y[i] + matrix.get(2, 0));
			v = (float)(matrix.get(0, 1) * x[i] + matrix.get(1, 1) * y[i] + matrix.get(2, 1));
			w = (float)(matrix.get(0, 2) * x[i] + matrix.get(1, 2) * y[i] + matrix.get(2, 2));

			x[i] = u / w;
			y[i] = v / w;
		}
  }
}
