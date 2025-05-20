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
 * Performs a Negative operation (subtracts the value at each pixel from 255) on an image.
 * <code>image</code> is changed in all the methods of this class. Supports GrayImage,
 * RealGrayImage.
 * <p>
 * The value of <code>image</code> is assumed to be in the range 0..255. There is much different
 * between this class and class <code>Negate</code>
 */
public class Negative extends SimpleOperator {
	/** Dummy Constructor */
	public Negative() {
	}

	/**
	 * Subtracts the value at each pixel from 255 and returns a new image.
	 * 
	 * @param image GrayImage to negate.
	 * @return <code>image</code>.
	 */
	protected Image apply(GrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Subtracts the value at each pixel from 255. and returns a new image.
	 * 
	 * @param image RealGrayImage to negate.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Subtracts the value at each pixel from 255 in a Region of Interest and returns a new image.
	 * 
	 * @param image GrayImage to negative.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(GrayImage image, ROI roi) {
		int value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = image.get(x, y);
				value = 255 - value;
				image.set(x, y, value);
			}
		}
		return image;
	}

	/**
	 * Subtracts the value at each pixel from 255 in a Region of Interest and returns a new image.
	 * 
	 * @param image RealGrayImage to negate.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealGrayImage image, ROI roi) {
		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = image.get(x, y);
				value = 255 - value;
				image.set(x, y, value);
			}
		}
		return image;
	}

	/**
	 * Subtracts the value at each pixel from 255 and returns a new image. This is done by negating
	 * each plane separately.
	 * 
	 * @param image ColorImage to negate.
	 * @return <code>image</code>.
	 */
	protected Image apply(ColorImage image) {
		image.setPlane(0, (GrayImage) apply(image.plane(0), new ROI(0, 0, image.X(), image.Y())));
		image.setPlane(1, (GrayImage) apply(image.plane(1), new ROI(0, 0, image.X(), image.Y())));
		image.setPlane(2, (GrayImage) apply(image.plane(2), new ROI(0, 0, image.X(), image.Y())));

		return image;
	}

	/**
	 * Subtracts the value at each pixel from 255 in a Region of Interest and returns a new image.
	 * This is done by negating each plane separately.
	 * 
	 * @param image ColorImage to negate.
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
	 * Subtracts the value at each pixel from 255 and returns a new image. This is done by negating
	 * each plane separately.
	 * 
	 * @param image RealColorImage to negate.
	 * @return <code>image</code>.
	 */
	protected Image apply(RealColorImage image) {
		image.setPlane(0,
				(RealGrayImage) apply(image.plane(0), new ROI(0, 0, image.X(), image.Y())));
		image.setPlane(1,
				(RealGrayImage) apply(image.plane(1), new ROI(0, 0, image.X(), image.Y())));
		image.setPlane(2,
				(RealGrayImage) apply(image.plane(2), new ROI(0, 0, image.X(), image.Y())));

		return image;
	}

	/**
	 * Subtracts the value at each pixel from 255 in a Region of Interest and returns a new image.
	 * This is done by negating each plane separately.
	 * 
	 * @param image RealColorImage to negate.
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
	 * For commandline opertion. The syntax is "java Negative <u>input_filename</u>
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

			Negative negative = new Negative();

			image2 = negative.apply(image);

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
