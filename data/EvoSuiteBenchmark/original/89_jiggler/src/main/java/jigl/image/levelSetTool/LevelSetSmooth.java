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
 * <p>
 * LevelSetSmooth is a class for level-set smoothing on one small region of a image.
 * </p>
 * <p>
 * The level-set smoothing algorithm is based on the research done by Morse and Schwaetzwald:<br/>
 * 
 * <pre>
 * I(x,y) = I0(x,y) + alpha * k(x,y) (where k is curvature function)
 * </pre>
 * 
 * </p>
 * <p>
 * In order to avoid over-smoothing, we uses 2 of three constraints (inflection constraints and
 * topological constraints) put forward by Morse and Schwaetzwald to adjust over-smoothed value.
 * </p>
 */
public class LevelSetSmooth {
	/** Original image */
	private RealColorImage image;

	/**
	 * Isophote curvature flow map of the image in the specified region. Its size is 40x40.
	 */
	private RealGrayImage kappa;

	/**
	 * The images storing the modification amount at each pixel in the specified region.
	 */
	//	private RealGrayImage move;

	/**
	 * The images storing the new value at each pixel in the specified region.Its size is 40x40.
	 */
	private RealGrayImage want;

	/**
	 * The alpha value map of the image. In each plane, the alpha value is within [0.10 .. 0.55].
	 */
	private RealColorImage alpha;

	/** The alpha reduction (delta-alpha) map of the image. */
	private RealColorImage alpha_change;

	/** Object providing differential geometry calculations. */
	private LocalDifferentialGeometry ldg;

	/** The bound value for alpha. Default is 0.05. */
	float boundV;

	/**
	 * Initilize the internal image <code>alpha</code> and <code>alpha_change</code>.
	 * 
	 * @param image - a pointer to the original image
	 * @param mean - the mean of delta (the average difference between a pixel and its
	 *            eight-connected neighbors)
	 * @param std - maximum value of delta
	 * @param std2 - the standard deviation of delta
	 */
	void setAutoParameters(RealColorImage im, double mean, double std, double std2) {
		int X = im.X();
		int Y = im.Y();
		double val = 0.0;
		double val2 = 0.0;
		//		double val3 = 0.0;

		std = (mean + std);// FIXME: figure out what the heck this is doing

		double std3 = 0.0;

		RealGrayImage gimage, galpha, galpha_change;

		// std3 is beta for a point with mean delta, std2 must be sigma sub
		// delta
		std3 = mean + (1.0 / (1.0 + (Math.exp((mean - 35.0) * 1.0)))) * std2;

		for (int i = 0; i < 3; i++) {
			gimage = im.plane(i);
			galpha = alpha.plane(i);
			galpha_change = alpha_change.plane(i);
			for (int x = 0; x < X; x++)
				for (int y = 0; y < Y; y++) {
					// the change in step size. delta-alpha = delta(x,y)*mean*c
					val = gimage.get(x, y) / 2048.0 * (mean / 100);
					if (val < 0.001)
						val = 0.001;
					galpha_change.set(x, y, (float) val);// alpha_change is ????

					val2 = gimage.get(x, y);

					// comparing val2 (delta(x,y)) with beta (std3)
					if (val2 > std3) {
						val2 = 0.10;// delta > beta
					} else // delata <= beta
					{
						double temp = val2 - mean;
						if (temp < 0)
							temp = -temp;
						val2 = 0.15 + temp / mean;
					}
					if (val2 > 0.55)// alpha can not be greater than 0.55
						val2 = 0.55;
					galpha.set(x, y, (float) val2);
				}
		}
	}

	/** Default constructor */
	public LevelSetSmooth() {

	}

	/**
	 * Constructs a LevelSetSmooth object from a RealGrayImage object and three doubles.
	 * 
	 * @param image - a pointer to the original image
	 * @param mean - the mean of delta (the average difference between a pixel and its
	 *            eight-connected neighbors)
	 * @param maximum - maximum value of delta
	 * @param std2 - the standard deviation of delta
	 */
	public LevelSetSmooth(RealColorImage img, double mean, double maximum, double std2) {
		image = img;

		int X = image.X();
		int Y = image.Y();

		alpha = new RealColorImage(X, Y);
		alpha_change = new RealColorImage(X, Y);

		want = new RealGrayImage(40, 40);
		kappa = new RealGrayImage(40, 40);

		boundV = 0.05f;

		setAutoParameters(image, mean, maximum, std2);
	}

	/*
	 * Initializes internal values.
	 * 
	 * @param a - the value for <code>alphaV</code>
	 * 
	 * @param c - the value for <code>changeV</code>
	 * 
	 * @param b - dummy value
	 * 
	 * @param in - the value for <code>increase</code>
	 * 
	 * @param s - the value for <code>single</code>
	 * 
	 * @param au - the value for <code>automatic</code>
	 */
	// void setParameters(float a, float c, float b, BOOL in,
	// BOOL s, BOOL au);

	/**
	 * Applies local level-set smoothing to current image.
	 * 
	 * <p>
	 * Internal value <code>*image</code> is modified.
	 * 
	 * @param x1 - the left bound of the specified area
	 * @param y1 - the left bound of the specified area
	 * @param x2 - the right bound of the specified area
	 * @param y2 - the top bound of the specified area
	 * @param constrainTopology - flag for applying topological constraints
	 * @param constrainSigns - flags for applying inflection constrints
	 */
	public RealColorImage apply(int x1, int y1, int x2, int y2, boolean constrainTopology,
			boolean constrainSigns) {
		//		float newAlpha = 0.0f;
		RealGrayImage gimage, galpha, galpha_change;

		for (int i = 0; i < 3; i++) {
			gimage = image.plane(i);
			galpha = alpha.plane(i);
			galpha_change = alpha.plane(i);

			// create Kappa - the isophote curvature map
			ldg.calcIsophoteFlow(x1, y1, x2, y2, gimage, kappa);

			// same sign constraint - don't move same sign neighborhoods
			// (sets kappa to 0 if the 8-conn neighborhood all has same sign)
			if (constrainSigns) {
				constrainSigns(kappa, x2 - x1, y2 - y1);
			}

			// create wants (new + (alpha * move)) - this is how the new image
			// should look
			// float percent = 0;
			// float kapp = 0;
			float alphaV = 0;
			float wantV = 0;
			float changeV = 0;

			// automatic = true;

			// copyWant(x1-1, y1-1, x2+1, y2+1);
			for (int x = x1; x < x2; x++) {
				for (int y = y1; y < y2; y++) {
					alphaV = galpha.get(x, y);
					changeV = -galpha_change.get(x, y);
					wantV = gimage.get(x, y) + ((float) alphaV * (float) kappa.get(x - x1, y - y1));
					want.set(x - x1, y - y1, wantV);

					// boundV=0.05
					if (alphaV > boundV && alphaV + changeV > boundV)
						galpha.set(x, y, alphaV + changeV);
					else
						galpha.set(x, y, boundV);
				}
			}

			// topology constraint - don't let isophotes cross each other
			if (constrainTopology) {
				constrainTopology(want, gimage, x1, y1, x2, y2);
			}

			for (int x = x1; x < x2; x++) {
				for (int y = y1; y < y2; y++) {
					if (want.get(x - x1, y - y1) > 255)
						want.set(x - x1, y - y1, 255f);
					else if (want.get(x - x1, y - y1) < 0)
						want.set(x - x1, y - y1, 0f);
					gimage.set(x, y, want.get(x - x1, y - y1));
				}
			}
		}
		return image;
	}

	/**
	 * Applies same signs constrants (inflection constrains). If all the curvatures of the eight
	 * connected neighbors have the same sign, sets this pixel's curvature to 0.
	 * 
	 * @param img - pointer to curvature image
	 * @param a - the width of the modified area
	 * @param b - the height of the modified area
	 */
	public void constrainSigns(RealGrayImage img, int a, int b) {
		// local variables
		float[] moves = new float[9];
		int pos;
		int neg;

		// for each pixel in move map
		for (int x = 0; x < a; x++) {
			for (int y = 0; y < b; y++) {

				// get neighborhood values
				moves[0] = img.get(x - 1, y - 1);
				moves[1] = img.get(x, y - 1);
				moves[2] = img.get(x + 1, y - 1);
				moves[3] = img.get(x - 1, y);
				moves[4] = img.get(x, y);
				moves[5] = img.get(x + 1, y);
				moves[6] = img.get(x - 1, y + 1);
				moves[7] = img.get(x, y + 1);
				moves[8] = img.get(x + 1, y + 1);

				// see if neighborhood has all same sign
				if (moves[4] != 0) {
					pos = 0;
					neg = 0;
					for (int i = 0; i < 9; i++) {
						if (i != 4) {
							if (moves[i] >= 0) {
								pos++;
							}
							if (moves[i] <= 0) {
								neg++;
							}
						}
					}
					if ((neg == 8) || (pos == 8)) {
						img.set(x, y, 0f);
					}
				}
			}

		}

	}

	/**
	 * Applies topological constraints. This check is to make sure that all greater-valued neighbors
	 * (curvature) stay larger and all leser-valued neighbors stay smaller.
	 * 
	 * @param gimage - pointer to <code>*want</code>
	 * @param wnt - pointer to plane of current image. It is modified in this function. It is also
	 *            the returned pointer.
	 * @param x1 - the left bound of the specified area
	 * @param y1 - the left bound of the specified area
	 * @param x2 - the right bound of the specified area
	 * @param y2 - the top bound of the specified area
	 */
	public RealGrayImage constrainTopology(RealGrayImage gimage, RealGrayImage wnt, int x1, int x2,
			int y1, int y2) {

		// local variables
		float new_value;
		float old_value;
		float wnt_value;
		float move;

		float[] n = new float[9];
		float[] w = new float[9];
		float upper_bound;
		float lower_bound;

		// visit each pixel
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y1; y++) {
				old_value = gimage.get(x, y);
				wnt_value = wnt.get(x, y);
				move = old_value - wnt_value;
				new_value = old_value;

				// get neighborhood values (gimage)

				n[0] = gimage.get(x - 1, y - 1);
				n[1] = gimage.get(x, y - 1);
				n[2] = gimage.get(x + 1, y - 1);
				n[3] = gimage.get(x - 1, y);
				n[4] = gimage.get(x, y);
				n[5] = gimage.get(x + 1, y);
				n[6] = gimage.get(x - 1, y + 1);
				n[7] = gimage.get(x, y + 1);
				n[8] = gimage.get(x + 1, y + 1);

				// get neighborhood values (want)
				w[0] = wnt.get(x - 1 - x1, y - 1 - y1);
				w[1] = wnt.get(x - x1, y - 1 - y1);
				w[2] = wnt.get(x + 1 - x1, y - 1 - y1);
				w[3] = wnt.get(x - 1 - x1, y - y1);
				w[4] = wnt.get(x - x1, y - y1);
				w[5] = wnt.get(x + 1 - x1, y - y1);
				w[6] = wnt.get(x - 1 - x1, y + 1 - y1);
				w[7] = wnt.get(x - x1, y + 1 - y1);
				w[8] = wnt.get(x + 1 - x1, y + 1 - y1);

				// calculate new value
				if (move != 0) {

					// find min of wants or olds for olds greater than old_value
					upper_bound = 256;// for the smallest
					for (int i = 0; i < 9; i++) {
						if (n[i] > old_value) {
							upper_bound = (upper_bound < n[i]) ? upper_bound : n[i];
							upper_bound = (upper_bound < w[i]) ? upper_bound : w[i];
						}
					}

					// find max of wants or olds for olds less than old_value
					lower_bound = -1;// for the maximum
					for (int i = 0; i < 9; i++) {
						if (n[i] < old_value) {
							lower_bound = (lower_bound > n[i]) ? lower_bound : n[i];
							lower_bound = (lower_bound > w[i]) ? lower_bound : w[i];
						}
					}

					// clamp between upper_bound and lower_bound
					// if all the values are equal?????????
					upper_bound--;
					lower_bound++;
					if (move > 0) {
						new_value = (wnt_value > upper_bound) ? upper_bound : wnt_value;
					} else if (move < 0) {
						new_value = (wnt_value < lower_bound) ? lower_bound : wnt_value;
					}

				} else {
					new_value = old_value;
				}

				// update want gimage
				if (new_value != old_value) {

					// clamp dynamic range
					new_value = (new_value > 255) ? 255 : new_value;
					new_value = (new_value < 0) ? 0 : new_value;
					wnt.set(x, y, new_value);
				}

			}
		}
		return wnt;
	}

	/**
	 * Copies the region in the image <code>*gimage</code> to internal image <code>want</code>.
	 * 
	 * @param gimage - pointer to current image
	 * @param x1 - the left bound of the specified area
	 * @param y1 - the left bound of the specified area
	 * @param x2 - the right bound of the specified area
	 * @param y2 - the top bound of the specified area
	 */
	public void copyWant(RealGrayImage gimage, int x1, int y1, int x2, int y2) {
		for (int x = x1; x < x2; x++)
			for (int y = y1; y < y2; y++)
				want.set(x, y, gimage.get(x, y));
	}

}
