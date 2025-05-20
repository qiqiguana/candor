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
 * An InterpolatedRealColorImage is a RealColorImage that can be accessed at other than integral
 * locations. Supports nearest neighbor, bilinear, and bicubic interpolation.
 */
public class InterpolatedRealColorImage extends RealColorImage implements InterpolatedImage<Float[]> {

	/** The interpolation method that will be used. Default= NEIGHBOR */
	protected InterpolationMethod interpolationMethod = InterpolationMethod.NEIGHBOR;

	/*
	constructors
	*/

	/** Creates an empty color image */
	public InterpolatedRealColorImage() {
		X = 0;
		Y = 0;
		planes[0] = null;
		planes[1] = null;
		planes[2] = null;
	}

	public InterpolatedRealColorImage(RealColorImage img) {
		super(img);
		planes[0] = new InterpolatedRealGrayImage(img.plane(0));
		planes[1] = new InterpolatedRealGrayImage(img.plane(1));
		planes[2] = new InterpolatedRealGrayImage(img.plane(2));
	}

	/** Creates a color image of dimension x,y */
	public InterpolatedRealColorImage(int x, int y) {
		X = x;
		Y = y;
		planes[0] = new InterpolatedRealGrayImage(X, Y);
		planes[1] = new InterpolatedRealGrayImage(X, Y);
		planes[2] = new InterpolatedRealGrayImage(X, Y);
	}

	/** Creates a color image that is a shallow copy of img */
	public InterpolatedRealColorImage(InterpolatedRealColorImage img) {
		X = img.X();
		Y = img.Y();
		interpolationMethod = img.getInterpolationMethod();
		planes[0] = img.plane(0);
		planes[1] = img.plane(1);
		planes[2] = img.plane(2);
	}

	/** deep copy */
	public InterpolatedRealColorImage copy() {
		InterpolatedRealColorImage c = new InterpolatedRealColorImage(X, Y);
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

	/*
	Interpolation routines

	three different methods are available: <P>
	replication, linear interpolation and cubic interpolation
	*/

	/** Returns the interpolation method */
	public InterpolationMethod getInterpolationMethod() {
		return interpolationMethod;
	}

	/** Set the interpolation method */
	public void setInterpolationMethod(InterpolationMethod m) {
		interpolationMethod = m;
		((InterpolatedRealGrayImage) planes[0]).setInterpolationMethod(m);
		((InterpolatedRealGrayImage) planes[1]).setInterpolationMethod(m);
		((InterpolatedRealGrayImage) planes[2]).setInterpolationMethod(m);
	}

	/** Returns the interpolated triplet at (x,y). */
	public Float[] interp(float x, float y) {
		Float[] color = new Float[3];
		color[0] = ((InterpolatedRealGrayImage) planes[0]).interp(x, y);
		color[1] = ((InterpolatedRealGrayImage) planes[1]).interp(x, y);
		color[2] = ((InterpolatedRealGrayImage) planes[2]).interp(x, y);
		return color;
	}

	/** Adds the triplet to the surrounding area. */
	public void accum(float x, float y, float[] value) {
		((InterpolatedRealGrayImage) planes[0]).accum(x, y, value[0]);
		((InterpolatedRealGrayImage) planes[1]).accum(x, y, value[1]);
		((InterpolatedRealGrayImage) planes[2]).accum(x, y, value[2]);
	}

	/** Spread the triplet in the surrounding area. */
	public void splat(float x, float y, float[] value) {
		((InterpolatedRealGrayImage) planes[0]).splat(x, y, value[0]);
		((InterpolatedRealGrayImage) planes[1]).splat(x, y, value[1]);
		((InterpolatedRealGrayImage) planes[2]).splat(x, y, value[2]);
	}

}
