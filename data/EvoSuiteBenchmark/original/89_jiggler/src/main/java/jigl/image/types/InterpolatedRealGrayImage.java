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

import jigl.image.Image;
import jigl.image.InterpolationMethod;

/**
 * An InterpolatedRealGrayImage is a RealGrayImage that can be accessed at other than integral
 * locations. Supports nearest neighbor, bilinear, and bicubic interpolation.
 */
public class InterpolatedRealGrayImage extends RealGrayImage implements InterpolatedImage<Float> {

	protected InterpolationMethod interpolationMethod = InterpolationMethod.NEIGHBOR;

	/*
	 * constructors
	 */
	/** Creates an empty InterpolatedRealGrayImage. */
	public InterpolatedRealGrayImage() {
		super();
	}

	/** Creates an InterpolatedRealGrayImage from a RealGrayImage. */
	public InterpolatedRealGrayImage(RealGrayImage img) {
		super(img);
	}

	/** Create an InterpolatedGrayImage with width x and height y. */
	public InterpolatedRealGrayImage(int x, int y) {
		super(x, y);
	}

	/** Deep copy */
	public InterpolatedRealGrayImage(InterpolatedRealGrayImage img) {
		super(img);
		interpolationMethod = img.getInterpolationMethod();
	}

	/** Create an InterpolatedGrayImage from a Java image. */
	public InterpolatedRealGrayImage(java.awt.Image img) {
		super(img);
	}

	/** Deep copy */
	public InterpolatedRealGrayImage copy() {
		InterpolatedRealGrayImage g = new InterpolatedRealGrayImage(X, Y);
		g.setInterpolationMethod(interpolationMethod);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				g.data[y][x] = data[y][x];
			}
		}
		return g;
	}

	/*
	interpolation routines

	three different methods are available: <P>
	replication, linear interpolation and cubic interpolation
	*/
	/** Returns the interpolation method used. */
	public InterpolationMethod getInterpolationMethod() {
		return interpolationMethod;
	}

	/**
	 * Sets the interpolation method to be used. The edge cases of linear and cubic interpolation
	 * are treated differently to avoid ArrayIndexOutOfBoundsExceptions. Cubic uses linear
	 * interpolation when it is nest to the edge, and linear uses single-linear interpolation when
	 * it is right on the edge.
	 */
	public void setInterpolationMethod(InterpolationMethod m) {
		interpolationMethod = m;
	}

	/** Returns the interpolated value at (x,y). */
	public Float interp(float x, float y) {
		int intx, inty;
		float fracx, fracy;
		switch (interpolationMethod) {
		case NEIGHBOR:
			intx = (int) Math.round(x);
			inty = (int) Math.round(y);
			return (float) data[inty][intx];
		case LINEAR:
			intx = (int) x;
			inty = (int) y;
			fracx = x - intx;
			fracy = y - inty;

			if (intx >= data[0].length - 1 || inty >= data.length - 1)//edge case
			{
				if (intx >= data[0].length - 1 && inty >= data.length - 1) {
					return (float) data[inty][intx];//corner
				}
				if (intx >= data[0].length - 1) {
					return (float) (data[inty][intx] * (1 - fracy) + data[inty + 1][intx] * fracy);
				} else if (inty >= data.length - 1) {
					return (float) (data[inty][intx] * (1 - fracx) + data[inty][intx + 1] * fracx);
				} else
					throw new ArrayIndexOutOfBoundsException();
			}

			return (float) (data[inty][intx] * (1 - fracx) * (1 - fracy) + data[inty][intx + 1]
					* fracx * (1 - fracy) + data[inty + 1][intx] * (1 - fracx) * fracy + data[inty + 1][intx + 1]
					* fracx * fracy);
		case CUBIC:
			intx = (int) x;
			inty = (int) y;
			fracx = x - intx;
			fracy = y - inty;

			//if the point is on the border and would cause an ArrayIndexOutOfBoundsException
			//use linear interpolation instead
			if (intx < 1 || inty < 1 || intx > data[0].length - 3 || inty > data.length - 3) {
				if (intx >= data[0].length - 1 || inty >= data.length - 1)//edge case
				{
					if (intx >= data[0].length - 1 && inty >= data.length - 1) {
						return (float) data[inty][intx];//corner
					}
					if (intx >= data[0].length - 1) {
						return (float) (data[inty][intx] * (1 - fracy) + data[inty + 1][intx]
								* fracy);
					} else if (inty >= data.length - 1) {
						return (float) (data[inty][intx] * (1 - fracx) + data[inty][intx + 1]
								* fracx);
					} else
						throw new ArrayIndexOutOfBoundsException();
				}

				return (float) (data[inty][intx] * (1 - fracx) * (1 - fracy) + data[inty][intx + 1]
						* fracx * (1 - fracy) + data[inty + 1][intx] * (1 - fracx) * fracy + data[inty + 1][intx + 1]
						* fracx * fracy);
			}

			float y0,
			y1,
			y2,
			y3;
			float x0,
			x1,
			x2,
			x3;

			float t = fracx;

			y0 = data[inty - 1][intx - 1];
			y1 = data[inty - 1][intx];
			y2 = data[inty - 1][intx + 1];
			y3 = data[inty - 1][intx + 2];

			x0 = CatMull(t, y0, y1, y2, y3);

			y0 = data[inty][intx - 1];
			y1 = data[inty][intx];
			y2 = data[inty][intx + 1];
			y3 = data[inty][intx + 2];

			x1 = CatMull(t, y0, y1, y2, y3);

			y0 = data[inty + 1][intx - 1];
			y1 = data[inty + 1][intx];
			y2 = data[inty + 1][intx + 1];
			y3 = data[inty + 1][intx + 2];

			x2 = CatMull(t, y0, y1, y2, y3);

			y0 = data[inty + 2][intx - 1];
			y1 = data[inty + 2][intx];
			y2 = data[inty + 2][intx + 1];
			y3 = data[inty + 2][intx + 2];

			x3 = CatMull(t, y0, y1, y2, y3);

			t = fracy;

			return CatMull(t, x0, x1, x2, x3);
		}
		return 0f;
	}

	/** Add <i>value</i> to the surrounding area of (x,y). */
	public void accum(float x, float y, float value) {
		int intx, inty;
		float fracx, fracy;
		switch (interpolationMethod) {
		case NEIGHBOR:
			intx = (int) Math.round(x);
			inty = (int) Math.round(y);
			data[inty][intx] += (float) value;
		case CUBIC:
		case LINEAR:
			intx = (int) x;
			inty = (int) y;
			fracx = x - intx;
			fracy = y - inty;
			try {
				data[inty][intx] += (float) (value * (1 - fracx) * (1 - fracy));
				data[inty][intx + 1] += (float) (value * fracx * (1 - fracy));
				data[inty + 1][intx] += (float) (value * (1 - fracx) * fracy);
				data[inty + 1][intx + 1] += (float) (value * (1 - fracx) * (1 - fracy));
			} catch (ArrayIndexOutOfBoundsException e) {
				data[inty][intx] += (float) (value * (1 - fracx) * (1 - fracy));
				try {
					data[inty][intx + 1] += (float) (value * fracx * (1 - fracy));
				} catch (ArrayIndexOutOfBoundsException ee) {
				}
				try {
					data[inty + 1][intx] += (float) (value * (1 - fracx) * fracy);
				} catch (ArrayIndexOutOfBoundsException ee) {
				}
			}
			break;
		}
	}

	/** Spread <i>value</i> to the surrounding area of (x,y). */
	public void splat(float x, float y, float value) {
		int intx, inty;
		float fracx, fracy;
		switch (interpolationMethod) {
		case NEIGHBOR:
			intx = (int) Math.round(x);
			inty = (int) Math.round(y);
			data[inty][intx] = (float) value;
		case CUBIC:
		case LINEAR:
			intx = (int) x;
			inty = (int) y;
			fracx = x - intx;
			fracy = y - inty;
			try {
				data[inty][intx] = (float) (value * (1.0 - fracx) * (1.0 - fracy));
				data[inty][intx + 1] = (float) (value * fracx * (1.0 - fracy));
				data[inty + 1][intx] = (float) (value * (1.0 - fracx) * fracy);
				data[inty + 1][intx + 1] = (float) (value * fracx * fracy);
			} catch (ArrayIndexOutOfBoundsException e) {
				data[inty][intx] = (float) (value * (1 - fracx) * (1 - fracy));
				try {
					data[inty][intx + 1] = (float) (value * fracx * (1 - fracy));
				} catch (ArrayIndexOutOfBoundsException ee) {
				}
				try {
					data[inty + 1][intx] = (float) (value * (1 - fracx) * fracy);
				} catch (ArrayIndexOutOfBoundsException ee) {
				}
			}
			break;
		}
	}

	/**
	 * cubic interpolation using the CatMull-Rom method
	 */
	private static float CatMull(float t, float y0, float y1, float y2, float y3) {

		return (float) (-0.5f * (t * t * t * (y0 - 3 * y1 + 3 * y2 - y3) - t * t
				* (2 * y0 - 5 * y1 + 4 * y2 - y3) + t * (y0 - y2) - (2 * y1)));

	} // CatMull

}
