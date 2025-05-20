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
 * A magnitude signal is a signal which takes a ComplexSignal and computes each value by taking the
 * square root of the Real and Imaginary planes squared
 */
public class MagnitudeSignal extends RealSignal {
	/** Constructs an empty MagnitudeSignal object. */
	public MagnitudeSignal() {
		super();
	}

	/** Creates an empty magnitudeSignal of length <code>x</code> */
	public MagnitudeSignal(int x) {
		super(x);
	}

	/** Creates a one-dimensional MagnitudeSignal object (shallow copy) from anothe one. */
	public MagnitudeSignal(MagnitudeSignal s) {
		super(s);
	}

	/** Creates a MagnititudeSignal object from a ComplexSignal object. */
	public MagnitudeSignal(ComplexSignal s) {
		length = s.length();
		data = new float[length];
		float r, im;
		for (int x = 0; x < s.length; x++) {
			r = s.getReal(x);
			im = s.getImag(x);
			data[x] = (float) Math.sqrt(r * r + im * im);
		}
	}
}
