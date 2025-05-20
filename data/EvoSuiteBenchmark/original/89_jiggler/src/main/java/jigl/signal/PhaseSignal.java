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
 * A phase signal is a signal which takes a ComplexSignal and computes each pixel by taking the arc
 * tangent of Imaginary plane and the Real plane
 */
public class PhaseSignal extends RealSignal {
	/** Constructs an empty PhaseSignal object. */
	public PhaseSignal() {
		super();
	}

	/** Creates an empty PhaseSignal object of length <code>x</code> */
	public PhaseSignal(int x) {
		super(x);
	}

	/** Creates a PhaseSignal object (shallow copy) from a PhaseSignal object. */
	public PhaseSignal(PhaseSignal s) {
		super(s);
	}

	/** Constructs a PhaseSignal from a ComplexSignal object. */
	public PhaseSignal(ComplexSignal s) {
		length = s.length();
		data = new float[length];
		for (int x = 0; x < s.length; x++) {
			data[x] = (float) Math.atan2(s.getImag(x), s.getReal(x));
		}
	}

}
