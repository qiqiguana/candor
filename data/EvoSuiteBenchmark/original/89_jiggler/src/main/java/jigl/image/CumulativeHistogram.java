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
package jigl.image;

/** CumulativeHistogram creates a cumulative histogram from a Histogram. */
public class CumulativeHistogram extends Histogram {

	/** Creates a CumulativeHistogram from a histogram. */
	public CumulativeHistogram(Histogram histogram) {
		super((int) histogram.min, (int) histogram.max, (int) histogram.inc);

		int sum = 0;

		try {
			for (int x = 0; x < (max - min) + 1; x++) {
				values[x] = histogram.values[x] + sum;
				sum = sum + histogram.values[x];
			}
		} catch (Exception e) {
		}

	}

}
