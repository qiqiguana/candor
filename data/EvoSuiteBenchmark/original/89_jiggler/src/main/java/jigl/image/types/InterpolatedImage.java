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

import jigl.image.Image;
import jigl.image.InterpolationMethod;

/**
 * Interface for interpolation of Images. Each class that implements this interface should have an
 * <code>interp(float x, float y)</code> method to support floating-point coordinate access.
 */
public interface InterpolatedImage<T> extends Image<T> {

	/**
	 * Returns the interpolation method for this interpolated image.
	 */
	public InterpolationMethod getInterpolationMethod();

	/**
	 * Sets the interpolation method for this interpolated image.
	 */
	public void setInterpolationMethod(InterpolationMethod m);

	T interp(float x, float y);
}
