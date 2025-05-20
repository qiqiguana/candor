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
package jigl.image.levelSetTool;

import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;
import jigl.image.utils.LocalDifferentialGeometry;

/**
 * LevelSetSharpen is a class for level-set sharpening. Level-set sharpening uses level-set
 * constrained unsharp mask to get a excellent sharpening result.
 * <p>
 * <code>
	I = I0 + alpha * ((I0 - I_blur)/k^2)</code>
 * <p>
 * This sharpening methods brings many desirable effects: (1) it keeps high-detailed areas from
 * sharpened to fast. (2) it avoids the rapid exponentially increase of noise. (3) it gives high
 * returns in the beginning and lower returns in the end.
 */
public class LevelSetSharpen {
	/** Isophote curvature map. */
	private RealGrayImage kappa;

	// RealGrayImage *betaVals;//for calcBeta()
	/** Original image */
	private RealColorImage image;

	/** For local differential geometry calculation */
	private LocalDifferentialGeometry ldg;

	/** The blurred image. */
	private RealGrayImage blur;// , *blur2;

	/** The standard alpha value. Default is 1. */
	private double strength;

	// double numDevs;
	/** ??? */
	private boolean secondOrder;

	// float lambda;//for calcBeta()

	// float sigmoidThreshold;//for calcBeta()

	/** Default construct */
	public LevelSetSharpen() {
		ldg = new LocalDifferentialGeometry();
	}

	/**
	 * Constructs a LevelSetSharpen object from a RealColorImage object.
	 */
	public LevelSetSharpen(RealColorImage im) {
		strength = 1;
		image = im;
		ldg = new LocalDifferentialGeometry();
		kappa = new RealGrayImage(900, 900);
		blur = new RealGrayImage(900, 900);
	}

	/**
	 * Applies unsharp masking in the specified region. Assigns <code>levelset</code> to true if you
	 * want to use level-set sharpening.
	 * 
	 * <p>
	 * Internal value <code>*image</code> is modified. parameters: x1 - left bound of the specified
	 * region y1 - bottom bound of the specified region x2 - right bound of the specified region y2
	 * - top bound of the specified region levelset - flag for unsharp masking version. If true, the
	 * level-set version is used. Otherwise the standard version is used.
	 */
	public void apply(int x1, int y1, int x2, int y2, boolean levelset) {

		RealGrayImage imagePlane;
		RealGrayImage kappaBlur = new RealGrayImage(900, 900);

		double kap, newPix, alpha, avgKapSquare/* , stdDevAbsKappa, avgAbsKappa */, kapDiff;
		avgKapSquare = 0;
		String pixStr;

		// For each color plane in the image:
		for (int i = 0; i < 3; i++) {
			imagePlane = image.plane(i);

			// Calculate the isophote curvature across the image and pull out
			// some parameters
			ldg.calcIsophoteFlow(x1, y1, x2, y2, imagePlane, kappa);
			avgKapSquare = ldg.avgKappaSquare;
			// stdDevAbsKappa = ldg.stdDevAbsKappa;
			// avgAbsKappa = ldg.avgAbsKappa;

			pixStr = Float.toString((float) avgKapSquare);// , 5,
			// pixStr);//float
			// to string
			// ??????????????????, how to substitute message box
			// MessageBox(NULL, pixStr, "avgKapSquare", 0);
			System.out.println("Average Kappa Square: " + pixStr);

			// Calculate two iterations of blurred image
			calcBlur(imagePlane, blur, x1, y1, x2, y2);
			// calcBlur(blur, blur2, x1, y1, x2, y2);
			ldg.calcIsophoteFlow(x1, y1, x2, y2, blur, kappaBlur);

			// Calculate beta thresholds (1=constrain sharpening, 0=don't
			// constrain)
			// CalculateBetaValues(imagePlane, betaVals, x1, y1, x2, y2);

			// For each pixel in the specified region:
			for (int x = x1; x <= x2; x++)
				for (int y = y1; y <= y2; y++) {
					// Get the absolute isophote curvature for this pixel
					// For some reason the calcIsophoteFlow function moves the
					// k-values to the upper left corner,
					// so we have to use the offset from the start of the region
					// (x-x1)
					kap = Math.abs((double) kappa.get(x - x1, y - y1));
					// compute the difference between blurred and non-blurred
					// curvature
					// (noise should have large difference, detail small
					// difference)
					kapDiff = Math.abs(kap - kappaBlur.get(x - x1, y - y1));

					// If we're doing the level-set version:
					if (levelset) {
						// If this pixel is probably noise, then apply the
						// levelset-constrained sharpening
						/*
						 * if(betaVals.get(x,y) == 1)// && fabs(kap-avgAbsKappa)
						 * > numDevs*stdDevAbsKappa) {
						 */
						alpha = strength * avgKapSquare / (kap * kap); // should this be delta based?

						if (secondOrder)
							/*
							 * this actually borgifies the image...in a gridlike
							 * way...what about this algorithm is gridlike? The
							 * blur kernel? newPix = imagePlane.get(x,y) +
							 * alpha*(imagePlane.get(x,y) - blur.get(x, y) -
							 * blur2.get(x,y)/2); newPix = imagePlane.get(x,y) +
							 * alpha*(1.5*imagePlane.get(x,y) -
							 * 0.5*blur2.get(x,y));
							 */
							newPix = 10 * kapDiff;// blur2.get(x,y);
						else {
							// _gcvt(alpha, 5, pixStr);
							// MessageBox(NULL, pixStr, "Alpha", 0);

							// truncate alpha if it goes over the regular
							// strength
							// (which would defeat the purpose of adaptively
							// lowering the strength)
							if (alpha > 2 * strength)
								alpha = 2 * strength;

							newPix = imagePlane.get(x, y) + alpha
									* (imagePlane.get(x, y) - blur.get(x, y));
							// newPix = blur.get(x,y);
						}

					}
					// Otherwise do standard unsharp masking
					else {
						newPix = imagePlane.get(x, y) + strength
								* (imagePlane.get(x, y) - blur.get(x, y));
					}

					// If the calculated pixel escaped the valid range, bring it
					// back
					if (newPix < 0)
						newPix = 0;
					else if (newPix > 255)
						newPix = 255.0;

					imagePlane.set(x, y, (float) newPix);
				}
		}
	}

	/**
	 * Creates a blurred image and stores it in <code>*dest</code>.
	 * 
	 * @param src - pointer to the source image
	 * @param dest - pointer to destination image, storing the result.
	 * @param x1 - left bound of the specified region
	 * @param y1 - bottom bound of the specified region
	 * @param x2 - right bound of the specified region
	 * @param y2 - top bound of the specified region
	 */
	public void calcBlur(RealGrayImage src, RealGrayImage dest, int x1, int y1, int x2, int y2) {
		int kernelDim = 9;
		float kernelWeight = 256;
		float kernel[][] = { { 0, 0, 1, 1, 1, 1, 1, 0, 0 }, { 0, 1, 2, 3, 3, 3, 2, 1, 0 },
				{ 1, 2, 3, 6, 7, 6, 3, 2, 1 }, { 1, 3, 6, 9, 11, 9, 6, 3, 1 },
				{ 1, 3, 7, 11, 12, 11, 7, 3, 1 }, { 1, 3, 6, 9, 11, 9, 6, 3, 1 },
				{ 1, 2, 3, 6, 7, 6, 3, 2, 1 }, { 0, 1, 2, 3, 3, 3, 2, 1, 0 },
				{ 0, 0, 1, 1, 1, 1, 1, 0, 0 } };
		float temp;
		int neighborX, neighborY;

		// calculate derivatives
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				temp = 0;
				for (int i = 0; i < kernelDim; i++) {
					for (int j = 0; j < kernelDim; j++) {
						neighborX = x - 4 + i; // start 4 to left and move right
						neighborY = y - 4 + j;
						if (neighborX >= x1 && neighborY >= y1 && neighborX < x2 && neighborY < y2)
							temp += kernel[i][j] * src.get(neighborX, neighborY);
					}
				}
				temp = temp / kernelWeight; // divide out the total kernel weight

				// put the result float to output image
				dest.set(x, y, temp);
				// dest.set(x-x1, y-y1, temp);
			}
		}
	}

	// generates a corresponding image containing beta values
	// betas are used to distinguish detail - delta < Beta =>non-detail; delta >
	// Beta => detail
	// void CalculateBetaValues(RealGrayImage *kappa, RealGrayImage *kappaBlur, RealGrayImage *betas, int x1, int y1, int x2, int y2);
}
