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
import jigl.signal.SimpleOperator;

/** Adds gaussian noise to a signal. */
public class GaussianNoise extends SimpleOperator {
	/** Standard deviation of the Gaussian noise. */
	float m_stdDev;

	/**
	 * Constructs a GaussianNoise object from a value of standard deviation.
	 * 
	 * @param stdDev standard deviation for the noise
	 */
	public GaussianNoise(float stdDev) {
		m_stdDev = stdDev;
	}

	/**
	 * Adds gaussian noise to <code>signal</code>.
	 * 
	 * @param signal DiscreteSignal to add noise to.
	 * @return <code>signal</code>
	 */
	protected Signal apply(DiscreteSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Adds gaussian noise to <code>signal</code> in a Region of Interest.
	 * 
	 * @param signal DiscreteSignal to add noise to.
	 * @param roi Region of Interest of signal.
	 * @return <code>signal</code>
	 */
	protected Signal apply(DiscreteSignal signal, ROI roi) {
		java.util.Random random = new java.util.Random();
		float val = 0;
		float choice = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			choice = random.nextFloat();
			val = (float) random.nextGaussian();
			val = m_stdDev * val;
			if (choice > 0.5)
				signal.add(x, (int) val);
			else
				signal.subtract(x, (int) val);
		}

		return signal;
	}

	/**
	 * Adds gaussian noise to <code>signal</code>.
	 * 
	 * @param signal RealSignal to add noise to.
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Adds gaussian noise to <code>signal</code> in a Region of Interest.
	 * 
	 * @param signal RealSignal to add noise to.
	 * @param roi Region of Interest of signal.
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal, ROI roi) {
		java.util.Random random = new java.util.Random();
		float val = 0;
		float choice = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			choice = random.nextFloat();
			val = (float) random.nextGaussian();
			val = m_stdDev * val;
			if (choice > 0.5)
				signal.add(x, val);
			else
				signal.subtract(x, val);
		}

		return signal;
	}
}
