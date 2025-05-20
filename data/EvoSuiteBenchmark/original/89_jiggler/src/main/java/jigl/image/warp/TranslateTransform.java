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
 * Performs Translate Warp on a MappedImage.
 * <p>
 * To use this on an image, you need convert an image object to two one-dimention array first.
 */
public class TranslateTransform implements PointMapper {
	/** The amount of shifting in the x direction */
	private float xoffset = 0;
	/** The amount of shift in the y direction */
	private float yoffset = 0;

	/** Creates a Translate with an offset of x and y */
	public TranslateTransform(float x, float y) {
		xoffset = x;
		yoffset = y;
	}

	/**
	 * Performs the Translation Transformation
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 */
	public void transform(float[] x, float[] y) throws IllegalArgumentException {
		transform(x, y, 0, x.length);
	}

	/**
	 * Performs the inverse of the Translation Transformation
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 */
	public void inverseTransform(float[] x, float[] y) throws IllegalArgumentException {
		inverseTransform(x, y, 0, x.length);
	}

	/**
	 * Performs the Translation Transformation
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
			x[i] += xoffset;
			y[i] += yoffset;
		}
	}

	/**
	 * Performs the inverse of the Translation Transformation
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
		final int lastIndex = Math.min(x.length, offset + count);

		for (int i = offset; i < lastIndex; ++i) {
			x[i] -= xoffset;
			y[i] -= yoffset;
		}
	}
}
