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

import java.awt.image.ImageProducer;

import jigl.image.Image;
import jigl.image.ROI;
import jigl.math.Complex;

/**
 * A complex image is a set of two RealGrayImage plane: real plane and imaginary plane. ComplexImage
 * implements Image.
 */

public class ComplexImage implements Image<Complex> {

	/** The Real plane of the image */
	protected RealGrayImage real;
	/** The Imaginary part of the image */
	protected RealGrayImage imag;
	/** Cartesian width of the image */
	protected int X;
	/** Cartesian height of the image */
	protected int Y;

	/*
	 * constructors
	 */

	/**
	 * Creates a ComplexImage with height and width of zero and the real and imaginary planes set to
	 * null
	 */
	public ComplexImage() {
		X = 0;
		Y = 0;
		real = null;
		imag = null;
	}

	/** Creates a ComplexImage with height and width of x and y repectively */
	public ComplexImage(int x, int y) {
		X = x;
		Y = y;
		real = new RealGrayImage(X, Y);
		imag = new RealGrayImage(X, Y);
	}

	/**
	 * Creates a ComplexImage as a shallow copy of a complex image. Changing <i>this</i> will also
	 * change <i>img</i>.
	 */
	public ComplexImage(ComplexImage img) {
		X = img.X();
		Y = img.Y();
		real = img.real();
		imag = img.imag();
	}

	/*
	 * Java image conversion
	 */

	/** Creates a ComplexImage from the standard java.awt.Image */
	public ComplexImage(java.awt.Image img) {
		real = new RealGrayImage(img);
		X = real.X();
		Y = imag.Y();
		imag = new RealGrayImage(X, Y);
	}

	/**
	 * Returns the Java Image from this image
	 * 
	 * @see ImageProducer
	 */
	public ImageProducer getJavaImage() {
		return real.getJavaImage();
	}

	/*
	 * image shape parameters
	 */

	/**
	 * Returns the width (maximum x + 1 )
	 * 
	 * @param none
	 */
	public final int X() {
		return X;
	}

	/**
	 * Returns the height (maximum y + 1)
	 * 
	 * @param none
	 */
	public final int Y() {
		return Y;
	}

	/**
	 * Makes a copy of this image with a buffer so the resulting image has a width w and height h
	 * 
	 * @param w width of buffered image
	 * @param h height of buffered image
	 * @param rColor default color to buffer real plane with
	 * @param iColor default color to buffer imaginary plane with
	 * @return a deep copy of ComplexImage
	 */
	public ComplexImage addbuffer(int w, int h, float rColor, float iColor) {
		ComplexImage c = new ComplexImage(w, h);
		c.setReal(real.addbuffer(w, h, rColor));
		c.setImag(imag.addbuffer(w, h, iColor));

		return c;
	}

	/**
	 * Makes a copy of this image with a buffer so the resulting image has a width w and height h
	 * 
	 * @param w width of buffered image
	 * @param h height of buffered image
	 * @param xoff x offset of this image in the buffered image
	 * @param yoff y offset of this image in the buffered image
	 * @param rColor default color to buffer real plane with
	 * @param iColor default color to buffer imaginary plane with
	 * @return a deep copy of ComplexImage
	 */
	public ComplexImage addbuffer(int w, int h, int xoff, int yoff, float rColor, float iColor) {
		ComplexImage c = new ComplexImage(w, h);
		c.setReal(real.addbuffer(w, h, xoff, yoff, rColor));
		c.setImag(imag.addbuffer(w, h, xoff, yoff, iColor));

		return c;
	}

	/*
	 * copiers
	 */

	/**
	 * Makes a deep copy of this image
	 * 
	 * @param none
	 * @return a deep copy of ComplexImage
	 */
	public ComplexImage copy() {
		ComplexImage c = new ComplexImage(X, Y);

		c.real = real.copy();
		c.imag = imag.copy();

		/*		for(int y = 0; y < Y; y++) {
					for(int x = 0; x < X; x++) {
						c.real.set(x,y,real.get(x,y));
						c.imag.set(x,y,imag.get(x,y));
					}
				}*/
		return c;
	}

	/** Returns the real plane of this image */
	public final RealGrayImage real() {
		return real;
	}

	/**
	 * Set the real plane of this image
	 * 
	 * @param pl the RealGrayImage to set the real plane to
	 */
	public final void setReal(RealGrayImage pl) {
		real = pl;
	}
	
	@Deprecated
	public final RealGrayImage imag() {
		return imaginary();
	}

	/** Returns the imaginary plane of this image */
	public final RealGrayImage imaginary() {
		return imag;
	}

	@Deprecated
	public final void setImag(RealGrayImage pl) {
		setImaginary(pl);
	}
	
	/**
	 * Set the real plane of this image
	 * 
	 * @param pl the RealGrayImage to set the real plane to
	 */
	public final void setImaginary(RealGrayImage pl) {
		imag = pl;
	}

	/*
	 * buffer access
	 */

	public Complex get(int x, int y) {
		return new Complex(real.get(x, y), imag.get(x, y));
	}
	
	/**
	 * Returns the pixel value at the given x, y value of the real plane
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 */
	public final float getReal(int x, int y) {
		return real.get(x, y);
	}

	@Deprecated
	public final float getImag(int x, int y) {
		return getImaginary(x, y);
	}
	/**
	 * Returns the pixel value at the given x, y value of the imaginary plane
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 */
	public final float getImaginary(int x, int y) {
		return imag.get(x, y);
	}

	/**
	 * Sets the pixel value at x, y to a given value of the real plane
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param v the value to set the pixel to
	 */
	public final void setReal(int x, int y, float v) {
		real.set(x, y, v);
	}

	@Deprecated
	public final void setImag(int x, int y, float v) {
		setImaginary(x, y, v);
	}
	/**
	 * Sets the pixel value at x, y to a given value of the imaginary
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param v the value to set the pixel to
	 */
	public final void setImaginary(int x, int y, float v) {
		imag.set(x, y, v);
	}

	/**
	 * Sets the pixel value at x, y to a given value of this image
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param r the value to set the pixel to in the real plane
	 * @param i the value to set the pixel to in the imaginary plane
	 */
	public void set(int x, int y, float r, float i) {
		real.set(x, y, r);
		imag.set(x, y, i);
	}
	
	public void set(int x, int y, Complex z) {
		set(x, y, (float) z.real(), (float) z.imag());
	}

	/*
	 * range functions
	 */

	/**
	 * Returns the minimum magnitude of the complex number in this image
	 * 
	 * @param none
	 */
	public final Complex min() {
		Complex p = new Complex();
		Complex min = new Complex(Double.MAX_VALUE, Double.MAX_VALUE);

		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				p.real(getReal(x, y));
				p.imag(getImag(x, y));
				if (p.magnitude() < min.magnitude())
					min = p;
			}
		}
		return min;
	}

	/**
	 * Returns the maximum magnitude of the complex number in this image
	 * 
	 * @param none
	 */
	public final Complex max() {
		Complex p = new Complex();
		Complex max = new Complex(Double.MIN_VALUE, Double.MIN_VALUE);

		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				p.real(getReal(x, y));
				p.imag(getImag(x, y));
				if (p.magnitude() > max.magnitude())
					;
				max = p;
			}
		}
		return max;
	}

	/*
	 * single-pixel arithmetic operations
	 */

	/**
	 * Adds a value to a single pixel
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param r the value to add to the pixel in the real plane
	 * @param i the value to add to the pixel in the imaginary plane
	 */
	public final void add(int x, int y, float r, float i) {
		real.add(x, y, r);
		imag.add(x, y, i);
	}

	/**
	 * Subtracts a value from a single pixel
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param r the value to subtract in the real plane
	 * @param i the value to subtract in the imaginary plane
	 */
	public final void subtract(int x, int y, float r, float i) {
		real.subtract(x, y, r);
		imag.subtract(x, y, i);
	}

	/**
	 * Multiply a single pixel by a value
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param r the value to multiply in the real plane
	 * @param i the value to multiply in the imaginary plane
	 */
	public final void multiply(int x, int y, float r, float i) {
		float a = real.get(x, y) * r - imag.get(x, y) * i;
		float b = real.get(x, y) * i + imag.get(x, y) * r;
		real.set(x, y, a);
		imag.set(x, y, b);
	}

	/**
	 * Divide a single pixel by a value
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param r the value to divide in the real plane
	 * @param i the value to divide in the imaginary plane
	 */
	public final void divide(int x, int y, float r, float i) {
		float a;
		float b;
		float mag2;
		mag2 = r * r + i * i;
		a = (real.get(x, y) * r + imag.get(x, y) * i) / mag2;
		b = (imag.get(x, y) * r - real.get(x, y) * i) / mag2;
		real.set(x, y, a);
		imag.set(x, y, b);
	}

	/**
	 * Adds another ComplexImage to this image
	 * 
	 * @param im the ComplexImage to add
	 * @return this
	 */
	public final void add(ComplexImage im) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				add(x, y, im.getReal(x, y), im.getImag(x, y));
			}
		}
	}

	/**
	 * Subtracts another ComplexImage from this image
	 * 
	 * @param im the ComplexImage to subtract
	 * @return this
	 */
	public final void subtract(ComplexImage im) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				subtract(x, y, im.getReal(x, y), im.getImag(x, y));
			}
		}
	}

	/**
	 * Subtracts a RealColorImage from this image and returns the absolute value
	 * 
	 * @param im the RealColorImage to diff
	 * @return this
	 */
	public final ComplexImage diff(ComplexImage im) {

		real = real.diff(im.real());
		imag = imag.diff(im.imag());
		return this;
	}

	/**
	 * Multiplies this image by another ComplexImage
	 * 
	 * @param im the ComplexImage to multiply
	 * @return this
	 */
	public final void multiply(ComplexImage im) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				multiply(x, y, im.getReal(x, y), im.getImag(x, y));
			}
		}
	}

	/**
	 * Divides this image by another ComplexImage
	 * 
	 * @param im the ComplexImage to divide
	 * @return this
	 */
	public final void divide(ComplexImage im) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				divide(x, y, im.getReal(x, y), im.getImag(x, y));
			}
		}
	}

	/*
	 * image-wide arithmetic operations
	 */

	/**
	 * Adds a value to all the pixels in this image
	 * 
	 * @param r value to be added to the pixels in the real plane
	 * @param i value to be added to the pixels in the imaginary plane
	 * @return this
	 */
	public final ComplexImage add(float r, float i) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				add(x, y, r, i);
			}
		}
		return this;
	}

	/**
	 * Subtracts a value from all the pixels in this image
	 * 
	 * @param r value to be subtract from the pixels in the real plane
	 * @param i value to be subtracted from pixels in the imaginary plane
	 * @return this
	 */
	public final ComplexImage subtract(float r, float i) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				subtract(x, y, r, i);
			}
		}
		return this;
	}

	/**
	 * Multiplies all the pixels in this image by a value
	 * 
	 * @param r value to be multiplied by the pixels in the real plane
	 * @param i value to be multiplied by the pixels in the imaginary plane
	 * @return this
	 */
	public final ComplexImage multiply(float r, float i) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				multiply(x, y, r, i);
			}
		}
		return this;
	}

	/**
	 * Divides all the pixels by a value in this image
	 * 
	 * @param r value to be divided into the pixels in the real plane
	 * @param i value to be divided into the pixels in the imaginary plane
	 * @return this
	 */
	public final ComplexImage divide(float r, float i) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				divide(x, y, r, i);
			}
		}
		return this;
	}

	/**
	 * Prints the image in integer format. <DT>
	 * <DL>
	 * <DL>
	 * -Example of output on an image with width 100 and height 120:</DT>
	 * <DL>
	 * <DT>100 : 120</DT>
	 * <DT>5 23 54 7 3 23 46 253 23 53 65 34 ...</DT>
	 * </DL>
	 * <DL>
	 * <DT>100 : 120</DT>
	 * <DT>10 20 32 12 32 56 40 59 42 39 43 ...</DT>
	 * </DL>
	 * </DL></DL>
	 */
	public String toString() {
		String str;
		str = real.toString();
		str += imag.toString();
		return str;
	}

	/******************************************************************************************************
	 * /*********************************ROI
	 * STUFF************************************************************ /
	 ******************************************************************************************************/

	/**
	 * Makes a deep copy of a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return a deep copy of ComplexImage
	 */
	public ComplexImage copy(ROI roi) {
		ComplexImage c = new ComplexImage(roi.X(), roi.Y());

		c.real = real.copy(roi);
		c.imag = imag.copy(roi);

		/*		for(int y = roi.uy(); y < roi.ly(); y++) {
					for(int x = roi.ux(); x < roi.uy(); x++) {
						c.real.set(x-roi.ux(),y-roi.uy(),real.get(x,y));
						c.imag.set(x-roi.ux(),y-roi.uy(),imag.get(x,y));
					}
				}*/
		return c;
	}

	/**
	 * Returns the pixel value at the given x, y value of a Region of Interest in the real plane
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param roi Region of Interest
	 */
	public final float getReal(int x, int y, ROI roi) {
		return real.get(x + roi.ux(), y + roi.uy());
	}

	@Deprecated
	public final float getImag(int x, int y, ROI roi) {
		return getImaginary(x, y, roi);
	}
	
	/**
	 * Returns the pixel value at the given x, y value of a Region of Interest in the imaginary
	 * plane
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param roi Region of Interest
	 */
	public final float getImaginary(int x, int y, ROI roi) {
		return imag.get(x + roi.ux(), y + roi.uy());
	}

	/**
	 * Sets tthe pixel value at the given x, y value of a Region of Interest in the real plane
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param roi Region of Interest
	 */
	public final void setReal(int x, int y, float v, ROI roi) {
		real.set(x + roi.ux(), y + roi.uy(), v);
	}

	@Deprecated
	public final void setImag(int x, int y, float v, ROI roi) {
		setImaginary(x, y, v, roi);
	}
	
	/**
	 * Sets the pixel value at the given x, y value of a Region of Interest in the imaginary plane
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param roi Region of Interest
	 */
	public final void setImaginary(int x, int y, float v, ROI roi) {
		imag.set(x + roi.ux(), y + roi.uy(), v);
	}

	/**
	 * Sets the pixel value at x, y to a given value in a Region of Interest
	 * 
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param r the value to set the pixel to in the real plane
	 * @param i the value to set the pixel to in the imaginary plane
	 * @param roi Region of Interest
	 */
	public void set(int x, int y, float r, float i, ROI roi) {
		real.set(x, y, r, roi);
		imag.set(x, y, i, roi);
	}
	
	public void set(int x, int y, Complex c, ROI roi) {
		set(x, y, (float) c.real(), (float) c.imag(), roi);
	}

	/*
	 * range functions
	 */

	/**
	 * Returns the minimum magnitude of a Region of Interest
	 * 
	 * @param roi Region of Interest
	 */
	public final Complex min(ROI roi) {
		Complex p = new Complex();
		Complex min = new Complex(Double.MAX_VALUE, Double.MAX_VALUE);

		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.ux(); x++) {
				p.real(getReal(x, y));
				p.imag(getImag(x, y));
				if (p.magnitude() < min.magnitude())
					min = p;
			}
		}
		return min;
	}

	/**
	 * Returns the maximum magnitude of a Region of Interest
	 * 
	 * @param roi Region of Interest
	 */
	public final Complex max(ROI roi) {
		Complex p = new Complex();
		Complex max = new Complex(Double.MIN_VALUE, Double.MIN_VALUE);

		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.ux(); x++) {
				p.real(getReal(x, y));
				p.imag(getImag(x, y));
				if (p.magnitude() > max.magnitude())
					;
				max = p;
			}
		}
		return max;
	}

	/*
	 * single-pixel arithmetic operations
	 */

	/**
	 * Adds a value to a single pixel in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param r the value to add to the pixel in the real plane
	 * @param i the value to add to the pixel in the imaginary plane
	 * @param roi Region of Interest
	 */
	public final void add(int x, int y, float r, float i, ROI roi) {
		real.add(x, y, r, roi);
		imag.add(x, y, i, roi);
	}

	/**
	 * Subtracts a value from a single pixel in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param r the value to add to the pixel in the real plane
	 * @param i the value to add to the pixel in the imaginary plane
	 * @param roi Region of Interest
	 */
	public final void subtract(int x, int y, float r, float i, ROI roi) {
		real.subtract(x, y, r, roi);
		imag.subtract(x, y, i, roi);
	}

	/**
	 * Multiply a single pixel by a value in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param r the value to add to the pixel in the real plane
	 * @param i the value to add to the pixel in the imaginary plane
	 * @param roi Region of Interest
	 */
	public final void multiply(int x, int y, float r, float i, ROI roi) {
		float a = real.get(x, y) * r - imag.get(x, y) * i;
		float b = real.get(x, y) * i + imag.get(x, y) * r;
		real.set(x, y, a, roi);
		imag.set(x, y, b, roi);
	}

	/**
	 * Divide a single pixel by a value in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param r the value to add to the pixel in the real plane
	 * @param i the value to add to the pixel in the imaginary plane
	 * @param roi Region of Interest
	 */
	public final void divide(int x, int y, float r, float i, ROI roi) {
		float a;
		float b;
		float mag2;
		mag2 = r * r + i * i;
		a = (real.get(x, y) * r + imag.get(x, y) * i) / mag2;
		b = (imag.get(x, y) * r - real.get(x, y) * i) / mag2;
		real.set(x, y, a, roi);
		imag.set(x, y, b, roi);
	}

	/*
	 * image-wide arithmetic operations
	 */

	/**
	 * Adds a value to all the pixels in a Region of Interest
	 * 
	 * @param r value to be added to the pixels in the real plane
	 * @param i value to be added to the pixels in the imaginary plane
	 * @param roi Region of Interest
	 * @return this
	 */
	public final ComplexImage add(float r, float i, ROI roi) {
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				add(x, y, r, i);
			}
		}
		return this;
	}

	/**
	 * Subtracts a value from all the pixels in a Region of Interest
	 * 
	 * @param r value to be subtract from the pixels in the real plane
	 * @param i value to be subtracted from pixels in the imaginary plane
	 * @param roi Region of Interest
	 * @return this
	 */
	public final ComplexImage subtract(float r, float i, ROI roi) {
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				subtract(x, y, r, i);
			}
		}
		return this;
	}

	/**
	 * Multiplies all the pixels by a value in a Region of Interest
	 * 
	 * @param r value to be multiplied by the pixels in the real plane
	 * @param i value to be multiplied by the pixels in the imaginary plane
	 * @param roi Region of Interest
	 * @return this
	 */
	public final ComplexImage multiply(float r, float i, ROI roi) {
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				multiply(x, y, r, i);
			}
		}
		return this;
	}

	/**
	 * Divides all the pixels by a value in a Region of Interest
	 * 
	 * @param r value to be divided into the pixels in the real plane
	 * @param i value to be divided into the pixels in the imaginary plane
	 * @param roi Region of Interest
	 * @return this
	 */
	public final ComplexImage divide(float r, float i, ROI roi) {
		for (int y = roi.uy(); y <= roi.ly(); y++) {
			for (int x = roi.ux(); x <= roi.lx(); x++) {
				divide(x, y, r, i);
			}
		}
		return this;
	}

	/**
	 * Prints the Region of Interest in integer format. <DT>
	 * <DL>
	 * <DL>
	 * -Example of output on an image with width 100 and height 120:</DT>
	 * <DL>
	 * <DT>100 : 120</DT>
	 * <DT>5 23 54 7 3 23 46 253 23 53 65 34 ...</DT>
	 * </DL>
	 * <DL>
	 * <DT>100 : 120</DT>
	 * <DT>10 20 32 12 32 56 40 59 42 39 43 ...</DT>
	 * </DL>
	 * </DL></DL>
	 * 
	 * @param roi Region of Interest
	 */
	public String toString(ROI roi) {
		String str;
		str = real.toString(roi);
		str += imag.toString(roi);
		return str;
	}

	/**
	 * Adds a Region of Interest (sourceROI) in another ComplexImage to a Region of Interest
	 * (destROI) of this image
	 * 
	 * @param im the ComplexImage to add
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final void add(ComplexImage im, ROI sourceROI, ROI destROI) {
		for (int y = destROI.uy(); y <= destROI.ly(); y++) {
			for (int x = destROI.ux(); x <= destROI.lx(); x++) {
				add(x, y, im.getReal(x - destROI.ux() + sourceROI.ux(), y - destROI.uy()
						+ sourceROI.uy()), im.getImag(x - destROI.ux() + sourceROI.ux(), y
						- destROI.uy() + sourceROI.uy()));
			}
		}
	}

	/**
	 * Subtracts a Region of Interest (sourceROI) in another ComplexImage from a Region of Interest
	 * (destROI) of this image
	 * 
	 * @param im the ComplexImage to subtract
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final void subtract(ComplexImage im, ROI sourceROI, ROI destROI) {
		for (int y = destROI.uy(); y <= destROI.ly(); y++) {
			for (int x = destROI.ux(); x <= destROI.lx(); x++) {
				subtract(x, y, im.getReal(x - destROI.ux() + sourceROI.ux(), y - destROI.uy()
						+ sourceROI.uy()), im.getImag(x - destROI.ux() + sourceROI.ux(), y
						- destROI.uy() + sourceROI.uy()));
			}
		}
	}

	/**
	 * Multiplies a Region of Interest (sourceROI) of another GrayImage by a Region of Interest
	 * (destROI) of this image
	 * 
	 * @param im the ComplexImage to multiply
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final void multiply(ComplexImage im, ROI sourceROI, ROI destROI) {
		for (int y = destROI.uy(); y <= destROI.ly(); y++) {
			for (int x = destROI.ux(); x <= destROI.lx(); x++) {
				multiply(x, y, im.getReal(x - destROI.ux() + sourceROI.ux(), y - destROI.uy()
						+ sourceROI.uy()), im.getImag(x - destROI.ux() + sourceROI.ux(), y
						- destROI.uy() + sourceROI.uy()));
			}
		}
	}

	/**
	 * Divides by a Region of Interest (sourceROI) in this image by a Region of Interest (destROI)
	 * of another ComplexImage
	 * 
	 * @param im the ComplexImage to divide
	 * @param sourceROI Region of Interest for the Source Image
	 * @param destROI Region of Interest for the Destination Image
	 * @return this
	 */
	public final void divide(ComplexImage im, ROI sourceROI, ROI destROI) {
		for (int y = destROI.uy(); y <= destROI.ly(); y++) {
			for (int x = destROI.ux(); x <= destROI.lx(); x++) {
				divide(x, y, im.getReal(x - destROI.ux() + sourceROI.ux(), y - destROI.uy()
						+ sourceROI.uy()), im.getImag(x - destROI.ux() + sourceROI.ux(), y
						- destROI.uy() + sourceROI.uy()));
			}
		}
	}
	
	public final RealGrayImage phaseImage() {
		return new PhaseImage(this);
	}

	@Deprecated
	public final RealGrayImage getMagnitudeImage() {
		return magnitudeImage();
	}
	
	/** Returns the MagnitudeImage (RealGrayImage) of the this ComplexImage */
	public final RealGrayImage magnitudeImage() {
		return new MagnitudeImage(this);
//		RealGrayImage im = new RealGrayImage(X, Y);
//		float value = 0;
//		for (int x = 0; x < X; x++) {
//			for (int y = 0; y < Y; y++) {
//				value = (float) (Math
//						.sqrt((Math.pow(getReal(x, y), 2) + Math.pow(getImag(x, y), 2))));
//				im.set(x, y, value);
//			}
//		}
//		return im;
	}

	@Override
	public ComplexImage clear() {
		return clear(new Complex(0,0));
	}

	@Override
	public ComplexImage clear(Complex constant) {
		real.clear((float)constant.real());
		imag.clear((float)constant.imag());
		return this;
	}
}
