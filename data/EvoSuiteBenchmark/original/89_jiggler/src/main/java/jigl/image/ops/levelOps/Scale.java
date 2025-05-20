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
 * Performs a Scale operation on an image. <code>image</code> is changed in all the methods of this
 * class. Supports GrayImage, RealGrayImage, ColorImage, RealColorImage.
 */
public class Scale extends SimpleOperator {
	/** maximum value of the new image. */
	private float float_min;
	/** Minimum value of the new image. */
	private float float_max;

	/** Initializes Scale for use with integers */
	public Scale(int min, int max) {
		float_min = min;
		float_max = max;
	}

	/** Initializes Scale for use with floats */
	public Scale(float min, float max) {
		float_min = min;
		float_max = max;
	}

	/**
	 * Scales the range of <code>image</code> to an arbitrary min/max.
	 * 
	 * @param image GrayImage to scale.
	 * @return <code>image</code>.
	 */
	protected Image apply(GrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Scales the range of <code>image</code> to an arbitrary min/max in a Region of Interest.
	 * 
	 * @param image GrayImage to scale.
	 * @param roi Region of Interest of <code>image</code>
	 * @return <code>image</code>.
	 */
	protected Image apply(GrayImage image, ROI roi) {
		int int_max = (int) float_max;
		int int_min = (int) float_min;

		float min = image.min(roi);
		float max = image.max(roi);
		float r = 0;
		float range = max - min;

		if (range == 0)
			range = 1;//to avoid divide by zero problems

		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				r = (float) image.get(x, y);
				value = (((r - min) / (range) * (int_max - int_min)) + int_min);
				image.set(x, y, (int) value);
			}
		}
		return image;
	}

	/**
	 * Scales the range of <code>image</code> to an arbitrary min/max.
	 * 
	 * @param image RealGrayImage to scale.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Scales the range of <code>image</code> to an arbitrary min/max in a Region of Interest.
	 * 
	 * @param image RealGrayImage to scale.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image, ROI roi) {
		float min = image.min(roi);
		float max = image.max(roi);

		float r = 0;
		float range = max - min;

		if (range == 0)
			range = 1;//to avoid divide by zero (r-min will be zero so value will always be float_min)

		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				r = (float) image.get(x, y);
				value = ((float) ((r - min) / (range)) * (float_max - float_min)) + float_min;
				image.set(x, y, (float) value);
			}
		}
		return image;
	}

	/**
	 * Scales the range of <code>image</code> to an arbitrary min/max. The range used for all planes
	 * is the global max, min of the image.
	 * 
	 * @param image ColorImage to scale.
	 * @return <code>image</code>.
	 */
	protected Image apply(ColorImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Scales the range of <code>image</code> to an arbitrary min/max in a Region of Interest. The
	 * range used for all planes is the global max, min of the image.
	 * 
	 * @param image ColorImage to scale.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(ColorImage image, ROI roi) {
		GrayImage plane;
		int int_max = (int) float_max;
		int int_min = (int) float_min;
		float min = (float) image.min(roi);
		float max = (float) image.max(roi);
		float r = 0;
		float range = max - min;
		float value = 0;
		if (range == 0)
			range = 1;//to avoid divide by zero problems

		for (int i = 0; i < 3; i++) {
			plane = image.plane(i);

			for (int y = roi.uy(); y <= roi.ly(); y++) {
				for (int x = roi.ux(); x <= roi.lx(); x++) {
					r = (float) plane.get(x, y);
					value = (((r - min) / (range) * (int_max - int_min)) + int_min);
					plane.set(x, y, (int) value);
				}
			}

			image.setPlane(i, plane);
		}

		return image;
	}

	/**
	 * Scales the range of <code>image</code> to an arbitrary min/max. The range used for all planes
	 * is the global max, min of the image.
	 * 
	 * @param image RealColorImage to scale.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealColorImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Scales the range of <code>image</code> to an arbitrary min/max in a Region of Interest. The
	 * range used for all planes is the global max, min of the image.
	 * 
	 * @param image RealColorImage to scale.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealColorImage image, ROI roi) {
		RealGrayImage plane;

		float min = image.min(roi);
		float max = image.max(roi);
		float r = 0;
		float range = max - min;
		float value = 0;
		if (range == 0)
			range = 1;//to avoid divide by zero problems

		for (int i = 0; i < 3; i++) {
			plane = image.plane(i);

			for (int y = roi.uy(); y <= roi.ly(); y++) {
				for (int x = roi.ux(); x <= roi.lx(); x++) {
					r = plane.get(x, y);
					value = (((r - min) / (range) * (float_max - float_min)) + float_min);
					plane.set(x, y, value);
				}
			}

			image.setPlane(i, plane);
		}

		return image;
	}

	/**
	 * For commandline opertion. The syntax is "java Scale <u>min_value</u> <u>max_value</u>
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

			Scale scale = new Scale(val1, val2);

			image2 = scale.apply(image);

			// create a new ImageOutputStream
			ImageOutputStream os = new ImageOutputStream(argv[3]);
			os.write(image2);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
