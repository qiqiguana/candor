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
import jigl.image.utils.FFT;
import jigl.image.utils.ImageGenerator;

/**
 * A MIPMap variation that stores sub-images reduced by the <i>n</i>th root of 2 each time. Gaussian
 * noise is added to the image to get each sub-image.
 */
public class ComplexMIPMap {
	private InterpolatedRealColorImage[] m_imageArray;

	private float m_imageSizeX;
	private float m_imageSizeY;

	private float m_inc;//nth root of 2
	private int m_min = 16;//min image size

	private int m_n;//nth root of 2

	class Threader extends Thread {
		private float m_imageSize;

		private float m_stdDev;
		private int m_inc;

		private float m_xBuff, m_yBuff;

		private ComplexImage m_forward0;
		private ComplexImage m_forward1;
		private ComplexImage m_forward2;

		public InterpolatedRealColorImage m_reducedImage;

		public Threader(float squareImageSize, float xBuff, float yBuff, int inc, float stdDev,
				ComplexImage forward0, ComplexImage forward1, ComplexImage forward2) {
			m_imageSize = squareImageSize;
			m_inc = inc;
			m_xBuff = xBuff;
			m_yBuff = yBuff;
			m_stdDev = stdDev;
			m_forward0 = forward0;
			m_forward1 = forward1;
			m_forward2 = forward2;
		}

		public void run() {
			ComplexImage gauss0 = ImageGenerator.buildGaussInFreqDomain((float) (m_stdDev - 1.0),
					(int) Math.round(m_imageSize), (int) Math.round(m_imageSize), 0, 0, false);
			ComplexImage gauss1 = (ComplexImage) gauss0.copy();
			//ImageGenerator.buildGaussInFreqDomain(1/stdDev,(int)Math.round(squareImageSize),(int)Math.round(squareImageSize),0,0,false);
			ComplexImage gauss2 = (ComplexImage) gauss0.copy();
			//ImageGenerator.buildGaussInFreqDomain(1/stdDev,(int)Math.round(squareImageSize),(int)Math.round(squareImageSize),0,0,false);

			gauss0.multiply(m_forward0);
			gauss1.multiply(m_forward1);
			gauss2.multiply(m_forward2);

			ComplexImage reverseFFT0 = FFT.reverse(gauss0);
			ComplexImage reverseFFT1 = FFT.reverse(gauss1);
			ComplexImage reverseFFT2 = FFT.reverse(gauss2);

			RealColorImage blurred;

			//store the blurred image (same size as original)
			blurred = new RealColorImage(reverseFFT0.X(), reverseFFT0.Y());
			blurred.setPlane(0, reverseFFT0.real());
			blurred.setPlane(1, reverseFFT1.real());
			blurred.setPlane(2, reverseFFT2.real());

			Float[] val;

			if (m_inc > 1) {
				//reduce the image

				RealColorImage tempSquareImage = new RealColorImage((int) Math.round(m_imageSize
						/ m_inc), (int) Math.round(m_imageSize / m_inc));

				//sample image to get reduced version
				for (int x = 0, smallX = 0; x < m_imageSize; x += m_inc, smallX++) {
					for (int y = 0, smallY = 0; y < m_imageSize; y += m_inc, smallY++) {
						val = blurred.get(x, y);

						tempSquareImage.set(smallX, smallY, val);
					}
				}
				blurred = tempSquareImage;
			}

			//now blurred is the right size
			blurred.byteSize();
			if (m_xBuff == 0 && m_yBuff == 0) {
				//square
				m_reducedImage = new InterpolatedRealColorImage(blurred);
				m_reducedImage.setInterpolationMethod(InterpolationMethod.LINEAR);
			} else {
				//not square, trim excess
				m_reducedImage = new InterpolatedRealColorImage(blurred.addbuffer((int) Math
						.round(blurred.X() - m_xBuff), (int) Math.round(blurred.Y() - m_yBuff),
						new float[3]));
				m_reducedImage.setInterpolationMethod(InterpolationMethod.LINEAR);
			}
			System.out.println("exiting run");
		}
	}

	/**
	 * <i>image</i> must be square and a power of 2 in size
	 * 
	 * @param image RealColorImage to be used as the highest resolution image in the MIPMap.
	 * @param n <i>image</i> is reduced by the <i>n</i>th root of 2 for each sub-image.
	 */
	public ComplexMIPMap(RealColorImage image, int n) {
		Date start = new Date();

		float squareImageSize;//not to change!
		RealColorImage squareImage;//not to change!

		m_n = n;
		m_inc = 1 / (float) Math.pow(2.0, 1.0 / (double) n);

		System.out.println("m_inc: " + m_inc);

		double imageSizePowTwo;
		if (image.X() > image.Y())
			imageSizePowTwo = Math.log(image.X()) / Math.log(2.0);
		else
			imageSizePowTwo = Math.log(image.Y()) / Math.log(2.0);

		float xBuff, xTheoreticalBuff;
		float yBuff, yTheoreticalBuff;

		//make sure the image is square (for the FFT stuff)
		if (image.X() != image.Y()
				|| Math.abs(imageSizePowTwo - Math.round(imageSizePowTwo)) >= 0.0001) {
			//image isn't square power of 2
			System.out.println("not a square image");
			int newPow = (int) Math.ceil(imageSizePowTwo);//round up to an integer

			squareImage = image.addbuffer((int) Math.pow(2, newPow), (int) Math.pow(2, newPow),
					new float[3]);

			xBuff = squareImage.X() - image.X();
			yBuff = squareImage.Y() - image.Y();
		} else {
			System.out.println("square image");

			squareImage = image;

			xBuff = yBuff = 0;
		}

		System.out.println("the square image is " + squareImage.X() + "X" + squareImage.Y());

		//remember original image size
		m_imageSizeX = image.X();
		m_imageSizeY = image.Y();

		//remember square image size (should not change!)
		squareImageSize = squareImage.X();

		//see if it's worth computing
		if (m_min > squareImageSize) {
			return;
		}

		int numImages = 0;

		//compute how many images we'll end up with
		numImages = (int) (Math.ceil((Math.log(m_min) - Math.log(squareImageSize))
				/ Math.log(m_inc))) + 1;

		System.out.println("numImages: " + numImages);

		m_imageArray = new InterpolatedRealColorImage[numImages];

		//the first image is the original
		m_imageArray[0] = new InterpolatedRealColorImage(image);
		m_imageArray[0].setInterpolationMethod(InterpolationMethod.LINEAR);

		//		float[] val = new float[3];
		//		float[] tempval;

		float stdDev = 0;

		//initialization
		float squareTheoreticalSize = squareImageSize * m_inc;
		//		float squareCurrentSize = squareImageSize;
		xTheoreticalBuff = xBuff * m_inc;
		yTheoreticalBuff = yBuff * m_inc;

		//compute fft for original image (just once, used for each sub-image)
		ComplexImage forwardFFT0 = FFT.forward(squareImage.plane(0));
		ComplexImage forwardFFT1 = FFT.forward(squareImage.plane(1));
		ComplexImage forwardFFT2 = FFT.forward(squareImage.plane(2));

		/*		ComplexImage gauss0;
				ComplexImage gauss1;
				ComplexImage gauss2;

				ComplexImage reverseFFT0;
				ComplexImage reverseFFT1;
				ComplexImage reverseFFT2;
			*/

		Threader[] threadArray = new Threader[numImages - 1];
		int inc = 1;

		for (int i = 1; i < numImages; i++) {

			/*			//blur original image
						stdDev = squareImageSize/squareTheoreticalSize;
						System.out.println("stdDev: " + stdDev);

						//only the stdDev changes here
						gauss0 = ImageGenerator.buildGaussInFreqDomain((float)(stdDev-1.0),(int)Math.round(squareImageSize),(int)Math.round(squareImageSize),0,0,false);
						gauss1 = (ComplexImage)gauss0.copy();//ImageGenerator.buildGaussInFreqDomain(1/stdDev,(int)Math.round(squareImageSize),(int)Math.round(squareImageSize),0,0,false);
						gauss2 = (ComplexImage)gauss0.copy();//ImageGenerator.buildGaussInFreqDomain(1/stdDev,(int)Math.round(squareImageSize),(int)Math.round(squareImageSize),0,0,false);

						gauss0.multiply(forwardFFT0);
						gauss1.multiply(forwardFFT1);
						gauss2.multiply(forwardFFT2);

						reverseFFT0 = FFT.reverse(gauss0);
						reverseFFT1 = FFT.reverse(gauss1);
						reverseFFT2 = FFT.reverse(gauss2);

						RealColorImage blurred;

						//store the blurred image (same size as original)
						blurred = new RealColorImage(reverseFFT0.X(),reverseFFT0.Y());
						blurred.setPlane(0,reverseFFT0.real());
						blurred.setPlane(1,reverseFFT1.real());
						blurred.setPlane(2,reverseFFT2.real());

						//see how much we should reduce
			//			System.out.println("comparing " + squareTheoreticalSize + " - " + (float)Math.round(squareTheoreticalSize) + " < 0.0001");
						if (Math.abs(squareTheoreticalSize - (float)Math.round(squareTheoreticalSize)) < 0.0001)
						{
							squareCurrentSize = squareTheoreticalSize;

							xBuff = xTheoreticalBuff;
							yBuff = yTheoreticalBuff;
						}

						if (squareCurrentSize != squareImageSize)
						{
							//reduce the image

							RealColorImage tempSquareImage = new RealColorImage((int)Math.round(squareCurrentSize),(int)Math.round(squareCurrentSize));

							//compute the sampling increment
							inc = (int)Math.round(squareImageSize/squareCurrentSize);
							System.out.println("inc: " + inc);

							//sample image to get reduced version
							for (int x = 0, smallX = 0; x < squareImageSize; x+=inc, smallX++)
							{
								for (int y = 0, smallY = 0; y < squareImageSize; y+=inc, smallY++)
								{
									val = blurred.get(x,y);

									if (smallX < squareCurrentSize && smallY < squareCurrentSize)	tempSquareImage.set(smallX,smallY,val);
									else System.out.println("bad coordinates are: " + smallX + "," + smallY);
								}
							}
							blurred = tempSquareImage;
						}

						//now blurred is the right size
						blurred.byteSize();
						if (xBuff == 0 && yBuff == 0)
						{
							//square
							m_imageArray[i] = new InterpolatedRealColorImage(blurred);
							m_imageArray[i].setInterpolationMethod(InterpolatedRealColorImage.LINEAR);
						}
						else
						{
							//not square, trim excess
							m_imageArray[i] = new InterpolatedRealColorImage(blurred.addbuffer((int)Math.round(squareCurrentSize - xBuff),(int)Math.round(squareCurrentSize-yBuff),new float[3]));
							m_imageArray[i].setInterpolationMethod(InterpolatedRealColorImage.LINEAR);
						}

						squareTheoreticalSize *= m_inc;
						xTheoreticalBuff *= m_inc;
						yTheoreticalBuff *= m_inc;


						*/

			stdDev = squareImageSize / squareTheoreticalSize;

			if (Math.abs(squareTheoreticalSize - (float) Math.round(squareTheoreticalSize)) < 0.0001) {
				inc = (int) Math.round(squareImageSize / squareTheoreticalSize);

				xBuff = xTheoreticalBuff;
				yBuff = yTheoreticalBuff;
			}

			threadArray[i - 1] = new Threader(squareImageSize, xBuff, yBuff, inc, stdDev,
					forwardFFT0, forwardFFT1, forwardFFT2);

			System.out.println("starting thread " + i);
			threadArray[i - 1].start();

			squareTheoreticalSize *= m_inc;
			xTheoreticalBuff *= m_inc;
			yTheoreticalBuff *= m_inc;

		}

		for (int j = 1; j < numImages; j++) {
			try {
				threadArray[j - 1].join();
				System.out.println("joined thread " + j);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			m_imageArray[j] = threadArray[j - 1].m_reducedImage;
		}

		Date end = new Date();

		long calcTime = end.getTime() - start.getTime();

		System.out.println("" + ((double) calcTime / 1000) + " seconds to construct ComplexMIPMap");

		/*//debugging stuff (output to screen)

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
		}*/

	}

	/**
	 * Returns the pixel value at (x,y) of the reduced image.
	 * 
	 * @param x x value
	 * @param y y value
	 * @param reduction reduction amount of image (2.0 would return the point at the image reduced
	 *            by 1/2). must be >= 1.
	 */
	public float[] get(float x, float y, float reduction) {
		double val = Math.log(reduction) / Math.log(1 / m_inc);

		float lowX, lowY, upX, upY;

		Float[] tempLower = new Float[3];
		Float[] tempUpper = new Float[3];

		//desired reduction is between upper and lower images
		double upper = (int) Math.ceil(val);//index of upper image in m_imageArray
		double lower = (int) Math.floor(val);//index of lower image in m_imageArray

		int nUpper = (int) upper;
		int nLower = (int) lower;

		double weightLower = upper - val;
		double weightUpper = val - lower;

		//		System.out.println("weightUpper: " + weightUpper);
		//		System.out.println("weightLower: " + weightLower);

		if (upper == lower)
			weightLower = 1;

		float[] pixel = new float[3];

		//number of times upper and lower images were reduced by 2
		int numReductionsUpper = (int) (upper / m_n);
		int numReductionsLower = (int) (lower / m_n);

		double twoToNumReductionsLower = Math.pow(2.0, numReductionsLower);
		double twoToNumReductionsUpper = Math.pow(2.0, numReductionsUpper);

		lowX = (float) (x / twoToNumReductionsLower);
		lowY = (float) (y / twoToNumReductionsLower);
		if (lowX < 0)
			lowX = 0;
		if (lowX > m_imageArray[nLower].X())
			lowX = m_imageArray[nLower].X();
		if (lowY < 0)
			lowY = 0;
		if (lowY > m_imageArray[nLower].Y())
			lowY = m_imageArray[nLower].Y();

		tempLower = m_imageArray[nLower].interp(lowX, lowY);

		upX = (float) (x / twoToNumReductionsUpper);
		upY = (float) (y / twoToNumReductionsUpper);
		if (upX < 0)
			upX = 0;
		if (upX > m_imageArray[nUpper].X())
			upX = m_imageArray[nUpper].X();
		if (upY < 0)
			upY = 0;
		if (upY > m_imageArray[nUpper].Y())
			upY = m_imageArray[nUpper].Y();

		tempUpper = m_imageArray[nUpper].interp(upX, upY);

		pixel[0] = (float) (weightLower * tempLower[0] + weightUpper * tempUpper[0]);
		pixel[1] = (float) (weightLower * tempLower[1] + weightUpper * tempUpper[1]);
		pixel[2] = (float) (weightLower * tempLower[2] + weightUpper * tempUpper[2]);

		return pixel;
	}

	/**
	 * Returns an image at <i>reduction</i>. Faster than calling <i>get</i> for each pixel.
	 * 
	 * @param reduction reduction amount of image (2.0 would return the image reduced by 1/2). Must
	 *            be >= 1.
	 */
	public RealColorImage getImage(float reduction) {
		Date start = new Date();

		double val = Math.log(reduction) / Math.log(1 / m_inc);

		//		System.out.println("val: " + val);

		Float[] tempLower = new Float[3];
		Float[] tempUpper = new Float[3];

		//desired reduction is between upper and lower images
		double upper = (int) Math.ceil(val);//index of upper image in m_imageArray
		double lower = (int) Math.floor(val);//index of lower image in m_imageArray
		//		System.out.println("upper: " + upper);
		//		System.out.println("lower: " + lower);

		int nUpper = (int) upper;
		int nLower = (int) lower;

		double weightLower = upper - val;
		double weightUpper = val - lower;
		//		System.out.println("weightUpper: " + weightUpper);
		//		System.out.println("weightLower: " + weightLower);

		if (upper == lower)
			weightLower = 1;

		Float[] pixel = new Float[3];

		//number of times upper and lower images were reduced by 2
		int numReductionsUpper = (int) (upper / m_n);
		int numReductionsLower = (int) (lower / m_n);

		//		System.out.println("numReductionsUpper: " + numReductionsUpper);
		//		System.out.println("numReductionsLower: " + numReductionsLower);

		double twoToNumReductionsLower = Math.pow(2.0, numReductionsLower);
		double twoToNumReductionsUpper = Math.pow(2.0, numReductionsUpper);

		//		System.out.println("toToNumReductionsLower: " + twoToNumReductionsLower);
		//		System.out.println("twoToNumReductionsUpper: " + twoToNumReductionsUpper);

		int retSizeX = (int) (m_imageSizeX / reduction);
		int retSizeY = (int) (m_imageSizeY / reduction);

		//		System.out.println("retSizeX: " + retSizeX);
		//		System.out.println("retSizeY: " + retSizeY);

		RealColorImage retImage = new RealColorImage(retSizeX, retSizeY);

		int newX, newY;
		float x, y;

		float lowX, lowY, upX, upY;

		for (y = 0, newY = 0; y < m_imageSizeY; y += reduction, newY++) {
			lowY = (float) (y / twoToNumReductionsLower);
			if (lowY < 0)
				lowY = 0;
			if (lowY > m_imageArray[nLower].Y())
				lowY = m_imageArray[nLower].Y();

			upY = (float) (y / twoToNumReductionsUpper);
			if (upY < 0)
				upY = 0;
			if (upY > m_imageArray[nUpper].Y())
				upY = m_imageArray[nUpper].Y();

			for (x = 0, newX = 0; x < m_imageSizeX; x += reduction, newX++) {
				lowX = (float) (x / twoToNumReductionsLower);
				if (lowX < 0)
					lowX = 0;
				if (lowX > m_imageArray[nLower].X())
					lowX = m_imageArray[nLower].X();

				tempLower = m_imageArray[nLower].interp(lowX, lowY);

				upX = (float) (x / twoToNumReductionsUpper);
				if (upX < 0)
					upX = 0;
				if (upX > m_imageArray[nUpper].X())
					upX = m_imageArray[nUpper].X();

				tempUpper = m_imageArray[nUpper].interp(upX, upY);

				pixel[0] = (float) (weightLower * tempLower[0] + weightUpper * tempUpper[0]);
				//System.out.println(weightLower+" "+tempLower[0]+" : "+weightUpper+" "+tempUpper[0]+" :: "+pixel[0]);
				pixel[1] = (float) (weightLower * tempLower[1] + weightUpper * tempUpper[1]);
				pixel[2] = (float) (weightLower * tempLower[2] + weightUpper * tempUpper[2]);

				if (newX < retSizeX && newY < retSizeY)
					retImage.set(newX, newY, pixel);
				//				else System.out.println("not set this time");
			}
		}

		Date end = new Date();

		long calcTime = end.getTime() - start.getTime();

		System.out.println("" + ((double) calcTime / 1000) + " seconds to compute Image");

		return retImage;
	}

}
