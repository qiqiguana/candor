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

import java.util.Random;

import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * NoiseDetectImage is a class to calculate the delta value of an image. Delta value is the total
 * difference between a pixel and its eight-connected neighbors.
 * <p>
 * Adds noise to an image increases mean delta value.
 */
public class NoiseDetectImage {
	/** Original image */
	private RealColorImage cimage;
	/** Image storeing the delta value for each pixel. */
	private RealColorImage diff_clean;

	/** Width of the image */
	private int Y;
	/** Height of the image */
	private int X;

	/** Maximum delta value in the image */
	private double minimum;
	/** Minimum delta value in the image */
	private double maximum;

	/** Mean delta value for a pixel */
	private double global;

	/** ??? */
	public double std;

	/** Standard deviation of the delta value for the entire image */
	public double std2;

	/** Default constructor */
	public NoiseDetectImage() {

	}

	/** Initializes current NoiseDetectImage object from a RealColorImage object */
	public void init(RealColorImage image) {
		cimage = image;
		X = cimage.X();
		Y = cimage.Y();

		diff_clean = calculateDiff();

		calculateStd();

		calculateStdMean();

		//RealColorImage noisyImage=addNoise(cimage);

		//RealColorImage diff_noisy=calculateDiff(noisyImage);

		//compare2(diff_noisy, diff_clean);
	}

	/** ???Calculates the standard deviation of delta value over the entire image. */
	public void calculateStdMean() {
		RealGrayImage image;
		double sum = 0;
		double count = 0;

		for (int a = 0; a < 3; a++) {
			image = diff_clean.plane(a);
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					if (image.get(x, y) > global + std) {
						sum += image.get(x, y);
						count++;
					}
				}
			}
		}
		std2 = sum / count;
	}

	/** ???? */
	public void calculateStd() {
		RealGrayImage image;
		double sum = 0;
		double count = 0;

		for (int a = 0; a < 3; a++) {
			image = diff_clean.plane(a);
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					sum += Math.pow((image.get(x, y) - global), 2);
					count++;
				}
			}
		}
		std = sum / count;
		std = Math.sqrt(std);
	}

	//RealGrayImage noiseImage;

	/** Returns <code>global</code> */
	public double getGlobal() {
		return global;
	}

	/**
	 * Adds noise to image <code>cimage</code>. In each plane, the noise value is random and in the
	 * range of [-10..10].
	 */
	public RealColorImage addNoise() {
		int random = 0;
		RealGrayImage image;
		RealGrayImage dest;
		RealColorImage returnImage = new RealColorImage(X, Y);

		Random rand = new Random(System.currentTimeMillis());

		for (int a = 0; a < 3; a++) {
			image = cimage.plane(a);
			dest = returnImage.plane(a);
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					random = rand.nextInt() % 20;
					random = 10 - random;
					dest.set(x, y, image.get(x, y) + random);
				}
			}
		}
		return returnImage;
	}

	/**
	 * Calculates delta value (the sum of absolute difference of the value at each pixel from the
	 * values of its 8 connected neighbors). Returns a pointer to the result image storeing the
	 * delta value at each pixel.
	 */
	public RealColorImage calculateDiff() {
		RealGrayImage image;
		RealGrayImage dest;
		RealColorImage returnImage = new RealColorImage(X, Y);
		float sum;
		int count = 0;
		maximum = -4000.0;
		minimum = 99999.9999;
		global = 0.0;

		for (int a = 0; a < 3; a++) {
			image = cimage.plane(a);
			dest = returnImage.plane(a);
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					sum = 0;
					for (int j = -1; j <= 1; j++) {
						for (int i = -1; i <= 1; i++) {
							if ((x + i >= 0) && (y + j >= 0) && (x + i < X) && (y + j < Y))
								sum += (float) Math.abs(image.get((x + i), (y + j))
										- image.get(x, y));
						}
					}
					dest.set(x, y, (float) (sum));
					global += sum;
					if (sum < minimum)
						minimum = sum;
					if (sum > maximum)
						maximum = sum;
					count++;
				}
			}
		}
		global /= count;
		return returnImage;
	}

	//void compare(RealColorImage c1, RealColorImage c2);//change "global"(changed
	//in calcDiff())

	//void compare2(RealColorImage c1, RealColorImage c2);
}
