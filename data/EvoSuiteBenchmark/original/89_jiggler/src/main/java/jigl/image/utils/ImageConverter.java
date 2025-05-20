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
package jigl.image.utils;

import jigl.image.Image;
import jigl.image.exceptions.ColorModelNotSupportedException;
import jigl.image.exceptions.ColorModelUnknownException;
import jigl.image.types.BinaryImage;
import jigl.image.types.ColorImage;
import jigl.image.types.ComplexImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * Converts a Jigl image (ColorImage, ComplexImage, RealColorImage, RealGrayImage, ComplexImage) to
 * another specified type.
 * 
 * <p>
 * All methods are static.
 */
public final class ImageConverter {

	/** Default constructor (Does nothing) */
	private ImageConverter() {
	}

	/** Converts an Image to a GrayImage */
	public static GrayImage toGray(Image img) throws ColorModelNotSupportedException,
			ColorModelUnknownException {
		if (img instanceof GrayImage) {
			return toGray((GrayImage) img);
		} else if (img instanceof RealGrayImage) {
			return toGray((RealGrayImage) img);
		} else if (img instanceof ColorImage) {
			return toGray((ColorImage) img);
		} else if (img instanceof RealColorImage) {
			return toGray((RealColorImage) img);
		} else {
			return toGray((ComplexImage) img);
		}
	}

	/** Converts an Image to a RealGrayImage */
	public static RealGrayImage toRealGray(Image img) throws ColorModelNotSupportedException,
			ColorModelUnknownException {
		if (img instanceof GrayImage) {
			return toRealGray(img);
		} else if (img instanceof RealGrayImage) {
			return toRealGray((RealGrayImage) img);
		} else if (img instanceof ColorImage) {
			return toRealGray((ColorImage) img);
		} else if (img instanceof RealColorImage) {
			return toRealGray((RealColorImage) img);
		} else {
			return toRealGray((ComplexImage) img);
		}
	}

	/** Converts an Image to a ColorImage */
	public static ColorImage toColor(Image img) throws ColorModelNotSupportedException,
			ColorModelUnknownException {
		if (img instanceof GrayImage) {
			return toColor(img);
		} else if (img instanceof RealGrayImage) {
			return toColor((RealGrayImage) img);
		} else if (img instanceof ColorImage) {
			return toColor((ColorImage) img);
		} else if (img instanceof RealColorImage) {
			return toColor((RealColorImage) img);
		} else {
			return toColor((ComplexImage) img);
		}
	}

	/** Converts an Image to a RealColorImage */
	public static RealColorImage toRealColor(Image img) throws ColorModelNotSupportedException,
			ColorModelUnknownException {
		if (img instanceof GrayImage) {
			return toRealColor(img);
		} else if (img instanceof RealGrayImage) {
			return toRealColor((RealGrayImage) img);
		} else if (img instanceof ColorImage) {
			return toRealColor((ColorImage) img);
		} else if (img instanceof RealColorImage) {
			return toRealColor((RealColorImage) img);
		} else {
			return toRealColor((ComplexImage) img);
		}
	}

	/** Converts an Image to a ComplexImage */
	public static ComplexImage toComplex(Image img) throws ColorModelNotSupportedException,
			ColorModelUnknownException {
		if (img instanceof GrayImage) {
			return toComplex(img);
		} else if (img instanceof RealGrayImage) {
			return toComplex((RealGrayImage) img);
		} else if (img instanceof ColorImage) {
			return toComplex((ColorImage) img);
		} else if (img instanceof RealColorImage) {
			return toComplex((RealColorImage) img);
		} else {
			return toComplex((ComplexImage) img);
		}
	}

	/** Copies a GrayImage to form a new GrayImage. */
	public static GrayImage toGray(GrayImage img) {
		int X = img.X();
		int Y = img.Y();
		GrayImage newimg = new GrayImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				newimg.set(x, y, img.get(x, y));
			}
		}
		return newimg;
	}

	/** Converts a BinaryImage to a GrayImage */
	public static GrayImage toGray(BinaryImage img) {
		int X = img.X();
		int Y = img.Y();
		GrayImage newimg = new GrayImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				if ((int) img.get(x, y) == 0)
					newimg.set(x, y, 0);
				else
					newimg.set(x, y, 255);
			}
		}
		return newimg;
	}

	/** Converts a RealGrayImage to a GrayImage */
	public static GrayImage toGray(RealGrayImage img) {
		int X = img.X();
		int Y = img.Y();
		GrayImage newimg = new GrayImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				newimg.set(x, y, img.get(x, y).intValue());
			}
		}
		return newimg;
	}

	/**
	 * Converts an ColorImage to a GrayImage. <br>
	 * Uses the following formula for calculation: <code>
	gray value = (int)(red_value * 0.299 + green_value * 0.587 + blue_value * 0.114)
	</code>
	 */
	public static GrayImage toGray(ColorImage img) throws ColorModelNotSupportedException,
			ColorModelUnknownException {
		int X = img.X();
		int Y = img.Y();
		Integer[] color = new Integer[3];
		int gray;
		GrayImage newimg = null;
		switch (img.getColorModel()) {
		case RGB:
			newimg = new GrayImage(X, Y);
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					color = img.get(x, y);
					gray = (int) ((double) color[0] * 0.299 + (double) color[1] * 0.587 + (double) color[2] * 0.114);
					newimg.set(x, y, gray);
				}
			}
			break;
		case CMY:
		case YIQ:
		case HSV:
		case HLS:
			throw new ColorModelNotSupportedException();
		default:
			throw new ColorModelUnknownException();
		}
		return newimg;
	}

	/**
	 * Converts an RealColorImage to a GrayImage. <br>
	 * Uses the following formula for calculation: <code>
	gray value = (int)(red_value * 0.299 + green_value * 0.587 + blue_value * 0.114)
	</code>
	 */
	public static GrayImage toGray(RealColorImage img) throws ColorModelNotSupportedException,
			ColorModelUnknownException {
		int X = img.X();
		int Y = img.Y();
		Float[] color = new Float[3];
		int gray;
		GrayImage newimg = null;
		switch (img.getColorModel()) {
		case RGB:
			newimg = new GrayImage(X, Y);
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					color = img.get(x, y);
					gray = (int) (color[0] * 0.299 + color[1] * 0.587 + color[2] * 0.114);
					newimg.set(x, y, gray);
				}
			}
			break;
		case CMY:
		case YIQ:
		case HSV:
		case HLS:
			throw new ColorModelNotSupportedException();
		default:
			throw new ColorModelUnknownException();
		}
		return newimg;
	}

	/**
	 * Converts an ComplexImage to a GrayImage. Just throws away the imaginary part of the complex
	 * image.
	 */
	public static GrayImage toGray(ComplexImage img) {
		int X = img.X();
		int Y = img.Y();
		GrayImage newimg = null;
		newimg = new GrayImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				newimg.set(x, y, (int) img.getReal(x, y));
			}
		}
		return newimg;
	}

	/** Converts an GrayImage to a RealGrayImage */
	public static RealGrayImage toRealGray(GrayImage img) {
		int X = img.X();
		int Y = img.Y();
		RealGrayImage newimg = new RealGrayImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				newimg.set(x, y, (float) img.get(x, y));
			}
		}
		return newimg;
	}

	/** Converts a RealGrayImage to a RealGrayImage */
	public static RealGrayImage toRealGray(RealGrayImage img) {
		int X = img.X();
		int Y = img.Y();
		RealGrayImage newimg = new RealGrayImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				newimg.set(x, y, img.get(x, y));
			}
		}
		return newimg;
	}

	/**
	 * Converts a ColorImage to a RealGrayImage. <br>
	 * Uses the following formula for calculation: <code>
	gray value = (float)(red_value * 0.299 + green_value * 0.587 + blue_value * 0.114)
	</code>
	 */
	public static RealGrayImage toRealGray(ColorImage img) throws ColorModelNotSupportedException,
			ColorModelUnknownException {
		int X = img.X();
		int Y = img.Y();
		Integer[] color = new Integer[3];
		float gray;
		RealGrayImage newimg = null;
		
		//TODO: replace switch with polymorphism
		switch (img.getColorModel()) {
		case RGB:
			newimg = new RealGrayImage(X, Y);
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					color = img.get(x, y);
					gray = (float) color[0] * 0.299f + (float) color[1] * 0.587f + (float) color[2]
							* 0.114f;
					newimg.set(x, y, gray);
				}
			}
			break;
		case CMY:
		case YIQ:
		case HSV:
		case HLS:
			throw new ColorModelNotSupportedException();
		default:
			throw new ColorModelUnknownException();
		}
		return newimg;
	}

	/**
	 * Converts a RealColorImage to a RealGrayImage. <br>
	 * Uses the following formula for calculation: <code>
	gray value = (float)(red_value * 0.299 + green_value * 0.587 + blue_value * 0.114)
	</code>
	 */
	public static RealGrayImage toRealGray(RealColorImage img)
			throws ColorModelNotSupportedException, ColorModelUnknownException {
		int X = img.X();
		int Y = img.Y();
		Float[] color = new Float[3];
		float gray;
		RealGrayImage newimg = null;
		//TODO: replace switch with polymorphism
		switch (img.getColorModel()) {
		case RGB:
			newimg = new RealGrayImage(X, Y);
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					color = img.get(x, y);
					gray = color[0] * 0.299f + color[1] * 0.587f + color[2] * 0.114f;
					newimg.set(x, y, gray);
				}
			}
			break;
		case CMY:
		case YIQ:
		case HSV:
		case HLS:
			throw new ColorModelNotSupportedException();
		default:
			throw new ColorModelUnknownException();
		}
		return newimg;
	}

	/** Converts a ComplexImage to a RealGrayImage */
	public static RealGrayImage toRealGray(ComplexImage img) {
		int X = img.X();
		int Y = img.Y();
		RealGrayImage newimg = null;
		newimg = new RealGrayImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				newimg.set(x, y, img.getReal(x, y));
			}
		}
		return newimg;
	}

	/**
	 * Converts a GrayImage to a ColorImage. Each plane of the color image has the same value in
	 * every pixel.
	 */
	public static ColorImage toColor(GrayImage img) {
		int X = img.X();
		int Y = img.Y();
		Integer[] color = new Integer[3];
		int gray;
		ColorImage newimg = new ColorImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				gray = img.get(x, y);
				color[0] = gray;
				color[1] = gray;
				color[2] = gray;
				newimg.set(x, y, color);
			}
		}
		return newimg;
	}

	/**
	 * Converts a RealGrayImage to a ColorImage. Each plane of the color image has the same value in
	 * every pixel.
	 */
	public static ColorImage toColor(RealGrayImage img) {
		int X = img.X();
		int Y = img.Y();
		Integer[] color = new Integer[3];
		float gray;
		ColorImage newimg = new ColorImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				gray = img.get(x, y);
				color[0] = (int) gray;
				color[1] = (int) gray;
				color[2] = (int) gray;
				newimg.set(x, y, color);
			}
		}
		return newimg;
	}

	/** Copies a ColorImage to form a new ColorImage */
	public static ColorImage toColor(ColorImage img) {
		int X = img.X();
		int Y = img.Y();
		ColorImage newimg = null;
		newimg = new ColorImage(X, Y);
		newimg.setColorModel(img.getColorModel());
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				newimg.set(x, y, img.get(x, y));
			}
		}
		return newimg;
	}

	/** Converts a RealColorImage to a ColorImage */
	public static ColorImage toColor(RealColorImage img) {
		int X = img.X();
		int Y = img.Y();
		Float[] color = new Float[3];
		Integer[] icolor = new Integer[3];
		ColorImage newimg = null;
		newimg = new ColorImage(X, Y);
		newimg.setColorModel(img.getColorModel());
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				color = img.get(x, y);
				icolor[0] = color[0].intValue();
				icolor[1] = color[1].intValue();
				icolor[2] = color[2].intValue();
				newimg.set(x, y, icolor);
			}
		}
		return newimg;
	}

	/**
	 * Converts a ComplexImage to a ColorImage. Uses only the real plane of the complex image.
	 */
	public static ColorImage toColor(ComplexImage img) {
		int X = img.X();
		int Y = img.Y();
		ColorImage newimg = null;
		newimg = new ColorImage(X, Y);
		Integer[] color = new Integer[3];
		int gray;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				gray = (int) img.getReal(x, y);
				color[0] = gray;
				color[1] = gray;
				color[2] = gray;
				newimg.set(x, y, color);
			}
		}
		return newimg;
	}

	/**
	 * Converts a GrayImage to a RealColorImage. Each plane of the color image has the same value in
	 * every pixel.
	 */
	public static RealColorImage toRealColor(GrayImage img) {
		int X = img.X();
		int Y = img.Y();
		Float[] color = new Float[3];
		float gray;
		RealColorImage newimg = new RealColorImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				gray = img.get(x, y).floatValue();
				color[0] = gray;
				color[1] = gray;
				color[2] = gray;
				newimg.set(x, y, color);
			}
		}
		return newimg;
	}

	/**
	 * Converts a RealGrayImage to a RealColorImage. Each plane of the color image has the same
	 * value in every pixel.
	 */
	public static RealColorImage toRealColor(RealGrayImage img) {
		int X = img.X();
		int Y = img.Y();
		Float[] color = new Float[3];
		float gray;
		RealColorImage newimg = new RealColorImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				gray = img.get(x, y);
				color[0] = gray;
				color[1] = gray;
				color[2] = gray;
				newimg.set(x, y, color);
			}
		}
		return newimg;
	}

	/** Converts a ColorImage to a RealColorImage. */
	public static RealColorImage toRealColor(ColorImage img) {
		int X = img.X();
		int Y = img.Y();
		Float[] color = new Float[3];
		Integer[] icolor = new Integer[3];
		RealColorImage newimg = null;
		newimg = new RealColorImage(X, Y);
		newimg.setColorModel(img.getColorModel());
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				icolor = img.get(x, y);
				color[0] = (float) icolor[0];
				color[1] = (float) icolor[1];
				color[2] = (float) icolor[2];
				newimg.set(x, y, color);
			}
		}
		return newimg;
	}

	/** Converts a RealColorImage to a RealColorImage */
	public static RealColorImage toRealColor(RealColorImage img) {
		int X = img.X();
		int Y = img.Y();
		RealColorImage newimg = null;
		newimg = new RealColorImage(X, Y);
		newimg.setColorModel(img.getColorModel());
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				newimg.set(x, y, img.get(x, y));
			}
		}
		return newimg;
	}

	/**
	 * Converts a ComplexImage to a RealColorImage. Uses only the real plane of the complex image.
	 */
	public static RealColorImage toRealColor(ComplexImage img) {
		int X = img.X();
		int Y = img.Y();
		RealColorImage newimg = null;
		newimg = new RealColorImage(X, Y);
		Float[] color = new Float[3];
		float gray;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				gray = img.getReal(x, y);
				color[0] = gray;
				color[1] = gray;
				color[2] = gray;
				newimg.set(x, y, color);
			}
		}
		return newimg;
	}

	/** Converts a GrayImage to a ComplexImage */
	public static ComplexImage toComplex(GrayImage img) {
		int X = img.X();
		int Y = img.Y();
		float gray;
		ComplexImage newimg = new ComplexImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				gray = img.get(x, y);
				newimg.setReal(x, y, gray);
				newimg.setImag(x, y, 0f);
			}
		}
		return newimg;
	}

	/** Converts a RealGrayImage to a ComplexImage */
	public static ComplexImage toComplex(RealGrayImage img) {
		int X = img.X();
		int Y = img.Y();
		float gray;
		ComplexImage newimg = new ComplexImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				gray = img.get(x, y);
				newimg.setReal(x, y, gray);
				newimg.setImag(x, y, 0f);
			}
		}
		return newimg;
	}

	/**
	 * Converts a ColorImage to a ComplexImage. The ColorImage is first converted to a RealGrayImage
	 * object and then to a ComplexImage object.
	 */
	public static ComplexImage toComplex(ColorImage img) throws ColorModelNotSupportedException,
			ColorModelUnknownException {
		int X = img.X();
		int Y = img.Y();
		Integer[] color = new Integer[3];
		float real;
		ComplexImage newimg = null;
		
		//TODO: replace switch with polymorphism
		switch (img.getColorModel()) {
		case RGB:
			newimg = new ComplexImage(X, Y);
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					color = img.get(x, y);
					real = (float) color[0] * 0.299f + (float) color[1] * 0.587f + (float) color[2]
							* 0.114f;
					newimg.setReal(x, y, real);
					newimg.setImag(x, y, 0f);
				}
			}
			break;
		case CMY:
		case YIQ:
		case HSV:
		case HLS:
			throw new ColorModelNotSupportedException();
		default:
			throw new ColorModelUnknownException();
		}
		return newimg;
	}

	/**
	 * Converts a RealColorImage to a ComplexImage. The RealColorImage is first converted to a
	 * RealGrayImage object and then to a ComplexImage object.
	 */
	public static ComplexImage toComplex(RealColorImage img)
			throws ColorModelNotSupportedException, ColorModelUnknownException {
		int X = img.X();
		int Y = img.Y();
		Float[] color = new Float[3];
		float real;
		ComplexImage newimg = null;
		
		//TODO: replace switch with polymorphism
		switch (img.getColorModel()) {
		case RGB:
			newimg = new ComplexImage(X, Y);
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					color = img.get(x, y);
					real = (float) color[0] * 0.299f + (float) color[1] * 0.587f + (float) color[2]
							* 0.114f;
					newimg.setReal(x, y, real);
					newimg.setImag(x, y, 0f);
				}
			}
			break;
		case CMY:
		case YIQ:
		case HSV:
		case HLS:
			throw new ColorModelNotSupportedException();
		default:
			throw new ColorModelUnknownException();
		}
		return newimg;
	}

	/** Converts a ComplexImage to a ComplexImage */
	public static ComplexImage toComplex(ComplexImage img) {
		int X = img.X();
		int Y = img.Y();
		ComplexImage newimg = null;
		newimg = new ComplexImage(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				newimg.setReal(x, y, img.getReal(x, y));
				newimg.setImag(x, y, img.getImag(x, y));
			}
		}
		return newimg;
	}

}
