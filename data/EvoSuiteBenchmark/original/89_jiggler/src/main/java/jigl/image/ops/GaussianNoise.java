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
package jigl.image.ops;

import jigl.image.ROI;
import jigl.image.types.ColorImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * Adds gaussian noise distributions to an image. Supports GrayImage, RealGrayImage, ColorImage,
 * RealColorImage.
 */
public class GaussianNoise extends SimpleOperator {
	/** Standard deviation of Gaussian noise. */
	private float m_stdDeviation;

	/**
	 * constructor.
	 * 
	 * @param stdDev Standard Deviation
	 */
	public GaussianNoise(float stdDev) {
		m_stdDeviation = stdDev;
	}

	/**
	 * Adds noise to <code>image</code> following the gaussian distribution.
	 * 
	 * @param image GrayImage to add noise to.
	 * @return <code>image</code>.
	 */
	protected GrayImage apply(GrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Adds noise to <code>image</code> in the specified Region of Interest, following the gaussian
	 * distribution.
	 * 
	 * @param image GrayImage to add noise to.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return a GrayImage object.
	 */
	protected GrayImage apply(GrayImage image, ROI roi) {
		java.util.Random random = new java.util.Random();
		float val = 0;
		float choice = 0;

		for (int x = roi.ux(); x <= roi.lx(); x++)
			for (int y = roi.uy(); y <= roi.ly(); y++) {
				choice = random.nextFloat();
				val = (float) random.nextGaussian();
				val = m_stdDeviation * val;
				if (choice > 0.5)
					image.add(x, y, (int) val);
				else
					image.subtract(x, y, (int) val);
			}

		return image;

	}

	/**
	 * Adds noise to <code>image</code> following the gaussian distribution.
	 * 
	 * @param image RealGrayImage to add noise to.
	 * @return <code>image</code>.
	 */
	protected RealGrayImage apply(RealGrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Adds noise to <code>image</code> in the specified Region of Interest, following the gaussian
	 * distribution.
	 * 
	 * @param image RealGrayImage to add noise to.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected RealGrayImage apply(RealGrayImage image, ROI roi) {
		java.util.Random random = new java.util.Random();
		float val = 0;
		float choice = 0;

		for (int x = roi.ux(); x <= roi.lx(); x++)
			for (int y = roi.uy(); y <= roi.ly(); y++) {
				choice = random.nextFloat();
				val = (float) random.nextGaussian();
				val = m_stdDeviation * val;
				if (choice > 0.5)
					image.add(x, y, val);
				else
					image.subtract(x, y, val);
			}

		return image;

	}

	/**
	 * Adds noise to <code>image</code> following the gaussian distribution. This is done by adding
	 * noise to each plane separately.
	 * 
	 * @param image ColorImage to add noise to.
	 * @return <code>image</code>.
	 */
	protected ColorImage apply(ColorImage image) {
		image.setPlane(0, apply(image.plane(0), new ROI(0, 0, image.X() - 1,
				image.Y() - 1)));
		image.setPlane(1, apply(image.plane(1), new ROI(0, 0, image.X() - 1,
				image.Y() - 1)));
		image.setPlane(2, apply(image.plane(2), new ROI(0, 0, image.X() - 1,
				image.Y() - 1)));

		return image;
	}

	/**
	 * Adds noise to <code>image</code> in the specified Region of Interest, following the gaussian
	 * distribution. This is done by adding noise to each plane separately.
	 * 
	 * @param image ColorImage to add noise to.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected ColorImage apply(ColorImage image, ROI roi) {
		image.setPlane(0, apply(image.plane(0), roi));
		image.setPlane(1, apply(image.plane(1), roi));
		image.setPlane(2, apply(image.plane(2), roi));

		return image;
	}

	/**
	 * Adds noise to <code>image</code> following the gaussian distribution. This is done by adding
	 * noise to each plane separately.
	 * 
	 * @param image RealColorImage to add noise to.
	 * @return <code>image</code>.
	 */
	protected RealColorImage apply(RealColorImage image) {
		image.setPlane(0, apply(image.plane(0), new ROI(0, 0, image.X() - 1, image
				.Y() - 1)));
		image.setPlane(1, apply(image.plane(1), new ROI(0, 0, image.X() - 1, image
				.Y() - 1)));
		image.setPlane(2, apply(image.plane(2), new ROI(0, 0, image.X() - 1, image
				.Y() - 1)));

		return image;
	}

	/**
	 * Adds noise to <code>image</code> in the specified Region of Interest, following the gaussian
	 * distribution. This is done by adding noise to each plane separately.
	 * 
	 * @param image RealColorImage to add noise to.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return <code>image</code>.
	 */
	protected RealColorImage apply(RealColorImage image, ROI roi) {
		image.setPlane(0, apply(image.plane(0), roi));
		image.setPlane(1, apply(image.plane(1), roi));
		image.setPlane(2, apply(image.plane(2), roi));

		return image;
	}
}
