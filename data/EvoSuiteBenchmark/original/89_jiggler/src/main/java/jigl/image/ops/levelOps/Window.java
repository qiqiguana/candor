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
import jigl.image.utils.ColorSpace;
import jigl.image.utils.ImageConverter;

/**
 * Performs a Window operation on an image. <code>image</code> is changed in all the methods of this
 * class. Supports GrayImage, RealGrayImage, ColorImage, RealColorImage.
 * <P>
 * It is combination of class ByteSize and Clip. Its methods ByteSize any value among
 * <code>[min .. max]</code> and clip any value beyond that range.
 */
public class Window extends SimpleOperator {
	/** Minimum value for window */
	private int int_min = 0;
	/** Maximum value for window */
	private int int_max = 0;
	/** Minimum value for window */
	private float float_min = 0;
	/** Maximum value for window */
	private float float_max = 0;

	/** Initializes Window for use with integers. To be used for GrayImages. */
	public Window(int min, int max) {
		int_min = min;
		int_max = max;
	}

	/**
	 * Initilizes Window for use with floats. To be used for RealGray, Color, and RealColorImages.
	 * For RealGrayImages, min and max are RGB range numbers (1..255). For Color and
	 * RealColorImages, the min and max are HSV values (0..1).
	 */
	public Window(float min, float max) {
		float_min = min;
		float_max = max;
	}

	/**
	 * Windows the range of <code>image</code> to an arbitrary min/max.
	 * 
	 * @param image GrayImage to window.
	 * @return <code>image</code>.
	 */
	protected Image apply(GrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Windows the range of <code>image</code> to an arbitrary min/max.
	 * 
	 * @param image RealGrayImage to window.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Windows the range of <code>image</code> to an arbitrary min/max in a Region of Interest.
	 * 
	 * @param image GrayImage to window.
	 * @param roi Region of Interest of <code>image</code>
	 * @return <code>image</code>.
	 */
	protected Image apply(GrayImage image, ROI roi) {
		if (int_min == 0 && int_max == 0) {
			int_min = (int) float_min;
			int_max = (int) float_max;
		}
		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = image.get(x, y);
				if (value >= int_max)
					value = 255;
				else if (value <= int_min)
					value = 0;
				else
					value = (255 * ((value - int_min) / (int_max - int_min)));
				image.set(x, y, (int) value);
			}
		}
		return image;
	}

	/**
	 * Windows the range of <code>image</code> to an arbitrary min/max in a Region of Interest.
	 * 
	 * @param image RealGrayImage to window.
	 * @param roi Region of Interest of <code>image</code>
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image, ROI roi) {
		if (float_min == 0 && float_max == 0) {
			float_min = (float) int_min;
			float_max = (float) int_max;
		}
		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = image.get(x, y);
				if (value >= float_max)
					value = 255;
				else if (value <= float_min)
					value = 0;
				else
					value = 255 * ((value - float_min) / (float_max - float_min));
				image.set(x, y, value);
			}
		}
		return image;
	}

	/**
	 * Windows the values of <code>image</code> to an arbitrary min/max. <code>image</code> is
	 * assumed to be in RGB colorspace. The color values are converted to HSV, and the value (V) is
	 * windowed to the min, max specified. The min, max must be in the range 0..1.
	 * 
	 * @param image ColorImage to window.
	 * @return <code>image</code>.
	 */
	protected Image apply(ColorImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Windows the values of <code>image</code> to an arbitrary min/max in a Region of Interest.
	 * <code>image</code> is assumed to be in RGB colorspace. The color values are converted to HSV,
	 * and the value (V) is windowed to the min, max specified. The min, max must be in the range
	 * 0..1.
	 * 
	 * @param image ColorImage to window.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(ColorImage image, ROI roi) {
		//		if (float_min < 0){ float_min = 0; System.out.println("min < 0");}
		//		if (float_min > 1){ float_min = 1; System.out.println("min > 1");}
		//		if (float_max < 0){ float_max = 0; System.out.println("max < 0");}
		//		if (float_max > 1){ float_max = 1; System.out.println("max > 1");}

		RealColorImage rc = ImageConverter.toRealColor(image);

		rc = ColorSpace.RGBtoHSV(rc);

		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = rc.plane(2).get(x, y);
				//				if (value > 1 || value < 0) System.out.println("ColorSpace isn't working: " + value);
				if (value >= float_max)
					value = 255;
				else if (value <= float_min)
					value = 0;
				else
					value = 255 * (value - float_min) / (float_max - float_min);
				//				if (value >1 || value < 0) System.out.println("my calculations aren't working");
				rc.plane(2).set(x, y, value);
			}
		}

		try {
			rc = ColorSpace.HSVtoRGB(rc);
		} catch (Exception e) {
			System.out.println("couldn't convert from HSVtoRGB");
		}

		image = ImageConverter.toColor(rc);

		return image;
	}

	/**
	 * Windows the values of <code>image</code> to an arbitrary min/max. <code>image</code> is
	 * assumed to be in RGB colorspace. The color values are converted to HSV, and the value (V) is
	 * windowed to the min, max specified. The min, max must be in the range 0..1.
	 * 
	 * @param image RealColorImage to window.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealColorImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Windows the values of <code>image</code> to an arbitrary min/max in a Region of Interest.
	 * <code>image</code> is assumed to be in RGB colorspace. The color values are converted to HSV,
	 * and the value (V) is windowed to the min, max specified. The min, max must be in the range
	 * 0..1.
	 * 
	 * @param image RealColorImage to window.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealColorImage image, ROI roi) {
		//		if (float_min < 0) float_min = 0;
		//		if (float_min > 1) float_min = 1;
		//		if (float_max < 0) float_max = 0;
		//		if (float_max > 1) float_max = 1;

		image = ColorSpace.RGBtoHSV(image);

		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = image.plane(2).get(x, y);
				if (value >= float_max)
					value = 255;
				else if (value <= float_min)
					value = 0;
				else
					value = 255 * (value - float_min) / (float_max - float_min);
				image.plane(2).set(x, y, value);
			}
		}

		try {
			image = ColorSpace.HSVtoRGB(image);
		} catch (Exception e) {
			System.out.println("Couldn't convert from HSVtoRGB");
		}

		return image;
	}

	/**
	 * For commandline opertion. The syntax is "java Window <u>min_value</u> <u>max_value</u>
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

			Window window = new Window(val1, val2);

			image2 = window.apply(image);

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
