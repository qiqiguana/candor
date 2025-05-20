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

import jigl.image.Image;
import jigl.image.ROI;
import jigl.image.io.ImageInputStream;
import jigl.image.io.ImageOutputStream;
import jigl.image.ops.SimpleOperator;
import jigl.image.types.GrayImage;
import jigl.image.types.RealGrayImage;

/**
 * Performs a Threshold operation on an image. When the value is greater than or equals threshold
 * value it is reset to 255. Otherwise it is reset to 0. Supports GrayImage, RealGrayImage.
 * <code>image</code> is changed in all the methods of this class.
 */
public class Threshold extends SimpleOperator {
	/** Threshold value. */
	private int int_max;
	/** Threshold value */
	private float float_max;

	/** Initializes Threshold for use with integers */
	public Threshold(int max) {
		int_max = max;
	}

	/** Initializes Threshold for use with floats */
	public Threshold(float max) {
		float_max = max;
	}

	/**
	 * Thresholds the range of <code>image</code> according to an arbitrary threshold value.
	 * 
	 * @param image GrayImage to threshold.
	 * @return <code>image</code>.
	 */
	protected Image apply(GrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Thresholds the range of <code>image</code> according to an arbitrary threshold value.
	 * 
	 * @param image RealGrayImage to threshold.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Thresholds the range of <code>image</code> according to an arbitrary threshold value in a
	 * Region of Interest.
	 * 
	 * @param image GrayImage to threshold.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(GrayImage image, ROI roi) {
		if (int_max == 0) {
			int_max = (int) float_max;
		}
		int value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = image.get(x, y);
				if (value >= int_max)
					value = 255;
				else
					value = 0;
				image.set(x, y, value);
			}
		}
		return image;
	}

	/**
	 * Thresholds the range of <code>image</code> according to an arbitrary threshold value in a
	 * Region of Interest.
	 * 
	 * @param image RealGrayImage to threshold.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image, ROI roi) {
		if (float_max == 0) {
			float_max = (float) int_max;
		}
		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = image.get(x, y);
				if (value >= float_max)
					value = 255f;
				else
					value = 0f;
				image.set(x, y, value);
			}
		}
		return image;
	}

	/**
	 * For commandline opertion. The syntax is "java Threshold <u>threshold_value</u>
	 * <u>input_filename</u> <u>output_filename</u>".
	 */
	public static void main(String[] argv) {

		try {
			Image image = null;
			String inputfile = argv[1];
			Image image2 = null;

			ImageInputStream is = new ImageInputStream(inputfile);
			image = is.read();
			is.close();

			Float f_val1 = Float.valueOf(argv[0]);
			float val1 = f_val1.floatValue();

			Threshold threshold = new Threshold(val1);

			image2 = threshold.apply(image);

			//put command line stuff here.

			// create a new ImageOutputStream
			ImageOutputStream os = new ImageOutputStream(argv[2]);
			os.write(image2);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
