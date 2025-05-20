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
import jigl.signal.utils.SignalConverter;

/**
 * Performs the Log function on each pixel in this signal. Supports DiscreteSignal, RealSignal.
 * Please note this class <i>always</i> return a RealSignal.
 * <p>
 * Provides command line option.
 */
public class Log extends SimpleOperator {
	/** Dummy Constructor */
	public Log() {
	}

	/**
	 * Performs the Log function on a DiscreteSignal. Returned Signal is a RealSignal (
	 * <code>signal</code> is not modified).
	 * 
	 * @param signal DiscreteSignal
	 * @return RealSignal
	 */
	protected Signal apply(DiscreteSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Performs the Log function on a DiscreteSignal in a region of interest. Returned Signal is a
	 * RealSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal DiscreteSignal
	 * @param roi Region of Interest
	 * @return RealSignal
	 */
	protected Signal apply(DiscreteSignal signal, ROI roi) {
		RealSignal rg = SignalConverter.toReal(signal);
		float value = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = rg.get(x);
			value = (float) java.lang.Math.log(value);
			rg.set(x, value);
		}

		return rg;
	}

	/**
	 * Performs the Log function on a RealSignal. Returned Signal is a RealSignal (
	 * <code>signal</code> is not modified).
	 * 
	 * @param signal RealSignal
	 * @return RealSignal
	 */
	protected Signal apply(RealSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Performs the Log function on a RealSignal in a region of interest. Returned Signal is a
	 * RealSignal.
	 * 
	 * @param signal RealSignal
	 * @param roi Region of Interest of signal
	 * @return RealSignal
	 */
	protected Signal apply(RealSignal signal, ROI roi) {
		float value = 0;

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			value = signal.get(x);
			value = (float) java.lang.Math.log(value);
			signal.set(x, value);
		}

		return signal;
	}

	/**
	 * For command line option. The systax is
	 * "java Log <u>input_filename</u> <u>output_filename</u>".
	 */
	public static void main(String[] argv) {

		try {
			Signal image = null;
			String inputfile = argv[0];
			Signal image2 = null;

			SignalInputStream is = new SignalInputStream(inputfile);
			image = is.read();
			is.close();

			Log lg = new Log();

			image2 = lg.apply(image);

			//put command line stuff here.

			// create a new SignalOutputStream
			SignalOutputStream os = new SignalOutputStream(argv[1]);
			os.write(image2);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
