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
package jigl.image.ops;

import jigl.image.Image;
import jigl.image.ROI;
import jigl.image.exceptions.ImageNotSupportedException;

/**
 * Operator is an interface for performing operations on an image.
 * 
 * @see jigl.image.ops.SimpleOperator
 */
public interface Operator {
	@SuppressWarnings("unchecked")
	public Image apply(Image image) throws ImageNotSupportedException;

	@SuppressWarnings("unchecked")
	public Image apply(Image image, ROI roi) throws ImageNotSupportedException;
}
