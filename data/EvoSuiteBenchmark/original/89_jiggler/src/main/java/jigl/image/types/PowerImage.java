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
package jigl.image.types;

/**
 * PowerImage is the power spectrum of a ComplexImage and computes the value at (x,y) by the sum of
 * the real and imaginary planes squared.
 */
public class PowerImage extends RealGrayImage {

	/** Constructor that calls super() */
	public PowerImage() {
		super();
	}

	/** Creates an empty PowerImage with dimensions x,y. */
	public PowerImage(int x, int y) {
		super(x, y);
	}

	/** Deep copy a PowerImage. */
	public PowerImage(PowerImage image) {
		super(image);
	}

	/** Creates a PowerImage object from <i>image</i>. */
	public PowerImage(ComplexImage image) {
		super(image.X(), image.Y());
//		X = image.X();
//		Y = image.Y();
//		data = new float[Y][X];
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				float r = image.getReal(x, y);
				float im = image.getImag(x, y);
				data[y][x] = r * r + im * im;
			}
		}
	}

}
