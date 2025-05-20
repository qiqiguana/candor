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
 * An InterpolatedGrayImage is a GrayImage that can be accessed at other than integral locations.
 * Supports nearest neighbor, bilinear, and bicubic interpolation.
 */
public class InterpolatedGrayImage extends GrayImage implements InterpolatedImage<Integer> {

	/** The interpolation method that will be used. Default= NEIGHBOR */
	protected InterpolationMethod interpolationMethod = InterpolationMethod.NEIGHBOR;

	/*
	 * constructors
	 */
	/** Create an empty InterpolatedGrayImage. */
	public InterpolatedGrayImage() {
		super();
	}

	/** Create an InterpolatedGrayImage with width x and height y. */
	public InterpolatedGrayImage(int x, int y) {
		super(x, y);
	}

	/** Create an InterpolatedGrayImage from a GrayImage. */
	public InterpolatedGrayImage(GrayImage img) {
		super(img);
	}

	/** Deep copy of an InterpolatedGrayImage. */
	public InterpolatedGrayImage(InterpolatedGrayImage img) {
		super(img);
		interpolationMethod = img.getInterpolationMethod();
	}

	/** Create an InterpolatedGrayImage from a Java image. */
	public InterpolatedGrayImage(java.awt.Image img) {
		super(img);
	}

	/** deep copy */
	public InterpolatedGrayImage copy() {
		
		InterpolatedGrayImage g = new InterpolatedGrayImage(X, Y);
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
		System.out.println("Interpolation set to: " + m);
	}

	/** Returns the interpolated value at (x,0). */
	public int interp(double x) throws java.lang.ArrayIndexOutOfBoundsException {
		int intx;
		double fracx;
		switch (interpolationMethod) {
		case NEIGHBOR:
			intx = (int) Math.round(x);
			return data[0][intx];
		case LINEAR:
			intx = (int) x;
			fracx = x - intx;
			return (int) ((double) data[0][intx] * (1 - fracx) + (double) data[0][intx + 1] * fracx);
		case CUBIC:
			try {
				intx = (int) x;
				fracx = x - intx;
				double t = fracx;
				double y0 = data[0][intx - 1];
				double y1 = data[0][intx];
				double y2 = data[0][intx + 1];
				double y3 = data[0][intx + 2];
				return (int) CatMull(t, y0, y1, y2, y3);
			} catch (Exception e) {
				intx = (int) x;
				fracx = x - intx;
				return (int) ((double) data[0][intx] * (1 - fracx) + (double) data[0][intx + 1]
						* fracx);
			}
		}
		return 0;
	}

	/** Returns the interpolated value at (x,y). */
	public final Integer interp(float x, float y) throws java.lang.ArrayIndexOutOfBoundsException {
		int intx, inty;
		double fracx, fracy;
		//System.out.println("Interpolation value: "+interpolationMethod);
		switch (interpolationMethod) {
		case NEIGHBOR:
			intx = Math.round(x);
			inty = Math.round(y);
			return (int) data[inty][intx];
		case LINEAR:
			intx = (int) x;
			inty = (int) y;
			fracx = x - intx;
			fracy = y - inty;
			if (intx >= data[0].length - 1 || inty >= data.length - 1)//edge case
			{
				if (intx >= data[0].length - 1 && inty >= data.length - 1) {
					return (int) data[inty][intx];//corner
				} else if (intx >= data[0].length - 1) {
					return (int) ((double) data[inty][intx] * (1 - fracy) + (double) data[inty + 1][intx]
							* fracy);
				} else //if (inty >= data.length-1)
				{
					return (int) ((double) data[inty][intx] * (1 - fracx) + (double) data[inty][intx + 1]
							* fracx);
				}
				//else throw new ArrayIndexOutOfBoundsException();
			}

			return (int) ((double) data[inty][intx] * (1 - fracx) * (1 - fracy)
					+ (double) data[inty][intx + 1] * fracx * (1 - fracy)
					+ (double) data[inty + 1][intx] * (1 - fracx) * fracy + (double) data[inty + 1][intx + 1]
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
						return (int) data[inty][intx];//corner
					}
					if (intx >= data[0].length - 1) {
						return (int) ((double) data[inty][intx] * (1 - fracy) + (double) data[inty + 1][intx]
								* fracy);
					} else if (inty >= data.length - 1) {
						return (int) ((double) data[inty][intx] * (1 - fracx) + (double) data[inty][intx + 1]
								* fracx);
					} else
						throw new ArrayIndexOutOfBoundsException();
				}

				return (int) ((double) data[inty][intx] * (1 - fracx) * (1 - fracy)
						+ (double) data[inty][intx + 1] * fracx * (1 - fracy)
						+ (double) data[inty + 1][intx] * (1 - fracx) * fracy + (double) data[inty + 1][intx + 1]
						* fracx * fracy);
			}

			double y0,
			y1,
			y2,
			y3;
			double x0,
			x1,
			x2,
			x3;

			double t = fracx;

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

			return (int) CatMull(t, x0, x1, x2, x3);
		}
		return 0;
	}

	/** Add <i>value</i> to the surrounding area of (x,0). */
	public final void accum(double x, int value) {
		int intx;
		switch (interpolationMethod) {
		case NEIGHBOR:
			intx = (int) Math.round(x);
			data[0][intx] += (short) value;
			break;
		case CUBIC:
		case LINEAR:
			intx = (int) x;
			double fracx = x - intx;
			try {
				data[0][intx] += (short) (value * (1 - fracx));
				data[0][intx + 1] += (short) (value * fracx);
			} catch (ArrayIndexOutOfBoundsException e) {
				data[0][intx] += (short) (value * (1 - fracx));
			}
			break;
		}
	}

	/** Add <i>value</i> to the surrounding area of (x,y). */
	public final void accum(double x, double y, int value) {
		int intx, inty;
		switch (interpolationMethod) {
		case NEIGHBOR:
			intx = (int) Math.round(x);
			inty = (int) Math.round(y);
			data[inty][intx] += (short) value;
			break;
		case CUBIC:
		case LINEAR:
			intx = (int) x;
			inty = (int) y;
			double fracx = x - intx;
			double fracy = y - inty;
			try {
				data[inty][intx] += (short) (value * (1 - fracx) * (1 - fracy));
				data[inty][intx + 1] += (short) (value * fracx * (1 - fracy));
				data[inty + 1][intx] += (short) (value * (1 - fracx) * fracy);
				data[inty + 1][intx + 1] += (short) (value * (1 - fracx) * (1 - fracy));
			} catch (ArrayIndexOutOfBoundsException e) {
				data[inty][intx] += (short) (value * (1 - fracx) * (1 - fracy));
				try {
					data[inty][intx + 1] += (short) (value * fracx * (1 - fracy));
				} catch (ArrayIndexOutOfBoundsException ee) {
				}
				try {
					data[inty + 1][intx] += (short) (value * (1 - fracx) * fracy);
				} catch (ArrayIndexOutOfBoundsException ee) {
				}
			}
			break;
		}
	}

	/** Spread <i>value</i> in the surrounding area of (x,0). */
	public final void splat(double x, int value) {
		int intx;
		switch (interpolationMethod) {
		case NEIGHBOR:
			intx = (int) Math.round(x);
			data[0][intx] = value;
			//FIXME?
		case CUBIC:
			//FIXME?
		case LINEAR:
			intx = (int) x;
			double fracx = x - intx;
			try {
				data[0][intx] = (int) (value * (1 - fracx));
				data[0][intx + 1] =(int) (value * (fracx));

			} catch (ArrayIndexOutOfBoundsException e) {
				data[0][intx] = (int) (value * (1 - fracx));
			}
			break;
		}
	}

	/** Spread <i>value</i> to the surrounding area of (x,y). */
	public final void splat(double x, double y, int value) {
		int intx, inty;
		switch (interpolationMethod) {
		case NEIGHBOR:
			intx = (int) Math.round(x);
			inty = (int) Math.round(y);
			data[inty][intx] = (int) value;
			break;
		case CUBIC:
		case LINEAR:
			intx = (int) x;
			inty = (int) y;
			double fracx = x - intx;
			double fracy = y - inty;
			try {
				data[inty][intx] = (int) (value * (1.0 - fracx) * (1.0 - fracy));
				data[inty][intx + 1] = (int) (value * fracx * (1.0 - fracy));
				data[inty + 1][intx] = (int) (value * (1.0 - fracx) * fracy);
				data[inty + 1][intx + 1] = (int) (value * fracx * fracy);
			} catch (ArrayIndexOutOfBoundsException e) {
				data[inty][intx] = (int) (value * (1 - fracx) * (1 - fracy));
				try {
					data[inty][intx + 1] = (int) (value * fracx * (1 - fracy));
				} catch (ArrayIndexOutOfBoundsException ee) {
				}
				try {
					data[inty + 1][intx] = (int) (value * (1 - fracx) * fracy);
				} catch (ArrayIndexOutOfBoundsException ee) {
				}
			}
			break;
		} // switch
	}

	/**
	 * cubic interpolation using the CatMull-Rom method
	 */
	private static final double CatMull(double t, double y0, double y1, double y2, double y3) {

		return -0.5
				* (t * t * t * (y0 - 3 * y1 + 3 * y2 - y3) - t * t
						* (2 * y0 - 5 * y1 + 4 * y2 - y3) + t * (y0 - y2) - (2 * y1));

	} // CatMull

}
