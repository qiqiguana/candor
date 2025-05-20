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

import java.util.Date;

import jigl.image.InterpolationMethod;
import jigl.image.ROI;

/**
 * Allows for quick access to an image at an arbitrary reduction. Stores sub-images of the original
 * image, reduced by 1/2 each time. Reduction is done by averaging each group of 4 pixel values.
 * <p>
 * Combining with <i>jigl.image.utils.ImageConverter</i>, you can make an arbitrary reduction of
 * other types of images.
 */
public class MIPMap {
	/** Stores sub-images of the original image which is reduced by 1/2 each time. */
	private InterpolatedRealColorImage[] m_imageArray;

	/** Width of the image. */
	private int m_imageSizeX;

	/** Height of the image. */
	private int m_imageSizeY;

	/** The minimum image size that can be handled. */
	private int m_min = 16;

	/*Constructs a MIPMap object from a RealColorImage.
	@param image Original image, must be RealColorImage type.*/
	public MIPMap(RealColorImage image) {
		Date start = new Date();

		m_imageSizeX = image.X();
		m_imageSizeY = image.Y();

		System.out.println("the image is size " + m_imageSizeX + "X" + m_imageSizeY);

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

		//how many times it can be reduced half.
		System.out.println("numImages: " + numImages);

		m_imageArray = new InterpolatedRealColorImage[numImages];

		m_imageArray[0] = new InterpolatedRealColorImage(image);
		m_imageArray[0].setInterpolationMethod(InterpolationMethod.LINEAR);

		Float[] val = new Float[3];
		Float[] tempval;

		int currentX = image.X();
		int currentY = image.Y();

		//		int nextX,nextY;

		RealColorImage tempImage;

		for (int i = 1; i < numImages; i++) {
			//reduce the image to 1/2 the previous image
			tempImage = new RealColorImage((int) (currentX / 2.0), (int) (currentY / 2.0));

			for (int x = 0, nextX = 0; x < currentX - 1; x += 2, nextX++) {
				for (int y = 0, nextY = 0; y < currentY - 1; y += 2, nextY++) {
					val[0] = val[1] = val[2] = 0f;

					for (int x2 = 0; x2 < 2; x2++) {
						for (int y2 = 0; y2 < 2; y2++) {
							tempval = m_imageArray[i - 1].get(x + x2, y + y2);

							val[0] += tempval[0];//red
							val[1] += tempval[1];//green
							val[2] += tempval[2];//blue
						}
					}
					val[0] /= 4;
					val[1] /= 4;
					val[2] /= 4;

					tempImage.set(nextX, nextY, val);
				}
			}

			m_imageArray[i] = new InterpolatedRealColorImage(tempImage);
			m_imageArray[i].setInterpolationMethod(InterpolationMethod.LINEAR);

			currentX = (int) (currentX / 2.0);
			currentY = (int) (currentY / 2.0);
		}

		Date end = new Date();

		long calcTime = end.getTime() - start.getTime();

		System.out.println("" + ((double) calcTime / 1000) + " seconds to construct MIPMap");

		//debugging stuff (output to screen)
		/*
				System.out.println("outputting");

				JFrame frame;
		//		RealColorImage tempImage = new RealColorImage();

				for (int k = 0; k < numImages; k++)
				{
					frame = new JFrame();
					JImageCanvas canvas = new JImageCanvas();
					try{
						canvas.setImage((RealColorImage)m_imageArray[k]);
					}catch(Exception e){System.out.println("error");}
					frame.getContentPane().add(canvas);
					frame.setSize(m_imageArray[k].X() + 11, m_imageArray[k].Y() + 35);
					frame.setVisible(true);
				}
				*/
	}

	/** Returns the width of the original image. */
	public int X() {
		return m_imageSizeX;
	}

	/** Returns the height of the original image. */
	public int Y() {
		return m_imageSizeY;
	}

	/**
	 * Return the RGB value at (x,y) of the reduced image by <i>reduction</i>
	 * 
	 * @param x x value.
	 * @param y y value.
	 * @param reduction Reduction amount of image (2.0 would return the point at the image reduced
	 *            by 1/2). must be >= 1.
	 */
	public Float[] get(float x, float y, float reduction) {
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

		Float[] lowerPixel = new Float[3];
		Float[] upperPixel = new Float[3];

		Float[] pixel = new Float[3];

		if (nNumReductionsLower == 0)
			lowX = x;
		else
			lowX = (float) (x / dTwoToNumReductionsLower - dOffsetLower);
		if (lowX < 0)
			lowX = 0;
		if (lowX > m_imageArray[nLower].X() - 1)
			lowX = m_imageArray[nLower].X() - 1;

		if (nNumReductionsLower == 0)
			lowY = y;
		else
			lowY = (float) (y / dTwoToNumReductionsLower - dOffsetLower);
		if (lowY < 0)
			lowY = 0;
		if (lowY > m_imageArray[nLower].Y() - 1)
			lowY = m_imageArray[nLower].Y() - 1;

		lowerPixel = m_imageArray[nLower].interp(lowX, lowY);

		if (nNumReductionsUpper == 0)
			upX = x;
		else
			upX = (float) (x / dTwoToNumReductionsUpper - dOffsetUpper);
		if (upX < 0)
			upX = 0;
		if (upX > m_imageArray[nUpper].X() - 1)
			upX = m_imageArray[nUpper].X() - 1;

		if (nNumReductionsUpper == 0)
			upY = y;
		else
			upY = (float) (y / dTwoToNumReductionsUpper - dOffsetUpper);
		if (upY < 0)
			upY = 0;
		if (upY > m_imageArray[nUpper].Y() - 1)
			upY = m_imageArray[nUpper].Y() - 1;

		upperPixel = m_imageArray[nUpper].interp(upX, upY);

		pixel[0] = (float) (dWeightLower * lowerPixel[0] + dWeightUpper * upperPixel[0]);
		pixel[1] = (float) (dWeightLower * lowerPixel[1] + dWeightUpper * upperPixel[1]);
		pixel[2] = (float) (dWeightLower * lowerPixel[2] + dWeightUpper * upperPixel[2]);

		return pixel;
	}

	/**
	 * Returns an image at <i>reduction</i>. Faster than calling <i>get</i> for each pixel.
	 * 
	 * @param reduction Desired reduction amount of image.
	 * @param roi Region of Interest of original image.
	 */
	public RealColorImage getImage(float reduction, ROI roi) {
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

		Float[] lowerPixel = new Float[3];
		Float[] upperPixel = new Float[3];

		Float[] pixel = new Float[3];

		int retSizeX = (int) (roi.X() / reduction);
		int retSizeY = (int) (roi.Y() / reduction);

		RealColorImage retImage = new RealColorImage(retSizeX, retSizeY);
		System.out.println("retSizeX: " + retSizeX);
		System.out.println("retSizeY: " + retSizeY);

		int newX, newY;
		float x, y;

		//		System.out.println("starting computation.");
		//		System.out.println("lower image size: " + m_imageArray[nLower].X() + " X " + m_imageArray[nLower].Y());
		//		System.out.println("upper image size: " + m_imageArray[nUpper].X() + " X " + m_imageArray[nUpper].Y());
		for (y = 0, newY = 0; y < roi.Y(); y += reduction, newY++) {
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
				upY = (float) (y / dTwoToNumReductionsUpper - dOffsetUpper);
			if (upY < 0)
				upY = 0;
			if (upY > m_imageArray[nUpper].Y() - 1)
				upY = m_imageArray[nUpper].Y() - 1;

			for (x = 0, newX = 0; x < roi.X(); x += reduction, newX++) {

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
					upX = (float) (x / dTwoToNumReductionsUpper - dOffsetUpper);
				if (upX < 0)
					upX = 0;
				if (upX > m_imageArray[nUpper].X() - 1)
					upX = m_imageArray[nUpper].X() - 1;

				lowerPixel = m_imageArray[nLower].interp(lowX, lowY);

				upperPixel = m_imageArray[nUpper].interp(upX, upY);

				pixel[0] = (float) (dWeightLower * lowerPixel[0] + dWeightUpper * upperPixel[0]);
				pixel[1] = (float) (dWeightLower * lowerPixel[1] + dWeightUpper * upperPixel[1]);
				pixel[2] = (float) (dWeightLower * lowerPixel[2] + dWeightUpper * upperPixel[2]);

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
	 * Returns an image at <i>reduction</i>. Faster than calling <i>get</i> for each pixel.
	 * 
	 * @param reduction Desired reduction amount of image (2.0 would return the image reduced by
	 *            1/2). must be >= 1.
	 */
	public RealColorImage getImage(float reduction) {
		return getImage(reduction, new ROI(0, 0, (int) m_imageSizeX - 1, (int) m_imageSizeY - 1));
	}
}
