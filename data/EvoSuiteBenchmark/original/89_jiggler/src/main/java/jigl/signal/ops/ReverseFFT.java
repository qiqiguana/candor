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
package jigl.signal.ops;

import jigl.signal.ComplexSignal;
import jigl.signal.DiscreteSignal;
import jigl.signal.RealSignal;
import jigl.signal.Signal;
import jigl.signal.SimpleOperator;
import jigl.signal.utils.FFT;

/**
 * Provides a SimpleOperator wrapper around jigl.signal.utils.FFT. Supports DiscreteSignal,
 * ComplexSignal, RealSignal.
 */
public class ReverseFFT extends SimpleOperator {
	/** Constructs an empty ReverseFFT object. */
	public ReverseFFT() {
	}

	/** Calls {@link jigl.signal.utils.FFT#inverse(signal)} */
	protected Signal apply(DiscreteSignal signal) {
		return FFT.inverse(signal);
	}

	/** Calls {@link jigl.signal.utils.FFT#inverse(signal)} */
	protected Signal apply(ComplexSignal signal) {
		return FFT.inverse(signal);
	}

	/** Calls {@link jigl.signal.utils.FFT#inverse(signal)} */
	protected Signal apply(RealSignal signal) {
		return FFT.inverse(signal);
	}
}
