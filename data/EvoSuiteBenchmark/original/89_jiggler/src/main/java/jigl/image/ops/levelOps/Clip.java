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
import jigl.image.types.ColorImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * Performs a clip operation on an image to an arbitrary min/max. <code>image</code> is changed in
 * all the methods of this class. Supports GrayImage, RealGrayImage, ColorImage, RealColorImage.
 */
public class Clip extends SimpleOperator {
	/** Minimum value of the clipping range. For GrayImage and ColorImage. */
	private int int_min;
	/** Maximum value of the clipping range. For GrayImage and ColorImage. */
	private int int_max;
	/** Minimum value of the clipping range. For RealGrayImage and RealColorImage. */
	private float float_min;
	/** Maximum value of the clipping range. For RealGrayImage and RealColorImage. */
	private float float_max;

	/** Initializes Clip for use with integers. */
	public Clip(int min, int max) {
		int_min = min;
		int_max = max;
	}

	/** Initializes clip for use with floats. */
	public Clip(float min, float max) {
		float_min = min;
		float_max = max;
	}

	/**
	 * Clips the range of <code>image</code> to an arbitrary min/max.
	 * 
	 * @param image GrayImage to clip.
	 * @return <code>image</code>.
	 */
	protected Image apply(GrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Clips the range of <code>image</code> to an arbitrary min/max in a Region of Interest.
	 * 
	 * @param image GrayImage to clip.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(GrayImage image, ROI roi) {
		if ((int_min == 0) && (int_max == 0)) {
			int_min = (int) float_min;
			int_max = (int) float_max;
		}
		int value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = image.get(x, y);
				value = (value > int_max) ? int_max : value;
				value = (value < int_min) ? int_min : value;
				image.set(x, y, value);
			}
		}
		return image;
	}

	/**
	 * Clips the range of <code>image<code> to an arbitrary min/max.
	 * 
	 * @param image RealGrayImage to clip.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Clips the range of <code>image</code> to an arbitrary min/max in a Region of Interest.
	 * 
	 * @param image RealGrayImage to clip.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image, ROI roi) {
		if ((float_min == 0) && (float_max == 0)) {
			float_min = (float) int_min;
			float_max = (float) int_max;
		}
		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = image.get(x, y);
				value = (value > float_max) ? float_max : value;
				value = (value < float_min) ? float_min : value;
				image.set(x, y, value);
			}
		}
		return image;
	}

	/**
	 * Clips the range of <code>image</code> to an arbitrary min/max. This done by clipping each
	 * plane separately.
	 * 
	 * @param image ColorImage to clip.
	 * @return <code>image</code>.
	 */
	protected Image apply(ColorImage image) {
		image.setPlane(0, (GrayImage) apply(image.plane(0), new ROI(0, 0, image.X() - 1,
				image.Y() - 1)));
		image.setPlane(1, (GrayImage) apply(image.plane(1), new ROI(0, 0, image.X() - 1,
				image.Y() - 1)));
		image.setPlane(2, (GrayImage) apply(image.plane(2), new ROI(0, 0, image.X() - 1,
				image.Y() - 1)));

		return image;
	}

	/**
	 * Clips the range of <code>image</code> to an arbitrary min/max in a Region of Interest. This
	 * done by clipping each plane separately.
	 * 
	 * @param image ColorImage to clip.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(ColorImage image, ROI roi) {
		image.setPlane(0, (GrayImage) apply(image.plane(0), roi));
		image.setPlane(1, (GrayImage) apply(image.plane(1), roi));
		image.setPlane(2, (GrayImage) apply(image.plane(2), roi));

		return image;
	}

	/**
	 * Clips the range of this image to an arbitrary min/max in a Region of Interest. This is done
	 * by clipping each plane separately.
	 * 
	 * @param image RealColorImage to clip.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealColorImage image) {
		image.setPlane(0, (RealGrayImage) apply(image.plane(0), new ROI(0, 0, image.X() - 1, image
				.Y() - 1)));
		image.setPlane(1, (RealGrayImage) apply(image.plane(1), new ROI(0, 0, image.X() - 1, image
				.Y() - 1)));
		image.setPlane(2, (RealGrayImage) apply(image.plane(2), new ROI(0, 0, image.X() - 1, image
				.Y() - 1)));

		return image;
	}

	/**
	 * Clips the range of this image to an arbitrary min/max in a Region of Interest. This is done
	 * by clipping each plane separately.
	 * 
	 * @param image RealColorImage to clip.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealColorImage image, ROI roi) {
		image.setPlane(0, (RealGrayImage) apply(image.plane(0), roi));
		image.setPlane(1, (RealGrayImage) apply(image.plane(1), roi));
		image.setPlane(2, (RealGrayImage) apply(image.plane(2), roi));

		return image;
	}

	/**
	 * For commandline opertion. The syntax is "java Clip <u>float_min</u> <u>float_max</u>
	 * <u>input_filename</u> <u>output_filename</u>".
	 */
	public static void main(String[] argv) {

		try {
			Image image = null;
			String inputfile = argv[2];
			Image image2 = null;

			ImageInputStream is = new ImageInputStream(inputfile);
			image = is.read();
			is.close();

			Float f_val1 = Float.valueOf(argv[0]);
			Float f_val2 = Float.valueOf(argv[1]);
			float val1 = f_val1.floatValue();
			float val2 = f_val2.floatValue();

			Clip clip = new Clip(val1, val2);

			image2 = clip.apply(image);

			//put command line stuff here.

			// create a new ImageOutputStream
			ImageOutputStream os = new ImageOutputStream(argv[3]);
			os.write(image2);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
