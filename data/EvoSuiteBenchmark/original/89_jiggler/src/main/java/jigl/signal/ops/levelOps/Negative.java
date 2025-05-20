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
package jigl.signal.ops.levelOps;

import jigl.signal.DiscreteSignal;
import jigl.signal.ROI;
import jigl.signal.RealSignal;
import jigl.signal.Signal;
import jigl.signal.SimpleOperator;

/**
 * Performs a Negative operation on a signal. Assumes that all data is in the range of [0..255].
 * Supports DiscreteSignal, RealSignal.
 * 
 * <p>
 * Please note the difference from {@link jigl.signal.ops.levelOps.Negate}. If the value at a given
 * position is 3, using Negative operation you get 252 (255-3) while using Negate operation you get
 * -3.
 */
public class Negative extends SimpleOperator {
	/** Default constructor */
	public Negative() {
	}

	/**
	 * Subtracts the value at each pixel in <code>signal</code> from 255.
	 * 
	 * @param signal DiscreteSignal
	 * @return <code>signal</code>
	 */
	protected Signal apply(DiscreteSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Subtracts the value at each pixel in <code>signal</code> from 255 in a region of interest.
	 * 
	 * @param signal DiscreteSignal
	 * @param roi Region of Interest of signal
	 * @return <code>signal</code>
	 */
	protected Signal apply(DiscreteSignal signal, ROI roi) {
		int value = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = signal.get(x);
			value = 255 - value;
			signal.set(x, (short) value);
		}

		return signal;
	}

	/**
	 * Subtracts the value at each pixel in <code>signal</code> from 255.
	 * 
	 * @param signal RealSignal
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Subtracts the value at each pixel in <code>signal</code> from 255 in a region of interest.
	 * 
	 * @param signal RealSignal
	 * @param roi Region of Interest of signal
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal, ROI roi) {
		float value = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = signal.get(x);
			value = 255 - value;
			signal.set(x, value);
		}

		return signal;
	}
}
