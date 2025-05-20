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

/** Allows for composition of transformations on a mapped image. */
public class Composite implements PointMapper {
	/** The first PointerMapper object */
	private PointMapper pointMapper1 = null;
	/** The second PointerMapper object */
	private PointMapper pointMapper2 = null;

	/**
	 * Create a composite of two PointMappers.
	 * 
	 * @param pm1 the first PointMapper to be applied
	 * @param pm2 the pointMapper to applied last.
	 */
	public Composite(PointMapper pm1, PointMapper pm2) {
		pointMapper1 = pm1;
		pointMapper2 = pm2;
	}

	/**
	 * Performs the Composite transforms. First <code>pointMapper1</code> applies a transform on it,
	 * then <code>pointMapper2</code> applies a transform on it.
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 */
	public void transform(float[] x, float[] y) throws IllegalArgumentException {
		pointMapper1.transform(x, y);
		pointMapper2.transform(x, y);
	}

	/**
	 * Performs the composite transforms.
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 * @param offset the starting point of the array
	 * @param count the number of points from the <i>offset</i> to apply the transformation
	 */
	public void transform(float[] x, float[] y, int offset, int count)
			throws IllegalArgumentException {
		pointMapper1.transform(x, y, offset, count);
		pointMapper2.transform(x, y, offset, count);
	}

	/**
	 * Performs the inverse of the composite transforms.
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 */
	public void inverseTransform(float[] x, float[] y) throws IllegalArgumentException {
		pointMapper2.inverseTransform(x, y);
		pointMapper1.inverseTransform(x, y);
	}

	/**
	 * Performs the inverse of the composite transforms.
	 * 
	 * @param x an array containing the x coordinates
	 * @param y an array containing the y coordinates
	 * @param offset the starting point of the array
	 * @param count the number of points from the <i>offset</i> to apply the transformation
	 */
	public void inverseTransform(float[] x, float[] y, int offset, int count)
			throws IllegalArgumentException {
		pointMapper2.inverseTransform(x, y, offset, count);
		pointMapper1.inverseTransform(x, y, offset, count);
	}
}
