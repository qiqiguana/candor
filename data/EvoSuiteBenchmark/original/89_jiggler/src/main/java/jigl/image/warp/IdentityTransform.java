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
package jigl.image.warp;


/** Performs Identity Warp on a MappedImage. It is not implemented yet.*/
public class IdentityTransform implements PointMapper{
	
  public IdentityTransform(){
	}
	
  /** Performs the Identity Transformation
	@param x an array containing the x coordinates
	@param y an array containing the y coordinates
	*/
	public void transform(float[] x, float[] y) throws IllegalArgumentException {
	}
	
  /** Performs the inverse of the Identity Transformation
	@param x an array containing the x coordinates
	@param y an array containing the y coordinates
	*/
	public void inverseTransform(float[] x, float[] y) throws IllegalArgumentException {
	}
	
	/** Performs the Identity Transformation
	@param x an array containing the x coordinates
	@param y an array containing the y coordinates
	@param offset the starting point of the array
	@param count the number of points from the <i>offset</i> to apply the transformation*/
	public void transform(float[] x, float[] y, int offset, int count) throws IllegalArgumentException {
	}
	
  /** Performs the inverse of the Identity Transformation
	@param x an array containing the x coordinates
	@param y an array containing the y coordinates
	@param offset the starting point of the array
	@param count the number of points from the <i>offset</i> to apply the transformation*/
	public void inverseTransform(float[] x, float[] y, int offset, int count) throws IllegalArgumentException {
	}
} // image

