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
 * SimpleOperator is a base class that all levelOps and morph classes derive from. SimpleOperator
 * supports BinarySignal, DiscreteSignal, RealSignal, and ComplexSignal.
 */
public class SimpleOperator implements Operator {

	/**
	 * apply an operator to the signal. Default implementation throws SignalNotSupportedException.
	 * 
	 * @param signal Signal to perform the operation on.
	 */
	public Signal apply(Signal signal) throws SignalNotSupportedException {
		if (signal instanceof BinarySignal)
			return apply((BinarySignal) signal);
		else if (signal instanceof DiscreteSignal)
			return apply((DiscreteSignal) signal);
		else if (signal instanceof RealSignal)
			return apply((RealSignal) signal);
		else if (signal instanceof ComplexSignal)
			return apply((ComplexSignal) signal);
		else
			throw new SignalNotSupportedException();
	}

	/**
	 * apply an operator to the signal within a region of interest. Default implementation throws
	 * SignalNotSupportedException.
	 * 
	 * @param signal Signal to perform the operation on.
	 * @param roi Region of Interest of signal
	 */
	public Signal apply(Signal signal, ROI roi) throws SignalNotSupportedException {
		if (null == roi)
			return apply(signal);

		if (signal instanceof BinarySignal)
			return apply((BinarySignal) signal, roi);
		else if (signal instanceof DiscreteSignal)
			return apply((DiscreteSignal) signal, roi);
		else if (signal instanceof RealSignal)
			return apply((RealSignal) signal, roi);
		else if (signal instanceof ComplexSignal)
			return apply((ComplexSignal) signal, roi);
		else
			throw new SignalNotSupportedException();
	}

	/** Utility method */
	protected Signal apply(BinarySignal signal) throws SignalNotSupportedException {
		throw new SignalNotSupportedException();
	}

	/** Utility method */
	protected Signal apply(DiscreteSignal signal) throws SignalNotSupportedException {
		throw new SignalNotSupportedException();
	}

	/** Utility method */
	protected Signal apply(RealSignal signal) throws SignalNotSupportedException {
		throw new SignalNotSupportedException();
	}

	/** Utility method */
	protected Signal apply(ComplexSignal signal) throws SignalNotSupportedException {
		throw new SignalNotSupportedException();
	}

	/** Utility method */
	protected Signal apply(BinarySignal signal, ROI roi) throws SignalNotSupportedException {
		throw new SignalNotSupportedException();
	}

	/** Utility method */
	protected Signal apply(DiscreteSignal signal, ROI roi) throws SignalNotSupportedException {
		throw new SignalNotSupportedException();
	}

	/** Utility method */
	protected Signal apply(RealSignal signal, ROI roi) throws SignalNotSupportedException {
		throw new SignalNotSupportedException();
	}

	/** Utility method */
	protected Signal apply(ComplexSignal signal, ROI roi) throws SignalNotSupportedException {
		throw new SignalNotSupportedException();
	}
}
