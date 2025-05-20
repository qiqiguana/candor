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

import jigl.image.ROI;
import jigl.image.ops.Convolve;
import jigl.image.types.ImageKernel;
import jigl.image.types.KernelType;
import jigl.image.types.RealGrayImage;

/**
 * This class performs various differential geometry operations on a specified area of an image.
 * While {@link jigl.image.utils.DifferentialGeometry} does the calculations for the whole image.
 * <p>
 * It calculates only as needed and then stores the results into various internal images. For
 * example, if an operation needs the first derivative calculated, then it will store the result
 * internally for use later on.
 * 
 * <p>
 * Suports only RealGrayImage
 */
public class LocalDifferentialGeometry {
	/** The average squared Kappa value for current image. */
	public double avgKappaSquare;
	/** The average absolute Kappa value for current image. */
	public double avgAbsKappa;
	/** The standard deviation of Kappa value. */
	public double stdDevAbsKappa;
	/** The standard deviation of squared Kappa value. */
	public double stdDevKappaSquare;

	/** Original image */
	public RealGrayImage image;
	/** First derivative in the x direction. */
	public RealGrayImage IxImage;
	/** First derivative in the y direction. */
	public RealGrayImage IyImage;
	/** Second derivative in the x direction. */
	public RealGrayImage IxxImage;
	/** Second derivative in the y direction. */
	public RealGrayImage IyyImage;
	/** second derivative in the xy direction */
	public RealGrayImage IxyImage;
	/** A image stores the gradient magnitude (steep) at each pixel. */
	public RealGrayImage GradientMagnitude;
	/** A image stores the isophote curvature at each pixel. */
	public RealGrayImage IsophoteCurvature;
	/** A image stores the isophote curvature at each pixel. */
	public RealGrayImage IsophoteCurvatureFlow;
	/** A image stores the gradient phase at each pixel. */
	public RealGrayImage OrientationMap;

	/** Constructs a LocalDifferentialGeometry object from a RealGrayImage. */
	public LocalDifferentialGeometry(RealGrayImage im) {
		image = im;
		IsophoteCurvatureFlow = null;
		IxImage = null;
		IyImage = null;
		GradientMagnitude = null;
		IxxImage = null;
		IyyImage = null;
		OrientationMap = null;
		IsophoteCurvature = null;
		avgKappaSquare = 0;
		avgAbsKappa = 0;
		stdDevAbsKappa = 0;
		stdDevKappaSquare = 0;
	}

	/** Default constructor */
	public LocalDifferentialGeometry() {
		image = null;
		IsophoteCurvatureFlow = null;
		IxImage = null;
		IyImage = null;
		GradientMagnitude = null;
		IxxImage = null;
		IyyImage = null;
		IxyImage = null;
		OrientationMap = null;
		IsophoteCurvature = null;
		avgKappaSquare = 0;
		avgAbsKappa = 0;
		stdDevAbsKappa = 0;
		stdDevKappaSquare = 0;
	}

	/**
	 * Calculates Ix (first derivative in the x direction) of the image in a specified area.
	 * parameters: x1 - the left bound of the specified area y1 - the right bottom bound of the
	 * specified area x2 - the right bound of the specified area y2 - the top bound of the specified
	 * area
	 */
	public RealGrayImage calcIx(int x1, int y1, int x2, int y2) {
		if (IxImage == null)
			IxImage = new RealGrayImage(image.X(), image.Y());

		try {
			ImageKernel ik = new ImageKernel(KernelType.SOBEL_X);
			Convolve c = new Convolve(ik);
			ROI roi = new ROI(x1, y1, x2, y2);
			IxImage = (RealGrayImage) c.apply(image, roi);// x1, y1, x2,
			// y2);

			for (int x = x1; x < x2; x++)
				for (int y = y1; y < y2; y++)
					IxImage.set(x, y, IxImage.get(x, y) / 8.0f);
		} catch (Exception e) {
		}

		return IxImage;
	}

	/**
	 * Calculate Iy (first derivative in the y direction) of the image in a specified area.
	 * parameters: x1 - the left bound of the specified area y1 - the right bottom bound of the
	 * specified area x2 - the right bound of the specified area y2 - the top bound of the specified
	 * area
	 */
	public RealGrayImage calcIy(int x1, int y1, int x2, int y2) {
		if (IyImage == null)
			IyImage = new RealGrayImage(image.X(), image.Y());

		try {
			ImageKernel ik = new ImageKernel(KernelType.SOBEL_Y);
			Convolve c = new Convolve(ik);
			IyImage = (RealGrayImage) c.apply(image, new ROI(x1, y1, x2, y2));

			for (int x = x1; x < x2; x++)
				for (int y = y1; y < y2; y++)
					IyImage.set(x, y, (float) (IyImage.get(x, y) / 8.0));
		} catch (Exception e) {
		}
		return IyImage;
	}

	/**
	 * Calculates Ixx (second derivative in the x direction) of the image in a specified area.
	 * parameters: x1 - the left bound of the specified area y1 - the right bottom bound of the
	 * specified area x2 - the right bound of the specified area y2 - the top bound of the specified
	 * area
	 */
	public RealGrayImage calcIxx(int x1, int y1, int x2, int y2) {
		if (IxxImage == null)
			IxxImage = new RealGrayImage(image.X(), image.Y());

		try {
			ImageKernel ik = new ImageKernel(KernelType.D_XX);
			Convolve c = new Convolve(ik);
			IxxImage = (RealGrayImage) c.apply(image, new ROI(x1, y1, x2, y2));

			IxxImage = IxxImage.divide(3f);
		} catch (Exception e) {
		}

		return IxxImage;
	}

	/**
	 * Calculates Iyy (second derivative in the y direction) of the image in a specified area.
	 * parameters: x1 - the left bound of the specified area y1 - the right bottom bound of the
	 * specified area x2 - the right bound of the specified area y2 - the top bound of the specified
	 * area
	 */
	public RealGrayImage calcIyy(int x1, int y1, int x2, int y2) {
		if (IyyImage == null)
			IyyImage = new RealGrayImage(image.X(), image.Y());

		try {
			ImageKernel ik = new ImageKernel(KernelType.D_YY);
			Convolve c = new Convolve(ik);
			IyyImage = (RealGrayImage) c.apply(image, new ROI(x1, y1, x2, y2));

			IyyImage = IyyImage.divide(3f);
		} catch (Exception e) {
		}
		return IyyImage;
	}

	/**
	 * Calculates Ixy (second derivative in the xy direction) of the image in a specified area.
	 * parameters: x1 - the left bound of the specified area y1 - the right bottom bound of the
	 * specified area x2 - the right bound of the specified area y2 - the top bound of the specified
	 * area
	 */
	public RealGrayImage calcIxy(int x1, int y1, int x2, int y2) {
		if (IxyImage == null)
			IxyImage = new RealGrayImage(image.X(), image.Y());

		try {
			ImageKernel ik;
			ik = new ImageKernel(KernelType.D_XY);

			Convolve c = new Convolve(ik);
			IxyImage = (RealGrayImage) c.apply(image, new ROI(x1, y1, x2, y2));

			IxyImage = IxyImage.divide(3f);
		} catch (Exception e) {
		}
		return IxyImage;
	}

	/**
	 * Calculates the isophote curvature at each pixel in a specified area of current image and
	 * returns the result curvature image. The formula is:<br>
	 * 
	 * <pre>
	 * Isophote curvature = ((Iy * Iy * Ixx) - (2 * Ix * Ixy * Iy) + (Iyy * Ix * Ix))
	 * 		/ sqrt(((Ix * Ix) + (Iy * Iy)) * ((Ix * Ix) + (Iy * Iy)) * ((Ix * Ix) + (Iy * Iy)));
	 * </pre>
	 * 
	 * parameters: x1 - the left bound of the specified area y1 - the right bottom bound of the
	 * specified area x2 - the right bound of the specified area y2 - the top bound of the specified
	 * area
	 */
	public RealGrayImage calcIsophoteStore(int x1, int y1, int x2, int y2) {

		IxImage = calcIx(x1, y1, x2, y2);
		IyImage = calcIy(x1, y1, x2, y2);
		IxxImage = calcIxx(x1, y1, x2, y2);
		IyyImage = calcIyy(x1, y1, x2, y2);
		IxyImage = calcIxy(x1, y1, x2, y2);

		int X = image.X();
		int Y = image.Y();

		double Iso = 0;
		float Ix, Iy, Ixx, Iyy, Ixy;

		if (IsophoteCurvature == null)
			IsophoteCurvature = new RealGrayImage(X, Y);

		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {

				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);
				Ixx = IxxImage.get(x, y);
				Iyy = IyyImage.get(x, y);
				Ixy = IxyImage.get(x, y);

				Iso = ((Iy * Iy * Ixx) - (2 * Ix * Ixy * Iy) + (Iyy * Ix * Ix))
						/ Math
								.sqrt((double) (((Ix * Ix) + (Iy * Iy)) * ((Ix * Ix) + (Iy * Iy)) * ((Ix * Ix) + (Iy * Iy))));

				// put the result float to output image
				IsophoteCurvature.set(x, y, (float) Iso);

			}
		}
		return IsophoteCurvature;
	}

	/**
	 * This method caluculates the isophote curvature image in a specified area. This function is
	 * less memory floatensive than <i>calcIsophoteStore</i>. parameters: x1 - the left bound of the
	 * specified area y1 - the right bottom bound of the specified area x2 - the right bound of the
	 * specified area y2 - the top bound of the specified area
	 */
	public RealGrayImage calcIsophote(int x1, int y1, int x2, int y2) {
		// check for null images
		if (image == null)
			return null;

		// input image dimensions
		int X = image.X();
		int Y = image.Y();

		if (IsophoteCurvature == null)
			IsophoteCurvature = new RealGrayImage(X, Y);

		// variables for calculating Isophote
		float Iso;
		float Ix;
		float Iy;
		float Ixx;
		float Ixy;
		float Iyy;

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
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {

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
							.sqrt((double) (((Ix * Ix) + (Iy * Iy)) * ((Ix * Ix) + (Iy * Iy)) * ((Ix * Ix) + (Iy * Iy)))));

					// put the result float to output image
					IsophoteCurvature.set(x, y, Iso);
				} catch (Exception ex) {
				}
			}
		}

		return IsophoteCurvature;

	} // calcIsophote

	/** isophote flow??? */
	public RealGrayImage calcIsophoteFlowStore(int x1, int y1, int x2, int y2) {
		IxImage = calcIx(x1, y1, x2, y2);
		IyImage = calcIy(x1, y1, x2, y2);
		IxxImage = calcIxx(x1, y1, x2, y2);
		IyyImage = calcIyy(x1, y1, x2, y2);
		IxyImage = calcIxy(x1, y1, x2, y2);

		int X = image.X();
		int Y = image.Y();

		double Iso = 0;
		float Ix, Iy, Ixx, Iyy, Ixy;

		if (IsophoteCurvatureFlow == null)
			IsophoteCurvatureFlow = new RealGrayImage(X, Y);

		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {

				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);
				Ixx = IxxImage.get(x, y);
				Iyy = IyyImage.get(x, y);
				Ixy = IxyImage.get(x, y);

				Iso = ((Iy * Iy * Ixx) - (2 * Ix * Ixy * Iy) + (Iyy * Ix * Ix))
						/ (1 + (Ix * Ix) + (Iy * Iy));

				// put the result float to output image
				IsophoteCurvatureFlow.set(x, y, (float) Iso);

			}
		}
		return IsophoteCurvatureFlow;
	}

	/**
	 * This method caluculates the Isophote flow of a RealGrayImage image at the specified area.
	 * This function is less memory floatensive than <i>calcIsophoteFlowStore</i>
	 */
	public RealGrayImage calcIsophoteFlow(int x1, int y1, int x2, int y2) {

		// check for null images
		if (image == null)
			return null;

		// input image dimensions
		int X = image.X();
		int Y = image.Y();

		// variables for calculating Isophote
		float Iso;
		float Ix;
		float Iy;
		float Ixx;
		float Ixy;
		float Iyy;

		float c;
		float n;
		float ne;
		float e;
		float se;
		float s;
		float sw;
		float w;
		float nw;

		if (IsophoteCurvatureFlow == null)
			IsophoteCurvatureFlow = new RealGrayImage(X, Y);

		// calculate derivatives
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				c = image.get(x, y);
				if (y - 1 > 0)
					n = image.get(x, y - 1);
				else
					n = 0;
				if (y - 1 > 0 && x + 1 < X)
					ne = image.get(x + 1, y - 1);
				else
					ne = 0;
				if (x + 1 < X)
					e = image.get(x + 1, y);
				else
					e = 0;
				if (y + 1 < Y && x + 1 < X)
					se = image.get(x + 1, y + 1);
				else
					se = 0;
				if (y + 1 < Y)
					s = image.get(x, y + 1);
				else
					s = 0;
				if (x - 1 > 0 && y + 1 < Y)
					sw = image.get(x - 1, y + 1);
				else
					sw = 0;
				if (x - 1 > 0)
					w = image.get(x - 1, y);
				else
					w = 0;
				if (y - 1 > 0 && x - 1 > 0)
					nw = image.get(x - 1, y - 1);
				else
					nw = 0;

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
				double temp = ((Iy * Iy * Ixx) - (2 * Ix * Ixy * Iy) + (Iyy * Ix * Ix))
						/ (0.01 + (Ix * Ix) + (Iy * Iy));
				Iso = (float) temp;
				// put the result floato output image
				IsophoteCurvatureFlow.set(x, y, Iso);
			}
		}

		return IsophoteCurvatureFlow;

	} // calcIsophote

	/**
	 * Calculates the gradient magnitude for each pixel in a specified area and stores the result in
	 * a RealGrayImage pointer. parameters: x1 - the left bound of the specified area y1 - the right
	 * bottom bound of the specified area x2 - the right bound of the specified area y2 - the top
	 * bound of the specified area
	 */
	public RealGrayImage calcGradientMagnitude(int x1, int y1, int x2, int y2) {
		IxImage = calcIx(x1, y1, x2, y2);
		IyImage = calcIy(x1, y1, x2, y2);

		int X = image.X();
		int Y = image.Y();

		double Iso = 0;
		float Ix, Iy;

		if (GradientMagnitude == null)
			GradientMagnitude = new RealGrayImage(X, Y);

		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);
				Iso = Math.sqrt((double) ((Iy * Iy) + (Ix * Ix)));

				// put the result floato output image
				GradientMagnitude.set(x, y, (float) Iso);
			}
		}
		return GradientMagnitude;
	}

	/**
	 * Calculates the approximate gradient magnitude for each pixel in a specified area and stores
	 * the result in a RealGrayImage pointer. parameters: x1 - the left bound of the specified area
	 * y1 - the right bottom bound of the specified area x2 - the right bound of the specified area
	 * y2 - the top bound of the specified area
	 */
	public RealGrayImage calcApproximateGradientMagnitude(int x1, int y1, int x2, int y2) {
		IxImage = calcIx(x1, y1, x2, y2);
		IyImage = calcIy(x1, y1, x2, y2);

		int X = image.X();
		int Y = image.Y();

		double Iso = 0;
		float Ix, Iy;

		if (GradientMagnitude == null)
			GradientMagnitude = new RealGrayImage(X, Y);

		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {

				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);

				Iso = (Iy + Ix);

				// put the result floato output image
				GradientMagnitude.set(x, y, (float) Iso);

			}
		}
		return GradientMagnitude;
	}

	/**
	 * Calculates the gradient phase for each pixel in a specified area and stores the result in a
	 * RealGrayImage pointer. parameters: x1 - the left bound of the specified area y1 - the right
	 * bottom bound of the specified area x2 - the right bound of the specified area y2 - the top
	 * bound of the specified area
	 */
	public RealGrayImage calcOrientationMap(int x1, int y1, int x2, int y2) {
		IxImage = calcIx(x1, y1, x2, y2);
		IyImage = calcIy(x1, y1, x2, y2);

		int X = image.X();
		int Y = image.Y();

		double Iso = 0;

		float Ix, Iy;

		if (OrientationMap == null)
			OrientationMap = new RealGrayImage(X, Y);

		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {

				// calculate Iso
				Ix = IxImage.get(x, y);
				Iy = IyImage.get(x, y);
				Iso = Math.atan2(Iy, Ix);
				// put the result floato output image
				OrientationMap.set(x, y, (float) Iso);

			}
		}
		return OrientationMap;
	}

	/**
	 * Overloaded function to stores the result in a RealGrayImage object pointed by
	 * <code>dest</code>.
	 */
	public void calcIsophoteCurvature(int x1, int y1, int x2, int y2, RealGrayImage src,
			RealGrayImage dest) {
		double totalPix = 0;
		double totalSquare = 0;
		double totalAbsKappa = 0;
		// double totalQuad = 0;

		// input image dimensions
		int X = src.X();
		int Y = src.Y();

		// variables for calculating Isophote
		float Iso;
		float Ix;
		float Iy;
		float Ixx;
		float Ixy;
		float Iyy;

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
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				c = src.get(x, y);
				if (y - 1 > 0)
					n = src.get(x, y - 1);
				else
					n = 0;
				if (y - 1 > 0 && x + 1 < X)
					ne = src.get(x + 1, y - 1);
				else
					ne = 0;
				if (x + 1 < X)
					e = src.get(x + 1, y);
				else
					e = 0;
				if (y + 1 < Y && x + 1 < X)
					se = src.get(x + 1, y + 1);
				else
					se = 0;
				if (y + 1 < Y)
					s = src.get(x, y + 1);
				else
					s = 0;
				if (x - 1 > 0 && y + 1 < Y)
					sw = src.get(x - 1, y + 1);
				else
					sw = 0;
				if (x - 1 > 0)
					w = src.get(x - 1, y);
				else
					w = 0;
				if (y - 1 > 0 && x - 1 > 0)
					nw = src.get(x - 1, y - 1);
				else
					nw = 0;

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
				double temp = ((Iy * Iy * Ixx) - (2 * Ix * Ixy * Iy) + (Iyy * Ix * Ix))
						/ (1e-0 + Math.pow((Ix * Ix) + (Iy * Iy), 1.5));
				Iso = (float) temp;
				// put the result float to output image
				dest.set(x - x1, y - y1, Iso);

				totalAbsKappa += (float) Math.abs(Iso);
				totalSquare += Iso * Iso;
				// totalQuad += totalSquare*totalSquare;
				totalPix++;
			}
		}
		avgKappaSquare = totalSquare / totalPix;
		avgAbsKappa = totalAbsKappa / totalPix;
		stdDevAbsKappa = Math.sqrt(avgKappaSquare - (avgAbsKappa * avgAbsKappa));
		// stdDevKappaSquare = sqrt((totalQuad/totalPix) - avgKappaSquare);
	} // calcIsophote

	/**
	 * Overloaded function to stores the result in a RealGrayImage object pointed by
	 * <code>dest</code>.
	 */
	public void calcIsophoteFlow(int x1, int y1, int x2, int y2, RealGrayImage src,
			RealGrayImage dest) {
		// input image dimensions
		int X = src.X();
		int Y = src.Y();

		// variables for calculating Isophote
		float Iso;
		float Ix;
		float Iy;
		float Ixx;
		float Ixy;
		float Iyy;

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
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				c = src.get(x, y);
				if (y - 1 > 0)
					n = src.get(x, y - 1);
				else
					n = 0;
				if (y - 1 > 0 && x + 1 < X)
					ne = src.get(x + 1, y - 1);
				else
					ne = 0;
				if (x + 1 < X)
					e = src.get(x + 1, y);
				else
					e = 0;
				if (y + 1 < Y && x + 1 < X)
					se = src.get(x + 1, y + 1);
				else
					se = 0;
				if (y + 1 < Y)
					s = src.get(x, y + 1);
				else
					s = 0;
				if (x - 1 > 0 && y + 1 < Y)
					sw = src.get(x - 1, y + 1);
				else
					sw = 0;
				if (x - 1 > 0)
					w = src.get(x - 1, y);
				else
					w = 0;
				if (y - 1 > 0 && x - 1 > 0)
					nw = src.get(x - 1, y - 1);
				else
					nw = 0;

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
				double temp = ((Iy * Iy * Ixx) - (2 * Ix * Ixy * Iy) + (Iyy * Ix * Ix))
						/ (0.1 + (Ix * Ix) + (Iy * Iy));// why 0.1????
				Iso = (float) temp;
				// put the result float to output image
				dest.set(x - x1, y - y1, Iso);
			}
		}
	} // calcIsophote

	/**
	 * Substitutes current image by another image in a specified rectangle area. parameters: x1 -
	 * the left bound of the specified area y1 - the right bottom bound of the specified area x2 -
	 * the right bound of the specified area y2 - the top bound of the specified area
	 */
	public void setImage(RealGrayImage im, int x1, int y1, int x2, int y2) {
		for (int x = x1; x < x2; x++)
			for (int y = y1; y < y2; y++)
				image.set(x, y, im.get(x, y));
	}

	/**
	 * Overloaded methods to calculate Ix and the result is stored in the image pointed to by
	 * <code>dest</code>. The origin of <code>*dest</code> is (x1,y1) of image <code>*src</code>.
	 */
	public void calcIy(RealGrayImage src, RealGrayImage dest, int x1, int y1, int x2, int y2) {
		// input image dimensions
		int X = src.X();
		int Y = src.Y();

		// variables for calculating Isophote
		// float c;
		float n;
		float ne;
		// float e;
		float se;
		float s;
		float sw;
		// float w;
		float nw;

		float temp;

		// calculate derivatives
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				// c = src.get(x, y);
				if (y - 1 > 0)
					n = src.get(x, y - 1);
				else
					n = 0;

				if (y - 1 > 0 && x + 1 < X)
					ne = src.get(x + 1, y - 1);
				else
					ne = 0;

				// if (x + 1 < X)
				// e = src.get(x + 1, y);
				// else
				// e = 0;

				if (y + 1 < Y && x + 1 < X)
					se = src.get(x + 1, y + 1);
				else
					se = 0;

				if (y + 1 < Y)
					s = src.get(x, y + 1);
				else
					s = 0;

				if (x - 1 > 0 && y + 1 < Y)
					sw = src.get(x - 1, y + 1);
				else
					sw = 0;

				// if (x - 1 > 0)
				// w = src.get(x - 1, y);
				// else
				// w = 0;

				if (y - 1 > 0 && x - 1 > 0)
					nw = src.get(x - 1, y - 1);
				else
					nw = 0;

				// calculate Iy
				temp = (float) ((-nw - 2.0 * n - ne + sw + 2.0 * s + se) / 8.0);

				// put the result float to output image
				dest.set(x - x1, y - y1, temp);
			}
		}

	}

	/**
	 * Overloaded methods to calculate Ix and the result is stored in the image pointed to by
	 * <code>dest</code>. The origin of <code>*dest</code> is (x1,y1) of image <code>*src</code>.
	 */
	public void calcIx(RealGrayImage src, RealGrayImage dest, int x1, int y1, int x2, int y2) {
		// input image dimensions
		int X = src.X();
		int Y = src.Y();

		// variables for calculating Isophote
		// float c;
		// float n;
		float ne;
		float e;
		float se;
		// float s;
		float sw;
		float w;
		float nw;

		float temp;

		// calculate derivatives
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				// c = src.get(x, y);
				// if (y - 1 > 0)
				// n = src.get(x, y - 1);
				// else
				// n = 0;

				if (y - 1 > 0 && x + 1 < X)
					ne = src.get(x + 1, y - 1);
				else
					ne = 0;

				if (x + 1 < X)
					e = src.get(x + 1, y);
				else
					e = 0;

				if (y + 1 < Y && x + 1 < X)
					se = src.get(x + 1, y + 1);
				else
					se = 0;

				// if (y + 1 < Y)
				// s = src.get(x, y + 1);
				// else
				// s = 0;

				if (x - 1 > 0 && y + 1 < Y)
					sw = src.get(x - 1, y + 1);
				else
					sw = 0;

				if (x - 1 > 0)
					w = src.get(x - 1, y);
				else
					w = 0;

				if (y - 1 > 0 && x - 1 > 0)
					nw = src.get(x - 1, y - 1);
				else
					nw = 0;

				// calculate Ix
				temp = (float) ((-nw - 2.0 * w - sw + ne + 2.0 * e + se) / 8.0);

				// put the result float to output image
				dest.set(x - x1, y - y1, temp);
			}
		}

	} // calcIx

}
