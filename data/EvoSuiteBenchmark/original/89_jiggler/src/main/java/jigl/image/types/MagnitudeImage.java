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
 * A MagnitudeImage is the magnitude part of a ComplexImage. The value at (x,y) is computed by
 * taking the square root of the sum of Real and Imaginary parts squared.
 */
public class MagnitudeImage extends RealGrayImage {

	/** Constructor that calls super() */
	public MagnitudeImage() {
		super();
	}

	/** Creates an empty Magnitude Image */
	public MagnitudeImage(int x, int y) {
		super(x, y);
	}

	/** Creates a one dimensional MagnitudeImage (shallow copy) */
	public MagnitudeImage(MagnitudeImage image) {
		super(image);
	}

	/** Creates a MagnitudeImage from the Real and Imaginary planes of the ComplexImage */
	public MagnitudeImage(ComplexImage image) {
		super(image.X(), image.Y());
//		X = image.X();
//		Y = image.Y();
//		data = new Float[Y][X];
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				this.set(x, y, (float) Math.sqrt(image.getReal(x, y) * image.getReal(x, y)
						+ image.getImaginary(x, y) * image.getImaginary(x, y)));
			}
		}
	}

}
