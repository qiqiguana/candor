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

import java.awt.image.ImageProducer;

/**
 * An image accessible on a 2-dimensional plane. It is recommended that
 * implementations deal with pixel accesses in a row-major fashion.
 */
public interface Image<T> {	
	/** Returns the width of the image */
	public int X();

	/** Returns the height of the image */
	public int Y();

	//---Access and Manipulation---
	T get(int x, int y);
	
	void set(int x, int y, T value);
	
	void set(int x, int y, T value, ROI roi);
	
	Image<T> clear();
	
	Image<T> clear(T constant);
	
	T min();
	
	T max();
	
	/** Returns a string representation of an image */
	public String toString();

	/**
	 * Turns this image into a Java Image (java.awt.Image).
	 * 
	 * @param none
	 * @see java.awt.image.ImageProducer
	 */
	public ImageProducer getJavaImage();

	/** Returns a deep copy of an image */
	public Image<T> copy();

	/**
	 * Returns a deep copy of the specified region of interest of the image.
	 * 
	 * @param r region of interest of the image.
	 */
	public Image<T> copy(ROI r);

}
