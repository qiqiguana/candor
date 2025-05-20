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
import jigl.signal.RealSignal;
import jigl.signal.Signal;
import jigl.signal.SimpleOperator;
import jigl.signal.io.SignalInputStream;
import jigl.signal.io.SignalOutputStream;

/**
 * Performs a ByteSize operation on a signal. Supports DiscreteSignal, RealSignal.
 * <p>
 * This class supports command line options in the following format:<br>
 * <code>  java ByteSize <input file> <output file> </code>.
 */
public class ByteSize extends SimpleOperator {
	/** Default constructor. */
	public ByteSize() {

	}

	/**
	 * ByteSizes the range of <code>signal</code> to [0..255].
	 * 
	 * @param signal DiscreteSignal
	 * @return <code>signal</code>
	 */
	protected Signal apply(DiscreteSignal signal) {
		float min = signal.min();
		float max = signal.max();

		float range = max - min;

		// convert to byte depth
		int value = 0;

		for (int x = 0; x < signal.length(); x++) {
			value = (int) ((255.0 / range) * ((float) signal.get(x) - min));
			value = 0x00FF & value;
			signal.set(x, (short) value);
		}

		return signal;
	}

	/**
	 * ByteSizes the range of <code>signal</code> to [0.0 .. 255.0].
	 * 
	 * @param signal RealSignal
	 * @return <code>signal</code>
	 */
	protected Signal apply(RealSignal signal) {
		float min = signal.min();
		float max = signal.max();

		float range = max - min;

		// convert to byte depth
		float value = 0;

		for (int x = 0; x < signal.length(); x++) {
			value = (float) ((255.0 / range) * (signal.get(x) - min));
			//value = 0x00FF & (int)value;
			signal.set(x, value);
		}

		return signal;
	}

	/**
	 * For command line option. The syntax is "java ByteSize <u>input_filename</u>
	 * <u>output_filename</u>".
	 */
	public static void main(String[] argv) {

		try {
			Signal image = null;
			String inputfile = argv[0];
			Signal image2 = null;

			SignalInputStream is = new SignalInputStream(inputfile);
			image = is.read();
			is.close();

			ByteSize bytesize = new ByteSize();

			image2 = bytesize.apply(image);

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
