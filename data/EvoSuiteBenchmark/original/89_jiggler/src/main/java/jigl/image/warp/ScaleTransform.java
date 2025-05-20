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

/**
 * Performs Scale Warp on a MappedImage. To use this on an image, you need convert an image object
 * to two one-dimention array first.
 */
public class ScaleTransform implements PointMapper {

	/** Scale factor in x direction */
	private float xscale = 0;
	/** Scale factor in y direction. */
	private float yscale = 0;

	/** Creates a Scale with scaling factor of x and y */
	public ScaleTransform(float x, float y) {
		xscale = x;
		yscale = y;
	}

	/** Creates a Scale with scaling factor of s for both x and y directions */
	public ScaleTransform(float s) {
		xscale = s;
		yscale = s;
	}

	/**
	 * Performs the Scale Transformation
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 */
	public void transform(float[] x, float[] y) throws IllegalArgumentException {
		transform(x, y, 0, x.length);
	}

	/**
	 * Performs the inverse of the Scale Transformation
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 */
	public void inverseTransform(float[] x, float[] y) throws IllegalArgumentException {
		inverseTransform(x, y, 0, x.length);
	}

	/**
	 * Performs the Scale Transformation
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
			x[i] *= xscale;
			y[i] *= yscale;
		}
	}

	/**
	 * Performs the inverse of the Scale Transformation
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 * @param offset the starting point of the array
	 * @param count the number of points from the <i>offset</i> to apply the transformation
	 */
	public void inverseTransform(float[] x, float[] y, int offset, int count)
			throws IllegalArgumentException {
		if (x.length != y.length)
			throw new IllegalArgumentException();
		//		float a=0;
		//		float b=0;	
		final int lastIndex = Math.min(x.length, offset + count);

		for (int i = offset; i < lastIndex; ++i) {
			x[i] /= xscale;
			y[i] /= yscale;
		}
	}
}
