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

import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.ops.Convolve;
import jigl.image.types.GrayImage;
import jigl.image.types.ImageKernel;
import jigl.image.types.KernelType;

/** This class have most of the functions of {@link jigl.image.utils.DifferentialGeometry}. */
class Scaler {

	private GrayImage IxImage = null;
	private GrayImage IyImage = null;
	private GrayImage IxxImage = null;
	private GrayImage IyyImage = null;
	private GrayImage IxyImage = null;

	/**
	 * Returns Ix (first derivative in the x direction) of the image. If it has not been previously
	 * calculated this function will return null.
	 */
	public GrayImage getIx() {
		return IxImage;
	}

	/**
	 * Returns Iy (first derivative in the y direction) of the image. If it has not been previously
	 * calculated this function will return null.
	 */
	public GrayImage getIy() {
		return IyImage;
	}

	/**
	 * Returns Ixx (second derivative in the x direction) of the image. If it has not been
	 * previously calculated this function will return null.
	 */
	public GrayImage getIxx() {
		return IxxImage;
	}

	/**
	 * Returns Iyy (second derivative in the y direction) of the image. If it has not been
	 * previously calculated this function will return null.
	 */
	public GrayImage getIyy() {
		return IyyImage;
	}

	/**
	 * Returns Ixy (second derivative in the xy direction) of the image. If it has not been
	 * previously calculated this function will return null.
	 */
	public GrayImage getIxy() {
		return IxyImage;
	}

	/**
	 * Calculate Ix (first derivative in the x direction) of the image. Stores a copy of returned
	 * image which can be accessed through <i>getIx()</i>
	 */
	public GrayImage calcIx(GrayImage im) throws jigl.image.exceptions.ImageNotSupportedException {
		GrayImage gimage = null;
		try {
			ImageKernel ik = new ImageKernel(KernelType.SOBEL_X);
			Convolve c = new Convolve(ik);
			gimage = (GrayImage) c.apply(im);
		} catch (Exception e) {
			System.out.println("Internal Jigl ImageKernel error.");
			return null;
		}
		gimage.divide(8);
		IxImage = gimage;
		return gimage;
	}

	/**
	 * Calculate Iy (first derivative in the y direction) of the image. Stores a copy of returned
	 * image which can be accessed through <i>getIy()</i>
	 */
	public GrayImage calcIy(GrayImage im) throws jigl.image.exceptions.ImageNotSupportedException {
		GrayImage gimage = null;
		try {
			ImageKernel ik = new ImageKernel(KernelType.SOBEL_Y);
			Convolve c = new Convolve(ik);
			gimage = (GrayImage) c.apply(im);
		} catch (Exception e) {
			System.out.println("Internal Jigl ImageKernel error.");
			return null;
		}
		gimage.divide(8);
		IyImage = gimage;
		return gimage;
	}

	/**
	 * Calculate Ixx (second derivative in the x direction) of the image. Stores a copy of returned
	 * image which can be accessed through <i>getIxx()</i>
	 */
	public GrayImage calcIxx(GrayImage im) throws jigl.image.exceptions.ImageNotSupportedException {
		GrayImage gimage = null;
		try {
			ImageKernel ik = new ImageKernel(KernelType.D_XX);
			Convolve c = new Convolve(ik);
			gimage = (GrayImage) c.apply(im);
		} catch (Exception e) {
			System.out.println("Internal Jigl ImageKernel error.");
			return null;
		}
		gimage.divide(3);
		IxxImage = gimage;
		return gimage;
	}

	/**
	 * Calculate Iyy (second derivative in the y direction) of the image. Stores a copy of returned
	 * image which can be accessed through <i>getIyy()</i>
	 */
	public GrayImage calcIyy(GrayImage im) throws jigl.image.exceptions.ImageNotSupportedException {
		GrayImage gimage = null;
		try {
			ImageKernel ik = new ImageKernel(KernelType.D_YY);
			Convolve c = new Convolve(ik);
			gimage = (GrayImage) c.apply(im);
		} catch (Exception e) {
			System.out.println("Internal Jigl ImageKernel error.");
			return null;
		}
		gimage.divide(3);
		IyyImage = gimage;
		return gimage;
	}

	/**
	 * Calculate Ixy (second derivative in the xy direction) of the image. Stores a copy of returned
	 * image which can be accessed through <i>getIxy()</i>
	 */
	public GrayImage calcIxy(GrayImage im) throws jigl.image.exceptions.ImageNotSupportedException {
		GrayImage gimage = null;
		try {
			ImageKernel ik = new ImageKernel(KernelType.D_XY);
			Convolve c = new Convolve(ik);
			gimage = (GrayImage) c.apply(im);
		} catch (Exception e) {
			System.out.println("Internal Jigl ImageKernel error.");
			return null;
		}
		gimage.divide(4);
		IxyImage = gimage;
		return gimage;
	}

	/**
	 * This function calculates the Isophote of a GrayImage. If Ix, Iy, Ixx, Ixy, or Iyy had been
	 * previously been calculated, it will use them (unless <i>recalculate</i> is set to true
	 * 
	 * @param recalculate true=recalculate Ix, Iy, Ixx, Ixy, and Iyy; false=use previously
	 *            calculated values, calculate only those that have not been calculated yet.
	 */
	public GrayImage calcIsophoteStore(GrayImage im, boolean recalculate)
			throws ImageNotSupportedException {
		if (IxImage == null || recalculate)
			IxImage = calcIx(im);
		if (IyImage == null || recalculate)
			IyImage = calcIy(im);
		if (IxxImage == null || recalculate)
			IxxImage = calcIxx(im);
		if (IyyImage == null || recalculate)
			IyyImage = calcIyy(im);
		if (IxyImage == null || recalculate)
			IxyImage = calcIxy(im);

		int X = im.X();
		int Y = im.Y();

		int Iso = 0;
		int Ix, Iy, Ixx, Iyy, Ixy;

		GrayImage out = new GrayImage(X, Y);

		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {

				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);
				Ixx = IxxImage.get(x, y);
				Iyy = IyyImage.get(x, y);
				Ixy = IxyImage.get(x, y);

				Iso = (((Iy * Iy)) * Ixx - 2 * Ix * Ixy * Iy + Iyy * ((Ix * Ix)))
						/ (1 + (Ix * Ix) + (Iy * Iy));

				// put the result into output image
				out.set(x, y, Iso);

			}
		}
		return out;
	}

	/**
	 * This method caluculates the Isophote of a GrayImage. This function is less memory intensive
	 * than <i>calcIsophoteStore</i>
	 */
	public GrayImage calcIsophoteOnce(GrayImage in) {

		// check for null images
		if (in == null)
			return null;

		// input image dimensions
		int X = in.X();
		int Y = in.Y();

		// create output image
		GrayImage out = new GrayImage(X, Y);

		// variables for calculating Isophote
		int Iso;
		int Ix;
		int Iy;
		int Ixx;
		int Ixy;
		int Iyy;

		//    int aIx;
		//    int aIy;

		int c;
		int n;
		int ne;
		int e;
		int se;
		int s;
		int sw;
		int w;
		int nw;

		// calculate derivatives
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {

				try {
					c = in.get(x, y);
					n = in.get(x, y - 1);
					ne = in.get(x + 1, y - 1);
					e = in.get(x + 1, y);
					se = in.get(x + 1, y + 1);
					s = in.get(x, y + 1);
					sw = in.get(x - 1, y + 1);
					w = in.get(x - 1, y);
					nw = in.get(x - 1, y - 1);

					// calculate Ix
					Ix = (-w + e) / 2;

					// calculate Iy
					Iy = (-n + s) / 2;

					// calculate Ixx
					Ixx = w + (-2 * c) + e;

					// calculate Iyy
					Iyy = n + (-2 * c) + s;

					// calculate Ixy
					Ixy = (nw + -ne + -sw + se) / 4;

					// calculate Iso
					Iso = (((Iy * Iy)) * Ixx - 2 * Ix * Ixy * Iy + Iyy * ((Ix * Ix)))
							/ (1 + (Ix * Ix) + (Iy * Iy));

					// put the result into output image
					out.set(x, y, Iso);
				} catch (ArrayIndexOutOfBoundsException ex) {
				}
			}
		}

		return out;

	} // calcIsophote

}
