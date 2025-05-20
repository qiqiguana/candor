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
 * A PhaseImage is phase part of a ComplexImage. The value at (x,y) is computed by taking the
 * arctangent of Imaginary plane and the Real plane.
 */
public class PhaseImage extends RealGrayImage {

	/** Constructor that calls super() */
	public PhaseImage() {
		super();
	}

	/** Creates an empty PhaseImage */
	public PhaseImage(int x, int y) {
		super(x, y);
	}

	/** Creates a one dimensional PhaseImage (shallow copy) */
	public PhaseImage(PhaseImage image) {
		super(image);
	}

	/** Creates a PhaseImage from the Real and Imaginary planes of the ComplexImage */
	public PhaseImage(ComplexImage image) {
		super(image.X(), image.Y());
//		X = image.X();
//		Y = image.Y();
//		data = new float[Y][X];
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data[y][x] = (float) Math.atan2(image.getImaginary(x, y), image.getReal(x, y));
			}
		}
	}

}
