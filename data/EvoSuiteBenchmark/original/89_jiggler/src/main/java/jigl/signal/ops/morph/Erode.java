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
package jigl.signal.ops.morph;

import jigl.signal.BinarySignal;
import jigl.signal.ROI;
import jigl.signal.Signal;
import jigl.signal.SignalKernel;
import jigl.signal.SimpleOperator;
import jigl.signal.io.SignalInputStream;
import jigl.signal.io.SignalOutputStream;

/**
 * Erode performs an erode operation on a signal. Supports BinarySignal.
 * <p>
 * Provides command option.
 */
public class Erode extends SimpleOperator {
	/** morph kernel */
	SignalKernel kernel;
	/** center position */
	int center_x;

	/** Initilizes Erode */
	public Erode(SignalKernel ker, int x) {
		kernel = ker;
		center_x = x;

	}

	/**
	 * Erodes a BinarySignal. Returned signal is a BinarySignal (<code>signal</code> is not
	 * modified).
	 * 
	 * @param signal BinarySignal
	 * @return BinarySignal
	 */
	protected Signal apply(BinarySignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Erodes a BinarySignal in a region of interest. Returned signal is a BinarySignal (
	 * <code>signal</code> is not modified).
	 * 
	 * @param signal BinarySignal
	 * @param roi Region of Interest of signal
	 * @return BinarySignal
	 */
	protected Signal apply(BinarySignal signal, ROI roi) {
		byte[] temp = new byte[kernel.length()];
		BinarySignal signal2 = (BinarySignal) signal.copy();

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {

			if (signal.get(x) == 1) {

				for (int a = 0; a < kernel.length(); a++) {
					if (center_x - a + x < 0)
						temp[a] = 0;
					else if (center_x - a + x > signal.length() - 1)
						temp[a] = 0;
					else
						temp[a] = signal.get(center_x - a + x);
				}
				set: {
					for (int a = 0; a < kernel.length(); a++)
						if ((kernel.get(a) == 1) && (temp[a] != 1)) {
							signal2.set(x, 0);
							break set;
						}

					signal2.set(x, 1);
				}
			}
		}

		return signal2;
	}

	/**
	 * For command option. The syntax is "java Erode <u>input_filename</u> <u>kernel_filename</u>
	 * <u>center_x</u> <u>output_filename</u>"
	 */
	public static void main(String[] argv) {

		try {
			Signal signal = null;
			Signal signal2 = null;
			Signal signal3 = null;
			String inputfile = argv[0];
			String kernelfile = argv[1];

			SignalInputStream is = new SignalInputStream(inputfile);
			signal = is.read();
			is.close();

			is = new SignalInputStream(kernelfile);
			signal2 = is.read();
			is.close();

			Integer f_val1 = Integer.valueOf(argv[2]);
			int val1 = f_val1.intValue();

			Erode erode = new Erode((SignalKernel) signal2, val1);
			signal3 = erode.apply(signal);

			//put command line stuff here.

			// create a new SignalOutputStream
			SignalOutputStream os = new SignalOutputStream(argv[4]);
			os.write(signal3);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
