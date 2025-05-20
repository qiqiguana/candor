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
package jigl.image.types;

import jigl.image.InterpolationMethod;

/**
 * InterpolatedColorImage is a ColorImage that can be accessed at other than integral locations.
 * Supports nearest neighbor, bilinear, and bicubic interpolation.
 */

public class InterpolatedColorImage extends ColorImage implements InterpolatedImage<Integer[]> {
	/** The interpolation method that will be used. Default = NEIGHBOR */
	protected InterpolationMethod interpolationMethod = InterpolationMethod.NEIGHBOR;

	/*
	constructors
	*/

	/** Creates an empty color image. */
	public InterpolatedColorImage() {
		super(0, 0);
		planes[0] = null;
		planes[1] = null;
		planes[2] = null;
	}

	/** Creates a color image of dimension x,y. */
	public InterpolatedColorImage(final int x, final int y) {
		super(x, y);
		planes[0] = new InterpolatedGrayImage(X, Y);
		planes[1] = new InterpolatedGrayImage(X, Y);
		planes[2] = new InterpolatedGrayImage(X, Y);
	}

	/** Creates a color image that is a shallow copy of <i>img</i>. */
	public InterpolatedColorImage(ColorImage img) {
		super(img);
		planes[0] = new InterpolatedGrayImage(img.plane(0));
		planes[1] = new InterpolatedGrayImage(img.plane(1));
		planes[2] = new InterpolatedGrayImage(img.plane(2));
	}

	/** Creates a color image that is a shallow copy of <i>img</i>. */
	public InterpolatedColorImage(InterpolatedColorImage img) {
		super(img);
		interpolationMethod = img.getInterpolationMethod();
	}

	/** deep copy */
	public InterpolatedColorImage copy() {
		InterpolatedColorImage c = new InterpolatedColorImage(X, Y);
		c.setInterpolationMethod(interpolationMethod);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				c.planes[0].set(x, y, planes[0].get(x, y));
				c.planes[1].set(x, y, planes[1].get(x, y));
				c.planes[2].set(x, y, planes[2].get(x, y));
			}
		}
		return c;
	}

	/** Sets the <i>p</i> plane to image <i>pl</i>: a shallow copy */
	public void setPlane(int p, GrayImage pl) {
		planes[p] = new InterpolatedGrayImage(pl);
	}

	/*
	interpolation routines

	three different methods are available: <P>
	replication, linear interpolation and cubic interpolation
	*/

	/** the current interpolation method */
	public InterpolationMethod getInterpolationMethod() {
		return interpolationMethod;
	}

	/**
	 * Set the interpolation method
	 * <P>
	 * There are three possibilities: Nearest Neighbor, Linear or Cubic.
	 */
	public void setInterpolationMethod(InterpolationMethod m) {
		interpolationMethod = m;
		((InterpolatedGrayImage) planes[0]).setInterpolationMethod(m);
		((InterpolatedGrayImage) planes[1]).setInterpolationMethod(m);
		((InterpolatedGrayImage) planes[2]).setInterpolationMethod(m);
	}

	/** Returns the interpolated triplet at (x,0). */
	public int[] interp(double x) {
		int[] color = new int[3];
		color[0] = ((InterpolatedGrayImage) planes[0]).interp(x);
		color[1] = ((InterpolatedGrayImage) planes[1]).interp(x);
		color[2] = ((InterpolatedGrayImage) planes[2]).interp(x);
		return color;
	}

	/** Returns the interpolated triplet at (x,y) */
	public Integer[] interp(float x, float y) {
		Integer[] color = new Integer[3];
		color[0] = ((InterpolatedGrayImage) planes[0]).interp(x, y);
		color[1] = ((InterpolatedGrayImage) planes[1]).interp(x, y);
		color[2] = ((InterpolatedGrayImage) planes[2]).interp(x, y);
		return color;
	}

	/** Adds the triplet to the surrounding area. */
	public void accum(double x, int[] value) {
		((InterpolatedGrayImage) planes[0]).accum(x, value[0]);
		((InterpolatedGrayImage) planes[1]).accum(x, value[1]);
		((InterpolatedGrayImage) planes[2]).accum(x, value[2]);
	}

	/** Adds the triplet to the surrounding area. */
	public void accum(double x, double y, int[] value) {
		((InterpolatedGrayImage) planes[0]).accum(x, y, value[0]);
		((InterpolatedGrayImage) planes[1]).accum(x, y, value[1]);
		((InterpolatedGrayImage) planes[2]).accum(x, y, value[2]);
	}

	/** Spread the triplet in the surrounding area. */
	public void splat(double x, int[] value) {
		((InterpolatedGrayImage) planes[0]).splat(x, value[0]);
		((InterpolatedGrayImage) planes[1]).splat(x, value[1]);
		((InterpolatedGrayImage) planes[2]).splat(x, value[2]);
	}

	/** Spread the triplet in the surrounding area. */
	public void splat(double x, double y, int[] value) {
		((InterpolatedGrayImage) planes[0]).splat(x, y, value[0]);
		((InterpolatedGrayImage) planes[1]).splat(x, y, value[1]);
		((InterpolatedGrayImage) planes[2]).splat(x, y, value[2]);
	}

}
