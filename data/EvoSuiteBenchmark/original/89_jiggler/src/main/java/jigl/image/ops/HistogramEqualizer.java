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

import jigl.image.CumulativeHistogram;
import jigl.image.Histogram;
import jigl.image.ROI;
import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.ops.levelOps.LookupTable;
import jigl.image.types.ColorImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;
import jigl.image.utils.ColorSpace;
import jigl.image.utils.ImageConverter;

/**
 * Equalizes a Histogram and returns a new image with equalized histogram. It uses
 * <code>LookupTable</code> and <code>CumulativeHistogram</code> to implement equalization.
 * 
 * @see jigl.image.ops.levelOps.LookupTable LookupTable
 * @see jigl.image.CumulativeHistogram CumulativeHistogram
 */
public class HistogramEqualizer extends SimpleOperator {
	/**
	 * See {@link jigl.image.CumulativeHistogram CumulativeHistogram}
	 */
	private CumulativeHistogram hist = null;
	/**
	 * See {@link jigl.image.ops.levelOps.LookupTable LookupTable}
	 */
	private LookupTable table = null;

	/** Create a empty HistogramEqualizer object */
	public HistogramEqualizer() throws ImageNotSupportedException {
	}

	/**
	 * Returns an equalized version of <code>image</code>. Returned image is a GrayImage object.
	 */
	protected GrayImage apply(GrayImage image) throws ImageNotSupportedException {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Returns an equalized version of <code>image</code>. Returned image is a RealGrayImage object.
	 */
	protected RealGrayImage apply(RealGrayImage image) throws ImageNotSupportedException {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Returns an equalized region in an image <code>image</code>. Returned image is a GrayImage
	 * object.
	 */
	protected GrayImage apply(GrayImage image, ROI roi) throws ImageNotSupportedException {
		hist = new CumulativeHistogram(new Histogram(image, 1));
		float max = hist.max();
		float min = hist.min();
		//data stores the lookup table
		float[] data = new float[hist.length() + 1];
		for (int x = 0; x < hist.length(); x++) {
			data[x] = (float) (hist.count(x + (int) min) / (float) hist.count((int) max) * 255.0);
		}
		table = new LookupTable(data, (int) hist.min(), (int) data[0], (int) data[hist.length()]);
		return (GrayImage) table.apply(image, roi);
	}

	/**
	 * Returns an equalized region in an image <code>image</code>. Returned image is a RealGrayImage
	 * object.
	 */
	protected RealGrayImage apply(RealGrayImage image, ROI roi) throws ImageNotSupportedException {
		hist = new CumulativeHistogram(new Histogram(image, 1));
		float max = hist.max();
		float min = hist.min();
		float[] data = new float[hist.length() + 1];
		for (int x = 0; x < hist.length(); x++) {
			data[x] = (float) (hist.count(x + (int) min) / (float) hist.count((int) max) * 255.0);
		}
		table = new LookupTable(data, (int) hist.min(), (int) data[0], (int) data[hist.length()]);
		return (RealGrayImage) table.apply(image, roi);
	}

	/**
	 * Returns an equalized version of <code>image</code>. Returned image is a ColorImage object.
	 */
	protected ColorImage apply(ColorImage image) throws ImageNotSupportedException {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Returns an equalized version of <code>image</code>. Returned image is a RealColorImage
	 * object.
	 */
	protected RealColorImage apply(RealColorImage image) throws ImageNotSupportedException {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Returns an equalized region in an image <code>image</code>. Returned image is a ColorImage
	 * object.
	 */
	protected ColorImage apply(ColorImage image, ROI roi) throws ImageNotSupportedException {
		RealColorImage rc = ImageConverter.toRealColor(image);
		rc = apply(rc, roi);
		return ImageConverter.toColor(rc);
	}

	/**
	 * Returns an equalized region in an image <code>image</code>. Returned image is a
	 * RealColorImage object.
	 */
	protected RealColorImage apply(RealColorImage image, ROI roi) throws ImageNotSupportedException {
		image = ColorSpace.RGBtoHSV(image);

		hist = new CumulativeHistogram(new Histogram(image.plane(2), 1));
		float max = hist.max();
		float min = hist.min();
		float[] data = new float[hist.length() + 1];
		for (int x = 0; x < hist.length(); x++) {
			data[x] = (float) (hist.count(x + (int) min) / (float) hist.count((int) max) * 255.0);
		}
		table = new LookupTable(data, (int) hist.min(), (int) data[0], (int) data[hist.length()]);
		image.setPlane(2, (RealGrayImage) table.apply((image.plane(2)), roi));

		try {
			return ColorSpace.HSVtoRGB(image);
		} catch (Exception e) {
			System.out.println("couldn't convert back to RGB");
			return image;
		}
	}
}
