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

import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;

import jigl.image.Image;
import jigl.image.ROI;

/**
 * BinaryImage is implemented by a 2-d array of <i>byte</i>. All the values in a BinaryImage are
 * either 1(white) or 0(black).
 */

public class BinaryImage implements Image<Byte> {

	/** Two dimensional byte array */
	protected byte[][] data;

	/** Cartesian width */
	protected int X;

	/** Cartesian height */
	protected int Y;

	/**
	 * Creates an empty two dimensional BinaryImage with a height and width of zero
	 */
	public BinaryImage() {
		X = 0;
		Y = 0;
		data = null;
	}

	/**
	 * Creates a two dimensional BinaryImage with a width and height of x and y repectively
	 * 
	 * @param x width of image
	 * @param y height of image
	 */
	public BinaryImage(int x, int y) {
		X = x;
		Y = y;
		data = new byte[Y][X];
	}

	/**
	 * Gets the JavaImage from a JiglImage
	 * 
	 * @see java.awt.image.ImageProducer
	 * @return java.awt.image.ImageProducer
	 */
	public ImageProducer getJavaImage() {

		// get range of this image
		int min = 0;
		int max = 1;

		// keep byte images in original range
		if (min >= 0 && max <= 255) {
			min = 0;
			max = 255;
		}
		int range = max - min;

		// convert jigl image to java image
		int pix[] = new int[X * Y];
		int index = 0;
		int value = 0;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {

				// scale image values
				value = (int) ((255.0 / range) * ((float) data[y][x] - min));

				value = 0x00FF & value; // take lower 8 bits

				// put this pixel in the java image
				pix[index] = (0xFF << 24) | (value << 16) | (value << 8) | value;
				index++;
			}
		}

		// return java image
		return new MemoryImageSource(X, Y, pix, 0, X);
	}

	/**
	 * Creates a two dimensional BinaryImage with a width and height of x and y repectively. Image
	 * values are 1 if <code><i>data[][] > 0</i></code>, 0 otherwise.
	 * 
	 * @param x width of image
	 * @param y height of image
	 * @param data one dimensional array of <i>byte</i>. The array is length x*y.
	 */
	public BinaryImage(int x, int y, byte[] data) {
		X = x;
		Y = y;

		this.data = new byte[Y][X];

		for (int a = 0; a < Y; a++) {
			for (int b = 0; b < X; b++) {

				if (data[a * Y + b] > 0)
					this.data[a][b] = 1;
				else
					this.data[a][b] = 0;
			}
		}
	}

	/**
	 * Creates a BinaryImage from <i>data</i>. The size is <i>data.length</i> in y, and
	 * max(<i>data.length</i>) in x. Image values are 1 if <code><i>data[][] > 0</i></code>, 0
	 * otherwise.
	 * 
	 * @param data two dimensional array of <i>byte</i>.
	 */
	public BinaryImage(byte[][] data) {
		int max = 0;
		Y = data.length;

		//find the max length of data
		for (int y = 0; y < Y; y++) {
			if (data[y].length > max) {
				max = data[y].length;
			}
		}
		X = max;

		this.data = new byte[Y][X];

		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {

				if (data[y][x] > 0)
					this.data[y][x] = 1;
				else
					this.data[y][x] = 0;
			}
		}
	}

	/**
	 * Creates a two dimensional BinaryImage from a GrayImage. Any pixel value above 0 is assigned
	 * the value 1.
	 * 
	 * @param image GrayImage
	 */
	public BinaryImage(GrayImage image) {
		X = image.X();
		Y = image.Y();
		data = new byte[Y][X];
		for (int a = 0; a < X; a++)
			for (int b = 0; b < Y; b++)
				if (image.get(a, b) > 0) {
					data[b][a] = 1;
				} else
					data[b][a] = 0;
	}

	/**
	 * Creates a two dimensional BinaryImage from a RealGrayImage. Any pixel value above 0 is
	 * assigned the value 1.
	 * 
	 * @param image RealGrayImage
	 */
	public BinaryImage(RealGrayImage image) {
		X = image.X();
		Y = image.Y();
		data = new byte[Y][X];
		for (int a = 0; a < X; a++)
			for (int b = 0; b < Y; b++)
				if (image.get(a, b) > 0)
					data[b][a] = 1;//set(a,b,1);
				else
					data[b][a] = 0;//set(a,b,0);
	}

	/**
	 * Creates a two dimensional BinaryImage (shallow copy of data) from <i>image</i>.
	 * 
	 * @param image BinaryImage
	 */
	public BinaryImage(BinaryImage image) {
		X = image.X();
		Y = image.Y();
		data = image.data;
	}

	/**
	 * Returns a deep copy of the image data.
	 * 
	 * @return two-dimensional <i>byte</i> array (deep copy).
	 */
	public Byte[][] getData() {
		Byte[][] deepcopy = new Byte[Y][X];

		for (int y = 0; y < Y; y++) {
			System.arraycopy(data[y], 0, deepcopy[y], 0, data[y].length);
		}

		return deepcopy;
	}

	/**
	 * Makes a deep copy of this image
	 * 
	 * @return a deep copy of BinaryImage
	 */
	public Image copy() {
		BinaryImage b = new BinaryImage(X, Y);

		for (int y = 0; y < Y; y++) {
			System.arraycopy(data[y], 0, b.data[y], 0, data[y].length);
		}

		return b;
	}

	/**
	 * Makes a deep copy of this image in a Region of Interest
	 * 
	 * @param roi Region of Interest of the image
	 * @return a deep copy of BinaryImage
	 */
	public Image copy(ROI roi) {
		BinaryImage g = new BinaryImage(roi.X(), roi.Y());

		for (int y = roi.uy(); y <= roi.ly(); y++) {
			System.arraycopy(data[y], roi.ux(), g.data[y - roi.uy()], 0, roi.X());
		}
		return g;
	}

	/**
	 * Returns the width (maximum X value)
	 * 
	 * @return width of the image.
	 */
	public final int X() {
		return X;
	}

	/**
	 * Returns the height (maximum Y value)
	 * 
	 * @return height of image.
	 */
	public final int Y() {
		return Y;
	}

	/**
	 * Returns the pixel value at the given x, y value
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @return pixel value.
	 */
	public final Byte get(int x, int y) {
		return data[y][x];
	}

	/**
	 * Sets the pixel value at x, y to a given value
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param value the value to set the pixel to if greater than zero, it is given a value of 1,
	 *            and 0 otherwise.
	 */
	public final void set(int x, int y, Byte value) {
		if (value > 0)
			data[y][x] = 1;
		else
			data[y][x] = 0;
	}
	
	@Override
	public void set(int x, int y, Byte value, ROI roi) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Finds the union between this image and another BinaryImage
	 * 
	 * @param image BinaryImage
	 * @return this
	 */
	public final BinaryImage union(BinaryImage image) {

		for (int y = 0; y < Y; y++)
			for (int x = 0; x < X; x++)
				if (image.get(x, y) != data[y][x])
					data[y][x] = 1;

		return this;
	}

	/**
	 * Finds the intersection between this image and another BinaryImage. Return a Binary image
	 * whose pixel is on when both pixels are on in that position.
	 * 
	 * @param image BinaryImage
	 * @return this
	 */
	public final BinaryImage intersection(BinaryImage image) {
		for (int y = 0; y < Y; y++)
			for (int x = 0; x < X; x++)
				if (image.get(x, y) != data[y][x])
					data[y][x] = 0;

		return this;
	}

	/**
	 * Computes the complement of this image
	 * 
	 * @return this
	 */
	public final BinaryImage compliment() {
		for (int y = 0; y < Y; y++)
			for (int x = 0; x < X; x++)
				if (data[y][x] == 0)
					data[y][x] = 1;
				else
					data[y][x] = 0;

		return this;
	}

	/**
	 * Counts the number of "on" pixels
	 * 
	 * @return number of pixels of value 1.
	 */
	public final int count() {
		int count = 0;
		for (int y = 0; y < Y; y++)
			for (int x = 0; x < X; x++)
				if (data[y][x] != 0)
					count++;

		return count;
	}

	/**
	 * Returns the difference of this image and a BinaryImage
	 * 
	 * @param image BinaryImage
	 * @return this
	 */
	public final BinaryImage difference(BinaryImage image) {
		for (int y = 0; y < Y; y++)
			for (int x = 0; x < X; x++)
				if ((data[y][x] == 1) && (image.get(x, y) == 0))
					data[y][x] = 1;
				else if ((data[y][x] == 1) && (image.get(x, y) == 0))
					data[y][x] = 1;
				else
					data[y][x] = 0;

		return this;
	}

	/**
	 * Performs a shift on this image with out wrap.
	 * 
	 * @param horizonal for right shift horizonal is positive for left it is negative
	 * @param vertical for down shift vertical is positive for up it is negative
	 * @return this
	 */
	public final BinaryImage shift(int horizonal, int vertical) {
		BinaryImage image2 = (BinaryImage) this.copy();
		byte set1 = 0;
		for (int y = 0; y < Y; y++)
			for (int x = 0; x < X; x++) {
				if (y - vertical < 0 || y - vertical >= Y)
					set1 = 0;
				else if (x - horizonal < 0 || x - horizonal >= X)
					set1 = 0;
				else
					set1 = image2.get(x - horizonal, y - vertical);
				data[y][x] = set1;
			}
		return this;
	}

	@Override
	public BinaryImage clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public BinaryImage clear(Byte constant) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Byte max() {
		return Integer.valueOf(1).byteValue();
	}

	@Override
	public Byte min() {
		return Integer.valueOf(0).byteValue();
	}

}
