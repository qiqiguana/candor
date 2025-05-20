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
 * Performs a clip operation on a signal to an arbitrary min/max. Supports DiscreteSignal,
 * RealSignal.
 * <p>
 * Provides command line option.
 */
public class Clip extends SimpleOperator {
	/** Lower bound of clip (for DiscreteSignal) */

	private int int_min;
	/** Upper bound of clip (for DiscreteSignal) */
	private int int_max;
	/** Lower bound of clip (for RealSignal) */
	private float float_min;
	/** Upper bound of clip (for RealSignal) */
	private float float_max;

	/** Initilizes a Clip object for use with integers */
	public Clip(int min, int max) {
		int_min = min;
		int_max = max;
	}

	/** Initilizes a Clip object for use with floats */
	public Clip(float min, float max) {
		float_min = min;
		float_max = max;
	}

	/**
	 * Clips the range of this signal to an arbitrary min/max. Returned signal is a DiscreteSignal.
	 * 
	 * @param signal DiscreteSignal
	 * @return DiscreteSignal
	 */
	protected Signal apply(DiscreteSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Clips the range of <code>signal</code> to an arbitrary min/max in a region of interest.
	 * 
	 * @param signal DiscreteSignal
	 * @param roi Region of Interest of signal
	 * @return <code>signal</code>
	 */
	protected Signal apply(DiscreteSignal signal, ROI roi) {
		if (int_min == 0) {
			int_min = (int) float_min;
			int_max = (int) float_max;
		}
		int value = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {//FUTURE CHANGE? x < roi.ubound() && x < signal.length()
			value = signal.get(x);
			value = (value > int_max) ? int_max : value;
			value = (value < int_min) ? int_min : value;
			signal.set(x, (short) value);
		}

		return signal;
	}

	/**
	 * Clips the range of <code>signal</code> to an arbitrary min/max.
	 * 
	 * @param signal RealSignal
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Clips the range of <code>signal</code> to an arbitrary min/max in a region of interest.
	 * 
	 * @param signal RealSignal
	 * @param roi Region of Interest of signal
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal, ROI roi) {
		if (float_min == 0 && float_max == 0) {
			float_min = (float) int_min;
			float_max = (float) int_max;
		}
		float value = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = signal.get(x);
			value = (value > float_max) ? float_max : value;
			value = (value < float_min) ? float_min : value;
			signal.set(x, value);
		}

		return signal;
	}

	/**
	 * For command line option. The systax is "java Clip <u>min_value</u> <u>max_value</u>
	 * <u>input_filename</u> <u>output_filename</u>".
	 */
	public static void main(String[] argv) {
		try {
			Signal image = null;
			String inputfile = argv[2];
			Signal image2 = null;

			SignalInputStream is = new SignalInputStream(inputfile);
			image = is.read();
			is.close();

			Float f_val1 = Float.valueOf(argv[0]);
			Float f_val2 = Float.valueOf(argv[1]);
			float val1 = f_val1.floatValue();
			float val2 = f_val2.floatValue();

			Clip clip = new Clip(val1, val2);

			image2 = clip.apply(image);

			//put command line stuff here.

			// create a new SignalOutputStream
			SignalOutputStream os = new SignalOutputStream(argv[3]);
			os.write(image2);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
