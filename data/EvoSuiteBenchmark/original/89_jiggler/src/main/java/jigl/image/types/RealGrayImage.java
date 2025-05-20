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

import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import jigl.image.AbstractArrayImage;
import jigl.image.NumericImage;
import jigl.image.ROI;

/**
 * RealGrayImage is a 2-d array of floats
 * 
 * TODO: extends AbstractArrayImage<Float>
 */

public class RealGrayImage extends AbstractArrayImage<Float> implements NumericImage<Float> {

//	/** Two dimensional float array */
//	protected float[][] data;

//	/** Cartesian width */
//	protected int X;
//
//	/** Cartesian height */
//	protected int Y;

	/** Creates an empty two dimensional RealGrayImage with a height and width of zero */
	public RealGrayImage() {
		super(0, 0, null);
	}

	/** Creates a two dimensional RealGrayImage with a height and width of x and y respectively */
	public RealGrayImage(int x, int y) {
		super(x, y, new Float[y][x]);
//		X = x;
//		Y = y;
//		data = new float[Y][X];
	}

	/**
	 * Creates a two dimensional GrayImage with a height and width of x and y respectively
	 * 
	 * @param x width of image
	 * @param y height of image
	 * @param dat one dimensional array of float. The array is length x*y.
	 */
	public RealGrayImage(int x, int y, float[] data) {
		super(x, y, new Float[y][x]);
//		X = x;
//		Y = y;
//
//		this.data = new float[Y][X];
//
		for (int a = 0; a < y; a++) {
			System.arraycopy(data, a * Y, this.data[a], 0, x);
		}
	}

	/**
	 * Creates a two dimensional GrayImage from a two dimensional array
	 * 
	 * @param dat two dimensional array of short
	 */

	public RealGrayImage(float[][] data) {
		super(width(data), data.length, new Float[data.length][width(data)]);
//		X = data[0].length;
//		for(float[] arr : data) {
//			assert(arr.length == X);
//		}
//		Y = data.length;
////		int max = 0;
//		
////		for (int y = 0; y < Y; y++) {
////			if (dat[y].length > max) {
////				max = dat[y].length;
////			}
////		}
////		X = max;
//
//		
//		this.data = new float[Y][X];
//
//		for (int y = 0; y < Y; y++) {
//			System.arraycopy(data[y], 0, this.data[y], 0, data[y].length);
//		}
        for(int x = 0; x < X; x++) {
            for(int y = 0; y < Y; y++) {
                this.data[x][y] = data[x][y];
            }
        }
	}
	
	private static int width(float[][] data) {
		int max = 0;

		//find the max length of data
		for(float[] row : data) {
			if(row.length > max)
				max = row.length;
		}
		
		return max;
	}

	/** Creates an two dimensional RealGrayImage from RealGrayImage img */
	public RealGrayImage(RealGrayImage img) {
		this(img.X(), img.Y());
		for (int y = 0; y < Y; y++)
			for (int x = 0; x < X; x++)
				data[y][x] = img.data[y][x];
	}

	/** Creates a two dimensional RealGrayImage from the standard java.awt.Image */
	public RealGrayImage(java.awt.Image img) {
		super(img, new Float[jimgHeight(img)][jimgWidth(img)]);
//		int w = img.getWidth(jigl.internal.DummyObserver.dummy);
//		int h = img.getHeight(jigl.internal.DummyObserver.dummy);
//		X = w;
//		Y = h;
//		data = new float[Y][X];
		InitFromImage(img, 0, 0, X, Y);
	}

	/**
	 * Makes a deep copy of this image
	 * 
	 * @param none
	 * @return a deep copy of RealGrayImage
	 */
	public RealGrayImage copy() {
		RealGrayImage g = new RealGrayImage(X, Y);

		//g.data = (float[][])data.clone();
		for (int y = 0; y < Y; y++) {
			System.arraycopy(data[y], 0, g.data[y], 0, data[y].length);
		}
		return g;
	}

//	public float[][] getData() {
//		float[][] d = new float[data.length][data[0].length];
//		for (int y = 0; y < Y; y++) {
//			System.arraycopy(data[y], 0, d[y], 0, data[y].length);
//		}
//		return d;
//
//		//		return (float[][])data.clone();
//	}

	/*	public void convolve(ImageKernel kernel) {
			
			int NumX = kernel.X();
			int NumY = kernel.Y();
			int midX = NumX/2;
			int midY = NumY/2;
			double sum = 0;
			float[][] value = new float[Y][X];
			
			float[][] kern = ImageConverter.toRealGray(kernel).data; 		
			// for every pixel in the original image
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					
					//Convolve with the kernel
					sum = 0;
					for (int j = -midY; j <= midY; j++)
						for (int i = -midX; i <= midX; i++){
						  if ((y+j>=0) && (x+i>=0) && (y+j<Y) && (x+i<X) && (midY+j>=0) && (midX+i>=0) && (midY+j<NumY) && (midX+i<NumX))
								//try{
							  
								sum += data[y+j][x+i] * kern[midY+j][midX+i];
							//		}
							//catch (Exception e){System.out.print("");}
							
						}
						value[y][x] = (float)sum;
				}
				
			}
			
			this.data = value;
			
			
		}*/

	private void InitFromImage(java.awt.Image img, int x, int y, int w, int h) {
		int pixels[] = new int[w * h];
		PixelGrabber pg = new PixelGrabber(img, x, y, w, h, pixels, 0, w);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			System.err.println("interrupted waiting for pixels!");
			return;
		}
		if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
			System.err.println("image fetch aborted or errored");
			return;
		}

		// convert from grabbed pixels
		float red = 0;
		float green = 0;
		float blue = 0;
		int index = 0;
		for (int iy = 0; iy < Y; iy++) {
			for (int ix = 0; ix < X; ix++) {
				red = 0x0FF & pixels[index] >> 16;
				green = 0x0FF & pixels[index] >> 8;
				blue = 0x0FF & pixels[index];
				data[iy][ix] = (float) (red * 0.299 + green * 0.587 + blue * 0.114);
				index++;
			}
		}
	}

	/**
	 * Returns the width (maximum X value)
	 * 
	 * @param none
	 */
	public final int X() {
		return X;
	}

	/**
	 * Returns the height (maximum Y value)
	 * 
	 * @param none
	 */
	public final int Y() {
		return Y;
	}

	/**
	 * Returns the pixel value at the given x, y value
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 */
	public final Float get(int x, int y) {
		return data[y][x];
	}

	/**
	 * Sets the pixel value at x, y to a given value
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param value the value to set the pixel to
	 */
	public final void set(int x, int y, Float value) {
		data[y][x] = value;
	}

	/**
	 * Clears the image to zero
	 * 
	 * @param none
	 */
	public final RealGrayImage clear() {
		return clear(0f);
	}

	/**
	 * Clears to constant value
	 * 
	 * @param val the value to "clear" the image to
	 */
	public final RealGrayImage clear(Float val) {
		int x, y;
		for (y = 0; y < Y; y++) {
			for (x = 0; x < X; x++) {
				data[y][x] = val;
			}
		}
		return this;
	}

	/**
	 * Makes a copy of this image with a buffer so the resulting image has a width w and height h
	 * 
	 * @param w width of buffered image
	 * @param h height of buffered image
	 * @param color default color to buffer with
	 * @return a deep copy of RealGrayImage
	 */
	public RealGrayImage addbuffer(int w, int h, float color) {
		RealGrayImage g = new RealGrayImage(w, h);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if ((x < X) && (y < Y))
					g.data[y][x] = data[y][x];
				else
					g.data[y][x] = color;
			}
		}
		return g;
	}

	/**
	 * Makes a copy of this image with a buffer so the resulting image has a width w and height h
	 * 
	 * @param w width of buffered image
	 * @param h height of buffered image
	 * @param xoff x offset of this image in the buffered image
	 * @param yoff y offset of this image in the buffered image
	 * @param color default color to buffer with
	 * @return a deep copy of RealGrayImage
	 */
	public RealGrayImage addbuffer(int w, int h, int xoff, int yoff, float color) {
		RealGrayImage g = new RealGrayImage(w, h);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if ((x < xoff) || (y < yoff))
					g.data[y][x] = color;
				else if ((x >= xoff + X) || (y >= yoff + Y))
					g.data[y][x] = color;
				else
					g.data[y][x] = data[y - yoff][x - xoff];
			}
		}

		return g;
	}

	/**
	 * Adds a value to a single pixel
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value the value to add to the pixel
	 */
	public final void add(int x, int y, float value) {
		data[y][x] += value;
	}

	/**
	 * Subtracts a value from a single pixel
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value the value to subtract from the pixel
	 */
	public final void subtract(int x, int y, float value) {
		data[y][x] -= value;
	}

	/**
	 * Multiplies a single pixel by a value
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value - the value to multiply to the pixel
	 */
	public final void multiply(int x, int y, float value) {
		data[y][x] *= value;
	}

	/**
	 * Divides a single pixel by a value
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value - the divisor
	 */
	public final void divide(int x, int y, float value) {
		data[y][x] /= value;
	}

	/**
	 * Finds the minimum value of this image
	 * 
	 * @param none
	 * @return an float containing the minimum value
	 */
	public Float min() {
		float p;
		float min = Short.MAX_VALUE;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				p = data[y][x];
				if (p < min)
					min = p;
			}
		}
		return min;
	}

	/**
	 * Finds the maximum value of this image
	 * 
	 * @param none
	 * @return an integer containing the maximum value
	 */
	public Float max() {
		float p;
		float max = Short.MIN_VALUE;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				p = data[y][x];
				if (p > max)
					max = p;
			}
		}
		return max;
	}

	/** Adds all the values together */
	public final float addSum() {
		float sum = 0;
		for (int y = 0; y < Y; y++)
			for (int x = 0; x < X; x++)
				sum += data[y][x];
		return sum;
	}

	/** Adds absolute value of all the values together */
	public final float absSum() {
		float sum = 0;
		for (int y = 0; y < Y; y++)
			for (int x = 0; x < X; x++)
				if (data[y][x] < 0)
					sum += -(data[y][x]);
				else
					sum += data[y][x];
		return sum;
	}

	/** Adds the square of all the values together */
	public final double sqrSum() {
		double sum = 0;
		for (int y = 0; y < Y; y++)
			for (int x = 0; x < X; x++)
				sum += (data[y][x] * data[y][x]);
		return sum;
	}

	/**
	 * Adds a value to all the pixels in this image
	 * 
	 * @param v value to be added to the pixels
	 * @return this
	 */
	public final RealGrayImage add(float v) {
		float sv = v;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data[y][x] += sv;
			}
		}
		return this;
	}

	/**
	 * Subtracts a value from all the pixels in this image
	 * 
	 * @param v value to be added to the pixels
	 * @return this
	 */
	public final RealGrayImage subtract(float v) {
		float sv = v;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data[y][x] -= sv;
			}
		}
		return this;
	}

	/**
	 * Multiplies all the pixels in this image by a value
	 * 
	 * @param v value to be added to the pixels
	 * @return this
	 */
	public final RealGrayImage multiply(final float v) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data[y][x] *= v;
			}
		}
		return this;
	}

	/**
	 * Divides all the pixels in this image by a value
	 * 
	 * @param v value to be added to the pixels
	 * @return this
	 */
	public final RealGrayImage divide(final float v) {
//		float sv = (float) v;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data[y][x] /= v;
			}
		}
		return this;
	}

	/**
	 * Adds another RealGrayImage to this image
	 * 
	 * @param im the RealGrayImage to add
	 * @return this
	 */
	public final RealGrayImage add(RealGrayImage im) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				if (y < im.Y() && x < im.X())
					data[y][x] += im.get(x, y);
			}
		}
		return this;
	}

	/**
	 * Subtracts a RealGrayImage from this image
	 * 
	 * @param im the RealGrayImage to subtract
	 * @return this
	 */
	public final RealGrayImage subtract(RealGrayImage im) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				if (y < im.Y() && x < im.X())
					data[y][x] -= im.get(x, y);
			}
		}
		return this;
	}

	/** Subtracts the second image from the first and returns the absolute value */
	public final RealGrayImage diff(RealGrayImage im) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data[y][x] -= im.get(x, y);
				if (data[y][x] < 0)
					data[y][x] = -data[y][x];
			}
		}
		return this;
	}

	/**
	 * Multiplies a RealGrayImage by this image
	 * 
	 * @param im the RealGrayImage to multiply
	 * @return this
	 */
	public final RealGrayImage multiply(RealGrayImage im) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				if (y < im.Y() && x < im.X())
					data[y][x] *= im.get(x, y);
			}
		}
		return this;
	}

	/**
	 * Divides this image by a RealGrayImage
	 * 
	 * @param im the RealGrayImage to divide
	 * @return this
	 */
	public final RealGrayImage divide(RealGrayImage im) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				if (y < im.Y() && x < im.X())
					data[y][x] /= im.get(x, y);
			}
		}
		return this;
	}

	/**
	 * Prints the string in integer format. <DT>
	 * <DL>
	 * <DL>
	 * -Example of output on an image with width 100 and height 120:</DT>
	 * <DL>
	 * <DT>100 : 120</DT>
	 * <DT>10 20 32 12 32 56 40 59 42 39 43 ...</DT>
	 * </DL>
	 * </DL></DL>
	 */
	public String toString() {
		String str = X + ":" + Y + "\n";
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				str += data[y][x] + " ";
			}
			str += "\n";
		}
		return str;
	}

	/**
	 * Turns this image into a Java Image (java.awt.Image). Note: This method also scales the image
	 * so all the values are between 0 and 255.
	 * 
	 * @param none
	 * @see java.awt.image.ImageProducer
	 */
	public ImageProducer getJavaImage() {

		// get range of this image
		float min = min();
		float max = max();

		// keep byte images in original range
		if (min >= 0 && max <= 255) {
			min = 0;
			max = 255;
		}
		float range = max - min;

		// convert jigl image to java image
		int pix[] = new int[X * Y];
		int index = 0;
		int value = 0;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {

				// scale image values
				value = (int) ((255.0 / range) * (data[y][x].intValue() - min));

				value = 0x00FF & value; // take lower 8 bits

				// put this pixel in the java image
				pix[index] = (0xFF << 24) | (value << 16) | (value << 8) | value;
				index++;
			}
		}

		// return java image
		return new MemoryImageSource(X, Y, pix, 0, X);
	}

	/*
	Level operations
	*/

	/**
	 * Scales the range of this image to byte (0..255)
	 * 
	 * @param none
	 */
	public void byteSize() {

		// get range of this image
		float min = min();
		float max = max();

		float range = max - min;

		// convert to byte depth
		float value = 0;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				value = ((255.0f / range) * (data[y][x] - min));
				//value = 0x00FF & value;
				data[y][x] = value;
			}
		}

	}

	/**
	 * Clips the range of this image to an arbitrary min/max
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 */
	public void clip(int min, int max) {
		float value = 0;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				value = data[y][x];
				value = (value > max) ? max : value;
				value = (value < min) ? min : value;
				data[y][x] = value;
			}
		}

	}

	/** Sorts the array <i>vals[]</i> with length <i>size</i> ascendingly. Auxiliary method. */
	private float[] sort(float vals[], int size) {
		int i, j;
		float temp;

		for (i = 0; i < size; i++)
			for (j = 0; j < size - 1; j++) {
				try {
					if (vals[j] > vals[j + 1]) {
						temp = vals[j];
						vals[j] = vals[j + 1];
						vals[j + 1] = temp;
					}
				} catch (Exception e) {
				}
			}
		return vals;
	}

	/**
	 * Performs median filter on this image
	 * 
	 * @param size the size of the median filter
	 */
	public void median(int size) {

		int NumX = (size > X) ? X : size;
		int NumY = (size > Y) ? Y : size;
		int midX = NumX / 2;
		int midY = NumY / 2;
		float value[] = new float[NumX * NumY];
		int count;

		//for every pixel in the original image
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {

				//find median value
				count = 0;
				for (int j = -midY; j <= midY; j++) {
					for (int i = -midX; i <= midX; i++) {
						try {
							value[count++] = data[y + j][x + i];
						} catch (Exception e) {
							//ignore out of bounds pixels
						}
					}
				}
				value = sort(value, count);
				data[y][x] = value[count / 2];
			}
		}

	}

	//**************************************************************************************
	//****************************         ROI Stuff         *******************************
	//**************************************************************************************

	/**
	 * Makes a deep copy of a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return a deep copy of RealGrayImage
	 */
	public RealGrayImage copy(ROI roi) {
		RealGrayImage g = new RealGrayImage(roi.X(), roi.Y());

		for (int y = roi.uy(); y <= roi.ly(); y++) {
			System.arraycopy(data[y], roi.ux(), g.data[y - roi.uy()], 0, roi.X());
		}
		return g;
	}

	/**
	 * Sets the pixel value at x, y to a given value in a Region of Interest
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param value the value to set the pixel to
	 * @param roi Region of Interest
	 */
	public final void set(int x, int y, Float value, ROI roi) {
		data[(roi.uy() + y)][(roi.ux() + x)] = value;
	}

	/**
	 * Adds a value to a single pixel in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value the value to add to the pixel
	 * @param r Region of Interest
	 */
	public final void add(int x, int y, float value, ROI r) {
		data[(r.uy() + y)][(r.ux() + x)] += value;
	}

	/**
	 * Subtracts a value from a single pixel in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value the value to subtract from the pixel
	 * @param roi Region of Interest
	 */

	public final void subtract(int x, int y, float value, ROI roi) {
		data[(roi.uy() + y)][(roi.ux() + x)] -= value;
	}

	/**
	 * Multiplies a single pixel by a value in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value - the value to multiply to the pixel
	 * @param roi Region of Interest
	 */
	public final void multiply(int x, int y, float value, ROI roi) {
		data[(roi.uy() + y)][(roi.ux() + x)] *= value;
	}

	/**
	 * Divides a single pixel by a value in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param value - the value to multiply to the pixel
	 * @param roi Region of Interest
	 */
	public final void divide(int x, int y, float value, ROI roi) {
		data[(roi.uy() + y)][(roi.ux() + x)] /= value;
	}

	/**
	 * Finds the minimum value in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return an integer containing the minimum value
	 */
	public final float min(ROI roi) {
		float p;
		float min = Short.MAX_VALUE;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				p = data[y][x];
				if (p < min)
					min = p;
			}
		}
		return min;
	}

	/**
	 * Finds the maximum value in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return an integer containing the maximum value
	 */
	public final float max(ROI roi) {
		float p;
		float max = Short.MIN_VALUE;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				p = data[y][x];
				if (p > max)
					max = p;
			}
		}
		return max;
	}

	/**
	 * Adds a value to all the pixels in a Region of Interest
	 * 
	 * @param v value to be added to the pixels
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealGrayImage add(final float v, ROI roi) {
//		float sv = (float) v;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				data[y][x] += v;
			}
		}
		return this;
	}

	/**
	 * Subtracts a value from all the pixels in a Region of Interest
	 * 
	 * @param v value to be added to the pixels
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealGrayImage subtract(final float v, ROI roi) {
//		float sv = (float) v;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				data[y][x] -= v;
			}
		}
		return this;
	}

	/**
	 * Multiplies all the pixels in a Region of Interest by a value
	 * 
	 * @param v value to be added to the pixels
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealGrayImage multiply(final float v, ROI roi) {
//		float sv = (float) v;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				data[y][x] *= v;
			}
		}
		return this;
	}

	/**
	 * Divides all the pixels by a value in a Region of Interest
	 * 
	 * @param v value to be added to the pixels
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealGrayImage divide(final float v, ROI roi) {
//		float sv = (float) v;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				data[y][x] /= v;
			}
		}
		return this;
	}

	/**
	 * Adds a Region of Interest (souceROI) in another RealGrayImage to a Region of Interest
	 * (destROI) of this image
	 * 
	 * @param im the RealGrayImage to add
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final RealGrayImage add(RealGrayImage im, ROI sourceROI, ROI destROI) {
		for (int y = destROI.uy(); y <= destROI.ly(); y++) {
			for (int x = destROI.ux(); x <= destROI.lx(); x++) {
				data[x][y] += im.get((x - destROI.ux() + sourceROI.ux()),
						(y - destROI.uy() + sourceROI.uy()));
			}
		}
		return this;
	}

	/**
	 * Subtracts a Region of Interest (sourceROI) in another RealGrayImage from a Region of Interest
	 * (destROI) of this image
	 * 
	 * @param im the RealGrayImage to subtract
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final RealGrayImage subtract(RealGrayImage im, ROI sourceROI, ROI destROI) {
		for (int y = destROI.uy(); y <= destROI.ly(); y++) {
			for (int x = destROI.ux(); x <= destROI.lx(); x++) {
				data[x][y] -= im.get((x - destROI.ux() + sourceROI.ux()),
						(y - destROI.uy() + sourceROI.uy()));
			}
		}
		return this;
	}

	/**
	 * Multiplies a Region of Interest (sourceROI) of another RealGrayImage by a Region of Interest
	 * (destROI) of this image
	 * 
	 * @param im the RealGrayImage to multiply
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final RealGrayImage multiply(RealGrayImage im, ROI sourceROI, ROI destROI) {
		for (int y = destROI.uy(); y <= destROI.ly(); y++) {
			for (int x = destROI.ux(); x <= destROI.lx(); x++) {
				data[x][y] *= im.get((x - destROI.ux() + sourceROI.ux()),
						(y - destROI.uy() + sourceROI.uy()));
			}
		}
		return this;
	}

	/**
	 * Divides by a Region of Interest (sourceROI) in this image by a Region of Interest (destROI)
	 * of another RealGrayImage
	 * 
	 * @param im the RealGrayImage to divide
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final RealGrayImage divide(RealGrayImage im, ROI sourceROI, ROI destROI) {
		for (int y = destROI.uy(); y <= destROI.ly(); y++) {
			for (int x = destROI.ux(); x <= destROI.lx(); x++) {
				data[x][y] /= im.get((x - destROI.ux() + sourceROI.ux()),
						(y - destROI.uy() + sourceROI.uy()));
			}
		}
		return this;
	}

	/**
	 * Prints the string in integer format in a Region of Interest.
	 * 
	 * @param roi Region of Interest <DT>
	 *            <DL>
	 *            <DL>
	 *            -Example of output on an image with width 100 and height 120:</DT>
	 *            <DL>
	 *            <DT>100 : 120</DT>
	 *            <DT>10 20 32 12 32 56 40 59 42 39 43 ...</DT>
	 *            </DL>
	 *            </DL></DL>
	 */
	public String toString(ROI roi) {
		String str = X + ":" + Y + "\n";
		for (int x = roi.ux(); x <= roi.lx(); x++) {
			for (int y = roi.uy(); y <= roi.ly(); y++) {
				str += data[y][x] + " ";
			}
			str += "\n";
		}
		return str;
	}

	/**
	 * Clips the range of this image to an arbitrary min/max in a Region of Interest
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 * @param roi Region of Interest
	 */
	public void clip(int min, int max, ROI roi) {
		float value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = data[y][x];
				value = (value > max) ? max : value;
				value = (value < min) ? min : value;
				data[y][x] = value;
			}
		}

	}

	/**
	 * Scales the range of a region of interest of this image to byte (0..255)
	 * 
	 * @param none
	 */
	public void byteSize(ROI roi) {

		// get range of this image
		float min = min(roi);
		float max = max(roi);

		float range = max - min;

		// convert to byte depth
		Integer value = 0;
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				value = (int) ((255.0 / range) * (data[y][x] - min));
				value = 0x00FF & value;
				data[y][x] = value.floatValue();
			}
		}
	}

	/**
	 * Performs median filter in a Region of Interest
	 * 
	 * @param size the size of the median filter
	 * @param roi Region of Interest
	 */
	public void median(int size, ROI roi) {
		int NumX = (size > X) ? X : size;
		int NumY = (size > Y) ? Y : size;
		int midX = NumX / 2;
		int midY = NumY / 2;
		float value[] = new float[NumX * NumY];
		int count;

		//for every pixel in the original image
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {

				//find median value
				count = 0;
				for (int j = -midY; j <= midY; j++) {
					for (int i = -midX; i <= midX; i++) {
						try {
							value[count++] = data[y + j][x + i];
						} catch (Exception e) {
							//ignore out of bounds pixels
						}
					}
				}
				value = sort(value, count);
				data[y][x] = value[count / 2];
			}
		}
	}

}
