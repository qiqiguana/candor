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

import jigl.image.Image;
import jigl.image.ROI;
import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.types.BinaryImage;
import jigl.image.types.ColorImage;
import jigl.image.types.ComplexImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * SimpleOperator is a base class that extends the Operator interface, and provides apply methods
 * for every known image type with a default behavior of simply throwing an
 * ImageNotSupportedException exception. This allows operators that extend this class to only
 * override the apply methods for the image types that it supports. SimpleOperator supports
 * GrayImage, RealGrayImage, ColorImage, RealColorImage, BinaryImage, and ComplexImage.
 */
public class SimpleOperator implements Operator {
	/**
	 * Apply an operation to the image. Default implementation throws ImageNotSupportedException.
	 * 
	 * @param image Image to perform the operation on.
	 */
	@SuppressWarnings("unchecked")
	public Image apply(Image image) throws ImageNotSupportedException {
		if (image instanceof GrayImage)
			return apply((GrayImage) image);
		else if (image instanceof RealGrayImage)
			return apply((RealGrayImage) image);
		else if (image instanceof ColorImage)
			return apply((ColorImage) image);
		else if (image instanceof RealColorImage)
			return apply((RealColorImage) image);
		else if (image instanceof BinaryImage)
			return apply((BinaryImage) image);
		else if (image instanceof ComplexImage)
			return apply((ComplexImage) image);
		else
			throw new ImageNotSupportedException();
	}

	/**
	 * Apply an operation to the image in a Region of Interest. Default implementation throws
	 * ImageNotSupportedException.
	 * 
	 * @param image Image to perform the operation on.
	 * @param roi Region of Interest of the image
	 */
	@SuppressWarnings("unchecked")
	public Image apply(Image image, ROI roi) throws ImageNotSupportedException {
		if (null == roi)
			return image;

		if (image instanceof GrayImage)
			return apply((GrayImage) image, roi);
		else if (image instanceof RealGrayImage)
			return apply((RealGrayImage) image, roi);
		else if (image instanceof ColorImage)
			return apply((ColorImage) image, roi);
		else if (image instanceof RealColorImage)
			return apply((RealColorImage) image, roi);
		else if (image instanceof BinaryImage)
			return apply((BinaryImage) image, roi);
		else if (image instanceof ComplexImage)
			return apply((ComplexImage) image, roi);
		else
			throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(GrayImage image) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(RealGrayImage image) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(ColorImage image) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(RealColorImage image) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(BinaryImage image) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(ComplexImage image) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(GrayImage image, ROI roi) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(RealGrayImage image, ROI roi) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(ColorImage image, ROI roi) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(RealColorImage image, ROI roi) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(BinaryImage image, ROI roi) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}

	/** Auxiliary method. */
	protected Image apply(ComplexImage image, ROI roi) throws ImageNotSupportedException {
		throw new ImageNotSupportedException();
	}
}
