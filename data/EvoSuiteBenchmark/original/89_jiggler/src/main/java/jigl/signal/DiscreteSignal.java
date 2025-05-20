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
 * A discrete signal is a 1-d of shorts. It is called "discrete" because the values of the signal
 * are not discrete integers.
 */
public class DiscreteSignal implements Signal {

	/** One dimensional integer array */
	protected short[] data;

	/** Length of the Signal */
	protected int length;

	/** Creates an empty one dimensional DiscreteSignal with a height and width of zero */
	public DiscreteSignal() {
		length = 0;
		data = null;
	}

	/** Creates an empty one dimensional of length x */
	public DiscreteSignal(int x) {
		length = x;
		data = new short[length];
	}

	/** Creates a one-dimensional DiscreteSignal (shallow copy) for a DiscreteSignal */
	public DiscreteSignal(DiscreteSignal s) {
		length = s.length();
		data = s.data();
	}

	/** Creates a one-dimension DiscreteSignal object from an array of short integers. */
	public DiscreteSignal(short[] data) {
		length = data.length;
		this.data = (short[]) data.clone();
	}

	/** Converts a DiscreteSignal object into an array of short integers. */
	public short[] getData() {
		return (short[]) data.clone();
	}

	/**
	 * Makes a deep copy of this signal.
	 * 
	 * @param none
	 * @return a deep copy of DiscreteSignal
	 */
	public Signal copy() {
		DiscreteSignal s = new DiscreteSignal(length);

		s.length = length;

		System.arraycopy(data, 0, s.data, 0, length);

		return s;
	}

	/** Returns the length of this signal */
	public final int length() {
		return length;
	}

	/**
	 * Returns a deep copy of a JIGL signal's sample buffer.
	 * 
	 * @param none
	 * @return a deep copy of this signal's buffer
	 */
	public final short[] data() {
		return (short[]) data.clone();
	}

	/**
	 * Returns the sample value at the given x
	 * 
	 * @param x the X coordinate
	 */
	public final int get(int x) {
		return (int) data[x];
	}

	/**
	 * Sets the sample value at x to a given value
	 * 
	 * @param x the X coordinate
	 * @param value the value to set the sample to
	 */
	public final void set(int x, int value) {
		data[x] = (short) value;
	}

	/**
	 * Clears the signal to zero
	 * 
	 * @param none
	 */
	public final DiscreteSignal clear() {
		return clear(0);
	}

	/**
	 * Clears to constant value
	 * 
	 * @param val the value to "clear" the signal to
	 */
	public final DiscreteSignal clear(int val) {
		for (int x = 0; x < length; x++) {
			data[x] = (short) val;
		}
		return this;
	}

	/**
	 * Adds a value to a single sample
	 * 
	 * @param x X-coordinate
	 * @param value the value to add to the sample
	 */
	public final void add(int x, int value) {
		data[x] += (short) value;
	}

	/**
	 * Subtracts a value from a single sample
	 * 
	 * @param x X-coordinate
	 * @param value the value to subtract from the sample
	 */
	public final void subtract(int x, int value) {
		data[x] -= (short) value;
	}

	/**
	 * Mutiplies a single sample by a value
	 * 
	 * @param x X-coordinate
	 * @param value - the value to mutiply to the sample
	 */
	public final void multiply(int x, int value) {
		data[x] *= (short) value;
	}

	/**
	 * Divides a single sample by a value. <code>value</code> should not be 0.
	 * 
	 * @param x X-coordinate
	 * @param value - the value to mutiply to the sample
	 */
	public final void divide(int x, int value) {
		data[x] /= (short) value;
	}

	/**
	 * Finds the minimum value of this signal.
	 * 
	 * @param none
	 * @return an integer containing the minimum value
	 */
	public final int min() {
		short p;
		short min = Short.MAX_VALUE;
		for (int x = 0; x < length; x++) {
			p = data[x];
			if (p < min)
				min = p;
		}
		return (int) min;
	}

	/**
	 * Finds the maximum value of this signal
	 * 
	 * @param none
	 * @return an integer containing the maximum value
	 */
	public final int max() {
		short p;
		short max = Short.MIN_VALUE;
		for (int x = 0; x < length; x++) {
			p = data[x];
			if (p > max)
				max = p;
		}
		return (int) max;
	}

	/**
	 * Adds a value to all the samples in this signal
	 * 
	 * @param v value to be added to the samples
	 * @return this
	 */
	public final DiscreteSignal add(int v) {
		short sv = (short) v;
		for (int x = 0; x < length; x++) {
			data[x] += sv;
		}
		return this;
	}

	/**
	 * Subtracts a value from all the samples in this signal
	 * 
	 * @param v value to be added to the samples
	 * @return this
	 */
	public final DiscreteSignal subtract(int v) {
		short sv = (short) v;
		for (int x = 0; x < length; x++) {
			data[x] -= sv;
		}
		return this;
	}

	/**
	 * Multiplies all the samples in this signal by a value
	 * 
	 * @param v value to be added to the samples
	 * @return this
	 */
	public final DiscreteSignal multiply(int v) {
		short sv = (short) v;
		for (int x = 0; x < length; x++) {
			data[x] *= sv;
		}
		return this;
	}

	/**
	 * Divides all the samples in this signal by a value
	 * 
	 * @param v value to be added to the samples
	 * @return this
	 */
	public final DiscreteSignal divide(int v) {
		short sv = (short) v;
		for (int x = 0; x < length; x++) {
			data[x] /= sv;
		}
		return this;
	}

	/**
	 * Adds another DiscreteSignal to this signal
	 * 
	 * @param s the DiscreteSignal to add
	 * @return this
	 */
	public final DiscreteSignal add(DiscreteSignal s) {
		for (int x = 0; x < length; x++) {
			data[x] += s.get(x);
		}
		return this;
	}

	/**
	 * Makes a copy of this signal with a buffer so the resulting signal has a width w
	 * 
	 * @param w width of buffered signal
	 * @param color default buffer value
	 * @return a deep copy of DiscreteSignal
	 */
	public DiscreteSignal addbuffer(int w, int color) {
		int Y = length;
		DiscreteSignal g = new DiscreteSignal(w);
		for (int y = 0; y < w; y++) {
			if ((y < Y))
				g.data[y] = data[y];
			else
				g.data[y] = (short) color;
		}
		return g;
	}

	/**
	 * Subtracts a DiscreteSignal from this signal. <code>this</code> is modified.
	 * 
	 * @param s the DiscreteSignal to subtract
	 * @return this
	 */
	public final DiscreteSignal subtract(DiscreteSignal s) {
		for (int x = 0; x < length; x++) {
			data[x] -= s.get(x);
		}
		return this;
	}

	/**
	 * Subtracts the second signal from the first and returns the <i>absolute</i> value
	 * 
	 * @param s the DiscreteSignal to subtract
	 * @return this
	 */
	public final DiscreteSignal diff(DiscreteSignal s) {
		for (int x = 0; x < length; x++) {
			data[x] -= s.get(x);
			if (data[x] < 0)
				data[x] = (short) -data[x];
		}
		return this;
	}

	/**
	 * Multiplies a DiscreteSignal by this signal. <code>this</code> is modified.
	 * 
	 * @param s the DiscreteSignal to multiply
	 * @return this
	 */
	public final DiscreteSignal multiply(DiscreteSignal s) {
		for (int x = 0; x < length; x++) {
			data[x] *= s.get(x);
		}
		return this;
	}

	/**
	 * Divides this signal by a DiscreteSignal. <code>this</code> is modified.
	 * 
	 * @param s the DiscreteSignal to divide
	 * @return this
	 */
	public final DiscreteSignal divide(DiscreteSignal s) {
		for (int x = 0; x < length; x++) {
			data[x] /= s.get(x);
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
	 * Scales the range of this signal to byte [0..255]
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
		int value = 0;
		for (int x = 0; x < length; x++) {
			value = (int) ((255.0 / range) * ((double) data[x] - min));
			value = 0x00FF & value;
			data[x] = (short) value;
		}

	}

	/**
	 * Clips the range of this signal to an arbitrary min/max.
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 */
	public final void clip(int min, int max) {
		// clip
		int value = 0;
		for (int x = 0; x < length; x++) {
			value = data[x];
			value = (value > max) ? max : value;
			value = (value < min) ? min : value;
			data[x] = (short) value;
		}
	}

	/**
	 * Performs convolution in place with a kernel signal on this signal. The result signal has the
	 * same length as <code>this</code>.
	 * 
	 * @param kernel kernel to perform the convolution with
	 */
	public void convolve(DiscreteSignal kernel) {

		int Num = kernel.length();
		int mid = Num / 2;
		//if(mid*2==Num)
		//{
		// System.out.println("Warning: The length of kernel is not even. \nThis convolution result is just the approximation of what you want.");
		//}

		double sum = 0;
		short[] value = new short[length];
		float[] kern = SignalConverter.toReal(kernel).data;

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
			value[x] = (short) sum;
		}

		this.data = value;
	}

	/** Utility method: sorting a double array. */
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
	 * Performs median filter on this signal. among values
	 * <code>[data[x-size/2] .. data[x+size/2]]</code> <code>size</code> should be odd.
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
			data[x] = (short) value[count / 2];
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
		DiscreteSignal s = new DiscreteSignal(roi.length());

		System.arraycopy(data, roi.lbound(), s.data, 0, roi.length());

		/*		for(int x = roi.lbound(); x < roi.ubound(); x++) {
					s.data[x-roi.lbound()] = data[x];
				}*/
		return s;
	}

	/**
	 * Returns the sample value at the given x value in a Region of Interest
	 * 
	 * @param x the X coordinate
	 * @param roi Region of Interest
	 */
	public final int get(int x, ROI roi) {
		return (int) data[(x + roi.lbound())];
	}

	/**
	 * Sets the sample value at x to a given value in a Region of Interest
	 * 
	 * @param x the X coordinate
	 * @param value the value to set the sample to
	 * @param roi Region of Interest
	 */
	public final void set(int x, int value, ROI roi) {
		data[(x + roi.lbound())] = (short) value;
	}

	/**
	 * Clears the signal to zero in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 */

	public final DiscreteSignal clear(ROI roi) {
		for (int i = roi.lbound(); i <= roi.ubound(); i++) {
			data[i] = (short) 0;
		}
		return this;
	}

	/**
	 * Clears to constant value in a Region of Interest
	 * 
	 * @param val the value to "clear" the signal to
	 * @param roi Region of Interest
	 */
	public final DiscreteSignal clear(int val, ROI roi) {
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			data[x] = (short) val;
		}
		return this;
	}

	/**
	 * Adds a value to a single sample in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param value the value to add to the sample
	 * @param roi Region of Interest
	 */
	public final void add(int x, int value, ROI roi) {
		data[(x + roi.lbound())] += (short) value;
	}

	/**
	 * Subtracts a value from a single sample in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param value the value to subtract from the sample
	 * @param roi Region of Interest
	 */
	public final void subtract(int x, int value, ROI roi) {
		data[(x + roi.lbound())] -= (short) value;
	}

	/**
	 * Mutiplies a single sample by a value in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param value - the value to mutiply to the sample
	 * @param roi Region of Interest
	 */
	public final void multiply(int x, int value, ROI roi) {
		data[(x + roi.lbound())] *= (short) value;
	}

	/**
	 * Divides a single sample by a value in a Region of Interest
	 * 
	 * @param x X-coordinate
	 * @param value - the value to mutiply to the sample
	 * @param roi Region of Interest
	 */
	public final void divide(int x, int value, ROI roi) {
		data[(x + roi.lbound())] /= (short) value;
	}

	/**
	 * Finds the minimum value of in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return an integer containing the minimum value
	 */
	public final int min(ROI roi) {
		short p;
		short min = Short.MAX_VALUE;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			p = data[x];
			if (p < min)
				min = p;
		}
		return (int) min;
	}

	/**
	 * Finds the maximum value of in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 * @return an integer containing the maximum value
	 */
	public final int max(ROI roi) {
		short p;
		short max = Short.MIN_VALUE;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			p = data[x];
			if (p > max)
				max = p;
		}
		return (int) max;
	}

	/**
	 * Adds a value to all the samples in in a Region of Interest
	 * 
	 * @param v value to be added to the samples
	 * @param roi Region of Interest
	 * @return this
	 */
	public final DiscreteSignal add(int v, ROI roi) {
		short sv = (short) v;
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
	public final DiscreteSignal subtract(int v, ROI roi) {
		short sv = (short) v;
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
	public final DiscreteSignal multiply(int v, ROI roi) {
		short sv = (short) v;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			data[x] *= sv;
		}
		return this;
	}

	/**
	 * Divides all the samples in in a Region of Interest by a value
	 * 
	 * @param v value to be added to the samples
	 * @param roi Region of Interest
	 * @return this
	 */
	public final DiscreteSignal divide(int v, ROI roi) {
		short sv = (short) v;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			data[x] /= sv;
		}
		return this;
	}

	/**
	 * Adds a Region of Interest of another DiscreteSignal to a Region of Interest of this signal
	 * 
	 * @param s the DiscreteSignal to add
	 * @param sourceROI Region of Interest for Source Signal
	 * @param destROI Region of Interest for Destination Signal
	 * @return this
	 */
	public final DiscreteSignal subtract(DiscreteSignal s, ROI sourceROI, ROI destROI) {
		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			data[x] += s.get((x - destROI.lbound() + sourceROI.lbound()));
		}
		return this;
	}

	/**
	 * Subtracts a Region of Interest from another DiscreteSignal from a Region of Interest of this
	 * signal
	 * 
	 * @param s the DiscreteSignal to subtract
	 * @param sourceROI Region of Interest for Source Signal
	 * @param destROI Region of Interest for Destination Signal
	 * @return this
	 */
	public final DiscreteSignal multiply(DiscreteSignal s, ROI sourceROI, ROI destROI) {
		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			data[x] += s.get((x - destROI.lbound() + sourceROI.lbound()));
		}
		return this;
	}

	/**
	 * Multiplies a Region of Interest of another DiscreteSignal to a Region of Interest of this
	 * signal
	 * 
	 * @param s the DiscreteSignal to multiply
	 * @param sourceROI Region of Interest for Source Signal
	 * @param destROI Region of Interest for Destination Signal
	 * @return this
	 */
	public final DiscreteSignal divide(DiscreteSignal s, ROI sourceROI, ROI destROI) {
		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			data[x] += s.get((x - destROI.lbound() + sourceROI.lbound()));
		}
		return this;
	}

	/**
	 * Divides this signal's Region of Interest by a Region of Interest of another DiscreteSignal
	 * 
	 * @param s the DiscreteSignal to divide
	 * @param sourceROI Region of Interest for Source Signal
	 * @param destROI Region of Interest for Destination Signal
	 * @return this
	 */
	public final DiscreteSignal add(DiscreteSignal s, ROI sourceROI, ROI destROI) {
		for (int x = destROI.lbound(); x <= destROI.ubound(); x++) {
			data[x] += s.get((x - destROI.lbound() + sourceROI.lbound()));
		}
		return this;
	}

	/**
	 * Prints the string in integer format in a Region of Interest. <DT>
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
	 * Scales the range of a Region of Interest to byte [0..255] in a Region of Interest
	 * 
	 * @param roi Region of Interest
	 */
	public void byteSize(ROI roi) {

		// get range of this signal
		double min = min(roi);
		double max = max(roi);

		double range = max - min;

		// convert to byte depth
		int value = 0;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = (int) ((255.0 / range) * ((double) data[x] - min));
			value = 0x00FF & value;
			data[x] = (short) value;
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
		int value = 0;
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = data[x];
			value = (value > max) ? max : value;
			value = (value < min) ? min : value;
			data[x] = (short) value;
		}
	}

	/**
	 * Performs convolution in place with a kernel signal on a Region of Interest. The result signal
	 * has the same length as <code>this</code>.
	 * 
	 * @param kernel kernel to perform the convolution with
	 * @param roi Region of Interest
	 */
	public void convolve(DiscreteSignal kernel, ROI roi) {

		int Num = kernel.length();
		int mid = Num / 2;
		double sum = 0;
		short[] value = new short[length];
		float[] kern = SignalConverter.toReal(kernel).data;

		//if(mid*2==Num)
		//{
		//  System.out.println("Warning: The length of kernel is not even. \nThis convolution result is just the approximation of what you want.");
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

		for (int x = 0; x < length; x++)
			value[x] = data[x];

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
			value[x] = (short) sum;
		}

		this.data = value;
	}

	/** Utility method: sorting */
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
		//	System.out.println("Warning:the length of the filter is not odd."+
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
			data[x] = (short) value[count / 2];
		}

	}

}
