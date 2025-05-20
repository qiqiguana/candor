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
package jigl.signal;

/**
 * SignalKernel is an extension of DiscreteSignal with some pre-defined kernals. All the kernels
 * created here may not be normalized.
 */

public class SignalKernel extends RealSignal {
	/**
	 * Uniform Kernal.
	 * <TABLE BORDER CELLPADDING=5 COLS=1 WIDTH="13" >
	 * <TR>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * </TR>
	 * </TABLE>
	 */
	public static final int UNIFORM = 0;
	/**
	 * Difference Kernel
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>1</TD>
	 * <TD>-1</TD>
	 * </TR>
	 * </TABLE>
	 */
	public static final int DIFFERENCE = 1;

	/**
	 * Creates a kenel of one of the predefined types. If <code>val==0</code>, uses UNIFORM kernel.
	 * If <code>val==1</code>, uses DIFFERENCE kernel. Otherwise a InvalidKernelException is thrown.
	 * The default length of the kernel is 2.
	 * 
	 * @param val kernel type
	 * @see jigl.signal.SignalKernel#UNIFORM
	 * @see jigl.signal.SignalKernel#DIFFERENCE
	 */
	public SignalKernel(int val) throws InvalidKernelException {

		super(2);

		float[] data2 = null;

		switch (val) {

		case 0: {
			float[] data1 = { 1, 1 };
			data2 = data1;
			break;
		}

		case 1: {
			float[] data1 = { 1, -1 };
			data2 = data1;
			break;
		}

		default:
			throw new InvalidKernelException();
		}
		data = data2;
	}

	/**
	 * Creates a uniform kernel of specified length
	 * 
	 * @param val uniform value for the kernel
	 * @param dimension size of kernel (the length)
	 */
	public SignalKernel(float val, int dimension) {
		super(dimension);
		for (int x = 0; x < dimension; x++)
			data[x] = val;

	}

	/**
	 * Creates a specified kernel out of the given data. Note: The kernel created will have
	 * dimensions equal to the number of elements in the first row.
	 * 
	 * @param dat two-dimensional data array
	 */

	public SignalKernel(float[] dat) {
		super(dat.length);
		data = dat;
	}

	/** Create a specified kernel from a given RealSignal object */
	public SignalKernel(RealSignal signal) {
		super(signal);
		data = signal.data;
	}

	/** Normalize the kernel */
	public void normalize() {
		float total = 0;
		for (int i = 0; i < length; i++)
			total += data[i];

		if (total == 0.0f)
			total = 1.0f;

		for (int i = 0; i < length; i++)
			data[i] = data[i] / total;
	}
}
