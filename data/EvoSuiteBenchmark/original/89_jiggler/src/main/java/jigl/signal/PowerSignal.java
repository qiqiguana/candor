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
 * PowerSignal takes a ComplexSignal and computes the value at x by adding the squared real and
 * image plane.
 */
public class PowerSignal extends RealSignal {
	/** Constructs an empty PowerSignal object. */
	public PowerSignal() {
		super();
	}

	/** Creates an empty PowerSignal object of length x */
	public PowerSignal(int x) {
		super(x);
	}

	/** Creates a PowerSignal object (shallow copy) from a PowerSignal object. */
	public PowerSignal(PowerSignal s) {
		super(s);
	}

	/** Constructs a PowerSignal object from a ComplexSignal object. */
	public PowerSignal(ComplexSignal s) {
		length = s.length();
		data = new float[length];
		for (int x = 0; x < s.length; x++) {
			data[x] = (float) (s.getReal(x) * s.getReal(x) + s.getImag(x) * s.getImag(x));
		}
	}

}
