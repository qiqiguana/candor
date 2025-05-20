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
 * Performs rotate warp about a specified point. To use this on an image, you
 * need convert an image object to two one-dimension array first.
 * <p>
 * Another way to solve rotation is to get a general transform matrix and use
 * the apply() method of Mapper on the whole image directly.
 * 
 * TODO: share code with RotateTransform?
 */
public class RotateAboutTransform implements PointMapper {
	/** The rotation angle. Its direction is clockwise. */
	// private float angle=0;

	/** Value of <code>cos(angle)</code> */
	private float cosang = 0;

	/** Value of <code>sin(angle)</code> */
	private float sinang = 0;

	/** The x coordinate of the specified point */
	private float xoffset = 0;

	/** The y coordinate of the specified point */
	private float yoffset = 0;

	/**
	 * constructor.
	 * 
	 * @param ang
	 *            angle (in radians) to rotate
	 * @param x
	 *            x coordinate of point to rotate about
	 * @param y
	 *            y coordinate of point to rotate about
	 */
	public RotateAboutTransform(float ang, float x, float y) {
		xoffset = x;
		yoffset = y;
		// angle = ang;
		cosang = (float) Math.cos(ang);
		sinang = (float) Math.sin(ang);
	}

	/**
	 * Performs the Rotation Transformation about an arbitrary point.
	 * <code>x[], y[]</code> are changed.
	 * 
	 * @param x
	 *            an array containing the x coordinates
	 * @param y
	 *            an array containing the y coordinates
	 */
	public void transform(float[] x, float[] y) throws IllegalArgumentException {
		transform(x, y, 0, x.length);
	}

	/**
	 * Performs the inverse of the Rotation Transformation about an arbitrary
	 * point. <code>x[], y[]</code> are changed.
	 * 
	 * @param x
	 *            an array containing the x coordinates
	 * @param y
	 *            an array containing the y coordinates
	 */
	public void inverseTransform(float[] x, float[] y)
			throws IllegalArgumentException {
		inverseTransform(x, y, 0, x.length);
	}

	/**
	 * Performs the Rotation Transformation about an arbitrary point.
	 * <code>x[], y[]</code> are changed.
	 * 
	 * @param x
	 *            an array containing the x coordinates
	 * @param y
	 *            an array containing the y coordinates
	 * @param offset
	 *            the starting point of the array
	 * @param count
	 *            the number of points from the <i>offset</i> to apply the
	 *            transformation
	 */
	public void transform(float[] x, float[] y, int offset, int count)
			throws IllegalArgumentException {
		if (x.length != y.length)
			throw new IllegalArgumentException();

		float a = 0;
		float b = 0;
		final int lastIndex = Math.min(x.length, offset + count);

		for (int i = offset; i < lastIndex; ++i) {
			a = x[i];
			b = y[i];
			x[i] = (a - xoffset) * cosang + (b - yoffset) * sinang + xoffset;
			y[i] = -((a - xoffset) * sinang) + (b - yoffset) * cosang + yoffset;
		}
	}

	/**
	 * Performs the inverse of the Rotation Transformation about an arbitrary
	 * point. <code>x[], y[]</code> are changed.
	 * 
	 * @param x
	 *            an array containing the x coordinates
	 * @param y
	 *            an array containing the y coordinates
	 * @param offset
	 *            the starting point of the array
	 * @param count
	 *            the number of points from the <i>offset</i> to apply the
	 *            transformation
	 */
	public void inverseTransform(float[] x, float[] y, int offset, int count)
			throws IllegalArgumentException {
		if (x.length != y.length)
			throw new IllegalArgumentException();

		float a = 0;
		float b = 0;
		final int lastIndex = Math.min(x.length, offset + count);

		for (int i = offset; i < lastIndex; ++i) {
			a = x[i];
			b = y[i];
			x[i] = (a - xoffset) * cosang + (b - yoffset) * -sinang + xoffset;
			y[i] = (a - xoffset) * sinang + (b - yoffset) * cosang + yoffset;
		}
	}
}
