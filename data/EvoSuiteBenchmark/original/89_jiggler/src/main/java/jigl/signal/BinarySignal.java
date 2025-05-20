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
package jigl.signal;

/**
 * BinarySignal is a 1-d array of byte. All the values in a BinarySignal are always either 1 or 0.
 */
public class BinarySignal implements Signal {

	/** One dimensional byte array */
	protected byte[] data;

	/** Cartesian width */
	protected int length;

	/** Creates an empty one dimensional BinarySignal with a length of zero */
	public BinarySignal() {
		length = 0;

		data = null;
	}

	/** Creates a one dimensional BinarySignal with a length of x */
	public BinarySignal(int x) {
		length = x;
		data = new byte[length];
	}

	/**
	 * Creates a one dimensional BinarySignal with a length of x from array <code>dat</code>. If
	 * <code>dat[k]>0</code>,the value of the signal is set as 1. Otherwise the value is set to 0.
	 * 
	 * @param x width of image
	 * @param dat one dimensional array of short. The array is length x.
	 */
	public BinarySignal(int x, byte[] dat) {
		length = x;

		// int count=0;

		data = new byte[length];

		for (int b = 0; b < length; b++) {

			if (dat[b] > 0)
				data[b] = 1;
			else
				data[b] = 0;
		}
	}

	/**
	 * Creates a one dimensional BinarySignal from a DiscreteSignal. It converts a DiscreteSignal to
	 * a BinarySignal.
	 */
	public BinarySignal(DiscreteSignal img) {
		length = img.length();
		data = new byte[length];
		for (int b = 0; b < length; b++)
			if (img.get(b) > 0)
				data[b] = 1;
			else
				data[b] = 0;
	}

	/**
	 * Creates a one dimensional BinarySignal from a RealSignal. It converts a RealSignal to a
	 * BinarySignal.
	 */
	public BinarySignal(RealSignal img) {
		length = img.length();

		data = new byte[length];

		for (int b = 0; b < length; b++)
			if (img.get(b) > 0)
				set(b, 1);
			else
				set(b, 0);
	}

	/**
	 * Creates a one dimensional BinarySignal (shallow copy) from BinarySignal img
	 */
	public BinarySignal(BinarySignal img) {
		length = img.length();
		data = img.data;
	}

	/**
	 * Makes a deep copy of this Signal
	 * 
	 * @param none
	 * @return a deep copy of BinarySignal
	 */
	public Signal copy() {
		BinarySignal g = new BinarySignal(length);

		System.arraycopy(data, 0, g.data, 0, length);
		/*
		 * for(int x = 0; x < length; x++) { g.data[x] = data[x]; }
		 */
		return g;
	}

	/**
	 * Returns the width (maximum length value)
	 * 
	 * @param none
	 */
	public final int length() {
		return length;
	}

	/**
	 * Returns the pixel value at the given x value
	 * 
	 * @param x the length coordinate
	 */
	public final byte get(int x) {
		return (byte) data[x];
	}

	/**
	 * Sets the pixel value at x to a given value
	 * 
	 * @param x the length coordinate
	 * @param value the value to set the pixel to
	 */
	public final void set(int x, int value) {
		if (value > 0)
			data[x] = 1;
		else
			data[x] = 0;
	}

	/**
	 * Finds the union between this signal and another BinarySignal
	 * 
	 * @return this
	 */
	public final BinarySignal union(BinarySignal signal) {
		for (int x = 0; x < length; x++)
			if (signal.get(x) != data[x])
				data[x] = 1;
		return this;
	}

	/**
	 * Finds the intersection between this image and another BinarySignal
	 * 
	 * @return this
	 */
	public final BinarySignal intersection(BinarySignal signal) {
		for (int x = 0; x < length; x++)
			if (signal.get(x) != data[x])
				data[x] = 0;

		return this;
	}

	/** Returns the complement signal of this signal */
	public final BinarySignal compliment() {
		for (int x = 0; x < length; x++)
			if (data[x] == 0)
				data[x] = 1;
			else
				data[x] = 0;

		return this;
	}

	/** Counts the number of "on" pixels */
	public final int count() {
		int count = 0;

		for (int x = 0; x < length; x++)
			if (data[x] != 0)
				count++;

		return count;
	}

	/**
	 * Returns the difference of this signal and a BinarySignal
	 * 
	 * @return this
	 */
	public final BinarySignal difference(BinarySignal signal) {
		for (int x = 0; x < length; x++)
			if ((data[x] == 1) && (signal.get(x) == 0))
				data[x] = 1;
			else if ((data[x] == 0) && (signal.get(x) == 1))
				data[x] = 1;
			else
				data[x] = 0;

		return this;
	}

	/**
	 * Performs a shift on this image. The new part of the signal is set to 0.
	 * 
	 * @param horizontal for right shift if horizontal is positive and for left if it is negative
	 * @return this
	 */
	public final BinarySignal shift(int horizontal) {
		BinarySignal image2 = (BinarySignal) this.copy();
		byte set1 = 0;

		for (int x = 0; x < length; x++) {
			if (x - horizontal < 0 || x - horizontal >= length)
				set1 = 0;
			else
				set1 = image2.get(x - horizontal);
			data[x] = set1;
		}
		return this;
	}

}
