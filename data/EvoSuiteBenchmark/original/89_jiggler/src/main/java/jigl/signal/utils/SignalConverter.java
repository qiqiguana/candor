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

/**
 * Signal type conversions. All methods in this class are static. Supports DiscreteSignal,
 * RealSignal, ComplexSignal.
 */
public class SignalConverter {
	/** Default Constructor. */
	public SignalConverter() {
	}

	/**
	 * Converts a Signal to a DescreteSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal signal to convert to DiscreteSignal.
	 * @return a DiscreteSignal.
	 */
	public static DiscreteSignal toDiscrete(Signal signal) {
		if (signal instanceof DiscreteSignal) {
			return toDiscrete((DiscreteSignal) signal);
		} else if (signal instanceof RealSignal) {
			return toDiscrete((RealSignal) signal);
		} else {
			return toDiscrete((ComplexSignal) signal);
		}
	}

	/**
	 * Converts a Signal to a RealSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal signal to convert to RealSignal.
	 * @return a RealSignal.
	 */
	public static RealSignal toReal(Signal signal) {
		if (signal instanceof DiscreteSignal) {
			return toReal((DiscreteSignal) signal);
		} else if (signal instanceof RealSignal) {
			return toReal((RealSignal) signal);
		} else {
			return toReal((ComplexSignal) signal);
		}
	}

	/**
	 * Converts a Signal to a ComplexSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal signal to convert to ComplexSignal.
	 * @return a ComplexSignal.
	 */
	public static ComplexSignal toComplex(Signal signal) {
		if (signal instanceof DiscreteSignal) {
			return toComplex((DiscreteSignal) signal);
		} else if (signal instanceof RealSignal) {
			return toComplex((RealSignal) signal);
		} else {
			return toComplex((ComplexSignal) signal);
		}
	}

	/**
	 * Converts a DiscreteSignal to a DiscretetSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal DiscreteSignal to convert to DiscreteSignal.
	 * @return a DiscreteSignal.
	 */
	public static DiscreteSignal toDiscrete(DiscreteSignal signal) {
		int length = signal.length();
		DiscreteSignal newsignal = new DiscreteSignal(length);
		for (int x = 0; x < length; x++) {
			newsignal.set(x, signal.get(x));
		}
		return newsignal;
	}

	/**
	 * Converts a RealSignal to a DiscretetSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal RealSignal to convert to DiscreteSignal.
	 * @return a DiscreteSignal.
	 */
	public static DiscreteSignal toDiscrete(RealSignal signal) {
		int length = signal.length();
		DiscreteSignal newsignal = new DiscreteSignal(length);
		for (int x = 0; x < length; x++) {
			newsignal.set(x, (short) signal.get(x));
		}
		return newsignal;
	}

	/**
	 * Converts a ComplexSignal to a DiscretetSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal ComplexSignal to convert to DiscreteSignal.
	 * @return a DiscreteSignal.
	 */
	public static DiscreteSignal toDiscrete(ComplexSignal signal) {
		int length = signal.length();
		DiscreteSignal newsignal = null;
		newsignal = new DiscreteSignal(length);
		for (int x = 0; x < length; x++) {
			newsignal.set(x, (int) signal.getReal(x));
		}
		return newsignal;
	}

	/**
	 * Converts a DiscreteSignal to a RealSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal DiscreteSignal to convert to RealSignal.
	 * @return a RealSignal.
	 */
	public static RealSignal toReal(DiscreteSignal signal) {
		int length = signal.length();
		RealSignal newsignal = new RealSignal(length);
		for (int x = 0; x < length; x++) {
			newsignal.set(x, (float) signal.get(x));
		}
		return newsignal;
	}

	/**
	 * Converts a RealSignal to a RealSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal RealSignal to convert to RealSignal.
	 * @return a RealSignal.
	 */
	public static RealSignal toReal(RealSignal signal) {
		int length = signal.length();
		RealSignal newsignal = new RealSignal(length);
		for (int x = 0; x < length; x++) {
			newsignal.set(x, signal.get(x));
		}
		return newsignal;
	}

	/**
	 * Converts a ComplexSignal to a RealSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal ComplexSignal to convert to RealSignal.
	 * @return a RealSignal.
	 */
	public static RealSignal toReal(ComplexSignal signal) {
		int length = signal.length();
		RealSignal newsignal = null;
		newsignal = new RealSignal(length);
		for (int x = 0; x < length; x++) {
			newsignal.set(x, signal.getReal(x));
		}
		return newsignal;
	}

	/**
	 * Converts a DiscreteSignal to a ComplexSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal DiscreteSignal to convert to ComplexSignal.
	 * @return a ComplexSignal.
	 */
	public static ComplexSignal toComplex(DiscreteSignal signal) {
		int length = signal.length();
		// Complex complex = new Complex();
		ComplexSignal newsignal = new ComplexSignal(length);
		for (int x = 0; x < length; x++) {
			newsignal.setReal(x, (float) signal.get(x));
			newsignal.setImag(x, 0f);
		}
		return newsignal;
	}

	/**
	 * Converts a RealSignal to a ComplexSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal RealSignal to convert to ComplexSignal.
	 * @return a ComplexSignal.
	 */
	public static ComplexSignal toComplex(RealSignal signal) {
		int length = signal.length();
		// Complex complex = new Complex();
		ComplexSignal newsignal = new ComplexSignal(length);
		for (int x = 0; x < length; x++) {
			newsignal.setReal(x, signal.get(x));
			newsignal.setImag(x, 0f);
		}
		return newsignal;
	}

	/**
	 * Converts a ComplexSignal to a ComplexSignal (<code>signal</code> is not modified).
	 * 
	 * @param signal ComplexSignal to convert to ComplexSignal.
	 * @return a ComplexSignal.
	 */
	public static ComplexSignal toComplex(ComplexSignal signal) {
		int length = signal.length();
		ComplexSignal newsignal = null;
		newsignal = new ComplexSignal(length);
		for (int x = 0; x < length; x++) {
			newsignal.setReal(x, signal.getReal(x));
			newsignal.setImag(x, signal.getImag(x));
		}
		return newsignal;
	}

	//	public static void main(String args[]) {
	//		short[] data = { 1, 2, 3, 4, 5 };
	//		Signal sig = new DiscreteSignal(data);
	//		RealSignal rs = toReal(sig);
	//	}

}
