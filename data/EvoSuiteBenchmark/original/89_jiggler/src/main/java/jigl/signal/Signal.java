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
 * Signal is the base class for BinarySignal, RealSignal, ComplexSignal, DiscreteSignal.
 * <P>
 * In addition, all typed signal classes must support the following typed methods: <br>
 * 
 * <pre>
 * Signal_value_type get(int)	Signal_value_type get(int,ROI)
 * 	void set(int)			void set(int)
 * 	void clear()			void clear()
 * </pre>
 * 
 * TODO: use generics to make this interface meaningful
 */
public interface Signal {

	/** returns the length of the signal */
	public int length();

	/** Returns the string representation of a signal */
	public String toString();

	/** Returns a deep copy of a signal */
	public Signal copy();

} // Signal

