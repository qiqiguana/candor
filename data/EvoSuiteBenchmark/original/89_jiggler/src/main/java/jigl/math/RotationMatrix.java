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
package jigl.math;

/**
 * Written By: Michael Reese Bastian
 * <P>
 * Last Modified: 24 October 1997
 * <P>
 * Rotation matrix for homogeneous coordinates. The center is positioned at the origin of the
 * coordinates.
 */
public class RotationMatrix extends Matrix {
	/**
	 * This constructor creates a 3-by-3 rotation matrix for homogeneous 2-dimensional coordinates.
	 * <P>
	 */
	public RotationMatrix(double theta) {
		super(3, 3);

		mem[0][0] = Math.cos(theta);
		mem[0][1] = Math.sin(theta);

		mem[1][0] = -Math.sin(theta);
		mem[1][1] = Math.cos(theta);

		mem[2][2] = 1.0;
	}

	/**
	 * This constructor creates a 4-by-4 rotation matrix for homogeneous 3-dimensional coordinates.
	 * <P>
	 */
	public RotationMatrix(double omega, double phi, double kappa) {
		super(4, 4);

		mem[0][0] = Math.cos(phi) * Math.cos(kappa);
		mem[0][1] = Math.sin(omega) * Math.sin(phi) * Math.cos(kappa) + Math.cos(omega)
				* Math.sin(kappa);
		mem[0][2] = -Math.cos(omega) * Math.sin(phi) * Math.cos(kappa) + Math.sin(omega)
				* Math.sin(kappa);

		mem[1][0] = -Math.cos(phi) * Math.sin(kappa);
		mem[1][1] = -Math.sin(omega) * Math.sin(phi) * Math.sin(kappa) + Math.cos(omega)
				* Math.cos(kappa);
		mem[1][2] = Math.cos(omega) * Math.sin(phi) * Math.sin(kappa) + Math.sin(omega)
				* Math.cos(kappa);

		mem[2][0] = Math.sin(phi);
		mem[2][1] = -Math.sin(omega) * Math.cos(phi);
		mem[2][2] = Math.cos(omega) * Math.cos(phi);

		mem[3][3] = 1.0;
	}
}
