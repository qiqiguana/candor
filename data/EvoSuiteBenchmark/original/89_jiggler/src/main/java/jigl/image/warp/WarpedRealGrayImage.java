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

import jigl.image.types.InterpolatedRealGrayImage;

/**
 * Gets values for an InterpolatedRealGrayImage through a PointMapper. To use it on a RealGrayImage
 * object, first creating a InterpolatedRealGrayGrayImage object, then creating a PointMapper object
 * using your transformation matrix, finally you can get the color value for each pixel of the new
 * image (after transforming) through {@link #get(float,float) GET} in this class.
 */
public class WarpedRealGrayImage implements WarpedImage {

	private InterpolatedRealGrayImage image = null;
	private PointMapper pointmapper = null;

	private float defColor;

	/*Creates a WarpedGrayImage*/
	public WarpedRealGrayImage(InterpolatedRealGrayImage im, PointMapper pm) {

		image = im;
		pointmapper = pm;
		defColor = 0;

	}

	/**
	 * Sets the color returned by <CODE>get(float,float)</CODE> when the coordinates are out of the
	 * image bounds
	 * 
	 * @param color desired color
	 */
	public void setDefaultColor(float color) {
		defColor = color;
	}

	/** Gets the value at x, y through the waor */
	public float get(float x, float y) {

		float[] x1 = new float[1];
		float[] y1 = new float[1];
		x1[0] = x;
		y1[0] = y;
		try {
			pointmapper.inverseTransform(x1, y1);
			return image.interp(x1[0], y1[0]);
		} catch (Exception e) {
			return defColor;
		}

	}

}
