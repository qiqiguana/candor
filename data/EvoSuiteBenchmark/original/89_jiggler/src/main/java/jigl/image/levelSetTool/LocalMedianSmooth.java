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

import jigl.image.types.RealGrayImage;

/**
 * LocalMedianSmooth is a class for median-smooth. Median-smooth is a smooth method by substituteing
 * the value in each pixel with the median value of the 9 connected neighbor pixels (including
 * itself).
 */
public class LocalMedianSmooth {
	//RealGrayImage* tempImage;
	//float vals[9];
	//float realvals[9];
	//BOOL test[9];

	/** Default constructor */
	public LocalMedianSmooth() {

	}

	//FastConvolve* fastConvolve;
	//Kernel* blurKernel;

	/**
	 * Applies median-smooth operation in the specified area. <code>image</code> is modified.
	 * Returns a pointer. parameters: x1 - the left bound of the specified area y1 - the bottom
	 * bound of the specified area x2 - the right bound of the specified area y2 - the top bound of
	 * the specified area image - pointer to the source image
	 */
	public RealGrayImage apply(int x1, int y1, int x2, int y2, RealGrayImage image) {

		//if (tempImage==NULL){
		//	tempImage=new RealGrayImage(image.getWidth(), image.getHeight());
		//}

		//	for (int x=x1-3; x<x2+3; x++)
		//	for (int y=y1-3; y<y2+3; y++){
		//		tempImage.set(x, y, image.get(x, y));			
		//	}

		float[] vals = new float[9];
		float[] realvals = new float[9];
		boolean[] test = new boolean[9];
		int x;

		for (x = 0; x < 9; x++) {
			vals[x] = 500;//default
			test[x] = true;
		}
		float min = 0;
		//int max=0;
		//		boolean t=false;
		int count = 0;
		int index = 0;

		int w = image.X();
		int h = image.Y();
		for (x = x1; x < x2; x++)
			for (int y = y1; y < y2; y++) {
				count = 0;
				int x_start = (x - 1 < 0) ? 0 : (x - 1);
				int x_end = (x + 1 > w - 1) ? (w - 1) : (x + 1);

				for (int i = x_start; i <= x_end; i++) {
					int y_start = (y - 1 < 0) ? 0 : (y - 1);
					int y_end = (y + 1 > h - 1) ? (h - 1) : (y + 1);
					for (int j = y_start; j <= y_end; j++) {
						vals[count] = image.get(i, j);
						count++;
					}
				}
				//find the first 5 smallest from 9 values.
				//realvals[4] is the median
				for (int i = 0; i < 5; i++) {
					min = 9999;
					for (int j = 0; j < 9; j++) {
						if (vals[j] < min && test[j]) {
							min = vals[j];
							index = j;
						}
					}
					realvals[i] = min;
					test[index] = false;
				}
				float median = 0;
				switch (count) {
				case 4:
					median = (realvals[1] + realvals[2]) / 2;
					break;
				case 6:
					median = (realvals[2] + realvals[3]) / 2;
					break;
				default:
					median = realvals[4];
				}

				image.set(x, y, median);
				for (int u = 0; u < 9; u++)
					test[u] = true;
			}
		return image;
	}
}
