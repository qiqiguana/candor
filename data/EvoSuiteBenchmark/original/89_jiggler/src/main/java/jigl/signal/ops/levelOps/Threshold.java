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
import jigl.signal.io.SignalInputStream;
import jigl.signal.io.SignalOutputStream;

/**
 * Performs a Threshold operation on a signal. Supports DiscreteSignal, RealSignal.
 * <p>
 * Provides command line option.
 */
public class Threshold extends SimpleOperator {
	/** The threshold value */
	private int int_max;
	/** The threshold value */
	private float float_max;

	/** Initilizes Threshold for use with integers */
	public Threshold(int max) {
		int_max = max;
	}

	/** Initilizes Threshold for use with floats */
	public Threshold(float max) {
		float_max = max;
	}

	/**
	 * Thresholds the range of <code>signal</code> to {0, 255}.
	 * 
	 * @param signal DiscreteSignal
	 * @return <code>signal</code>
	 */
	protected Signal apply(DiscreteSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Thresholds the range of <code>signal</code> to {0, 255} in a region of interest.
	 * 
	 * @param signal DiscreteSignal
	 * @param roi Region of Interest of signal
	 * @return <code>signal</code>
	 */
	protected Signal apply(DiscreteSignal signal, ROI roi) {
		if (int_max == 0) {
			int_max = (int) int_max;
		}
		int value = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = signal.get(x);
			if (value >= int_max)
				value = 255;
			else
				value = 0;
			signal.set(x, (short) value);
		}

		return signal;
	}

	/**
	 * Thresholds the range of <code>signal</code> to {0.0, 255.0}.
	 * 
	 * @param signal RealSignal
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Thresholds the range of <code>signal</code> to {0.0, 255.0} in a region of interest.
	 * 
	 * @param signal RealSignal
	 * @param roi Region of Interest of signal
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal, ROI roi) {
		if (float_max == 0) {
			float_max = (float) int_max;
		}
		float value = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = signal.get(x);
			if (value >= float_max)
				value = 255f;
			else
				value = 0f;
			signal.set(x, value);
		}

		return signal;
	}

	/**
	 * For command line option. The systax is "java Threshold <u>min_value</u> <u>max_value</u>
	 * <u>input_filename</u> <u>output_filename</u>".
	 */
	public static void main(String[] argv) {

		try {
			Signal image = null;
			String inputfile = argv[1];
			Signal image2 = null;

			SignalInputStream is = new SignalInputStream(inputfile);
			image = is.read();
			is.close();

			Float f_val1 = Float.valueOf(argv[0]);
			float val1 = f_val1.floatValue();

			Threshold threshold = new Threshold(val1);

			image2 = threshold.apply(image);

			//put command line stuff here.

			// create a new SignalOutputStream
			SignalOutputStream os = new SignalOutputStream(argv[2]);
			os.write(image2);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
