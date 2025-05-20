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
import jigl.image.utils.NotHSVColorSpaceException;

/**
 * Performs the Exponential function on each pixel in this image. Supports GrayImage, RealGrayImage,
 * ColorImage, RealColorImage.
 * <p>
 * Please note that this class will <i>always</i> return a RealGrayImage or RealColorImage.
 */
public class Exp extends SimpleOperator {
	/** Dummy Constructor */
	public Exp() {
	}

	/**
	 * Performs the Exp function on a GrayImage. Returned image is a RealGrayImage.
	 * <code>image</code> is not modified.
	 * 
	 * @param image GrayImage.
	 * @return RealGrayImage.
	 */
	protected Image apply(GrayImage image) {
		RealGrayImage rg = ImageConverter.toRealGray(image);
		return apply(rg, new ROI(0, 0, rg.X() - 1, rg.Y() - 1));
	}

	/**
	 * Performs the Exp function on <code>image</code>.
	 * 
	 * @param image RealGrayImage.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Performs the Exp function on a GrayImage in a Region of Interest. Returned image is a
	 * RealGrayImage. <code>image</code> is not modified.
	 * 
	 * @param image GrayImage.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return RealGrayImage.
	 */
	protected Image apply(GrayImage image, ROI roi) {
		RealGrayImage rg = ImageConverter.toRealGray(image);
		return apply(rg, roi);
	}

	/**
	 * Performs the Exp function on <code>image</code> in a Region of Interest.
	 * 
	 * @param image RealGrayImage
	 * @param roi Region of Interest of <code>image</code>
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image, ROI roi) {
		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = image.get(x, y);
				value = (float) java.lang.Math.exp(value);
				image.set(x, y, value);
			}
		}
		return image;
	}

	/**
	 * Performs the Exp function on a ColorImage. <code>image</code> is assumed to be in RGB
	 * colorspace. Each color is conveted to HSV, and the Exp function is applied to the value (V).
	 * The modified HSV value is converted back to RGB. Returned Image is a RealColorImage.
	 * <code>image</code> is not modified.
	 * 
	 * @param image ColorImage.
	 * @return RealColorImage.
	 */
	protected Image apply(ColorImage image) {
		RealColorImage rc = ImageConverter.toRealColor(image);
		return apply(rc, new ROI(0, 0, rc.X() - 1, rc.Y() - 1));
	}

	/**
	 * Performs the Exp function on a ColorImage in a Region of Interest. <code>image</code> is
	 * assumed to be in RGB colorspace. Each color value is conveted to HSV, and the Exp function is
	 * applied to the value (V). The modified HSV value is converted back to RGB. Returned Image is
	 * a RealColorImage. <code>image</code> is not modified.
	 * 
	 * @param image ColorImage.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return RealColorImage.
	 */
	protected Image apply(ColorImage image, ROI roi) {
		RealColorImage rc = ImageConverter.toRealColor(image);
		return apply(rc, roi);
	}

	/**
	 * Performs the Exp function on <code>image</code>. <code>image</code> is assumed to be in RGB
	 * colorspace. Each color value is conveted to HSV, and the Exp function is applied to the value
	 * (V). The modified HSV value is converted back to RGB.
	 * 
	 * @param image RealColorImage.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealColorImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Performs the Exp function on <code>image</code> in a Region of Interest. <code>image</code>
	 * is assumed to be in RGB colorspace. Each color value is conveted to HSV, and the Exp function
	 * is applied to the value (V). The modified HSV value is converted back to RGB.
	 * 
	 * @param image RealColorImage.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealColorImage image, ROI roi) {
		//convert to HSV
		image = ColorSpace.RGBtoHSV(image);

		//apply to the value (V) plane
		image.setPlane(2, (RealGrayImage) apply(image.plane(2), roi));

		//convert back to RGB
		try {
			image = ColorSpace.HSVtoRGB(image);
		} catch (NotHSVColorSpaceException e) {
			System.out.println("ColorSpace is not working");
		}

		return image;
	}

	/**
	 * For commandline opertion. The syntax is "java Exp <u>input_filename</u>
	 * <u>output_filename</u>".
	 */
	public static void main(String[] argv) {

		try {
			Image image = null;
			String inputfile = argv[0];
			Image image2 = null;

			ImageInputStream is = new ImageInputStream(inputfile);
			image = is.read();
			is.close();

			Exp exponential = new Exp();

			image2 = exponential.apply(image);

			//put command line stuff here.

			// create a new ImageOutputStream
			ImageOutputStream os = new ImageOutputStream(argv[1]);
			os.write(image2);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
