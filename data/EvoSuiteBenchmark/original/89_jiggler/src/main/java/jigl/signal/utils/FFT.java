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
package jigl.signal.utils;

import jigl.signal.ComplexSignal;
import jigl.signal.DiscreteSignal;
import jigl.signal.RealSignal;
import jigl.signal.Signal;
import jigl.util.MathUtil;

/**
 * Fast Fourier Transform Class for one-dimension signal. Makes sure that the length of your signal
 * is of power of 2 before applying FFT.
 * <p>
 * To get the correct shape of the Fourier Transform, you should shift the result signal right by
 * half length.
 */
public class FFT {
	/** Defines maximum length of the signal, which is <code>2^MAX_POW</code>. */
	static final int MAX_POW = 31;
	/** An array to store the integers of power of 2. */
	static final int[] pow2 = new int[MAX_POW + 1];

	// static initializer
	static {
		pow2[0] = 1;

		for (int i = 1; i <= MAX_POW; i++) {
			pow2[i] = 2 * pow2[i - 1];
		}
	}

	static int BitReverse(int i, int k) {
		int rev = 0;

		for (int j = 0; j < k; ++j) {
			if ((i & pow2[k - j - 1]) > 0)
				rev |= pow2[j];
		}

		return rev;
	}

	static void bitDump(int d) {
		System.out.println(d + ":");
		for (int i = 31; i >= 0; i--) {
			if ((d & pow2[i]) != 0)
				System.out.print("1");
			else
				System.out.print("0");
		}

		System.out.println();
	}

	/** Default constructor */
	private FFT() {
	}

	/** Perform the Fourier Transform */
	static public ComplexSignal forward(Signal im) {
		return doFFT(im, true);
	}

	/** Perform the Inverse Fourier Transform */
	static public ComplexSignal inverse(Signal im) {
		return doFFT(im, false);
	}

	/**
	 * Perform the Fourier transform
	 *
     * FIXME: PLEASE, PLEASE refactor me!
     *
	 * @param forward true=Fourier, false=Inverse Fourier
	 */
	static public ComplexSignal doFFT(final Signal im, final boolean forward) {
		int x = 0;
		int lgn = 0;
		int n = 0;
		int rev = 0;
		int size = im.length();

        if(!MathUtil.powerOf2(size))
            throw new IllegalArgumentException("The length of this signal is not a power of 2. You can pad zeros to change its length and apply FFT again.");

		float[][] a = null;

		while (x <= MAX_POW) {
			// find the next-highest power of two after the total size of im
			if (pow2[x] >= size) {
				lgn = x;
				n = pow2[lgn];

				// a is the output comlex buffer; NOTE:  1-d image.
				// a[0][n] = real; a[1][n] = imaginary
				a = new float[2][n];

				// load the values from im into the new ComplexSignal,
				// in reverse bit order, padding with 0's.
				for (int y = 0; y < n; y++) {
					rev = BitReverse(y, lgn);
					try {
						if (forward == true) {
							if (im instanceof DiscreteSignal) {
								DiscreteSignal ub = (DiscreteSignal) im;
								if (y < ub.length()) {
									a[0][rev] = (float) ub.get(y);
									a[1][rev] = 0f;
								} else
									a[0][rev] = 0f;
								a[1][rev] = 0f;
							} else if (im instanceof ComplexSignal) {
								ComplexSignal ci = (ComplexSignal) im;
								if (y < ci.length()) {
									a[0][rev] = ci.getReal(y);
									a[1][rev] = ci.getImag(y);
								} else {
									a[0][rev] = 0f;
									a[1][rev] = 0f;
								}
							} else if (im instanceof RealSignal) {
								RealSignal ub = (RealSignal) im;
								if (y < ub.length()) {
									a[0][rev] = ub.get(y);
									a[1][rev] = 0f;
								} else {
									a[0][rev] = 0f;
									a[1][rev] = 0f;
								}
							} else {
								// unsupported type - throw exception?
							}
						} else {
							if (im instanceof DiscreteSignal) {
								DiscreteSignal ub = (DiscreteSignal) im;
								if (y < ub.length())
									a[0][y] = (float) ub.get(y) / (float) n;
								else
									a[0][rev] = 0f;
								a[1][rev] = 0f;
							} else if (im instanceof ComplexSignal) {
								ComplexSignal ci = (ComplexSignal) im;
								if (y < ci.length()) {
									a[0][rev] = (float) ci.getReal(y) / (float) n;
									a[1][rev] = (float) ci.getImag(y) / (float) n;
								} else {
									a[0][rev] = 0f;
									a[1][rev] = 0f;
								}
							} else if (im instanceof RealSignal) {
								RealSignal ub = (RealSignal) im;
								if (y < ub.length())
									a[0][y] = ub.get(y) / (float) n;
								else
									a[0][rev] = 0f;
								a[1][rev] = 0f;
							} else {
								// unsupported type - throw exception?
							}
						}
					} // try
					catch (ArrayIndexOutOfBoundsException e) {
						a[0][rev] = 0f;
						a[1][rev] = 0f;
					} catch (Exception e) {
						System.out.println("FFT Error:  " + e.toString());
					}
				} // for
				break;
			} // if
			x++;
		}
		float[] t = new float[2];
		float[] u = new float[2];
		float temp;
		float t2;
		int m = 0;
		int mdiv2 = 0;
		float[] omega_m = null;

		// do the FFT
		for (int s = 1; s <= lgn; s++) {
			m = pow2[s];
			mdiv2 = m / 2;
			omega_m = new float[2];

			if (forward == true) {
				omega_m[0] = (float) (Math.cos((-2.0 * Math.PI) / (float) m));
				omega_m[1] = (float) (Math.sin((-2.0 * Math.PI) / (float) m));
			} else {
				omega_m[0] = (float) (Math.cos(2.0 * Math.PI / (double) m));
				omega_m[1] = (float) (Math.sin(2.0 * Math.PI / (double) m));
			}

			float[] omega = { 1f, 0f };

			for (int j = 0; j < mdiv2; ++j) {
				for (int k = j; k < n; k += m) {
					t[0] = omega[0];
					t[1] = omega[1];
					u[0] = a[0][k];
					u[1] = a[1][k];

					temp = (t[0] * a[0][k + mdiv2]) - (t[1] * a[1][k + mdiv2]);
					t[1] = (t[0] * a[1][k + mdiv2]) + (t[1] * a[0][k + mdiv2]);
					t[0] = temp;

					// a[k] = u + t; better way to do this?
					a[0][k] = u[0] + t[0];
					a[1][k] = u[1] + t[1];

					a[0][k + mdiv2] = u[0] - t[0];
					a[1][k + mdiv2] = u[1] - t[1];
				}

				t2 = (omega[0] * omega_m[0]) - (omega[1] * omega_m[1]);
				omega[1] = (omega[0] * omega_m[1]) + (omega[1] * omega_m[0]);
				omega[0] = t2;
			}
		}

		ComplexSignal ar = new ComplexSignal(im.length());

		for (int i = 0; i < n; i++) {
			ar.set(i, (float) a[0][i], (float) a[1][i]);
		}

		return ar;
	}
}
