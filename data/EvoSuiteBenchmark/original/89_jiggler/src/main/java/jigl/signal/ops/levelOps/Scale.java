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
 * Performs a Scale operation on a signal. Supports DiscreteSignal, RealSignal.
 * <p>
 * Provides command line option.
 */
public class Scale extends SimpleOperator {
	/** Minimum value for new range. */
	private int int_min;
	/** Maximum value for new range */
	private int int_max;
	/** Minimum value for new range. */
	private float float_min;
	/** Maximum value for new range */
	private float float_max;

	/** Initilizes Scale for use with integers */
	public Scale(int min, int max) {
		int_min = min;
		int_max = max;
	}

	/** Initilizes Scale for use with floats */
	public Scale(float min, float max) {
		float_min = min;
		float_max = max;
	}

	/**
	 * Scales the range of <code>signal</code> to an arbitrary min/max.
	 * 
	 * @param signal DiscreteSignal
	 * @return <code>signal</code>
	 */
	protected Signal apply(DiscreteSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Scales the range of <code>signal</code> to an arbitrary min/max in a region of interest.
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
		float min = signal.min();
		float max = signal.max();
		float r = 0;
		//		float range = max - min;

		// convert to byte depth
		float value = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			r = (float) signal.get(x);
			if (r == 0)
				value = 0;
			else
				value = (((r - min) / (max - min)) * (int_max - int_min)) + int_min;
			signal.set(x, (short) value);
		}

		return signal;
	}

	/**
	 * Scales the range of <code>signal</code> to an arbitrary min/max.
	 * 
	 * @param signal RealSignal
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Scales the range of <code>signal</code> to an arbitrary min/max in a region of interest.
	 * 
	 * @param signal RealSignal
	 * @param roi Region of Interest of signal
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal, ROI roi) {
		if (float_min == 0) {
			float_min = (float) int_min;
			float_max = (float) int_max;
		}
		float min = signal.min();
		float max = signal.max();

		float r = 0;
		//		float range = max - min;

		// convert to byte depth
		float value = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			r = (float) signal.get(x);
			if (r == 0)
				value = 0;
			else
				value = (float) ((r - min) / (max - min)) * (float_max - float_min) + float_min;
			signal.set(x, (float) value);
		}

		return signal;
	}

	/**
	 * For command line option. The systax is "java Scale <u>min_value</u> <u>max_value</u>
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

			Scale scale = new Scale(val1, val2);

			image2 = scale.apply(image);

			// create a new SignalOutputStream
			SignalOutputStream os = new SignalOutputStream(argv[3]);
			os.write(image2);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
