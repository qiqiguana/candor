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

import jigl.signal.DiscreteSignal;
import jigl.signal.ROI;
import jigl.signal.RealSignal;
import jigl.signal.Signal;
import jigl.signal.SignalKernel;
import jigl.signal.SimpleOperator;
import jigl.signal.io.SignalInputStream;
import jigl.signal.io.SignalOutputStream;

/**
 * GDilate performs a dilate operation on a signal. Supports DiscreteSignal, RealSignal.
 * <p>
 * Provides command option.
 */
public class GDilate extends SimpleOperator {

	/** morph kernel */
	private SignalKernel kernel;
	/** center position */
	private int center_x;

	/** Initilizes Dilate */
	public GDilate(SignalKernel ker, int x) {
		kernel = ker;
		center_x = x;

	}

	/**
	 * Dilates a DiscreteSignal. Returned signal is a DiscreteSignal (<code>signal</code> is not
	 * modified).
	 * 
	 * @param signal DiscreteSignal to dilate
	 * @return DiscreteSignal
	 */
	protected Signal apply(DiscreteSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Dilates a DiscreteSignal in a region of interest. Returned signal is a DiscreteSignal (
	 * <code>signal</code> is not modified).
	 * 
	 * @param signal DiscreteSignal to dilate
	 * @param roi Region of Interest of signal
	 * @return DiscreteSignal
	 */
	protected Signal apply(DiscreteSignal signal, ROI roi) {
		int[] temp = new int[kernel.length()];
		int ntemp = 0;
		int max = 0;

		DiscreteSignal signal2 = (DiscreteSignal) signal.copy();

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {

			for (int a = 0; a < kernel.length(); a++) {
				if (center_x - a + x < 0)
					temp[a] = 0;

				else if (center_x - a + x > signal.length() - 1)
					temp[a] = 0;

				else
					temp[a] = signal.get(center_x - a + x);
			}

			for (int a = 0; a < kernel.length(); a++) {
				ntemp = temp[a] + (int) kernel.get(a);
				if (max < ntemp)
					max = ntemp;
				//System.out.print(ntemp+" ");
			}
			//System.out.println("At "+x+" "+y+"   set to value "+max);  
			signal2.set(x, max);
			max = 0;

		}
		return signal2;
	}

	/**
	 * Dilates a RealSignal. Returned signal is a RealSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal RealSignal to dilate
	 * @return RealSignal
	 */
	protected Signal apply(RealSignal signal) {
		return apply(signal, new ROI(0, signal.length() - 1));
	}

	/**
	 * Dilates a RealSignal in a region of interest. Returned signal is a RealSignal (
	 * <code>signal</code> is not modified).
	 * 
	 * @param signal RealSignal to dilate
	 * @param roi Region of Interest of signal
	 * @return RealSignal
	 */
	protected Signal apply(RealSignal signal, ROI roi) {
		float[] temp = new float[kernel.length()];
		float ntemp = 0;
		float max = 0;

		RealSignal signal2 = (RealSignal) signal.copy();

		for (int x = roi.lbound(); x <= roi.ubound(); x++) {
			for (int a = 0; a < kernel.length(); a++) {
				if (center_x - a + x < 0)
					temp[a] = 0;

				else if (center_x - a + x > signal.length() - 1)
					temp[a] = 0;

				else
					temp[a] = signal.get(center_x - a + x);
			}

			for (int a = 0; a < kernel.length(); a++) {
				ntemp = temp[a] + kernel.get(a);
				if (max < ntemp)
					max = ntemp;
				//System.out.print(ntemp+" ");
			}
			//System.out.println("At "+x+" "+y+"   set to value "+max);  
			signal2.set(x, max);
			max = 0;

		}
		return signal2;
	}

	/**
	 * For command option. The syntax is "java GDilate <u>input_filename</u> <u>kernel_filename</u>
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

			GDilate dilate = new GDilate((SignalKernel) signal2, val1);
			signal3 = dilate.apply(signal);

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
