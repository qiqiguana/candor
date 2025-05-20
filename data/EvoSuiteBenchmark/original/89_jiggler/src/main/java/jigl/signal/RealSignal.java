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

import jigl.signal.utils.SignalConverter;

/**
 * A RealSignal is implemented by a one-dimension array of floats. RealSignal implements Signal
 * 
 * TODO: switch from float to double?
 * 
 * @see jigl.signal.Signal Signal
 */
public class RealSignal implements Signal {

	/** One dimensional float array */
	protected float[] data;

	/** Length of the Signal */
	protected int length;

	/** Creates an empty RealSignal with a length of 0. */
	public RealSignal() {
		length = 0;
		data = null;
	}

	/**
	 * Creates an empty RealSignal of length <code>x</code>. <code>x</code> must be no less than 0.
	 * If <code>x<0</code>, a <code>java.lang.NegativeArraySizeException</code> will be thrown.
	 */
	public RealSignal(int x) throws NegativeArraySizeException {
		length = x;
		data = new float[length];
	}

	/**
	 * Creates a RealSignal object (deep copy) from another RealSignal object. If <code>s</code> is
	 * null, a <code>NullPointerException</code> will be thrown.
	 */
	public RealSignal(RealSignal s) throws NullPointerException {
		length = s.length();
		data = new float[length];
		for (int i = 0; i < length; i++)
			data[i] = s.data[i];
	}

	/**
	 * Creates a RealSignal from <code>data</code> (deep copy). If <code>data</code> is null, a
	 * <code>NullPointerException</code> will be thrown.
	 */
	public RealSignal(float[] data) throws NullPointerException {
		this.data = data.clone();
		length = data.length;
	}

	/** Make a deep copy of the signal's float array. */
	public float[] getData() {
		return data.clone();
	}

	/**
	 * Makes a deep copy of this signal
	 * 
	 * @param none
	 * @return a deep copy of RealSignal
	 */
	public RealSignal copy() {
		RealSignal s = new RealSignal(length);
		s.length = length;

		s.data = data.clone();
		/*		for(int x = 0; x < length; x++) {
					s.data[x] = data[x];
				}*/
		return s;
	}

	/** Returns the length of this signal */
	public final int length() {
		return length;
	}

	/**
	 * Makes a shallow copy of a JIGL signal's sample buffer
	 * 
	 * @param none
	 * @return a pointer to RealSignal
	 */
	public final float[] data() {
		return data;
	}

	/**
	 * Returns the sample value at the given x. <code>x</code> should be less than
	 * <code>length</code>, otherwise a <code>ArrayIndexOutOfBoundsException</code> object will be
	 * thrown.
	 * 
	 * @param x the X coordinate
	 */
	public final float get(int x) throws ArrayIndexOutOfBoundsException {
		return data[x];
	}

	/**
	 * Sets the sample value at x to a given value. <code>x</code> should be less than
	 * <code>length</code>, otherwise a <code>ArrayIndexOutOfBoundsException</code> will be thrown.
	 * 
	 * @param x the X coordinate
	 * @param value the value to set the sample to
	 */
	public final void set(int x, float value) throws ArrayIndexOutOfBoundsException {
		data[x] = value;
	}

	/**
	 * Clears the signal to zero
	 * 
	 * @param none
	 */
	public final RealSignal clear() {
		clear(0);
		return this;
	}

	/**
	 * Clears the signal to constant value
	 * 
	 * @param val the value to "clear" the signal to.
	 */
	public final RealSignal clear(float val) {
		for (int x = 0; x < length; x++) {
			data[x] = val;
		}
		return this;
	}

	/**
	 * Adds a value to a single sample. If index <code>x<code> is greater than
	<code>this.length()-1<code>, a ArrayIndexOutOfBoundsException will be thrown.
	 * 
	 * @param x X-coordinate
	 * @param value the value to add to the sample
	 */
	public final void add(int x, float value) throws ArrayIndexOutOfBoundsException {
		data[x] += value;
	}

	/**
	 * Subtracts a value from a single sample. If index <code>x<code> is greater than
	<code>this.length()-1<code>, a ArrayIndexOutOfBoundsException will be thrown.
	 * 
	 * @param x X-coordinate
	 * @param value the value to subtract from the sample
	 */
	public final void subtract(int x, float value) throws ArrayIndexOutOfBoundsException {
		data[x] -= (float) value;
	}

	/**
	 * Mutiplies a single sample by a value.If index <code>x<code> is greater than
	<code>this.length()-1<code>, a ArrayIndexOutOfBoundsException will be thrown.
	 * 
	 * @param x X-coordinate
	 * @param value - the value to mutiply to the sample
	 */
	public final void multiply(int x, float value) throws ArrayIndexOutOfBoundsException {
		data[x] *= (float) value;
	}

	/**
	 * Divides a single sample by a value. <code>value</code> can not be 0. If index
	 * <code>x<code> is greater than <code>this.length()-1<code>,
	a ArrayIndexOutOfBoundsException will be thrown.
	 * 
	 * @param x X-coordinate
	 * @param value - the value to mutiply to the sample
	 */
	public final void divide(int x, float value) {
		data[x] /= (float) value;
	}

	/**
	 * Finds the minimum value of this signal
	 * 
	 * @param none
	 * @return an float containing the minimum value
	 */
	public final float min() {
		float p;
		float min = Short.MAX_VALUE;
		for (int x = 0; x < length; x++) {
			p = data[x];
			if (p < min)
				min = p;
		}
		return (float) min;
	}

	/**
	 * Finds the maximum value of this signal
	 * 
	 * @param none
	 * @return an float containing the maximum value
	 */
	public final float max() {
		float p;
		float max = Short.MIN_VALUE;
		for (int x = 0; x < length; x++) {
			p = data[x];
			if (p > max)
				max = p;
		}
		return (float) max;
	}

	/**
	 * Adds a value to all the samples in this signal. <code>this</code> is modified.
	 * 
	 * @param v value to be added to the samples
	 * @return this
	 */
	public final RealSignal add(float v) {
		float sv = (float) v;
		for (int x = 0; x < length; x++) {
			data[x] += sv;
		}
		return this;
	}

	/**
	 * Makes a copy of this signal with a buffer so the resulting signal has a width w
	 * 
	 * @param w width of buffered signal
	 * @param color default buffer color
	 * @return a deep copy of RealSignal
	 */
	public RealSignal addbuffer(int w, int color) {
		int Y = length;
		RealSignal g = new RealSignal(w);
		for (int y = 0; y < w; y++) {
			if ((y < Y))
				g.data[y] = data[y];
			else
				g.data[y] = (short) color;
		}
		return g;
	}

	/**
	 * Subtracts a value from all the samples in this signal. <code>this</code> is modified.
	 * 
	 * @param v value to be added to the samples
	 * @return this
	 */
	public final RealSignal subtract(float v) {
		float sv = (float) v;
		for (int x = 0; x < length; x++) {
			data[x] -= sv;
		}
		return this;
	}

	/**
	 * Multiplies all the samples in this signal by a value. <code>this</code> is modified.
	 * 
	 * @param v value to be added to the samples
	 * @return this
	 */
	public final RealSignal multiply(float v) {
		float sv = (float) v;
		for (int x = 0; x < length; x++) {
			data[x] *= sv;
		}
		return this;
	}

	/**
	 * Divides all the samples in this signal by a value. <code>this</code> is modified.
	 * 
	 * @param v value to be added to the samples
	 * @return this
	 */
	public final RealSignal divide(float v) {
		float sv = (float) v;
		for (int x = 0; x < length; x++) {
			data[x] /= sv;
		}
		return this;
	}

	/**
	 * Adds another RealSignal to this signal. <code>this</code> is modified.
	 * 
	 * @param s the RealSignal to add
	 * @return this
	 */
	public final RealSignal add(RealSignal s) {
		for (int x = 0; x < length; x++) {
			data[x] += s.get(x);
		}
		return this;
	}

	/**
	 * Subtracts a RealSignal from this signal. <code>this</code> is modified.
	 * 
	 * @param s the RealSignal to subtract
	 * @return this
	 */
	public final RealSignal subtract(RealSignal s) {
		for (int x = 0; x < length; x++) {
			data[x] -= s.get(x);
		}
		return this;
	}

	/**
	 * Subtracts the second signal from the first and returns the absolute value.<code>this</code>
	 * is modified.<code>this</code> is modified.
	 */
	public final RealSignal diff(RealSignal s) {
		for (int x = 0; x < length; x++) {
			data[x] -= s.get(x);
			if (data[x] < 0)
				data[x] = (short) -data[x];
		}
		return this;
	}

	/**
	 * Multiplies a RealSignal by this signal. <code>this</code> is modified.
	 * 
	 * @param s the RealSignal to multiply
	 * @return this
	 */
	public final RealSignal multiply(RealSignal s) {
		for (int x = 0; x < length; x++) {
			data[x] *= s.get(x);
		}
		return this;
	}

	/**
	 * Divides this signal by a RealSignal. <code>this</code> is modified.
	 * 
	 * @param s the RealSignal to divide
	 * @return this
	 */
	public final RealSignal divide(RealSignal s) {
		for (int x = 0; x < length; x++) {
			data[x] /= s.get(x);
		}
		return this;
	}

	/**
	 * Prints the string in float format. <DT>
	 * <DL>
	 * <DL>
	 * -Example of output on an signal with width 100 and height 120:</DT>
	 * <DL>
	 * <DT>100 : 120</DT>
	 * <DT>10 20 32 12 32 56 40 59 42 39 43 ...</DT>
	 * </DL>
	 * </DL></DL>
	 */
	public String toString() {
		String str = length + "\n";
		for (int x = 0; x < length; x++) {
			str += data[x] + " ";
		}
		str += "\n";
		return str;
	}

	/**
	 * Scales the range of this signal values to byte [0.0..255.0]. This function is useful when you
	 * want to display a signal with small length but a very broad range of signal values.
	 * 
	 * @param none
	 */
	public void byteSize() {

		// get range of this signal
		double min = min();
		double max = max();

		// keep byte signals in original range

		double range = max - min;

		// convert to byte depth
		float value = 0;
		for (int x = 0; x < length; x++) {
			value = (float) ((255.0 / range) * ((double) data[x] - min));
			//value = 0x00FF & value;
			data[x] = value;
		}

	}

	/**
	 * Clips the range of this signal to an arbitrary min/max
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 */
	public final void clip(int min, int max) {
		// clip
		float value = 0;
		for (int x = 0; x < length; x++) {
			value = data[x];
			value = (value > max) ? max : value;
			value = (value < min) ? min : value;
			data[x] = (float) value;
		}
	}

	/**
	 * Performs convolution in place with a kernel signal on this signal. The result signal has the
	 * same length as <code>this</code>.
	 * 
	 * @param kernel kernel to perform the convolution with
	 */
	public void convolve(RealSignal kernel) {

		int Num = kernel.length();
		int mid = Num / 2;
		double sum = 0;
		float[] value = new float[length];
		float[] kern = SignalConverter.toReal(kernel).data;

		/*if(mid*2==Num)
		{
		   System.out.println("Warning: The length of kernel is not even. \nThis convolution result is just the approximation of what you want.");
		}*/

		// find the sum of all values in the kernel
		for (int x = 0; x < Num; x++) {
			sum += kern[x];
		}

		// if the sum is not zero then normalize by the sum
		if (sum != 0) {
			for (int x = 0; x < Num; x++) {
				kern[x] /= sum;
			}
		}

		// for every sample in the original signal
		for (int x = 0; x < length; x++) {

			//Convolve with the kernel
			sum = 0;
			for (int i = -mid; i <= mid; i++) {
				try {
					sum += data[x + i] * kern[mid + i];
				} catch (Exception e) {
					//ignore out of bounds samples
				}
			}
			value[x] = (float) sum;
		}

		this.data = value;
	}

	/** Utility method: sorting an array. */
	private double[] sort(double vals[], int size) {
		int i, j;
		double temp;

		for (i = 0; i < size; i++) {
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
		}
		return vals;
	}

	/**
	 * Performs median filter on this signal. Subtitute the value at x with the median among values
	 * <code>[data[x-size/2] .. data[x+size/2]]</code>. <code>size</code> should be odd.
	 * 
	 * @param size the size of the median filter
	 */
	public void median(int size) {

		int Num = (size > length) ? length : size;
		int mid = Num / 2;
		double value[] = new double[Num + 1];
		int count;

		/*if(Num%2==0)
		{
			System.out.println("Warning:the length of the filter is not odd."+
			"\nYou only get the approximate result.");
		}*/

		//for every sample in the original signal
		for (int x = 0; x < length; x++) {

			//find median value
			count = 0;
			for (int i = -mid; i <= mid; i++) {
				try {
					value[count++] = data[x + i];
				} catch (Exception e) {
					//ignore out of bounds samples
				}
			}
			value = sort(value, count);
			data[x] = (float) value[count / 2];
		}

	}

	/***********************************************************************************
	 * /************************** ROI Stuff ***************************************** /
	 ***********************************************************************************/

	/**
	 * Makes a deep copy in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return a deep copy of a Region of Interest
	 */
	public Signal copy(ROI roi) {
		RealSignal s = new RealSignal((roi.ubound() - roi.lbound()));

		s.length = length;

		System.arraycopy(data, roi.lbound(), s.data, 0, roi.length());
		/*for(int x = roi.lbound(); x < roi.ubound(); x++) {
			s.data[x-roi.lbound()] = data[x];
		}*/
		return s;
	}

	/**
	 * Returns the sample value at the given x value in a Region of Interest <code>x</code> should
	 * be less than <code>length - roi.lbound()</code>.
	 * 
	 * @param x the X coordinate (starting from <code>roi.lbound()</code>)
	 * @param roi Region of Interest
	 */
	public final float get(int x, ROI roi) {
		return (float) data[(x + roi.lbound())];
	}

	/**
	 * Sets the sample value at x to a given value in a Region of Interest. <code>x</code> should be
	 * less than <code>length - roi.lbound()</code>.
	 * 
	 * @param x the X coordinate (starting from <code>roi.lbound()</code>)
	 * @param value the value to set the sample to
	 * @param roi Region of Interest
	 */
	public final void set(int x, float value, ROI roi) {
		data[(x + roi.lbound())] = (float) value;
	}

	/**
	 * Clears the signal to zero in a Region of Interest. Current Signal object is modified.
	 * 
	 * @param roi Region of Interest
	 */
	public final RealSignal clear(ROI roi) {
		clear(0, roi);
		return this;
	}

	/**
	 * Clears to constant value in a Region of Interest. Current Signal object is modified.
	 * 
	 * @param val the value to "clear" the signal to
	 * @param roi Region of Interest
	 */
	public final RealSignal clear(float val, ROI roi) {
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			data[x] = (float) val;
		}
		return this;
	}

	/**
	 * Adds a value to a single sample in a Region of Interest
	 * 
	 * @param x X-coordinate (starting from <code>roi.lbound()</code>)
	 * @param value the value to add to the sample
	 * @param roi Region of Interest
	 */
	public final void add(int x, float value, ROI roi) {
		data[(x + roi.lbound())] += (float) value;
	}

	/**
	 * Subtracts a value from a single sample in a Region of Interest
	 * 
	 * @param x X-coordinate (starting from <code>roi.lbound()</code>)
	 * @param value the value to subtract from the sample
	 * @param roi Region of Interest
	 */
	public final void subtract(int x, float value, ROI roi) {
		data[(x + roi.lbound())] -= (float) value;
	}

	/**
	 * Mutiplies a single sample by a value in a Region of Interest
	 * 
	 * @param x X-coordinate (starting from <code>roi.lbound()</code>)
	 * @param value - the value to mutiply to the sample
	 * @param roi Region of Interest
	 */
	public final void multiply(int x, float value, ROI roi) {
		data[(x + roi.lbound())] *= (float) value;
	}

	/**
	 * Divides a single sample by a value in a Region of Interest
	 * 
	 * @param x X-coordinate (starting from <code>roi.lbound()</code>)
	 * @param value - the value to mutiply to the sample
	 * @param roi Region of Interest
	 */
	public final void divide(int x, float value, ROI roi) {
		data[(x + roi.lbound())] /= (float) value;
	}

	/**
	 * Finds the minimum value of in a Region of Interest in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return an float containing the minimum value
	 */
	public final float min(ROI roi) {
		float p;
		float min = Short.MAX_VALUE;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			p = data[x];
			if (p < min)
				min = p;
		}
		return (float) min;
	}

	/**
	 * Finds the maximum value of in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return an float containing the maximum value
	 */
	public final float max(ROI roi) {
		float p;
		float max = Short.MIN_VALUE;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			p = data[x];
			if (p > max)
				max = p;
		}
		return (float) max;
	}

	/**
	 * Adds a value to all the samples in in a Region of Interest
	 * 
	 * @param v value to be added to the samples
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealSignal add(float v, ROI roi) {
		float sv = (float) v;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			data[x] += sv;
		}
		return this;
	}

	/**
	 * Subtracts a value from all the samples in in a Region of Interest
	 * 
	 * @param v value to be added to the samples
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealSignal subtract(float v, ROI roi) {
		float sv = (float) v;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			data[x] -= sv;
		}
		return this;
	}

	/**
	 * Multiplies all the samples in in a Region of Interest by a value
	 * 
	 * @param v value to be added to the samples
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealSignal multiply(float v, ROI roi) {
		float sv = (float) v;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			data[x] *= sv;
		}
		return this;
	}

	/**
	 * Divides all the samples in in a Region of Interest by a value <code>v</code> (<code>v</code>
	 * <i>can not be 0</i>).
	 * 
	 * @param v value to be added to the samples
	 * @param roi Region of Interest
	 * @return this
	 */
	public final RealSignal divide(float v, ROI roi) {
		float sv = (float) v;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			data[x] /= sv;
		}
		return this;
	}

	/**
	 * Adds a Region of Interest of another RealSignal to a Region of Interest of this signal
	 * 
	 * @param s the RealSignal to add
	 * @param sourceROI Region of Interest for Source Signal
	 * @param destROI Region of Interest for Destination Signal
	 * @return this
	 */
	public final RealSignal subtract(RealSignal s, ROI sourceROI, ROI destROI) {
		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			data[x] += s.get((x - destROI.lbound() + sourceROI.lbound()));
		}
		return this;
	}

	/**
	 * Subtracts a Region of Interest from another RealSignal from a Region of Interest of this
	 * signal
	 * 
	 * @param s the RealSignal to subtract
	 * @param sourceROI Region of Interest for Source Signal
	 * @param destROI Region of Interest for Destination Signal
	 * @return this
	 */
	public final RealSignal multiply(RealSignal s, ROI sourceROI, ROI destROI) {
		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			data[x] += s.get((x - destROI.lbound() + sourceROI.lbound()));
		}
		return this;
	}

	/**
	 * Multiplies a Region of Interest of another RealSignal to a Region of Interest of this signal
	 * 
	 * @param s the RealSignal to multiply
	 * @param sourceROI Region of Interest for Source Signal
	 * @param destROI Region of Interest for Destination Signal
	 * @return this
	 */
	public final RealSignal divide(RealSignal s, ROI sourceROI, ROI destROI) {
		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			data[x] += s.get((x - destROI.lbound() + sourceROI.lbound()));
		}
		return this;
	}

	/**
	 * Divides this signal's Region of Interest by a Region of Interest of another RealSignal
	 * 
	 * @param s the RealSignal to divide
	 * @param sourceROI Region of Interest for Source Signal
	 * @param destROI Region of Interest for Destination Signal
	 * @return this
	 */
	public final RealSignal add(RealSignal s, ROI sourceROI, ROI destROI) {
		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			data[x] += s.get((x - destROI.lbound() + sourceROI.lbound()));
		}
		return this;
	}

	/**
	 * Prints the string in float format. <DT>
	 * <DL>
	 * <DL>
	 * -Example of output on an signal with length 100:</DT>
	 * <DL>
	 * <DT>100</DT>
	 * <DT>10 20 32 12 32 56 40 59 42 39 43 ...</DT>
	 * </DL>
	 * </DL></DL>
	 * 
	 * @param roi Region of Interest
	 */
	public String toString(ROI roi) {
		String str = length + "\n";
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			str += data[x] + " ";
		}
		str += "\n";
		return str;
	}

	/**
	 * Scales the range of a Region of Interest to byte (0..255)
	 * 
	 * @param roi Region of Interest
	 */
	public void byteSize(ROI roi) {

		// get range of this signal
		double min = min(roi);
		double max = max(roi);

		// keep byte signals in original range

		double range = max - min;

		// convert to byte depth
		float value = 0;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = (float) ((255.0 / range) * ((double) data[x] - min));
			//value = 0x00FF & value;
			data[x] = value;
		}
	}

	/**
	 * Clips the range of in a Region of Interest to an arbitrary min/max
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 * @param roi Region of Interest
	 */
	public final void clip(int min, int max, ROI roi) {
		// clip
		float value = 0;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = data[x];
			value = (value > max) ? max : value;
			value = (value < min) ? min : value;
			data[x] = (float) value;
		}
	}

	/**
	 * Performs convolution in place with a kernel signal on a Region of Interest. The result signal
	 * has the same length as <code>this</code>.
	 * 
	 * @param kernel kernel to perform the convolution with
	 * @param roi Region of Interest
	 */
	public void convolve(RealSignal kernel, ROI roi) {

		int Num = kernel.length();
		int mid = Num / 2;
		double sum = 0;
		float[] value = new float[length];
		float[] kern = SignalConverter.toReal(kernel).data;

		//if(mid*2==Num)
		//{
		//   System.out.println("Warning: The length of kernel is not even. \nThis convolution result is just the approximation of what you want.");
		//}
		// find the sum of all values in the kernel
		for (int x = 0; x < Num; x++) {
			sum += kern[x];
		}

		// if the sum is not zero then normalize by the sum
		if (sum != 0) {
			for (int x = 0; x < Num; x++) {
				kern[x] /= sum;
			}
		}

		// for every sample in the original signal
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {

			//Convolve with the kernel
			sum = 0;
			for (int i = -mid; i <= mid; i++) {
				try {
					sum += data[x + i] * kern[mid + i];
				} catch (Exception e) {
					//ignore out of bounds samples
				}
			}
			value[x] = (float) sum;
		}

		this.data = value;
	}

	/*private double[] sort(double vals[], int size, ROI r){
		int i,j;
		double temp;

		for (i=0;i<size;i++) {
			for (j=0;j<size-1;j++){
				try {
					if (vals[j] > vals[j+1]){
						temp = vals[j];
						vals[j] = vals[j+1];
						vals[j+1] = temp;
					}
				}
				catch(Exception e){
				}
			}
		}
		return vals;
	}*/

	/**
	 * Performs median filter on a Region of Interest. <code>size</code> should be odd.
	 * 
	 * @param size the size of the median filter
	 * @param roi Region of Interest
	 */
	public void median(int size, ROI roi) {

		int Num = (size > length) ? length : size;
		int mid = Num / 2;
		double value[] = new double[Num + 1];
		int count;

		//if(Num%2==0)
		//{
		//		System.out.println("Warning:the length of the filter is not odd."+
		//	"\nYou only get the approximate result.");
		//}
		//for every sample in the original signal
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {

			//find median value
			count = 0;
			for (int i = -mid; i <= mid; i++) {
				try {
					value[count++] = data[x + i];
				} catch (Exception e) {
					//ignore out of bounds samples
				}
			}
			value = sort(value, count);
			data[x] = (float) value[count / 2];
		}

	}
}
