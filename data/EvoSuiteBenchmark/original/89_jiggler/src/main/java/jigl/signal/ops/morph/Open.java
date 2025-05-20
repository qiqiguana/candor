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
 * Open performs an open operation on a signal. Supports BinarySignal.
 * <p>
 * Provides command option.
 */
public class Open extends SimpleOperator {

	/** morph kernel */
	private SignalKernel kernel;
	/** center position */
	private int center_x;

	/** Initilizes Open */
	public Open(SignalKernel ker, int x) {
		kernel = ker;
		center_x = x;

	}

	/**
	 * Performs an Open on a BinarySignal. Returned signal is a BinarySignal (<code>signal</code> is
	 * not modified).
	 * 
	 * @param signal BinarySignal to perform open on
	 * @return BinarySignal
	 */
	protected Signal apply(BinarySignal signal) {
		BinarySignal signal2 = (BinarySignal) signal.copy();
		Dilate d = new Dilate(kernel);//, center_x);
		Erode e = new Erode(kernel, center_x);
		signal2 = (BinarySignal) e.apply(signal);
		signal2 = (BinarySignal) d.apply(signal2);
		return signal2;

	}

	/**
	 * Performs an Open on a BinarySignal in a region of interest. Returned signal is a BinarySignal
	 * (<code>signal</code> is not modified).
	 * 
	 * @param signal BinarySignal to perform open on
	 * @param roi Region of interest of signal
	 * @return BinarySignal
	 */
	protected Signal apply(BinarySignal signal, ROI roi) {
		BinarySignal signal2 = (BinarySignal) signal.copy();
		Dilate d = new Dilate(kernel);//, center_x);
		Erode e = new Erode(kernel, center_x);
		signal2 = (BinarySignal) e.apply(signal, roi);
		signal2 = (BinarySignal) d.apply(signal2, roi);
		return signal2;

	}

	/**
	 * For command option. The syntax is "java Open <u>input_filename</u> <u>kernel_filename</u>
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

			Open open = new Open((SignalKernel) signal2, val1);
			signal3 = open.apply((BinarySignal) signal);

			//put command line stuff here.

			// create a new SignalOutputStream
			SignalOutputStream os = new SignalOutputStream(argv[3]);
			os.write(signal3);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
