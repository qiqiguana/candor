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
package jigl.image.ops.levelOps;

import jigl.image.ROI;
import jigl.image.ops.SimpleOperator;
import jigl.image.types.GrayImage;
import jigl.image.types.RealGrayImage;

/**
 * Retrieve the lookup table of a grayscale image. Supports GrayImage, RealGrayImage.
 * <p>
 * For each current grayscale value in a image, the lookup table contains a corresponding grayscale
 * values in the equalized image.
 */
public class LookupTable extends SimpleOperator {
	/** A array to store the lookup table */
	private float[] table = null;
	/** The start grayscale value in the histogram */
	private int min = 0;
	/** The length of lookup table */
	private int max = 0;
	/** <code>table[0]</code> */
	private int clip_min = 0;
	/** <code>table[max-1]</code> */
	private int clip_max = 0;

	/**
	 * Creates a LookupTable
	 * 
	 * @param data an array containing the data to be loaded
	 * @param start the lowest value
	 * @param min_val value for any pixel lower than start
	 * @param max_val value for any pixel larger than the high
	 */
	public LookupTable(float[] data, int start, int min_val, int max_val) {
		table = data;
		min = start;
		max = data.length;
		clip_min = min_val;
		clip_max = max_val;
	}

	/**
	 * Applies lookup table to <code>image</code> and return the equalized image.
	 * 
	 * @param image GrayImage to apply lookup table to.
	 * @return <code>image</code>
	 */
	protected GrayImage apply(GrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Applies lookup table to <code>image</code> and return the equalized image.
	 * 
	 * @param image RealGrayImage to apply lookup table to.
	 * @return <code>image</code>
	 */
	protected RealGrayImage apply(RealGrayImage gr) {
		return apply(gr, new ROI(0, 0, gr.X() - 1, gr.Y() - 1));
	}

	/**
	 * Applies lookup table to <code>image</code> in a specific Region of Interest.
	 * 
	 * @param image GrayImage to apply lookup table to.
	 * @param roi Region of Interest of image
	 * @return <code>image</code>
	 */
	protected GrayImage apply(GrayImage image, ROI roi) {

		int val = 0;

		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				val = image.get(x, y) - min;//absolute value above min
				if (val < 0)
					image.set(x, y, clip_min);
				else if (val > max)
					image.set(x, y, clip_max);
				else
					image.set(x, y, (int) table[val]);
			}
		}
		return image;
	}

	/**
	 * Applies lookup table to <code>image</code> in a specific Region of Interest.
	 * 
	 * @param image RealGrayImage to apply lookup table to.
	 * @param roi Region of Interest of image
	 * @return <code>image</code>
	 */
	protected RealGrayImage apply(RealGrayImage image, ROI roi) {

		float val = 0;

		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				val = (float) image.get(x, y) - (float) min;
				if (val < 0)
					image.set(x, y, (float) clip_min);
				else if (val > max)
					image.set(x, y, (float) clip_max);
				else
					image.set(x, y, table[(int) val]);
			}
		}
		return image;
	}

}
