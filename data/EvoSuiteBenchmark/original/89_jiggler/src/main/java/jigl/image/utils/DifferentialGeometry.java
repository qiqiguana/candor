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
import jigl.image.types.ImageKernel;
import jigl.image.types.KernelType;
import jigl.image.types.RealGrayImage;

/**
 * This class performs various differential geometry operations on an image. It calculates only as
 * needed and then stores the results into various internal images. For example, if an operation
 * needs the first derivative calculated, then it will store the result internally for use later on.
 */
public class DifferentialGeometry {
	/** Original image */
	protected RealGrayImage image = null;

	/** First derivative in the x direction. */
	private RealGrayImage IxImage = null;

	/** First derivative in the y direction. */
	private RealGrayImage IyImage = null;

	/** Second derivative in the x direction. */
	private RealGrayImage IxxImage = null;

	/** Second derivative in the y direction. */
	private RealGrayImage IyyImage = null;

	/** second derivative in the xy direction */
	private RealGrayImage IxyImage = null;

	/** A image stores the gradient magnitude (steep) at each pixel. */
	private RealGrayImage GradientMagnitude = null;

	/** A image stores the curvature at each pixel. */
	private RealGrayImage IsophoteCurvature = null;

	// /** A image stores the isophote curvature at each pixel. */
	// private RealGrayImage IsophoteCurvatureFlow = null;

	/** A image stores the gradient phase at each pixel. */
	private RealGrayImage OrientationMap = null;

	/**
	 * This constructor takes a RealGrayImage and stores a shallow copy (If you change the image
	 * that is passed it will change the stored image)
	 */
	public DifferentialGeometry(RealGrayImage grimage) {
		image = grimage;
	}

	/**
	 * Returns Ix (first derivative in the x direction) of the image. If it has not been previously
	 * calculated this function will return null.
	 */
	public RealGrayImage getIx() {
		try {
			if (IxImage == null)
				return calcIx();
			return IxImage;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns Iy (first derivative in the y direction) of the image. If it has not been previously
	 * calculated this function will return null.
	 */
	public RealGrayImage getIy() {
		try {
			if (IyImage == null)
				return calcIy();
			return IyImage;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns Ixx (second derivative in the x direction) of the image. If it has not been
	 * previously calculated this function will return null.
	 */
	public RealGrayImage getIxx() {
		try {
			if (IxxImage == null)
				return calcIxx();
			return IxxImage;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns Iyy (second derivative in the y direction) of the image. If it has not been
	 * previously calculated this function will return null.
	 */
	public RealGrayImage getIyy() {
		try {
			if (IyyImage == null)
				return calcIyy();
			return IyyImage;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns Ixy (second derivative in the xy direction) of the image. If it has not been
	 * previously calculated this function will return null.
	 */
	public RealGrayImage getIxy() {
		try {
			if (IxImage == null)
				return calcIxy();
			return IxyImage;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Calculates Ix (first derivative in the x direction) of the image. Stores a copy of returned
	 * image which can be accessed through <i>getIx()</i>
	 */
	public RealGrayImage calcIx() throws jigl.image.exceptions.ImageNotSupportedException {

		IxImage = new RealGrayImage(image.X(), image.Y());
		try {
			ImageKernel ik = new ImageKernel(KernelType.SOBEL_X);
			Convolve c = new Convolve(ik);
			IxImage = (RealGrayImage) c.apply(image);
		} catch (Exception e) {
			System.out.println("Internal Jigl ImageKernel error.");
			return null;
		}
		IxImage.divide(8);
		return IxImage;
	}

	/**
	 * Calculate Iy (first derivative in the y direction) of the image. Stores a copy of returned
	 * image which can be accessed through <i>getIy()</i>
	 */
	public RealGrayImage calcIy() throws jigl.image.exceptions.ImageNotSupportedException {

		IyImage = new RealGrayImage(image.X(), image.Y());
		try {
			ImageKernel ik = new ImageKernel(KernelType.SOBEL_Y);
			Convolve c = new Convolve(ik);
			IyImage = (RealGrayImage) c.apply(image);
		} catch (Exception e) {
			System.out.println("Internal Jigl ImageKernel error.");
			return null;
		}
		IyImage.divide(8);
		return IyImage;
	}

	/**
	 * Calculate Ixx (second derivative in the x direction) of the image. Stores a copy of returned
	 * image which can be accessed through <i>getIxx()</i>
	 */
	public RealGrayImage calcIxx() throws jigl.image.exceptions.ImageNotSupportedException {

		IxxImage = new RealGrayImage(image.X(), image.Y());
		try {
			ImageKernel ik = new ImageKernel(KernelType.D_XX);
			Convolve c = new Convolve(ik);
			IxxImage = (RealGrayImage) c.apply(image);
		} catch (Exception e) {
			System.out.println("Internal Jigl ImageKernel error.");
			return null;
		}
		IxxImage.divide(3);
		return IxxImage;
	}

	/**
	 * Calculate Iyy (second derivative in the y direction) of the image. Stores a copy of returned
	 * image which can be accessed through <i>getIyy()</i>
	 */
	public RealGrayImage calcIyy() throws jigl.image.exceptions.ImageNotSupportedException {

		IyyImage = new RealGrayImage(image.X(), image.Y());
		try {
			ImageKernel ik = new ImageKernel(KernelType.D_YY);
			Convolve c = new Convolve(ik);
			IyyImage = (RealGrayImage) c.apply(image);
		} catch (Exception e) {
			System.out.println("Internal Jigl ImageKernel error.");
			return null;
		}
		IyyImage.divide(3);
		return IyyImage;
	}

	/**
	 * Calculate Ixy (second derivative in the xy direction) of the image. Stores a copy of returned
	 * image which can be accessed through <i>getIxy()</i>
	 */
	public RealGrayImage calcIxy() throws jigl.image.exceptions.ImageNotSupportedException {

		IxyImage = new RealGrayImage(image.X(), image.Y());
		try {
			ImageKernel ik = new ImageKernel(KernelType.D_YY);
			Convolve c = new Convolve(ik);
			IxyImage = (RealGrayImage) c.apply(image);
		} catch (Exception e) {
			System.out.println("Internal Jigl ImageKernel error.");
			return null;
		}
		IxyImage.divide(3);
		return IxyImage;
	}

	/**
	 * This function calculates the Isophote of a RealGrayImage. If Ix, Iy, Ixx, Ixy, or Iyy had
	 * been previously been calculated, it will use them.
	 */
	public RealGrayImage calcIsophoteStore() throws ImageNotSupportedException {
		if (IxImage == null)
			IxImage = calcIx();
		if (IyImage == null)
			IyImage = calcIy();
		if (IxxImage == null)
			IxxImage = calcIxx();
		if (IyyImage == null)
			IyyImage = calcIyy();
		if (IxyImage == null)
			IxyImage = calcIxy();

		int X = image.X();
		int Y = image.Y();

		double Iso = 0;
		float Ix, Iy, Ixx, Iyy, Ixy;

		IsophoteCurvature = new RealGrayImage(X, Y);

		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {

				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);
				Ixx = IxxImage.get(x, y);
				Iyy = IyyImage.get(x, y);
				Ixy = IxyImage.get(x, y);

				Iso = ((Iy * Iy * Ixx) - (2 * Ix * Ixy * Iy) + (Iyy * Ix * Ix))
						/ Math.sqrt(((Ix * Ix) + (Iy * Iy)) * ((Ix * Ix) + (Iy * Iy))
								* ((Ix * Ix) + (Iy * Iy)));

				// put the result floato output image
				IsophoteCurvature.set(x, y, (float) Iso);

			}
		}
		return IsophoteCurvature;
	}

	/**
	 * This method caluculates the Isophote of a RealGrayImage. This function is less memory
	 * floatensive than <i>calcIsophoteStore</i>
	 */
	public RealGrayImage calcIsophoteOnce() {

		// check for null images
		if (image == null)
			return null;

		// input image dimensions
		int X = image.X();
		int Y = image.Y();

		// create output image
		IsophoteCurvature = new RealGrayImage(X, Y);

		// variables for calculating Isophote
		float Iso;
		float Ix;
		float Iy;
		float Ixx;
		float Ixy;
		float Iyy;

		// float aIx;
		// float aIy;

		float c;
		float n;
		float ne;
		float e;
		float se;
		float s;
		float sw;
		float w;
		float nw;

		// calculate derivatives
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {

				try {
					c = image.get(x, y);
					n = image.get(x, y - 1);
					ne = image.get(x + 1, y - 1);
					e = image.get(x + 1, y);
					se = image.get(x + 1, y + 1);
					s = image.get(x, y + 1);
					sw = image.get(x - 1, y + 1);
					w = image.get(x - 1, y);
					nw = image.get(x - 1, y - 1);

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
					Iso = (float) (((Iy * Iy * Ixx) - (2 * Ix * Ixy * Iy) + (Iyy * Ix * Ix)) / Math
							.sqrt(((Ix * Ix) + (Iy * Iy)) * ((Ix * Ix) + (Iy * Iy))
									* ((Ix * Ix) + (Iy * Iy))));

					// put the result floato output image
					IsophoteCurvature.set(x, y, Iso);
				} catch (ArrayIndexOutOfBoundsException ex) {
				}
			}
		}

		return IsophoteCurvature;

	} // calcIsophote

	/** Calculate isophote flow of current image. */
	public RealGrayImage calcIsophoteFlowStore() throws ImageNotSupportedException {
		if (IxImage == null)
			IxImage = calcIx();
		if (IyImage == null)
			IyImage = calcIy();
		if (IxxImage == null)
			IxxImage = calcIxx();
		if (IyyImage == null)
			IyyImage = calcIyy();
		if (IxyImage == null)
			IxyImage = calcIxy();

		int X = image.X();
		int Y = image.Y();

		double Iso = 0;
		float Ix, Iy, Ixx, Iyy, Ixy;

		IsophoteCurvature = new RealGrayImage(X, Y);

		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {

				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);
				Ixx = IxxImage.get(x, y);
				Iyy = IyyImage.get(x, y);
				Ixy = IxyImage.get(x, y);

				Iso = ((Iy * Iy * Ixx) - (2 * Ix * Ixy * Iy) + (Iyy * Ix * Ix))
						/ (1 + (Ix * Ix) + (Iy * Iy));

				// put the result floato output image
				IsophoteCurvature.set(x, y, (float) Iso);

			}
		}
		return IsophoteCurvature;
	}

	/**
	 * This method calculates the Isophote of a RealGrayImage. This function is less memory
	 * floatensive than <i>calcIsophoteStore</i>.
	 */
	public RealGrayImage calcIsophoteFlowOnce() {

		// check for null images
		if (image == null)
			return null;

		// input image dimensions
		int X = image.X();
		int Y = image.Y();

		// create output image
		IsophoteCurvature = new RealGrayImage(X, Y);

		// variables for calculating Isophote
		float Iso;
		float Ix;
		float Iy;
		float Ixx;
		float Ixy;
		float Iyy;

		// float aIx;
		// float aIy;

		float c;
		float n;
		float ne;
		float e;
		float se;
		float s;
		float sw;
		float w;
		float nw;

		// calculate derivatives
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {

				try {
					c = image.get(x, y);
					n = image.get(x, y - 1);
					ne = image.get(x + 1, y - 1);
					e = image.get(x + 1, y);
					se = image.get(x + 1, y + 1);
					s = image.get(x, y + 1);
					sw = image.get(x - 1, y + 1);
					w = image.get(x - 1, y);
					nw = image.get(x - 1, y - 1);

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
					Iso = ((Iy * Iy * Ixx) - (2 * Ix * Ixy * Iy) + (Iyy * Ix * Ix))
							/ (1 + (Ix * Ix) + (Iy * Iy));

					// put the result floato output image
					IsophoteCurvature.set(x, y, Iso);
				} catch (ArrayIndexOutOfBoundsException ex) {
				}
			}
		}

		return IsophoteCurvature;

	} // calcIsophote

	/** Calculate the magnitude part of the gradient. */
	RealGrayImage GradientMagnitude() throws ImageNotSupportedException {
		if (IxImage == null)
			IxImage = calcIx();
		if (IyImage == null)
			IyImage = calcIy();

		int X = image.X();
		int Y = image.Y();

		double Iso = 0;
		float Ix, Iy/*, Ixx, Iyy, Ixy*/;

		GradientMagnitude = new RealGrayImage(X, Y);

		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {

				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);
				//				Ixx = IxxImage.get(x, y);
				//				Iyy = IyyImage.get(x, y);
				//				Ixy = IxyImage.get(x, y);

				Iso = Math.sqrt((Iy * Iy) + (Ix * Ix));

				// put the result float to output image
				GradientMagnitude.set(x, y, (float) Iso);

			}
		}
		return GradientMagnitude;
	}

	/** An approximate way to calculate the magnitude part of gradient. */
	RealGrayImage ApproximateGradientMagnitude() throws ImageNotSupportedException {
		if (IxImage == null)
			IxImage = calcIx();
		if (IyImage == null)
			IyImage = calcIy();

		int X = image.X();
		int Y = image.Y();

		double Iso = 0;
		float Ix, Iy/*, Ixx, Iyy, Ixy*/;

		RealGrayImage rgi = new RealGrayImage(X, Y);

		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {

				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);
				//				Ixx = IxxImage.get(x, y);
				//				Iyy = IyyImage.get(x, y);
				//				Ixy = IxyImage.get(x, y);

				Iso = (Iy + Ix);

				// put the result float on output image
				rgi.set(x, y, (float) Iso);

			}
		}
		return rgi;
	}

	/** Calculate the pahse part of gradient. */
	RealGrayImage OrientationMap() throws ImageNotSupportedException {
		if (IxImage == null)
			IxImage = calcIx();
		if (IyImage == null)
			IyImage = calcIy();

		int X = image.X();
		int Y = image.Y();

		double Iso = 0;
		float Ix, Iy/*, Ixx, Iyy, Ixy*/;

		OrientationMap = new RealGrayImage(X, Y);

		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {

				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);
				//				Ixx = IxxImage.get(x, y);
				//				Iyy = IyyImage.get(x, y);
				//				Ixy = IxyImage.get(x, y);

				Iso = Math.atan2(Iy, Ix);

				// put the result float to output image
				OrientationMap.set(x, y, (float) Iso);

			}
		}
		return OrientationMap;
	}

}
