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

import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import jigl.image.ColorModel;
import jigl.image.Image;
import jigl.image.ROI;
import jigl.util.ArrayUtil;

/**
 * A RealColorImage is a set of three RealGrayImage planes.
 */
public class RealColorImage implements Image<Float[]> {

	/** Set of 3 GrayImages */
	protected RealGrayImage[] planes = new RealGrayImage[3];

	/** Cartesian width */
	protected int X;

	/** Cartesian height */
	protected int Y;

	/** Current Color Model; Default is RGB */
	protected ColorModel colorModel = ColorModel.RGB;

	/*
	constructors
	*/

	/** Creates an empty two dimensional RealColorImage with a height and width of zero */
	public RealColorImage() {
		X = 0;
		Y = 0;
		planes[0] = null;
		planes[1] = null;
		planes[2] = null;
	}

	/** Creates a two dimensional RealColorImage with a height and width of x and y repectively */
	public RealColorImage(int x, int y) {
		X = x;
		Y = y;
		planes[0] = new RealGrayImage(X, Y);
		planes[1] = new RealGrayImage(X, Y);
		planes[2] = new RealGrayImage(X, Y);
	}

	/** Creates a two dimensional RealColorImage from RealColorImage img */
	public RealColorImage(RealColorImage img) {
		X = img.X();
		Y = img.Y();
		RealGrayImage r, g, b;
		r = new RealGrayImage(img.plane(0));
		g = new RealGrayImage(img.plane(1));
		b = new RealGrayImage(img.plane(2));
		planes[0] = r;
		planes[1] = g;
		planes[2] = b;
	}

	/** Creates a two dimensional RealColorImage from the standard java.awt.Image */
	public RealColorImage(java.awt.Image img) {
		int w = img.getWidth(jigl.internal.DummyObserver.dummy);
		int h = img.getHeight(jigl.internal.DummyObserver.dummy);
		X = w;
		Y = h;
		planes[0] = new RealGrayImage(X, Y);
		planes[1] = new RealGrayImage(X, Y);
		planes[2] = new RealGrayImage(X, Y);
		InitFromImage(img, 0, 0, w, h);
	}

	/**
	 * Makes a deep copy of this image
	 * 
	 * @param none
	 * @return a deep copy of RealColorImage
	 */
	public RealColorImage copy() {
		RealColorImage c = new RealColorImage(X, Y);

		c.setColorModel(colorModel);

		c.planes[0] = (RealGrayImage) planes[0].copy();
		c.planes[1] = (RealGrayImage) planes[1].copy();
		c.planes[2] = (RealGrayImage) planes[2].copy();

		/*		for(int y = 0; y < Y; y++) {
					for(int x = 0; x < X; x++) {
						c.planes[0].set(x,y,planes[0].get(x,y));
						c.planes[1].set(x,y,planes[1].get(x,y));
						c.planes[2].set(x,y,planes[2].get(x,y));
					}
				}*/
		return c;
	}

	/**
	 * Returns the width (maximum X value)
	 * 
	 * @param none
	 */
	public final int X() {
		return X;
	}

	/**
	 * Returns the height (maximum Y value)
	 * 
	 * @param none
	 */
	public final int Y() {
		return Y;
	}

	/**
	 * Returns the color model. <DT>
	 * <DL>
	 * <DL>
	 * <RGB = 0</DT> <DT>CMY = 1</DT> <DT>YIQ = 2</DT> <DT>HSV = 3</DT> <DT>HLS = 4</DT> <DT>default
	 * = RGB</DT> <DT></DT></DL></DL>
	 * 
	 * @param none
	 */
	public final ColorModel getColorModel() {
		return colorModel;
	}

	/**
	 * Returns the color model. <DT>
	 * <DL>
	 * <DL>
	 * <RGB = 0</DT> <DT>CMY = 1</DT> <DT>YIQ = 2</DT> <DT>HSV = 3</DT> <DT>HLS = 4</DT> <DT>default
	 * = RGB</DT> <DT></DT></DL></DL>
	 * 
	 * @param none
	 */
	public final void setColorModel(ColorModel cm) {
		colorModel = cm;
	}

	/**
	 * Returns the plane of this image
	 * <P>
	 * If this were an RGB image, plane(0) would return the red plane.
	 * <P>
	 * 
	 * @param p the plane of this image
	 * @return a shallow copy
	 */
	public final RealGrayImage plane(int p) {
		return planes[p];
	}

	/**
	 * Set the plane to a RealColorImage pl
	 * <P>
	 * 
	 * @param p the plane of this image
	 * @param pl RealColorImage to set the plane to
	 * @return a shallow copy
	 */
	public final void setPlane(int p, RealGrayImage pl) {
		planes[p] = pl;
	}

	/**
	 * initializes plane data from a Java image. Used by the java image constructor.
	 */
	private void InitFromImage(java.awt.Image img, int x, int y, int w, int h) {
		int pixels[] = new int[w * h];
		PixelGrabber pg = new PixelGrabber(img, x, y, w, h, pixels, 0, w);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			System.err.println("interrupted waiting for pixels!");
			return;
		}
		if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
			System.err.println("image fetch aborted or errored");
			return;
		}

		// convert from grabbed pixels
		int red = 0;
		int green = 0;
		int blue = 0;
		int index = 0;
		for (int iy = 0; iy < Y; iy++) {
			for (int ix = 0; ix < X; ix++) {
				red = 0x0FF & pixels[index] >> 16;
				green = 0x0FF & pixels[index] >> 8;
				blue = 0x0FF & pixels[index];
				planes[0].set(ix, iy, (float) red);
				planes[1].set(ix, iy, (float) green);
				planes[2].set(ix, iy, (float) blue);
				index++;
			}
		}
	}

	/**
	 * Returns the pixel value at the given x, y value as a triplet
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @return three element array of floats
	 */
	public Float[] get(int x, int y) {
		Float[] color = new Float[3];
		color[0] = planes[0].get(x, y);
		color[1] = planes[1].get(x, y);
		color[2] = planes[2].get(x, y);
		return color;
	}

	/**
	 * Sets the pixel value at the given x, y value as a triplet
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param v array of three floats holding the values for the set
	 */
	public void set(int x, int y, Float[] v) {
		planes[0].set(x, y, v[0]);
		planes[1].set(x, y, v[1]);
		planes[2].set(x, y, v[2]);
	}

	/**
	 * Makes a copy of this image with a buffer so the resulting image has a width w and height h
	 * 
	 * @param w width of buffered image
	 * @param h height of buffered image
	 * @param color array of 3 floats that is the default color to buffer with
	 * @return a deep copy of RealColorImage
	 */
	public RealColorImage addbuffer(int w, int h, float[] color) {
		RealColorImage g = new RealColorImage(w, h);

		g.setPlane(0, planes[0].addbuffer(w, h, color[0]));
		g.setPlane(1, planes[1].addbuffer(w, h, color[1]));
		g.setPlane(2, planes[2].addbuffer(w, h, color[2]));

		return g;
	}

	/**
	 * Makes a copy of this image with a buffer so the resulting image has a width w and height h
	 * 
	 * @param w width of buffered image
	 * @param h height of buffered image
	 * @param xoff x offset of this image in the buffered image
	 * @param yoff y offset of this image in the buffered image
	 * @param color array of 3 floats that is the default color to buffer with
	 * @return a deep copy of RealColorImage
	 */
	public RealColorImage addbuffer(int w, int h, int xoff, int yoff, float[] color) {
		RealColorImage g = new RealColorImage(w, h);

		g.setPlane(0, planes[0].addbuffer(w, h, xoff, yoff, color[0]));
		g.setPlane(1, planes[1].addbuffer(w, h, xoff, yoff, color[1]));
		g.setPlane(2, planes[2].addbuffer(w, h, xoff, yoff, color[2]));

		return g;
	}

	/**
	 * Adds a triplet to a single pixel
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value triplet to add to the pixel
	 */
	public void add(int x, int y, Float[] value) {
		planes[0].add(x, y, value[0]);
		planes[1].add(x, y, value[1]);
		planes[2].add(x, y, value[2]);
	}

	/**
	 * Adds a triplet to a single pixel
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param val0 value to add to the pixel in the first plane
	 * @param val1 value to add to the pixel in the second plane
	 * @param val2 value to add to the pixel in the third plane
	 */
	public final void add(int x, int y, float val0, float val1, float val2) {
		planes[0].add(x, y, val0);
		planes[1].add(x, y, val1);
		planes[2].add(x, y, val2);
	}

	/**
	 * Subtracts a triplet from a single pixel
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value triplet to add to the pixel
	 */
	public final void subtract(int x, int y, float[] value) {
		planes[0].subtract(x, y, value[0]);
		planes[1].subtract(x, y, value[1]);
		planes[2].subtract(x, y, value[2]);
	}

	/**
	 * Subtracts a triplet from a single pixel
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param val0 value to subtract from the pixel in the first plane
	 * @param val1 value to subtract from the pixel in the second plane
	 * @param val2 value to subtract from the pixel in the third plane
	 */
	public final void subtract(int x, int y, float val0, float val1, float val2) {
		planes[0].subtract(x, y, val0);
		planes[1].subtract(x, y, val1);
		planes[2].subtract(x, y, val2);
	}

	/**
	 * Multiplies a triplet with a single pixel
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value triplet to add to the pixel
	 */
	public final void multiply(int x, int y, float[] value) {
		planes[0].multiply(x, y, value[0]);
		planes[1].multiply(x, y, value[1]);
		planes[2].multiply(x, y, value[2]);
	}

	/**
	 * Multiplies a triplet with a single pixel
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param val0 value to multiply the pixel by in the first plane
	 * @param val1 value to multiply the pixel by in the second plane
	 * @param val2 value to multiply the pixel by in the third plane
	 */

	public final void multiply(int x, int y, float val0, float val1, float val2) {
		planes[0].multiply(x, y, val0);
		planes[1].multiply(x, y, val1);
		planes[2].multiply(x, y, val2);
	}

	/**
	 * Divides a single pixel by a triplet
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value triplet to add to the pixel
	 */
	public final void divide(int x, int y, float[] value) {
		planes[0].divide(x, y, value[0]);
		planes[1].divide(x, y, value[1]);
		planes[2].divide(x, y, value[2]);
	}

	/**
	 * Divides a single pixel by a triplet
	 * 
	 * @param x X-coordinate
	 * @param val0 value to divide by the pixel in the first plane
	 * @param val1 value to divide by the pixel in the second plane
	 * @param val2 value to divide by the pixel in the third plane
	 */
	public final void divide(int x, int y, float val0, float val1, float val2) {
		planes[0].divide(x, y, val0);
		planes[1].divide(x, y, val1);
		planes[2].divide(x, y, val2);
	}

	/**
	 * Finds the minimum value of all the planes of this image
	 * 
	 * @param none
	 * @return an float containing the minimum value
	 */
	public Float[] min() {
		return new Float[] {planes[0].min(), planes[1].min(), planes[2].min()};
	}
	
	public Float minComponent() {
		return ArrayUtil.arrayMin(min());
	}

	/**
	 * Finds the minimum value of a single plane of this image
	 * 
	 * @param p the plane
	 * @return an float containing the minimum value
	 */
	public float min(int p) {
		return planes[p].min();
	}

	/**
	 * Finds the maximum value of all the planes of this image
	 * 
	 * @param none
	 * @return an float containing the maximum value
	 */
	public Float[] max() {
		return new Float[] {planes[0].max(), planes[1].max(), planes[2].max()};
	}
	
	public Float maxComponent() {
		return ArrayUtil.arrayMax(max());
	}

	/**
	 * Finds the maximum value of a single plane of this image
	 * 
	 * @param p the plane
	 * @return an integer containing the maximum value
	 */
	public final float max(int p) {
		return planes[p].max();
	}

	/*
	Image-wide scalar operations
	*/

	/** Adds all the values together */
	public final float[] addSum() {
		float[] sum = new float[3];
		sum[0] = planes[0].addSum();
		sum[1] = planes[1].addSum();
		sum[2] = planes[2].addSum();
		return sum;
	}

	/** Adds absolute value of all the values together */
	public final float[] absSum() {
		float[] sum = new float[3];
		sum[0] = planes[0].absSum();
		sum[1] = planes[1].absSum();
		sum[2] = planes[2].absSum();
		return sum;
	}

	/** Adds the square of all the values together */
	public final double[] sqrSum() {
		double[] sum = new double[3];
		sum[0] = planes[0].sqrSum();
		sum[1] = planes[1].sqrSum();
		sum[2] = planes[2].sqrSum();
		return sum;
	}

	/**
	 * Adds triplet to all the pixels in this image
	 * 
	 * @param val0 value to add to the pixel in the first plane
	 * @param val1 value to add to the pixel in the second plane
	 * @param val2 value to add to the pixel in the third plane
	 * @return this
	 */
	public final RealColorImage add(float val0, float val1, float val2) {
		planes[0] = planes[0].add(val0);
		planes[1] = planes[1].add(val1);
		planes[2] = planes[2].add(val2);
		return this;
	}

	/**
	 * Adds triplet to all the pixels in this image
	 * 
	 * @param v triplet to be added to the pixels
	 * @return this
	 */
	public final RealColorImage add(float[] v) {
		planes[0] = planes[0].add(v[0]);
		planes[1] = planes[1].add(v[1]);
		planes[2] = planes[2].add(v[2]);
		return this;
	}

	/**
	 * Adds a value to all the pixels in a plane of this image
	 * 
	 * @param p the plane
	 * @param v value to be added to the pixels
	 * @return this
	 */
	public final RealColorImage add(int p, float v) {
		planes[p] = planes[p].add(v);
		return this;
	}

	/**
	 * Subtracts a triplet from all the pixels of this image
	 * 
	 * @param val0 value to subtract from the pixel in the first plane
	 * @param val1 value to subtract from the pixel in the second plane
	 * @param val2 value to subtract from the pixel in the third plane
	 * @return this
	 */
	public final RealColorImage subtract(float val0, float val1, float val2) {
		planes[0] = planes[0].subtract(val0);
		planes[1] = planes[1].subtract(val1);
		planes[2] = planes[2].subtract(val2);
		return this;
	}

	/**
	 * Subtracts a triplet from all the pixels of this image
	 * 
	 * @param v triplet to be subtracted from the pixels
	 * @return this
	 */
	public final RealColorImage subtract(float[] v) {
		planes[0] = planes[0].subtract(v[0]);
		planes[1] = planes[1].subtract(v[1]);
		planes[2] = planes[2].subtract(v[2]);
		return this;
	}

	/** subtracts the value from all pixels in plane p */
	/**
	 * Subtracts a value from all the pixels in a plane of this image
	 * 
	 * @param p the plane
	 * @param v value to be added to the pixels
	 * @return this
	 */
	public final RealColorImage subtract(int p, float v) {
		planes[p] = planes[p].subtract(v);
		return this;
	}

	/**
	 * Multiplies a triplet by all the pixels of this image
	 * 
	 * @param v triplet to be multiplied by
	 * @return this
	 */
	public final RealColorImage multiply(float[] v) {
		planes[0] = planes[0].multiply(v[0]);
		planes[1] = planes[1].multiply(v[1]);
		planes[2] = planes[2].multiply(v[2]);
		return this;
	}

	/**
	 * Multiplies a triplet by all the pixels of this image
	 * 
	 * @param val0 value to multiply the pixel by in the first plane
	 * @param val1 value to multiply the pixel by in the second plane
	 * @param val2 value to multiply the pixel by in the third plane
	 * @return this
	 */
	public final RealColorImage multiply(float val0, float val1, float val2) {
		planes[0] = planes[0].multiply(val0);
		planes[1] = planes[1].multiply(val1);
		planes[2] = planes[2].multiply(val2);
		return this;
	}

	/**
	 * Multiplies all the pixels in a plane of this image by a value
	 * 
	 * @param p the plane
	 * @param v value to be added to the pixels
	 * @return this
	 */
	public final RealColorImage multiply(int p, float v) {
		planes[p] = planes[p].multiply(v);
		return this;
	}

	/**
	 * Divides all the pixels of this image by a triplet
	 * 
	 * @param v triplet to be divided by
	 * @return this
	 */
	public final RealColorImage divide(float[] v) {
		planes[0] = planes[0].divide(v[0]);
		planes[1] = planes[1].divide(v[1]);
		planes[2] = planes[2].divide(v[2]);
		return this;
	}

	/**
	 * Divides all the pixels of this image by a triplet
	 * 
	 * @param val0 value to divide by the pixel in the first plane
	 * @param val1 value to divide by the pixel in the second plane
	 * @param val2 value to divide by the pixel in the third plane
	 * @return this
	 */
	public final RealColorImage divide(float val0, float val1, float val2) {
		planes[0] = planes[0].divide(val0);
		planes[1] = planes[1].divide(val1);
		planes[2] = planes[2].divide(val2);
		return this;
	}

	/**
	 * Divides all the pixels in a plane of this image by a value
	 * 
	 * @param p the plane
	 * @param v value to be added to the pixels
	 * @return this
	 */
	public final RealColorImage divide(int p, float v) {
		planes[p] = planes[p].divide(v);
		return this;
	}

	/*
	Image-by-image arithmetic operations
	*/

	/**
	 * Adds another RealColorImage to this image
	 * 
	 * @param im the RealColorImage to add
	 * @return this
	 */
	public final RealColorImage add(RealColorImage im) {
		planes[0] = planes[0].add(im.plane(0));
		planes[1] = planes[1].add(im.plane(1));
		planes[2] = planes[2].add(im.plane(2));
		return this;
	}

	/**
	 * Subtracts a RealColorImage from this image
	 * 
	 * @param im the RealColorImage to subtract
	 * @return this
	 */
	public final RealColorImage subtract(RealColorImage im) {
		planes[0] = planes[0].subtract(im.plane(0));
		planes[1] = planes[1].subtract(im.plane(1));
		planes[2] = planes[2].subtract(im.plane(2));
		return this;
	}

	/**
	 * Subtracts a RealColorImage from this image and returns the absolute value
	 * 
	 * @param im the RealColorImage to diff
	 * @return this
	 */
	public final RealColorImage diff(RealColorImage im) {
		planes[0] = planes[0].diff(im.plane(0));
		planes[1] = planes[1].diff(im.plane(1));
		planes[2] = planes[2].diff(im.plane(1));
		return this;
	}

	/**
	 * Multiplies a RealColorImage by this image
	 * 
	 * @param im the RealColorImage to multiply
	 * @return this
	 */
	public final RealColorImage multiply(RealColorImage im) {
		planes[0] = planes[0].multiply(im.plane(0));
		planes[1] = planes[1].multiply(im.plane(1));
		planes[2] = planes[2].multiply(im.plane(2));
		return this;
	}

	/**
	 * Divides this image by a RealColorImage
	 * 
	 * @param im the RealColorImage to divide
	 * @return this
	 */
	public final RealColorImage divide(RealColorImage im) {
		planes[0] = planes[0].divide(im.plane(0));
		planes[1] = planes[1].divide(im.plane(1));
		planes[2] = planes[2].divide(im.plane(2));
		return this;
	}

	/**
	 * Prints the string in integer format. <DT>
	 * <DL>
	 * <DL>
	 * -Example of output on an image with width 100 and height 120:</DT>
	 * <DL>
	 * <DT>100 : 120</DT>
	 * <DT>10 87 32 65 32 65 40 59 43 12 43 ...</DT>
	 * <DT>100 : 120</DT>
	 * <DT>10 20 32 12 32 56 40 45 42 39 43 ...</DT>
	 * <DT>100 : 120</DT>
	 * <DT>10 20 32 12 54 56 73 59 42 23 43 ...</DT>
	 * </DL>
	 * </DL></DL>
	 */
	public final String toString() {
		String str = ""; // = ndims + " planes\n";
		str += planes[0].toString();
		str += planes[1].toString();
		str += planes[2].toString();
		return str;
	}

	/**
	 * Convert this image to a Java Image. The RealColorImage is assumed to be RGB.
	 * <P>
	 * 
	 * @param none
	 * @see java.awt.image.ImageProducer
	 */
	public final ImageProducer getJavaImage() {

		// get range of this image
		double min = minComponent();
		double max = maxComponent();

		// keep byte images in original range
		if (min >= 0 && max <= 255) {
			min = 0.0;
			max = 255.0;
		}
		final double range = max - min;

		// convert jigl image to java image
		int pix[] = new int[X * Y];
		int index = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		Float[] color = new Float[3];
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {

				// map image values
				color = get(x, y);
				red = (int) ((255.0 / range) * ((double) color[0] - min));
				green = (int) ((255.0 / range) * ((double) color[1] - min));
				blue = (int) ((255.0 / range) * ((double) color[2] - min));

				// take lower 8 bits
				red = 0x00FF & red;
				green = 0x00FF & green;
				blue = 0x00FF & blue;

				// put this pixel in the java image
				pix[index] = (0xFF << 24) | (red << 16) | (green << 8) | blue;
				index++;
			}
		}

		// return java image
		return new MemoryImageSource(X, Y, pix, 0, X);
	}

	/**
	 * Scales the range of this image to byte (0..255)
	 * 
	 * @param none
	 */
	public void byteSize() {

		// get range of this image
		final double min = minComponent();
		final double max = maxComponent();

		// keep byte images in original range

		final double range = max - min;

		// convert to byte depth
		int red = 0;
		int green = 0;
		int blue = 0;
		Float color[] = new Float[3];
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				color = get(x, y);
				red = (int) ((255.0 / range) * ((double) color[0] - min));
				green = (int) ((255.0 / range) * ((double) color[1] - min));
				blue = (int) ((255.0 / range) * ((double) color[2] - min));

				// take lower 8 bits
				red = 0x00FF & red;
				green = 0x00FF & green;
				blue = 0x00FF & blue;
				color[0] = (float) red;
				color[1] = (float) green;
				color[2] = (float) blue;
				set(x, y, color);
			}
		}

	}

	/**
	 * Clips the range of this image to an arbitrary min/max
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 */
	public final void clip(int min, int max) {
		// clip each plane
		planes[0].clip(min, max);
		planes[1].clip(min, max);
		planes[2].clip(min, max);
	}

	//*****************************************************************************************************
	//**********************************  ROI *************************************************************
	//*****************************************************************************************************

	/**
	 * Makes a deep copy of a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return a deep copy of RealColorImage
	 */
	public RealColorImage copy(ROI roi) {
		RealColorImage c = new RealColorImage(roi.X(), roi.Y());

		c.setColorModel(colorModel);

		c.planes[0] = (RealGrayImage) planes[0].copy(roi);
		c.planes[1] = (RealGrayImage) planes[1].copy(roi);
		c.planes[2] = (RealGrayImage) planes[2].copy(roi);

		/*for(int y = roi.uy(); y < roi.ly(); y++) {
					for(int x = roi.ux(); x < roi.lx(); x++) {
						c.planes[0].set(x-roi.ux(),y-roi.uy(),planes[0].get(x,y));
						c.planes[1].set(x-roi.ux(),y-roi.uy(),planes[1].get(x,y));
						c.planes[2].set(x-roi.ux(),y-roi.uy(),planes[2].get(x,y));
					}
				}*/
		return c;
	}

	/**
	 * Returns the pixel value at the given x, y value as a triplet in a Region of Interest
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param roi Region of Interest
	 * @return three element array of integers
	 */
	public final float[] get(int x, int y, ROI roi) {
		float[] color = new float[3];
		color[0] = planes[0].get(x + roi.ux(), y + roi.uy());
		color[1] = planes[1].get(x + roi.ux(), y + roi.uy());
		color[2] = planes[2].get(x + roi.ux(), y + roi.uy());
		return color;
	}

	/**
	 * Sets the pixel value at the given x, y value as a triplet in a Region of Interest
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param v array of three integers holding the values for the set
	 * @param roi Region of Interest
	 */
	public final void set(int x, int y, Float[] v, ROI roi) {
		planes[0].set(x, y, v[0], roi);
		planes[1].set(x, y, v[1], roi);
		planes[2].set(x, y, v[2], roi);
	}

	/**
	 * Adds a triplet to a single pixel in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param val0 value to add to the pixel in the first plane
	 * @param val1 value to add to the pixel in the second plane
	 * @param val2 value to add to the pixel in the third plane
	 * @param roi Region of Interest
	 */
	public final void add(int x, int y, float val0, float val1, float val2, ROI roi) {
		planes[0].set(x, y, val0, roi);
		planes[1].set(x, y, val1, roi);
		planes[2].set(x, y, val2, roi);
	}

	/**
	 * Adds a triplet to a single pixel in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value triplet to subtract from the pixel
	 * @param roi Region of Interest
	 */
	public final void add(int x, int y, float[] value, ROI roi) {
		planes[0].add(x, y, value[0], roi);
		planes[1].add(x, y, value[1], roi);
		planes[2].add(x, y, value[2], roi);
	}

	/**
	 * Subtracts a triplet from a single pixel in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param val0 value to subtract from the pixel in the first plane
	 * @param val1 value to subtract from the pixel in the second plane
	 * @param val2 value to subtract from the pixel in the third plane
	 * @param roi Region of Interest
	 */
	public final void subtract(int x, int y, float val0, float val1, float val2, ROI roi) {
		planes[0].subtract(x, y, val0, roi);
		planes[1].subtract(x, y, val1, roi);
		planes[2].subtract(x, y, val2, roi);

	}

	/**
	 * Subtracts a triplet from a single pixel in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value triplet to subtract from to the pixel
	 * @param roi Region of Interest
	 */
	public final void subtract(int x, int y, float[] value, ROI roi) {
		planes[0].subtract(x, y, value[0], roi);
		planes[1].subtract(x, y, value[1], roi);
		planes[2].subtract(x, y, value[2], roi);
	}

	/**
	 * Multiplies a triplet with a single pixel in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param val0 value to multiply the pixel by in the first plane
	 * @param val1 value to multiply the pixel by in the second plane
	 * @param val2 value to multiply the pixel by in the third plane
	 * @param roi Region of Interest
	 */
	public final void multiply(int x, int y, float val0, float val1, float val2, ROI roi) {
		planes[0].multiply(x, y, val0, roi);
		planes[1].multiply(x, y, val1, roi);
		planes[2].multiply(x, y, val2, roi);
	}

	/**
	 * Multiplies a triplet with a single pixel in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value triplet to multiply by the pixel
	 * @param roi Region of Interest
	 */
	public final void multiply(int x, int y, float[] value, ROI roi) {
		planes[0].multiply(x, y, value[0], roi);
		planes[1].multiply(x, y, value[1], roi);
		planes[2].multiply(x, y, value[2], roi);
	}

	/**
	 * Divides a single pixel by a triplet in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value triplet to add to the pixel
	 * @param roi Region of Interest
	 */
	public final void divide(int x, int y, float[] value, ROI roi) {
		planes[0].divide(x, y, value[0], roi);
		planes[1].divide(x, y, value[1], roi);
		planes[2].divide(x, y, value[2], roi);
	}

	/**
	 * Divides a single pixel by a triplet in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param val0 value to divide by the pixel in the first plane
	 * @param val1 value to divide by the pixel in the second plane
	 * @param val2 value to divide by the pixel in the third plane
	 * @param roi Region of Interest
	 */
	public final void divide(int x, int y, float val0, float val1, float val2, ROI roi) {
		planes[0].divide(x, y, val0, roi);
		planes[1].divide(x, y, val1, roi);
		planes[2].divide(x, y, val2, roi);
	}

	/**
	 * Finds the minimum value of all the planes in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return an integer containing the minimum value
	 */
	public final float min(ROI roi) {
		float m1, m2, m3;
		float min = Float.MAX_VALUE;
		m1 = planes[0].min(roi);
		m2 = planes[1].min(roi);
		m3 = planes[2].min(roi);
		if (m1 < min)
			min = m1;
		if (m2 < min)
			min = m2;
		if (m3 < min)
			min = m3;
		return min;
	}

	/**
	 * Finds the minimum value of a single plane in a Region of Interest
	 * 
	 * @param p the plane
	 * @param roi Region of Interest
	 * @return an integer containing the minimum value
	 */
	public final float min(int p, ROI roi) {
		return planes[p].min(roi);
	}

	/**
	 * Finds the maximum value of all the planes in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return an integer containing the maximum value
	 */
	public final float max(ROI roi) {
		float m1, m2, m3;
		float max = Float.MIN_VALUE;
		m1 = planes[0].max(roi);
		m2 = planes[1].max(roi);
		m3 = planes[2].max(roi);
		if (m1 > max)
			max = m1;
		if (m2 > max)
			max = m2;
		if (m3 > max)
			max = m3;
		return max;
	}

	/**
	 * Finds the maximum value of a single plane in a Region of Interest
	 * 
	 * @param p the plane
	 * @param roi Region of Interest
	 * @return an float containing the maximum value
	 */
	public final float max(int p, ROI roi) {
		return planes[p].max(roi);
	}

	/*
	Image-wide scalar operations
	*/

	/**
	 * Adds triplet to all the pixels in a Region of Interest
	 * 
	 * @param v triplet to be added to the pixels
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage add(float[] v, ROI roi) {
		planes[0] = planes[0].add(v[0], roi);
		planes[1] = planes[1].add(v[1], roi);
		planes[2] = planes[2].add(v[2], roi);
		return this;
	}

	/**
	 * Adds triplet to all the pixels in this image in a Region of Interest
	 * 
	 * @param val0 value to add to the pixel in the first plane
	 * @param val1 value to add to the pixel in the second plane
	 * @param val2 value to add to the pixel in the third plane
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage add(float val0, float val1, float val2, ROI roi) {
		planes[0] = planes[0].add(val0, roi);
		planes[1] = planes[1].add(val1, roi);
		planes[2] = planes[2].add(val2, roi);
		return this;
	}

	/**
	 * Adds a value to all the pixels in a plane in a Region of Interest
	 * 
	 * @param p the plane
	 * @param v value to be added to the pixels
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage add(int p, float v, ROI roi) {
		planes[p] = planes[p].add(v, roi);
		return this;
	}

	/**
	 * Subtracts a triplet from all the pixels in a Region of Interest
	 * 
	 * @param v triplet to be subtracted from the pixels
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage subtract(float[] v, ROI roi) {
		planes[0] = planes[0].subtract(v[0], roi);
		planes[1] = planes[1].subtract(v[1], roi);
		planes[2] = planes[2].subtract(v[2], roi);
		return this;
	}

	/**
	 * Subtracts a triplet from all the pixels in a Region of Interest
	 * 
	 * @param val0 value to subtract from the pixel in the first plane
	 * @param val1 value to subtract from the pixel in the second plane
	 * @param val2 value to subtract from the pixel in the third plane
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage subtract(float val0, float val1, float val2, ROI roi) {
		planes[0] = planes[0].subtract(val0, roi);
		planes[1] = planes[1].subtract(val1, roi);
		planes[2] = planes[2].subtract(val2, roi);
		return this;
	}

	/** subtracts the value from all pixels in plane p */
	/**
	 * Subtracts a value from all the pixels in a Region of Interest
	 * 
	 * @param p the plane
	 * @param v value to be added to the pixels
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage subtract(int p, float v, ROI roi) {
		planes[p] = planes[p].subtract(v, roi);
		return this;
	}

	/**
	 * Multiplies a triplet by all the pixels in a Region of Interest
	 * 
	 * @param v triplet to be multiplied by
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage multiply(float[] v, ROI roi) {
		planes[0] = planes[0].multiply(v[0], roi);
		planes[1] = planes[1].multiply(v[1], roi);
		planes[2] = planes[2].multiply(v[2], roi);
		return this;
	}

	/**
	 * Multiplies a triplet by all the pixels of this image in a Region of Interest
	 * 
	 * @param val0 value to multiply the pixel by in the first plane
	 * @param val1 value to multiply the pixel by in the second plane
	 * @param val2 value to multiply the pixel by in the third plane
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage multiply(float val0, float val1, float val2, ROI roi) {
		planes[0] = planes[0].multiply(val0, roi);
		planes[1] = planes[1].multiply(val1, roi);
		planes[2] = planes[2].multiply(val2, roi);
		return this;
	}

	/** multiplies all pixels in plane p by the value */
	/**
	 * Multiplies all the pixels in a plane in a Region of Interest by a value
	 * 
	 * @param p the plane
	 * @param v value to be added to the pixels
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage multiply(int p, float v, ROI roi) {
		planes[p] = planes[p].multiply(v, roi);
		return this;
	}

	/**
	 * Divides all the pixels in a Region of Interest by a triplet
	 * 
	 * @param v triplet to be divided by
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage divide(float[] v, ROI roi) {
		planes[0] = planes[0].divide(v[0], roi);
		planes[1] = planes[1].divide(v[1], roi);
		planes[2] = planes[2].divide(v[2], roi);
		return this;
	}

	/**
	 * Divides all the pixels in a Region of Interest by a triplet
	 * 
	 * @param val0 value to divide by the pixel in the first plane
	 * @param val1 value to divide by the pixel in the second plane
	 * @param val2 value to divide by the pixel in the third plane
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage divide(float val0, float val1, float val2, ROI roi) {
		planes[0] = planes[0].divide(val0, roi);
		planes[1] = planes[1].divide(val1, roi);
		planes[2] = planes[2].divide(val2, roi);
		return this;
	}

	/** divides all pixels in plane p by the value */
	/**
	 * Divides all the pixels in a plane in a Region of Interest by a value
	 * 
	 * @param p the plane
	 * @param v value to be added to the pixels
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealColorImage divide(int p, float v, ROI roi) {
		planes[p] = planes[p].divide(v, roi);
		return this;
	}

	/*
	Image-by-image arithmetic operations
	*/

	/**
	 * Adds a Region of Interest (sourceROI) from another RealColorImage to a Region of Interest
	 * (destROI) from this image
	 * 
	 * @param im the RealColorImage to add
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final RealColorImage add(RealColorImage im, ROI sourceROI, ROI destROI) {
		planes[0] = planes[0].add(im.plane(0), sourceROI, destROI);
		planes[1] = planes[1].add(im.plane(1), sourceROI, destROI);
		planes[2] = planes[2].add(im.plane(2), sourceROI, destROI);
		return this;
	}

	/**
	 * Subtracts a Region of Interest (sourceROI) from another RealColorImage from a Region of
	 * Interest (destROI) from this image
	 * 
	 * @param im the RealColorImage to subtract
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final RealColorImage subtract(RealColorImage im, ROI sourceROI, ROI destROI) {
		planes[0] = planes[0].subtract(im.plane(0), sourceROI, destROI);
		planes[1] = planes[1].subtract(im.plane(1), sourceROI, destROI);
		planes[2] = planes[2].subtract(im.plane(2), sourceROI, destROI);
		return this;
	}

	/**
	 * Multiplies a Region of Interest (sourceROI) from another RealColorImage by a Region of
	 * Interest (destROI) from this image
	 * 
	 * @param im the RealColorImage to multiply
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final RealColorImage multiply(RealColorImage im, ROI sourceROI, ROI destROI) {
		planes[0] = planes[0].multiply(im.plane(0), sourceROI, destROI);
		planes[1] = planes[1].multiply(im.plane(1), sourceROI, destROI);
		planes[2] = planes[2].multiply(im.plane(2), sourceROI, destROI);
		return this;
	}

	/**
	 * Divides a Region of Interest (sourceROI) from this image by a Region of Interest (destROI)
	 * from another RealColorImage
	 * 
	 * @param im the RealColorImage to divide
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final RealColorImage divide(RealColorImage im, ROI sourceROI, ROI destROI) {
		planes[0] = planes[0].divide(im.plane(0), sourceROI, destROI);
		planes[1] = planes[1].divide(im.plane(1), sourceROI, destROI);
		planes[2] = planes[2].divide(im.plane(2), sourceROI, destROI);
		return this;
	}

	/**
	 * Prints a Region of Interest from this images in float format. <DT>
	 * <DL>
	 * <DL>
	 * -Example of output on an image with width 100 and height 120:</DT>
	 * <DL>
	 * <DT>100 : 120</DT>
	 * <DT>10 87 32 65 32 65 40 59 43 12 43 ...</DT>
	 * <DT>100 : 120</DT>
	 * <DT>10 20 32 12 32 56 40 45 42 39 43 ...</DT>
	 * <DT>100 : 120</DT>
	 * <DT>10 20 32 12 54 56 73 59 42 23 43 ...</DT>
	 * </DL>
	 * </DL></DL>
	 * 
	 * @param roi Region of Interest
	 */
	public final String toString(ROI roi) {
		String str = ""; // = ndims + " planes\n";
		str += planes[0].toString(roi);
		str += planes[1].toString(roi);
		str += planes[2].toString(roi);
		return str;
	}

	/**
	 * Scales the range of this image to byte (0..255) in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 */
	public void byteSize(ROI roi) {

		// get range of this image
		final double min = min(roi);
		final double max = max(roi);
		final double range = max - min;

		// convert to byte depth
		int red = 0;
		int green = 0;
		int blue = 0;
		Float[] color = new Float[3];
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.ly(); x++) {
				color = get(x, y);
				red = (int) ((255.0 / range) * ((double) color[0] - min));
				green = (int) ((255.0 / range) * ((double) color[1] - min));
				blue = (int) ((255.0 / range) * ((double) color[2] - min));

				// take lower 8 bits
				red = 0x00FF & red;
				green = 0x00FF & green;
				blue = 0x00FF & blue;
				color[0] = (float) red;
				color[1] = (float) green;
				color[2] = (float) blue;
				set(x, y, color);
			}
		}
	}
	
	public final RealColorImage clear() {
		return clear(new Float[] {0f, 0f, 0f});
	}
	
	/**
	 * Clears an image to a value
	 * 
	 * @param v value to clear to
	 */
	public final RealColorImage clear(Float[] v) {
		planes[0].clear(v[0]);
		planes[1].clear(v[1]);
		planes[2].clear(v[2]);
		return this;
	}

	/**
	 * Clips the range of a Region of Interest to an arbitrary min/max
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 * @param roi Region of Interest+
	 */
	public void clip(int min, int max, ROI roi) {
		// clip each plane
		planes[0].clip(min, max, roi);
		planes[1].clip(min, max, roi);
		planes[2].clip(min, max, roi);
	}
}
