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
package jigl.image;

import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.exceptions.InvalidArgumentException;
import jigl.image.types.GrayImage;
import jigl.image.types.RealGrayImage;
import jigl.signal.RealSignal;

/**
 * Histogram keeps track of histogram information for an image. The range of histogram's grayscale
 * starts from <i>min()</i> and ends with <i>max()</i>. Supports only GrayImage and RealGrayImage.
 */
public class Histogram {
	/** An array stores the counts for every grayscale */
	protected int[] values = null;

	/** The maximum grayscale value in the histogram */
	protected float min = 0;

	/** The minimum grayscale value in the histogram */
	protected float max = 0;

	/** The "bucket" size for the histogram */
	protected float inc = 0;

	/**
	 * Creates a Histogram from <i>image</i>. <i>image</i> can only be GrayImage or RealGrayImage.
	 * 
	 * @param image GrayImage or RealGrayImage to create histogram from.
	 * @param interval the "bucket" size for the histogram. A value of 1 with a GrayImage will leave
	 *            a bucket for every grayscale value between image.max() and image.min().
	 * @throws ImageNotSupportedException if <i>image</i> is <i>null</i> or not a GrayImage or
	 *             RealGrayImage.
	 */
	public Histogram(Image image, int interval) throws ImageNotSupportedException {
		int number = 0;

		GrayImage gimage = null;
		RealGrayImage rgimage = null;

		if (image instanceof GrayImage) {
			gimage = (GrayImage) image;
			max = gimage.max();
			min = gimage.min();

			number = (int) (max - min);
			int val = 0;
			values = new int[(number / interval) + 1];
			for (int x = 0; x < image.X(); x++) {
				for (int y = 0; y < image.Y(); y++) {
					val = (gimage.get(x, y) / interval);
					values[(int) (val - min)] = values[(int) (val - min)] + 1;
				}
			}
		} else if (image instanceof RealGrayImage) {
			rgimage = (RealGrayImage) image;
			max = rgimage.max();
			min = rgimage.min();
			number = (int) (max - min);
			float val = 0;
			values = new int[(number / interval) + 1];
			for (int x = 0; x < image.X(); x++) {
				for (int y = 0; y < image.Y(); y++) {
					val = (rgimage.get(x, y) / interval);
					values[(int) (val - min)] = values[(int) (val - min)] + 1;

				}
			}
		} else
			throw new ImageNotSupportedException();
		inc = interval;
	}

	/**
	 * Creates a Histogram from <i>image</i>. <i>image</i> can only be GrayImage or RealGrayImage.
	 * 
	 * @param image GrayImage or RealGrayImage to create histogram from.
	 * @param interval the "bucket" size for the histogram. A value of 1 with a GrayImage will leave
	 *            a bucket for every grayscale value between image.max() and image.min().
	 * @param int_min minimum value in the histogram
	 * @param int_max maximum value in the histogram
	 * @throws ImageNotSupportedException if <i>image</i> is <i>null</i> or not a GrayImage or
	 *             RealGrayImage.
	 */
	public Histogram(Image image, int interval, int int_min, int int_max)
			throws ImageNotSupportedException {
		int number = 0;

		GrayImage gimage = null;
		RealGrayImage rgimage = null;

		if (image instanceof GrayImage) {
			gimage = (GrayImage) image;
			// int minval=gimage.min();
			// int maxval=gimage.max();
			max = int_max;
			min = int_min;

			number = (int) (max - min);
			int val = 0;
			values = new int[(number / interval) + 1];
			for (int x = 0; x < image.X(); x++) {
				for (int y = 0; y < image.Y(); y++) {
					if (val >= min && val < max)
						val = (gimage.get(x, y) / interval);
					else
						val = (int) min;
					values[(int) (val - min)] = values[(int) (val - min)] + 1;
				}
			}
		} else if (image instanceof RealGrayImage) {
			rgimage = (RealGrayImage) image;
			// float minval=rgimage.min();
			// float maxval=rgimage.max();
			max = int_max;
			min = int_min;
			number = (int) (max - min);
			float val = 0;
			values = new int[(number / interval) + 1];
			for (int x = 0; x < image.X(); x++) {
				for (int y = 0; y < image.Y(); y++) {
					if (val >= min && val < max)
						val = (rgimage.get(x, y) / interval);
					else
						val = min;
					values[(int) (val - min)] = values[(int) (val - min)] + 1;

				}
			}
		} else
			throw new ImageNotSupportedException();
		inc = interval;

	}

	/**
	 * Creates a Histogram from this image over a Region of Interest. <i>image</i> can only be
	 * GrayImage or RealGrayImage.
	 * 
	 * @param image GrayImage or RealGrayImage to create histogram from.
	 * @param interval the "bucket" size for the histogram. A value of 1 with a GrayImage will leave
	 *            a bucket for every grayscale value between image.max() and image.min().
	 * @param r defines the interest region in the <i>image</i>.
	 * @throws ImageNotSupportedException if </i>image</i> is </i>null</i> or not a GrayImage or
	 *             RealGrayImage.
	 */
	public Histogram(Image image, int interval, jigl.image.ROI r) throws ImageNotSupportedException {
		int number = 0;
		GrayImage gimage = null;
		RealGrayImage rgimage = null;

		if (image instanceof GrayImage) {
			gimage = (GrayImage) image;
			max = gimage.max(r);
			min = gimage.min(r);
			number = (int) (max - min);
			values = new int[(number / interval) + 1];
			for (int x = r.ux(); x <= r.lx(); x++) {
				for (int y = r.ux(); y <= r.lx(); y++) {
					values[gimage.get(x, y) / interval] = values[gimage.get(x, y) / interval] + 1;
				}
			}
		} else if (image instanceof RealGrayImage) {
			rgimage = (RealGrayImage) image;
			max = rgimage.max(r);
			min = rgimage.min(r);
			number = (int) (max - min);
			values = new int[(number / interval) + 1];
			for (int x = r.ux(); x <= r.lx(); x++) {
				for (int y = r.ux(); y <= r.lx(); y++) {
					values[(int) ((rgimage.get(x, y) / interval) + min)] = values[(int) ((rgimage
							.get(x, y) / interval) + min)] + 1;
				}
			}
		} else
			throw new ImageNotSupportedException();
		inc = interval;
	}

	/**
	 * Creates an empty Histogram of size <code>(max-min)/interval + 1</code>.
	 * 
	 * @param image GrayImage or RealGrayImage to create histogram from.
	 * @param min minimum grayscale value.
	 * @param max maximum grayscale value.
	 * @param interval grayscale values per bucket.
	 */
	public Histogram(int min, int max, int interval) {
		inc = interval;
		values = new int[(max - min) / interval + 1];
		this.min = min;
		this.max = max;
	}

	/**
	 * Creates a histogram from <code>data</code>. <code>data</code> must be of length
	 * <code>(max-min)/interval + 1</code>. If <code>data</code> is </i>null</i>, a
	 * {@link java.lang.NullPointerException NullPointerException} will be thrown.
	 * 
	 * @param data histogram data.
	 * @param min minimum grayscale value.
	 * @param max maximum grayscale value.
	 * @param interval grayscale values per bucket.
	 * @throws InvalidArgumentException if <code>data</code> is not the exact length.
	 */
	public Histogram(int[] data, int min, int max, int interval) throws InvalidArgumentException {
		if (data.length != (max - min) / interval + 1)
			throw new InvalidArgumentException();

		values = data.clone();
		this.min = min;
		this.max = max;
		inc = interval;
	}
	
	public Histogram(Histogram copyMe) {
		this.values = copyMe.values.clone();
		this.min = copyMe.min;
		this.max = copyMe.max;
		this.inc = copyMe.inc;
	}

	/** Returns a deep copy of the histogram data. */
	public int[] getData() {
		return values.clone();
	}

	/**
	 * Increments at the given value. Please note, all calculations are internal. For Example,
	 * suppose min=-20 and max=20, if you want to increment the value at -20 then pass in -20
	 * <i>not</i> 0.
	 */
	public void increment(int value) {
		incrementCount(value, 1);
	}
	
	public void incrementCount(int value, int incrementAmount) {
		values[(int) ((value - min) / inc)]+=incrementAmount;
	}

	/**
	 * Decrements at the given value. Please note, all calculations are internal.
	 * <code>value<code> should be between <code>min</code> and <code>max</code>.
	 * <p>
	 * For Example, suppose min=-20 and max=20, if you want to decrement the value at -20 then pass
	 * in -20 <i>not</i> 0.
	 */
	public void decrement(int value) {
		values[(int) ((value - min) / inc)]--;
	}

	/**
	 * Sets the count for a histogram value. <code>value<code>
	should be between <code>min</code> and <code>max</code>.
	 */
	public void setCount(int value, int count) {
		values[(int) ((value - min) / inc)] = count;
	}

	/**
	 * Returns the count at the given value. Please note, all calculations are internal. For
	 * Example, suppose min=-20 and max=20, if you want to get the value at -20 then pass in -20
	 * <i>not</i> 0.
	 */
	public int count(int value) {
		return values[(int) ((value - min) / inc)];
	}

	/**
	 * Turns this Histogram into a signal. The length of this signal is
	 * <code>(int)((max-min)/inc) + 1</code> and may not be 255.
	 */
	public RealSignal toSignal() {
		RealSignal signal = new RealSignal((int) ((max - min) / inc) + 1);
		for (int x = 0; x < ((max - min) / inc); x++) {
			signal.set(x, values[x]);
		}
		return signal;
	}

	/** Returns the minimum grayscale value for this histogram. */
	public float min() {
		return min;
	}

	/** Returns the maximum grayscale value for this histogram. */
	public float max() {
		return max;
	}

	/** Returns the incrementation value (interval). */
	public float inc() {
		return inc;
	}

	/** Returns the size of the data array */
	public int length() {
		return values.length;
	}
}
