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
package jigl.image.types;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import jigl.image.ROI;

/** MIPMap that uses TiledImages. All sub-images are created as temp files. */
public class TiledMIPMap {
	/** Stores the TileImage at each reduction. */
	private TiledImage[] m_imageArray;

	/** Width of the image. */
	private int m_imageSizeX;
	/** Height of the image. */
	private int m_imageSizeY;

	/** The minimum image size that can be handled. */
	private int m_min = 16;//min image size

	/**
	 * Constructor.
	 * 
	 * @param rawppmfilename File name of the raw PPM format image to use.
	 * @throws IOException If the file is not a raw PPM image.
	 * @throws FileNotFoundException If the file does not exist or can't be opened.
	 */
	public TiledMIPMap(String rawppmfilename) throws IOException, FileNotFoundException {
		Date start = new Date();

		int tilesizeX = 200;
		int tilesizeY = 200;

		TiledImage temp = new TiledImage(rawppmfilename, tilesizeX, tilesizeY);

		m_imageSizeX = temp.X();
		m_imageSizeY = temp.Y();

		System.out.println("image is " + m_imageSizeX + " X " + m_imageSizeY);

		int numImages = 0;

		//see if image is big enough to compute
		if (m_min > m_imageSizeX || m_min > m_imageSizeY) {
			//not big enough to reduce
			numImages = 1;
		} else {
			if (m_imageSizeX < m_imageSizeY)
				numImages = (int) (Math.ceil((Math.log(m_min) - Math.log((double) m_imageSizeX))
						/ Math.log(0.5))) + 1;
			else
				numImages = (int) (Math.ceil((Math.log(m_min) - Math.log((double) m_imageSizeY))
						/ Math.log(0.5))) + 1;
		}

		System.out.println("numImages: " + numImages);

		m_imageArray = new TiledImage[numImages];

		m_imageArray[0] = temp;

		int[] val = new int[3];
		Integer[] tempval;

		int currentX = temp.X();
		int currentY = temp.Y();

		//		int nextX,nextY;

		int tempfilenum = 0;

		for (int i = 1; i < numImages; i++) {
			//reduce the image to 1/2 the previous image
			if (currentX < 200)
				tilesizeX = currentX;
			if (currentY < 200)
				tilesizeY = currentY;
			m_imageArray[i] = new TiledImage("C:\\Images\\temp" + String.valueOf(tempfilenum++)
					+ ".ppm", (int) (currentX / 2.0), (int) (currentY / 2.0), tilesizeX, tilesizeY);

			for (int y = 0, nextY = 0; y < currentY - 1; y += 2, nextY++) {
				for (int x = 0, nextX = 0; x < currentX - 1; x += 2, nextX++) {
					val[0] = val[1] = val[2] = 0;

					for (int x2 = 0; x2 < 2; x2++) {
						for (int y2 = 0; y2 < 2; y2++) {
							tempval = m_imageArray[i - 1].get(x + x2, y + y2);

							val[0] += tempval[0];
							val[1] += tempval[1];
							val[2] += tempval[2];
						}
					}
					val[0] /= 4;
					val[1] /= 4;
					val[2] /= 4;

					m_imageArray[i].set(nextX, nextY, val);
					//					System.out.println("set: " + val[0] + "," +val[1]+","+val[2]);
				}
				//				System.out.println("finished a row");
			}

			currentX = (int) (currentX / 2.0);
			currentY = (int) (currentY / 2.0);
		}

		Date end = new Date();

		long calcTime = end.getTime() - start.getTime();

		System.out.println("" + ((double) calcTime / 1000) + " seconds to construct TiledMIPMap");

	}

	/**
	 * Closes the files used for the TiledImages. This should always be called when the TiledMipmap
	 * is no longer needed.
	 */
	public void dispose() {
		System.out.println("TiledMipmap dispose");
		for (int i = 0; i < m_imageArray.length; i++) {
			try {
				m_imageArray[i].close();
			} catch (Exception e) {
				System.out.println("couldn't close");
			}
		}
	}

	/**
	 * Width of the original image.
	 * 
	 * @return Width of image.
	 */
	public int X() {
		return m_imageSizeX;
	}

	/**
	 * Height of the original image.
	 * 
	 * @return Height of image.
	 */
	public int Y() {
		return m_imageSizeY;
	}

	/** Utility methods */
	private Integer[] biLinearInterp(float x, float y, int imageIndex) throws IOException {
		TiledImage image = m_imageArray[imageIndex];
		int intx, inty;
		double fracx, fracy;

		intx = (int) x;
		inty = (int) y;
		fracx = x - (double) intx;
		fracy = y - (double) inty;

		Integer[] result = new Integer[3];
		Integer[] tempxy;
		Integer[] tempx1y;
		Integer[] tempxy1;
		Integer[] tempx1y1;

		if (intx >= image.X() - 1 || inty >= image.Y() - 1)//edge case
		{
			if (fracx == 0 && fracy == 0)
				return image.get(intx, inty);//corner

			if (fracx == 0) {
				if (inty > image.Y() - 1)
					return image.get(intx, inty);//corner

				tempxy = image.get(intx, inty);
				tempxy1 = image.get(intx, inty + 1);

				result[0] = (int) ((double) tempxy[0] * (1 - fracy) + (double) tempxy1[0] * fracy);
				result[1] = (int) ((double) tempxy[1] * (1 - fracy) + (double) tempxy1[1] * fracy);
				result[2] = (int) ((double) tempxy[2] * (1 - fracy) + (double) tempxy1[2] * fracy);

				return result;
			}
			if (fracy == 0) {
				if (intx > image.X() - 1)
					return image.get(intx, inty);//corner

				tempxy = image.get(intx, inty);
				tempx1y = image.get(intx + 1, inty);

				result[0] = (int) ((double) tempxy[0] * (1 - fracx) + (double) tempx1y[0] * fracx);
				result[1] = (int) ((double) tempxy[1] * (1 - fracx) + (double) tempx1y[1] * fracx);
				result[2] = (int) ((double) tempxy[2] * (1 - fracx) + (double) tempx1y[2] * fracx);

				return result;
			}
			throw new ArrayIndexOutOfBoundsException();
		}

		tempxy = image.get(intx, inty);
		tempx1y = image.get(intx + 1, inty);
		tempxy1 = image.get(intx, inty + 1);
		tempx1y1 = image.get(intx + 1, inty + 1);

		result[0] = (int) ((double) tempxy[0] * (1 - fracx) * (1 - fracy) + (double) tempx1y[0]
				* fracx * (1 - fracy) + (double) tempxy1[0] * (1 - fracx) * fracy + (double) tempx1y1[0]
				* fracx * fracy);
		result[1] = (int) ((double) tempxy[1] * (1 - fracx) * (1 - fracy) + (double) tempx1y[1]
				* fracx * (1 - fracy) + (double) tempxy1[1] * (1 - fracx) * fracy + (double) tempx1y1[1]
				* fracx * fracy);
		result[2] = (int) ((double) tempxy[2] * (1 - fracx) * (1 - fracy) + (double) tempx1y[2]
				* fracx * (1 - fracy) + (double) tempxy1[2] * (1 - fracx) * fracy + (double) tempx1y1[2]
				* fracx * fracy);

		return result;

	}

	/**
	 * Returns the Region of Interest of the original image at <code>reduction</code>.
	 * 
	 * @param reduction Reduction amount of the desired image.
	 * @param roi Region of Interest of original image.
	 * @return A ColorImage
	 */
	public ColorImage getImage(float reduction, ROI roi) throws IOException {
		Date start = new Date();

		double dVal = Math.log(reduction) / Math.log(2);
		System.out.println("val: " + dVal);

		//desired reduction is between upper and lower images
		int nUpper = (int) Math.ceil(dVal);//index of upper image in m_imageArray
		int nLower = (int) Math.floor(dVal);//index of lower image in m_imageArray
		System.out.println("upper: " + nUpper);
		System.out.println("lower: " + nLower);

		double dWeightLower = (double) nUpper - dVal;
		double dWeightUpper = dVal - (double) nLower;
		System.out.println("weightUpper: " + dWeightUpper);
		System.out.println("weightLower: " + dWeightLower);

		if (nUpper == nLower)
			dWeightLower = 1;

		//number of times upper and lower images were reduced by 2
		int nNumReductionsUpper = nUpper;
		int nNumReductionsLower = nLower;
		System.out.println("numReductionsUpper: " + nNumReductionsUpper);
		System.out.println("numReductionsLower: " + nNumReductionsLower);

		double dTwoToNumReductionsLower = Math.pow(2.0, nNumReductionsLower);
		double dTwoToNumReductionsUpper = Math.pow(2.0, nNumReductionsUpper);

		//offset of 0,0 pixel from the original 0,0 (in this images coordinates)
		double dOffsetLower = (dTwoToNumReductionsLower - 1.0)
				/ Math.pow(2.0, nNumReductionsLower + 1);
		double dOffsetUpper = (dTwoToNumReductionsUpper - 1.0)
				/ Math.pow(2.0, nNumReductionsUpper + 1);
		System.out.println("offsetLower: " + dOffsetLower);
		System.out.println("offsetUpper: " + dOffsetUpper);

		float lowX, lowY, upX, upY;

		Integer[] lowerPixel = new Integer[3];
		Integer[] upperPixel = new Integer[3];

		Integer[] pixel = new Integer[3];

		int retSizeX = (int) (roi.X() / reduction);
		int retSizeY = (int) (roi.Y() / reduction);

		ColorImage retImage = new ColorImage(retSizeX, retSizeY);
		System.out.println("retSizeX: " + retSizeX);
		System.out.println("retSizeY: " + retSizeY);

		int newX, newY;
		float x, y;

		//		System.out.println("starting computation.");
		//		System.out.println("lower image size: " + m_imageArray[nLower].X() + " X " + m_imageArray[nLower].Y());
		//		System.out.println("upper image size: " + m_imageArray[nUpper].X() + " X " + m_imageArray[nUpper].Y());
		for (y = roi.uy(), newY = 0; y <= roi.ly(); y += reduction, newY++) {
			if (nNumReductionsLower == 0)
				lowY = y;
			else
				lowY = (float) (y / dTwoToNumReductionsLower - dOffsetLower);
			if (lowY < 0)
				lowY = 0;
			if (lowY > m_imageArray[nLower].Y() - 1)
				lowY = m_imageArray[nLower].Y() - 1;

			if (nNumReductionsUpper == 0)
				upY = y;
			else
				upY = (float) (y / dTwoToNumReductionsUpper - dOffsetLower);
			if (upY < 0)
				upY = 0;
			if (upY > m_imageArray[nUpper].Y() - 1)
				upY = m_imageArray[nUpper].Y() - 1;

			for (x = roi.ux(), newX = 0; x <= roi.lx(); x += reduction, newX++) {

				if (nNumReductionsLower == 0)
					lowX = x;
				else
					lowX = (float) (x / dTwoToNumReductionsLower - dOffsetLower);
				if (lowX < 0)
					lowX = 0;
				if (lowX > m_imageArray[nLower].X() - 1)
					lowX = m_imageArray[nLower].X() - 1;

				if (nNumReductionsUpper == 0)
					upX = x;
				else
					upX = (float) (x / dTwoToNumReductionsUpper - dOffsetLower);
				if (upX < 0)
					upX = 0;
				if (upX > m_imageArray[nUpper].X() - 1)
					upX = m_imageArray[nUpper].X() - 1;

				lowerPixel = biLinearInterp(lowX, lowY, nLower);

				upperPixel = biLinearInterp(upX, upY, nUpper);

				pixel[0] = (int) (dWeightLower * (double) lowerPixel[0] + dWeightUpper
						* (double) upperPixel[0]);
				pixel[1] = (int) (dWeightLower * (double) lowerPixel[1] + dWeightUpper
						* (double) upperPixel[1]);
				pixel[2] = (int) (dWeightLower * (double) lowerPixel[2] + dWeightUpper
						* (double) upperPixel[2]);

				if (newX < retSizeX && newY < retSizeY)
					retImage.set(newX, newY, pixel);
				//				else System.out.println("not set this time");

				//				System.out.println("value is (" + pixel[0] + "," + pixel[1]+","+pixel[2]+")");
			}
		}
		Date end = new Date();

		long calcTime = end.getTime() - start.getTime();

		System.out.println("" + ((double) calcTime / 1000) + " seconds to compute Image");

		return retImage;
	}

	/**
	 * Returns the image at reduction.
	 * 
	 * @param reduction Reduction amount of image (2.0 would return the image reduced by 1/2). must
	 *            be >= 1.
	 */
	public ColorImage getImage(float reduction) throws IOException {
		return getImage(reduction, new ROI(0, 0, (int) m_imageSizeX - 1, (int) m_imageSizeY - 1));
	}
}
