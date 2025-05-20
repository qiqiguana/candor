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

import jigl.image.types.RealColorImage;
import jigl.math.Matrix;

/**
 * This class converts between colorspaces. Currently supports conversions:
 * 
 * <pre>
 * RGB<-->HSV, RGB<-->HLS, RGB<-->CMY, RGB<-->YIQ
 * </pre>
 */
public class ColorSpace {
	/** Constructor */
	public ColorSpace() {
	}

	/** Find the maximum among <code>val[0]</code>, <code>val[1]</code> and <code>val[2]</code>. */
	private static float maximum(Float[] val) {
		if (val[0] >= val[1] && val[0] >= val[2])
			return val[0];
		else if (val[1] >= val[0] && val[1] >= val[2])
			return val[1];
		else
			return val[2];
	}

	/** Find the minimum among <code>val[0]</code>, <code>val[1]</code> and <code>val[2]</code>. */
	private static float minimum(Float[] val) {
		if (val[0] <= val[1] && val[0] <= val[2])
			return val[0];
		else if (val[1] <= val[0] && val[1] <= val[2])
			return val[1];
		else
			return val[2];
	}

	/** Utility method. */
	private static float value(float n1, float n2, float hue) {
		if (hue > 360)
			hue -= 360;
		else if (hue < 0)
			hue += 360;

		if (hue < 60)
			return (float) (n1 + (n2 - n1) * hue / 60.0);
		else if (hue < 180)
			return n2;
		else if (hue < 240)
			return (float) (n1 + (n2 - n1) * (240.0 - hue) / 60.0);
		else
			return n1;
	}

	/**
	 * Converts an image from RGB to HSV colorspace.
	 * 
	 * @param image RealColorImage to convert
	 * @return deep copy of <code>image</code>
	 */
	public static RealColorImage RGBtoHSV(RealColorImage image) {
		RealColorImage rImage = new RealColorImage(image.X(), image.Y());
		for (int x = 0; x < image.X(); x++)
			for (int y = 0; y < image.Y(); y++)
				rImage.set(x, y, RGBtoHSV(image.get(x, y)));

		return rImage;
	}

	/**
	 * Converts an image from HSV to RGB colorspace.
	 * 
	 * @param image RealColorImage to convert
	 * @return deep copy of <code>image</code>
	 */
	public static RealColorImage HSVtoRGB(RealColorImage image) throws NotHSVColorSpaceException {
		RealColorImage rImage = new RealColorImage(image.X(), image.Y());
		for (int x = 0; x < image.X(); x++)
			for (int y = 0; y < image.Y(); y++)
				rImage.set(x, y, HSVtoRGB(image.get(x, y)));

		return rImage;
	}

	/**
	 * Converts an image from RGB to HLS colorspace.
	 * 
	 * @param image RealColorImage to convert
	 * @return deep copy of <code>image</code>
	 */
	public static RealColorImage RGBtoHLS(RealColorImage image) {
		RealColorImage rImage = new RealColorImage(image.X(), image.Y());
		for (int x = 0; x < image.X(); x++) {
			for (int y = 0; y < image.Y(); y++) {
				rImage.set(x, y, RGBtoHLS(image.get(x, y)));
			}
		}

		return rImage;
	}

	/**
	 * Converts an image from HLS to RGB colorspace.
	 * 
	 * @param image RealColorImage to convert
	 * @return deep copy of <code>image</code>
	 */
	public static RealColorImage HLStoRGB(RealColorImage image) throws NotHLSColorSpaceException {
		RealColorImage rImage = new RealColorImage(image.X(), image.Y());
		for (int x = 0; x < image.X(); x++) {
			for (int y = 0; y < image.Y(); y++) {
				rImage.set(x, y, HLStoRGB(image.get(x, y)));
			}
		}

		return rImage;
	}

	/**
	 * Converts an image from RGB to CMY colorspace.
	 * 
	 * @param image RealColorImage to convert
	 * @return deep copy of <code>image</code>
	 */
	public static RealColorImage RGBtoCMY(RealColorImage image) {
		RealColorImage rImage = new RealColorImage(image.X(), image.Y());
		for (int x = 0; x < image.X(); x++) {
			for (int y = 0; y < image.Y(); y++) {
				rImage.set(x, y, RGBtoCMY(image.get(x, y)));
			}
		}

		return rImage;
	}

	/**
	 * Converts an image from CMY to RGB colorspace.
	 * 
	 * @param image RealColorImage to convert
	 * @return deep copy of <code>image</code>
	 */
	public static RealColorImage CMYtoRGB(RealColorImage image) {
		RealColorImage rImage = new RealColorImage(image.X(), image.Y());
		for (int x = 0; x < image.X(); x++) {
			for (int y = 0; y < image.Y(); y++) {
				rImage.set(x, y, CMYtoRGB(image.get(x, y)));
			}
		}

		return rImage;
	}

	/**
	 * Converts an image from RGB to YIQ colorspace.
	 * 
	 * @param image RealColorImage to convert
	 * @return deep copy of <code>image</code>
	 */
	public static RealColorImage RGBtoYIQ(RealColorImage image) {
		RealColorImage rImage = new RealColorImage(image.X(), image.Y());
		for (int x = 0; x < image.X(); x++) {
			for (int y = 0; y < image.Y(); y++) {
				rImage.set(x, y, RGBtoYIQ(image.get(x, y)));
			}
		}

		return rImage;
	}

	/**
	 * Converts an image from YIQ to RGB colorspace.
	 * 
	 * @param image RealColorImage to convert
	 * @return deep copy of <code>image</code>
	 */
	public static RealColorImage YIQtoRGB(RealColorImage image) {
		RealColorImage rImage = new RealColorImage(image.X(), image.Y());
		for (int x = 0; x < image.X(); x++) {
			for (int y = 0; y < image.Y(); y++) {
				rImage.set(x, y, YIQtoRGB(image.get(x, y)));
			}
		}

		return rImage;
	}

	/**
	 * Converts a RealColorImage value from RGB to HSV colorspace. R,G,B all (0..255), H (0..360)
	 * (or -1 when S == 0), S (0..1), V (0..255)
	 * 
	 * @param rgb RGB value of a RealColorImage
	 */
	public static Float[] RGBtoHSV(Float[] rgb) {

		Float[] hsv = new Float[3];
		float max = maximum(rgb);
		float min = minimum(rgb);

		//Calculate the value
		hsv[2] = max;

		//Calculate the saturation. Black's satuation is 0.
		if (max == 0) {
			hsv[1] = 0f;
		} else
			hsv[1] = ((max - min) / max);

		//Calculate the hue
		if (hsv[1] == 0) {
			hsv[0] = (float) -1.0; //-1 denotes an undefined hue
			//			if (max != 0)	System.out.println("(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ") has hue of 0: (" + hsv[0] + "," +hsv[1]+","+hsv[2]+")");
		} else {
			float delta = max - min;
			if (rgb[0] == max)
				hsv[0] = (rgb[1] - rgb[2]) / delta;
			else if (rgb[1] == max)
				hsv[0] = (float) (2.0 + (rgb[2] - rgb[0]) / delta);
			else if (rgb[2] == max)
				hsv[0] = (float) (4.0 + (rgb[0] - rgb[1]) / delta);

			//Convert to degree and make sure it is non negative
			hsv[0] *= 60f;
			if (hsv[0] < 0)
				hsv[0] += 360f;
		}
		return hsv;
	}

	/**
	 * Converts a RealColorImage value from HSV to RGB colorspace. R,G,B all (0..255), H (0..360)
	 * (or -1 when S == 0), S (0..1), V (0..255)
	 * 
	 * @param hsv HSV value of a RealColorImage
	 */
	public static Float[] HSVtoRGB(Float[] hsv) throws NotHSVColorSpaceException {

		Float[] rgb = new Float[3];
		if (hsv[1] == 0.0) {
			if (hsv[0] == -1.0) {
				//				System.out.println("(" + hsv[0] + "," + hsv[1] + "," + hsv[2] + ") is a valid hsv value");
				rgb[0] = hsv[2];
				rgb[1] = hsv[2];
				rgb[2] = hsv[2];
			} else {
				//				System.out.println("invalid HSV values are: (" + hsv[0] + "," + hsv[1] + "," + hsv[2] + ")");
				throw new NotHSVColorSpaceException();
			}
		} else {
			if (hsv[0] == 360.0)
				hsv[0] = 0f;
			hsv[0] /= 60f;
			int i = (int) Math.floor(hsv[0]);
			float f = (float) (hsv[0] - (float) i);
			float p = (float) (hsv[2] * (1.0 - hsv[1]));
			float q = (float) (hsv[2] * (1.0 - (hsv[1] * f)));
			float t = (float) (hsv[2] * (1.0 - (hsv[1] * (1.0 - f))));
			switch (i) {
			case 0:
				rgb[0] = hsv[2];
				rgb[1] = t;
				rgb[2] = p;
				break;
			case 1:
				rgb[0] = q;
				rgb[1] = hsv[2];
				rgb[2] = p;
				break;
			case 2:
				rgb[0] = p;
				rgb[1] = hsv[2];
				rgb[2] = t;
				break;
			case 3:
				rgb[0] = p;
				rgb[1] = q;
				rgb[2] = hsv[2];
				break;
			case 4:
				rgb[0] = t;
				rgb[1] = p;
				rgb[2] = hsv[2];
				break;
			case 5:
				rgb[0] = hsv[2];
				rgb[1] = p;
				rgb[2] = q;
				break;
			}
		}
		return rgb;
	}

	/**
	 * Converts a RealColorImage value from RGB to HLS colorspace. R,G,B all (0..255), H (0..360)
	 * (or -1 if S == 0),L (0..255), S (0..1)
	 * 
	 * @param rgb RGB value of a RealColorImage
	 */
	public static Float[] RGBtoHLS(Float[] rgb) {
		Float[] hls = new Float[3];
		float max = maximum(rgb);
		float min = minimum(rgb);

		//lightness
		hls[1] = (float) ((max + min) / 2.0);

		//saturation
		if (max == min) {
			hls[2] = 0f;//saturation
			hls[0] = -1f;//hue (undefined)
		} else {
			float delta = max - min;

			//saturation
			hls[2] = (hls[1] <= 0.5) ? (float) (delta / (max + min))
					: (float) (delta / (2.0 - (max + min)));

			//hue
			if (rgb[0] == max)
				hls[0] = (rgb[1] - rgb[2]) / delta;
			else if (rgb[1] == max)
				hls[0] = (float) (2.0 + (rgb[2] - rgb[0]) / delta);
			else if (rgb[2] == max)
				hls[0] = (float) (4.0 + (rgb[0] - rgb[1]) / delta);

			hls[0] *= 60f;

			if (hls[0] < 0)
				hls[0] += 360f;
		}

		return hls;
	}

	/**
	 * Converts a RealColorImage value from HLS to RGB colorspace. R,G,B all (0..255), H (0..360)
	 * (or -1 if S == 0),L (0..255), S (0..1)
	 * 
	 * @param hls HLS value of a RealColorImage
	 */
	public static Float[] HLStoRGB(Float[] hls) throws NotHLSColorSpaceException {
		Float[] rgb = new Float[3];
		float m1, m2;

		m2 = (hls[1] <= 0.5) ? (float) (hls[1] * (hls[1] + hls[2]))
				: (float) (hls[1] + hls[2] - (hls[1] * hls[2]));
		m1 = (float) ((2.0 * hls[1]) - m2);

		if (hls[2] == 0) {
			if (hls[0] == -1) {
				rgb[0] = rgb[1] = rgb[2] = hls[1];
			} else {
				//				System.out.println("invalid HLS values are: (" + hls[0] + "," + hls[1] + "," + hls[2] + ")");
				throw new NotHLSColorSpaceException();
			}
		} else {
			rgb[0] = value(m1, m2, (float) (hls[0] + 120.0));
			rgb[1] = value(m1, m2, hls[0]);
			rgb[2] = value(m1, m2, (float) (hls[0] - 120.0));
		}

		return rgb;
	}

	/**
	 * Converts a RealColorImage value from RGB to CMY colorspace. R,G,B,C,M,Y all (0..255)
	 * 
	 * @param rgb RGB value of a RealColorImage
	 */
	public static Float[] RGBtoCMY(Float[] rgb) {
		Float[] cmy = new Float[3];

		cmy[0] = 255 - rgb[0];
		cmy[1] = 255 - rgb[1];
		cmy[2] = 255 - rgb[2];

		return cmy;
	}

	/**
	 * Converts a RealColorImage value from CMY to RGB colorspace. R,G,B,C,M,Y all (0..255)
	 * 
	 * @param cmy CMY value of a RealColorImage
	 */
	public static Float[] CMYtoRGB(Float[] cmy) {
		Float[] rgb = new Float[3];

		rgb[0] = 255 - cmy[0];
		rgb[1] = 255 - cmy[1];
		rgb[2] = 255 - cmy[2];

		return rgb;
	}

	/**
	 * Converts a RealColorImage value from RGB to YIQ colorspace. R,G,B,Y,I,Q all (0..255)
	 * 
	 * @param rgb RGB value of a RealColorImage
	 */
	public static Float[] RGBtoYIQ(Float[] rgb) {
		Float[] yiq = new Float[3];

		//everything must be converted to double for the matrix operation
		double[][] drgb = new double[3][1];
		drgb[0][0] = (double) rgb[0];
		drgb[1][0] = (double) rgb[1];
		drgb[2][0] = (double) rgb[2];

		//conversion matrix for RGB -> YIQ
		double[][] data = { { 0.299, 0.587, 0.114 }, { 0.596, -.275, -.321 },
				{ 0.212, -.523, 0.311 } };

		Matrix m = new Matrix(data);

		Matrix val = new Matrix(drgb);

		val = m.mult(val);

		yiq[0] = (float) val.get(0, 0);
		yiq[1] = (float) val.get(1, 0);
		yiq[2] = (float) val.get(2, 0);

		return yiq;
	}

	/**
	 * Converts a RealColorImage value from YIQ to RGB colorspace. R,G,B,Y,I,Q all (0..255)
	 * 
	 * @param yiq YIQ value of a RealColorImage
	 */
	public static Float[] YIQtoRGB(Float[] yiq) {
		Float[] rgb = new Float[3];

		//everything must be converted to double for the matrix operation
		double[][] dyiq = new double[3][1];
		dyiq[0][0] = (double) yiq[0];
		dyiq[1][0] = (double) yiq[1];
		dyiq[2][0] = (double) yiq[2];

		//conversion matrix for YIQ -> RGB (inverse of matrix for RGB -> YIQ)
		double[][] data = { { 1.0000, 0.9557, 0.6199 }, { 1.0000, -.2716, -.6469 },
				{ 1.0000, -1.1082, 1.7051 } };

		Matrix m = new Matrix(data);

		Matrix val = new Matrix(dyiq);

		val = m.mult(val);

		rgb[0] = (float) val.get(0, 0);
		rgb[1] = (float) val.get(1, 0);
		rgb[2] = (float) val.get(2, 0);

		return rgb;
	}
}
