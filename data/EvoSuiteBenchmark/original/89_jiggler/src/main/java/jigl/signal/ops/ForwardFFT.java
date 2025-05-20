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
 * Provides a SimpleOperator wrapper around the jigl.signal.utils.FFT class. Simply calls the
 * appropriate static FFT method.
 * <p>
 * Supports RealSignal, ComplexSignal, DiscreteSignal.
 */
public class ForwardFFT extends SimpleOperator {
	/** Constructs an empty ForwardFFT object. */
	public ForwardFFT() {
	}

	/** Calls {@link jigl.signal.utils.FFT#forward(Signal)} */
	protected Signal apply(RealSignal signal) {
		return FFT.forward(signal);
	}

	/** Calls {@link jigl.signal.utils.FFT#forward(Signal)} */
	protected Signal apply(ComplexSignal signal) {
		return FFT.forward(signal);
	}

	/** Calls jigl.signal.utils.FFT.forward(signal) */
	protected Signal apply(DiscreteSignal signal) {
		return FFT.forward(signal);
	}
}
