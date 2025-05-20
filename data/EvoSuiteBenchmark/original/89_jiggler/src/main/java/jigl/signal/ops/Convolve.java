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
package jigl.signal.ops;

import jigl.signal.DiscreteSignal;
import jigl.signal.ROI;
import jigl.signal.RealSignal;
import jigl.signal.Signal;
import jigl.signal.SignalKernel;
import jigl.signal.SimpleOperator;

/**
 * Performs convolution on a signal with a supplied SignalKernel. It is always a good idea to
 * normalize the kernel before convolution.
 * <p>
 * Supports DiscreteSignal, RealSignal.
 */
public class Convolve extends SimpleOperator {
	/** Convolution kernel. */
	private SignalKernel kernel = null;

	/**
	 * Constructs a Convolve object from a Signalkerne object <code>k</code> (You'd better normalize
	 * <code>k</code>).
	 * 
	 * @param k kernel to be used with the convolve operation.
	 */
	public Convolve(SignalKernel k) {
		kernel = k;
	}

	/**
	 * Performs a convolution on a DiscreteSignal. The center of the kernel is specified at position
	 * <code>kernel.length()/2</code>. Returned signal is a DiscreteSignal with the same length as
	 * <code>signal</code>. ( <code>signal</code> is not modified).
	 * 
	 * @param signal DiscreteSignal to convolve
	 * @return DiscreteSignal
	 */
	protected Signal apply(DiscreteSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Performs a convolve on a DiscreteSignal in a region of interest. The center of the kernel is
	 * specified at position <code>kernel.length()/2</code>. Returned signal is a DiscreteSignal
	 * with the same length as <code>signal</code>. (<code>signal</code> is not modified).
	 * 
	 * @param signal DiscreteSignal to convolve
	 * @param roi Region of Interest of signal
	 * @return DiscreteSignal
	 */
	protected Signal apply(DiscreteSignal signal, ROI roi) {
		DiscreteSignal sig = new DiscreteSignal(signal.length());
		int NumX = kernel.length();

		int midX = NumX / 2;
		// if(NumX%2==0)
		// System.out.println("Warning: the length of kernel is not odd."+
		// "\nYou only get the approximate result.");

		double sum = 0;
		// double sum2= 0;
		// int count=0;

		for (int x = 0; x < signal.length(); x++)
			if ((x < roi.lbound()) || (x > roi.ubound()))
				sig.set(x, signal.get(x));

		// for every pixel in the original signal
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {

			// Convolve with the kernel
			sum = 0;

			for (int i = -midX; i <= midX; i++)
				if ((x + i >= 0) && (x + i < signal.length())) {
					try {
						sum += signal.get(x + i) * kernel.get(midX + i);
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}

			sig.set(x, (int) sum);
		}
		return sig;
	}

	/**
	 * Performs a convolution on a RealSignal. The center of the kernel is specified at position
	 * <code>kernel.length()/2</code>. Returned signal is a RealSignal with the same length as
	 * <code>signal</code>. ( <code>signal</code> is not modified).
	 * 
	 * @param signal RealSignal to convolve
	 * @return RealSignal
	 */
	protected Signal apply(RealSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Performs a convolve on a RealSignal in a region of interest. The center of the kernel is
	 * specified at position <code>kernel.length()/2</code>. Returned signal is a RealSignal with
	 * the same length as <code>signal</code>. (<code>signal</code> is not modified).
	 * 
	 * @param signal RealSignal to convolve
	 * @param roi Region of Interest of signal
	 * @return RealSignal
	 */
	protected Signal apply(RealSignal signal, ROI roi) {

		RealSignal sig = new RealSignal(signal.length());
		int NumX = kernel.length();

		int midX = NumX / 2;
		// if(NumX%2==0)
		// System.out.println("Warning: the length of kernel is not odd."+
		// "\nYou only get the approximate result.");

		double sum = 0;
		// double sum2= 0;
		// int count=0;

		for (int x = 0; x < signal.length(); x++)
			if ((x < roi.lbound()) || (x > roi.ubound()))
				sig.set(x, signal.get(x));

		// for every pixel in the original signal
		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			// Convolve with the kernel
			sum = 0;

			for (int i = -midX; i <= midX; i++)
				if ((x + i >= 0) && (x + i < signal.length())) {
					try {
						sum += signal.get(x + i) * kernel.get(midX + i);
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
			sig.set(x, (float) sum);
		}
		return sig;
	}

	/**
	 * Performs a convolution on a RealSignal and returns a longer signal. Returned signal is a
	 * RealSignal with the length specified by <code>signal.length() + kernel.length() - 1</code>. (
	 * <code>signal</code> is not modified).
	 * 
	 * @param signal RealSignal to convolve
	 * @return RealSignal
	 */
	public RealSignal apply_long(RealSignal signal) {
		int signalSize = signal.length();
		int kernelSize = kernel.length();
		RealSignal result = new RealSignal(signalSize + kernelSize - 1);
		for (int i = 0; i < signalSize + kernelSize - 1; i++) {
			int temp = 0;

			int start;
			if (i - kernelSize + 1 < 0)
				start = 0;
			else
				start = i - kernelSize + 1;

			for (int j = start; (j < signalSize) && (j <= i); j++) {
				// System.out.println("i="+i+",j="+j);
				temp += signal.get(j) * kernel.get(i - j);
			}
			result.set(i, temp);
		}
		return result;
	}

}
