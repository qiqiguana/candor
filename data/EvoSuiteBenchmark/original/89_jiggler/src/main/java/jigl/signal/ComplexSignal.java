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
package jigl.signal;

import jigl.math.Complex;

/**
 * A complex signal is a set of two RealSignal planes: real plane and imaginary plane.
 */
public class ComplexSignal implements Signal {

	/** The Real plane of the signal */
	protected RealSignal real;
	/** The Imaginary part of the signal */
	protected RealSignal imag;
	/** Cartesian length of the signal */
	protected int length;

	/*
	 * constructors
	 */

	/**
	 * Creates a ComplexSignal with length of zero and the real and imaginary planes set to null
	 */
	public ComplexSignal() {
		length = 0;
		real = null;
		imag = null;
	}

	/** Creates a ComplexSignal with length of x */
	public ComplexSignal(int x) {
		length = x;
		real = new RealSignal(length);
		imag = new RealSignal(length);
	}

	/** Creates a ComplexSignal as a shallow copy of a complex signal */
	public ComplexSignal(ComplexSignal s) {
		length = s.length();
		real = s.real();
		imag = s.imaginary();
	}

	/**
	 * Returns the length of the signal
	 * 
	 * @param none
	 */
	public final int length() {
		return length;
	}

	/*
	 * copiers
	 */

	/** Makes a shallow copy of the real plane */
	public final RealSignal real() {
		return real;
	}

	/** @see ComplexSignal#imaginary() */
	@Deprecated
	public final RealSignal imag() {
		return imaginary();
	}
	
	/** Makes a shallow copy of the imaginary plane */
	public final RealSignal imaginary() {
		return imag;
	}

	/**
	 * Makes a deep copy of this signal
	 * 
	 * @param none
	 * @return a deep copy of ComplexSignal
	 */
	public ComplexSignal copy() {
		ComplexSignal c = new ComplexSignal(length);

		c.real = real.copy();
		c.imag = imag.copy();

		/*for(int x = 0; x < length; x++) {
			c.real.set(x,real.get(x));
			c.imag.set(x,imag.get(x));
		}*/

		return c;
	}

	/**
	 * Set the real plane of this signal
	 * 
	 * @param pl the RealSignal to set the real plane to
	 */
	public final void setReal(RealSignal pl) {
		real = pl;
	}

	/**
	 * Set the imaginary plane of this signal
	 * 
	 * @param pl the RealSignal to set the imaginary plane to
	 */
	public final void setImag(RealSignal pl) {
		imag = pl;
	}

	/*
	 * buffer access
	 */

	/**
	 * Returns the pixel value at the given x value of the real plane
	 * 
	 * @param x the length coordinate
	 */
	public final float getReal(int x) {
		return real.get(x);
	}

	/**
	 * Returns the pixel value at the given x value of the imaginary plane
	 * 
	 * @param x the length coordinate
	 */
	public final float getImag(int x) {
		return imag.get(x);
	}

	/**
	 * Sets the pixel value at x to a given value of the real plane
	 * 
	 * @param x the length coordinate
	 * @param v the value to set the pixel to
	 */
	public final void setReal(int x, float v) {
		real.set(x, v);
	}

	/**
	 * Sets the pixel value at x to a given value of the imaginary
	 * 
	 * @param x the length coordinate
	 * @param v the value to set the pixel to
	 */
	public final void setImag(int x, float v) {
		imag.set(x, v);
	}

	/**
	 * Sets the pixel value at x to a given value of this signal
	 * 
	 * @param x the length coordinate
	 * @param r the value to set the pixel to in the real plane
	 * @param i the value to set the pixel to in the imaginary plane
	 */
	public final void set(int x, float r, float i) {
		real.set(x, r);
		imag.set(x, i);
	}

	/*
	 * range functions
	 */

	/**
	 * Returns the minimum magnitude in this signal
	 * 
	 * @param none
	 */
	public final Complex min() {
		Complex p = new Complex();
		Complex min = new Complex(Double.MAX_VALUE, Double.MAX_VALUE);

		for (int x = 0; x < length; x++) {
			p.setImaginary(getImag(x));
			p.setReal(getReal(x));
			if (p.magnitude() < min.magnitude())
				min = p;
		}
		return min;
	}

	/**
	 * Returns the maximum magnitude in this signal
	 * 
	 * @param none
	 */
	public final Complex max() {
		Complex p = new Complex();
		Complex max = new Complex(0, 0);

		for (int x = 0; x < length; x++) {
			p.setImaginary(getImag(x));
			p.setReal(getReal(x));
			if (p.magnitude() > max.magnitude())
				max = p;
		}

		return max;
	}

	/*
	 * single-pixel arithmetic operations
	 */

	/**
	 * Adds a value to a single pixel
	 * 
	 * @param x length-coordinate
	 * @param r the value to add to the pixel in the real plane
	 * @param i the value to add to the pixel in the imaginary plane
	 */
	public final void add(int x, float r, float i) {
		real.add(x, r);
		imag.add(x, i);
	}

	/**
	 * Subtracts a value from a single pixel
	 * 
	 * @param x length-coordinate
	 * @param r the value to subtract to the pixel in the real plane
	 * @param i the value to subtract to the pixel in the imaginary plane
	 */
	public final void subtract(int x, float r, float i) {
		real.subtract(x, r);
		imag.subtract(x, i);
	}

	/**
	 * Multiply a single pixel by a Complex number (r,i)
	 * 
	 * @param x length-coordinate
	 * @param r the value to multiply to the pixel in the real plane
	 * @param i the value to multiply to the pixel in the imaginary plane
	 */
	public final void multiply(int x, float r, float i) {
		float a = real.get(x) * r - imag.get(x) * i;
		float b = real.get(x) * i + imag.get(x) * r;
		real.set(x, a);
		imag.set(x, b);
	}

	/**
	 * Divide a single pixel by a Complex number (r,i)
	 * 
	 * @param x length-coordinate
	 * @param r the value to divide to the pixel in the real plane
	 * @param i the value to divide to the pixel in the imaginary plane
	 */
	public final void divide(int x, float r, float i) {
		float a;
		float b;
		float mag2;
		mag2 = r * r + i * i;
		a = (real.get(x) * r + imag.get(x) * i) / mag2;
		b = (imag.get(x) * r - real.get(x) * i) / mag2;

		real.set(x, a);
		//System.out.println(x+"  "+a);
		imag.set(x, b);
	}

	/**
	 * Adds another ComplexSignal to this signal
	 * 
	 * @param s the ComplexSignal to add
	 * @return this
	 */
	public final void add(ComplexSignal s) {

		for (int x = 0; x < length; x++) {
			add(x, s.getReal(x), s.getImag(x));
		}

	}

	/**
	 * Subtracts another ComplexSignal from this signal.
	 * 
	 * @param s the ComplexSignal to subtract
	 * @return this
	 */
	public final void subtract(ComplexSignal s) {

		for (int x = 0; x < length; x++) {
			subtract(x, s.getReal(x), s.getImag(x));
		}

	}

	/** Subtracts the second signal from the first and returns the absolute value */
	public final ComplexSignal diff(ComplexSignal s) {
		real.diff(s.real());
		imag.diff(s.imag());

		return this;
	}

	/**
	 * Multiplies this signal by another ComplexSignal
	 * 
	 * @param s the ComplexSignal to multiply
	 * @return this
	 */
	public final void multiply(ComplexSignal s) {

		for (int x = 0; x < length; x++) {
			multiply(x, s.getReal(x), s.getImag(x));
		}

	}

	/**
	 * Divides this signal by another ComplexSignal
	 * 
	 * @param s the ComplexSignal to divide
	 * @return this
	 */
	public final ComplexSignal divide(ComplexSignal s) {
		for (int x = 0; x < length; x++) {
			divide(x, s.getReal(x), s.getImag(x));
		}
		return this;
	}

	/*
	 * signal-wide arithmetic operations
	 */

	/**
	 * Adds a value to all the pixels in this signal. <code>this<code> is changed.
	 * 
	 * @param r value to be added to the pixels in the real plane
	 * @param i value to be added to the pixels in the imaginary plane
	 * @return this
	 */
	public final ComplexSignal add(float r, float i) {

		for (int x = 0; x < length; x++) {
			add(x, r, i);
		}

		return this;
	}

	/**
	 * Subtracts a value from all the pixels in this signal. <code>this<code> is changed.
	 * 
	 * @param r value to be subtract from the pixels in the real plane
	 * @param i value to be subtracted from pixels in the imaginary plane
	 * @return this
	 */
	public final ComplexSignal subtract(float r, float i) {

		for (int x = 0; x < length; x++) {
			subtract(x, r, i);
		}

		return this;
	}

	/**
	 * Multiplies all the pixels in this signal by a value. <code>this<code> is changed.
	 * 
	 * @param r value to be multiplied by the pixels in the real plane
	 * @param i value to be multiplied by the pixels in the imaginary plane
	 * @return this
	 */
	public final ComplexSignal multiply(float r, float i) {

		for (int x = 0; x < length; x++) {
			multiply(x, r, i);
		}

		return this;
	}

	/**
	 * Divides all the pixels by a value in this signal. <code>this<code> is changed.
	 * 
	 * @param r value to be divided into the pixels in the real plane
	 * @param i value to be divided into the pixels in the imaginary plane
	 * @return this
	 */
	public final ComplexSignal divide(float r, float i) {

		for (int x = 0; x < length; x++) {
			divide(x, r, i);
		}

		return this;
	}

	/**
	 * Prints the string in integer format. The first half is for real plane, the second part is for
	 * image plane. <DT>
	 * <DL>
	 * <DL>
	 * -Example of output on an signal with width 100 and height 120:</DT>
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

	/**
	 * Makes a deep copy of a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return a deep copy of ComplexSignal
	 */
	public Signal copy(ROI roi) {
		ComplexSignal c = new ComplexSignal(roi.length());

		c.real = (RealSignal) real.copy(roi);
		c.imag = (RealSignal) imag.copy(roi);

		return c;
	}

	/**
	 * Returns the pixel value at the given x value of a Region of Interest in the real plane
	 * 
	 * @param x the length coordinate (starting from <code>roi.lbound()</code>)
	 * @param roi Region of Interest
	 */
	public final float getReal(int x, ROI roi) {
		return real.get(x + roi.lbound());
	}

	/**
	 * Returns the pixel value at the given x value of a Region of Interest in the imaginary plane
	 * 
	 * @param x the length coordinate (starting from <code>roi.lbound()</code>)
	 * @param roi Region of Interest
	 */
	public final float getImag(int x, ROI roi) {
		return imag.get(x + roi.lbound());
	}

	/**
	 * Sets tthe pixel value at the given x value of a Region of Interest in the real plane
	 * 
	 * @param x the length coordinate (starting from <code>roi.lbound()</code>)
	 * @param v the value to set
	 * @param roi Region of Interest
	 */
	public final void setReal(int x, float v, ROI roi) {
		real.set(x + roi.lbound(), v);
	}

	/**
	 * Sets the pixel value at the given x value of a Region of Interest in the imaginary plane
	 * 
	 * @param x the length coordinate (starting from <code>roi.lbound()</code>)
	 * @param v the value to set
	 * @param roi Region of Interest
	 */
	public final void setImag(int x, float v, ROI roi) {
		imag.set(x + roi.lbound(), v);
	}

	/**
	 * Sets the pixel value at x to a given value in a Region of Interest
	 * 
	 * @param x the length coordinate (starting from <code>roi.lbound()</code>)
	 * @param r the value to set the pixel to in the real plane
	 * @param i the value to set the pixel to in the imaginary plane
	 * @param roi Region of Interest
	 */
	public final void set(int x, float r, float i, ROI roi) {
		real.set(x, r, roi);
		imag.set(x, i, roi);
	}

	/*
	 * range functions
	 */

	/**
	 * Returns the minimum magnitude in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 */
	public final Complex min(ROI roi) {
		Complex p = new Complex();
		Complex min = new Complex(Double.MAX_VALUE, Double.MAX_VALUE);

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			p.setImaginary(getImag(x));
			p.setReal(getReal(x));
			if (p.magnitude() < min.magnitude())
				min = p;
		}

		return min;
	}

	/**
	 * Returns the maximum magnitude in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 */
	public final Complex max(ROI roi) {
		Complex p = new Complex();
		Complex max = new Complex(0, 0);

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			p.setImaginary(getImag(x));
			p.setReal(getReal(x));
			if (p.magnitude() > max.magnitude())
				max = p;
		}

		return max;
	}

	/*
	 * single-pixel arithmetic operations
	 */

	/**
	 * Adds a value to a single pixel in a Region of Interest
	 * 
	 * @param x length-coordinate (starting from <code>roi.lbound()</code>)
	 * @param r the value to add to the pixel in the real plane
	 * @param i the value to add to the pixel in the imaginary plane
	 * @param roi Region of Interest
	 */
	public final void add(int x, float r, float i, ROI roi) {
		real.add(x, r, roi);
		imag.add(x, i, roi);
	}

	/**
	 * Subtracts a value from a single pixel in a Region of Interest
	 * 
	 * @param x length-coordinate (starting from <code>roi.lbound()</code>)
	 * @param r the value to add to the pixel in the real plane
	 * @param i the value to add to the pixel in the imaginary plane
	 * @param roi Region of Interest
	 */
	public final void subtract(int x, float r, float i, ROI roi) {
		real.subtract(x, r, roi);
		imag.subtract(x, i, roi);
	}

	/**
	 * Multiply a single pixel by a value in a Region of Interest
	 * 
	 * @param x length-coordinate (starting from <code>roi.lbound()</code>)
	 * @param r the value to add to the pixel in the real plane
	 * @param i the value to add to the pixel in the imaginary plane
	 * @param roi Region of Interest
	 */
	public final void multiply(int x, float r, float i, ROI roi) {
		float a = real.get(x) * r - imag.get(x) * i;
		float b = real.get(x) * i + imag.get(x) * r;
		real.set(x, a, roi);
		imag.set(x, b, roi);
	}

	/**
	 * Divide a single pixel by a value in a Region of Interest
	 * 
	 * @param x length-coordinate (starting from <code>roi.lbound()</code>)
	 * @param r the value to add to the pixel in the real plane
	 * @param i the value to add to the pixel in the imaginary plane
	 * @param roi Region of Interest
	 */
	public final void divide(int x, float r, float i, ROI roi) {
		float a;
		float b;
		float mag2;
		mag2 = r * r + i * i;
		a = (real.get(x) * r + imag.get(x) * i) / mag2;
		b = (imag.get(x) * r - real.get(x) * i) / mag2;
		real.set(x, a, roi);
		imag.set(x, b, roi);
	}

	/*
	 * signal-wide arithmetic operations
	 */

	/**
	 * Adds a value to all the pixels in a Region of Interest
	 * 
	 * @param r value to be added to the pixels in the real plane
	 * @param i value to be added to the pixels in the imaginary plane
	 * @param roi Region of Interest
	 * @return this
	 */
	public final ComplexSignal add(float r, float i, ROI roi) {

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			add(x, r, i);
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
	public final ComplexSignal subtract(float r, float i, ROI roi) {

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			subtract(x, r, i);
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
	public final ComplexSignal multiply(float r, float i, ROI roi) {

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			multiply(x, r, i);
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
	public final ComplexSignal divide(float r, float i, ROI roi) {

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			divide(x, r, i);
		}

		return this;
	}

	/**
	 * Prints the string in integer format. <DT>
	 * <DL>
	 * <DL>
	 * -Example of output on an signal with width 100 and height 120:</DT>
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
	 * Adds a Region of Interest in another GraySignal from a Region of Interest of this signal
	 * 
	 * @param s the ComplexSignal to add
	 * @param sourceROI Region of Interest for the Source Signal
	 * @param destROI Region of Interest for the Destination Signal
	 * @return this
	 */
	public final void add(ComplexSignal s, ROI sourceROI, ROI destROI) {

		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			add(x, s.getReal(x - destROI.lbound() + sourceROI.lbound()), s.getImag(x
					- destROI.lbound() + sourceROI.lbound()));
		}

	}

	/**
	 * Subtracts a Region of Interest in another GraySignal from a Region of Interest of this signal
	 * 
	 * @param s the ComplexSignal to subtract
	 * @param sourceROI Region of Interest for the Source Signal
	 * @param destROI Region of Interest for the Destination Signal
	 * @return this
	 */
	public final void subtract(ComplexSignal s, ROI sourceROI, ROI destROI) {

		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			subtract(x, s.getReal(x - destROI.lbound() + sourceROI.lbound()), s.getImag(x
					- destROI.lbound() + sourceROI.lbound()));
		}

	}

	/**
	 * Multiplies a Region of Interest of another GraySignal by a Region of Interest of this signal
	 * 
	 * @param s the ComplexSignal to multiply
	 * @param sourceROI Region of Interest for the Source Signal
	 * @param destROI Region of Interest for the Destination Signal
	 * @return this
	 */
	public final void multiply(ComplexSignal s, ROI sourceROI, ROI destROI) {

		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			multiply(x, s.getReal(x - destROI.lbound() + sourceROI.lbound()), s.getImag(x
					- destROI.lbound() + sourceROI.lbound()));
		}

	}

	/**
	 * Divides by a Region of Interest in this signal by a Region of Interest of another
	 * ComplexSignal
	 * 
	 * @param s the ComplexSignal to divide
	 * @param sourceROI Region of Interest for the Source Signal
	 * @param destROI Region of Interest for the Destination Signal
	 * @return this
	 */
	public final void divide(ComplexSignal s, ROI sourceROI, ROI destROI) {

		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			divide(x, s.getReal(x - destROI.lbound() + sourceROI.lbound()), s.getImag(x
					- destROI.lbound() + sourceROI.lbound()));
		}

	}
	
	public RealSignal phase() {
		return new PhaseSignal(this);
	}
	
	public RealSignal magnitude() {
		return new MagnitudeSignal(this);
	}
	
	public RealSignal power() {
		return new PowerSignal(this);
	}
}
