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

import jigl.signal.DiscreteSignal;
import jigl.signal.RealSignal;
import jigl.signal.Signal;
import jigl.signal.SimpleOperator;

/** Shifts a signal with or without wrapping around. */
public class Shift extends SimpleOperator {

	/** Flag for not using wrap */
	public static final int NOWRAP = 0;
	/** Flag for using wrap */
	public static final int WRAP = 1;
	/** The amount of shift. If <code>xoffset>0</code>, the signal is shifted right. */
	private int xoffset = 0;
	/** Flag for wrap, default value is false. */
	private boolean wrap = false;

	/**
	 * Constructs a Shift object from an offset and wrap flag.
	 * 
	 * @param x amount to shift
	 * @param wrap <code>if wrap=1</code>, apply wrap.
	 */
	public Shift(int x, int wrap) {
		xoffset = x;
		if (wrap == 1)
			this.wrap = true;

	}

	/**
	 * Returns a shifted copy of a DiscreteSignal object <code>signal</code>.
	 * 
	 * @param signal DiscreteSignal to apply shift to.
	 * @return shifted copy of <code>signal</code>.
	 */
	protected Signal apply(DiscreteSignal signal) {
		int X = signal.length();
		//Convert to the appropriate type
		DiscreteSignal temp = new DiscreteSignal(X);

		if (wrap) {
			for (int x = 0; x < X; x++) {
				if (xoffset > 0)
					temp.set((x + xoffset) % X, signal.get(x));
				else
					temp.set((X + xoffset + x) % X, signal.get(x));
			}
		} else {
			for (int x = 0; x < X; x++) {
				if ((x + xoffset >= 0) && (x + xoffset < X))
					temp.set(x, signal.get(x + xoffset));
				else
					temp.set(x, 0);
			}
		}

		return temp;
	}

	/**
	 * Returns a shifted copy of RealSignal object <code>signal</code>.
	 * 
	 * @param signal RealSignal to apply shift to.
	 * @return shifted copy of <code>signal</code>.
	 */
	protected Signal apply(RealSignal signal) {
		int X = signal.length();

		RealSignal temp = new RealSignal(X);

		if (wrap) {
			for (int x = 0; x < X; x++) {
				if (xoffset > 0)
					temp.set((x + xoffset) % X, signal.get(x));
				else
					temp.set((X + xoffset + x) % X, signal.get(x));
			}
		} else {
			for (int x = 0; x < X; x++) {
				if ((x + xoffset >= 0) && (x + xoffset < X))
					temp.set(x, signal.get(x + xoffset));
				else
					temp.set(x, 0);
			}
		}

		return temp;
	}

}
