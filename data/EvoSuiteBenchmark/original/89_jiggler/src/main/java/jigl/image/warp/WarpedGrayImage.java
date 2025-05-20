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

import jigl.image.types.InterpolatedGrayImage;

/**
 * Gets values for an InterpolatedGrayImage through a PointMapper. To use it on a GrayImage object,
 * first creating a InterpolatedGrayImage object, then creating a PointMapper object using your
 * transformation matrix, finally you can get the color value for each pixel of the new image (after
 * transforming) through {@link #get(float,float) GET} in this class.
 */
public class WarpedGrayImage implements WarpedImage {

	private InterpolatedGrayImage image = null;
	private PointMapper pointmapper = null;
	private int defColor = 0;

	/*Creates a WarpedGrayImage*/
	public WarpedGrayImage(InterpolatedGrayImage im, PointMapper pm) {
		image = im;
		pointmapper = pm;
	}

	/**
	 * Sets the color returned by <CODE>get(float,float)</CODE> when the coordinates are out of the
	 * image bounds
	 * 
	 * @param color the desired color
	 */
	public void setDefaultColor(int color) {
		defColor = color;
	}

	/** Gets the value at x, y through the waor */
	public int get(float x, float y) {

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
