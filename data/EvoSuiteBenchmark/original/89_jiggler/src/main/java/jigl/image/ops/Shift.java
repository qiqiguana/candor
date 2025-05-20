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

import jigl.image.types.ColorImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/** Shifts an image. Supports GrayImage, RealGrayImage, ColorImage, RealColorImage. */
public class Shift extends SimpleOperator {
	/** Flag for not using wrap */
	public static final int NOWRAP = 0;
	/** Flag for using wrap. */
	public static final int WRAP = 1;

	/** Shift right if <code>xoffset>0</code>. */
	private int xoffset = 0;
	/** Shift up if <code>yoffset>0</code>. */
	private int yoffset = 0;
	/** The flag of using wrapping */
	private boolean wrap = false;

	/**
	 * Constructs a shift
	 * 
	 * @param x shift along the x axis
	 * @param y shift along the y axis
	 * @param wrapp NOWRAP= no wrap WRAP=wrap.
	 */
	public Shift(int x, int y, int wrap) {
		xoffset = x;
		yoffset = y;
		if (wrap == 1)
			this.wrap = true;

	}

	/**
	 * Shifts a GrayImage. Returned image is a GrayImage. <code>image</code> is not modified.
	 * 
	 * @param image GrayImage to shift.
	 * @return GrayImage.
	 */
	protected GrayImage apply(GrayImage image) {
		int X = image.X();
		int Y = image.Y();
		//Convert to the appropriate type
		GrayImage temp = new GrayImage(X, Y);

		if (wrap) {
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					if (xoffset > 0 && yoffset > 0)
						temp.set((x + xoffset) % X, (y + yoffset) % Y, image.get(x, y));
					else if (xoffset > 0) {
						temp.set((x + xoffset) % X, (Y + yoffset + y) % Y, image.get(x, y));
					} else if (yoffset > 0) {
						temp.set((X + xoffset + x) % X, (y + yoffset) % Y, image.get(x, y));
					} else {
						temp.set((X + xoffset + x) % X, (y + yoffset + Y) % Y, image.get(x, y));
					}
				}
			}
		} else {
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					if ((x + xoffset >= 0) && (x + xoffset < X) && (y + yoffset >= 0)
							&& (y + yoffset < Y))
						temp.set(x, y, image.get(x + xoffset, y + yoffset));
					else
						temp.set(x, y, 0);
				}
			}
		}
		return temp;
	}

	/**
	 * Shifts a RealGrayImage. Returned image is a RealGrayImage. <code>image</code> is not
	 * modified.
	 * 
	 * @param image RealGrayImage to shift.
	 * @return RealGrayImage.
	 */
	protected RealGrayImage apply(RealGrayImage image) {
		int X = image.X();
		int Y = image.Y();
		//Convert to the appropriate type
		RealGrayImage temp = new RealGrayImage(X, Y);

		if (wrap) {
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					if (xoffset > 0 && yoffset > 0)
						temp.set((x + xoffset) % X, (y + yoffset) % Y, image.get(x, y));
					else if (xoffset > 0) {
						temp.set((x + xoffset) % X, (Y + yoffset + y) % Y, image.get(x, y));
					} else if (yoffset > 0) {
						temp.set((X + xoffset + x) % X, (y + yoffset) % Y, image.get(x, y));
					} else {
						temp.set((X + xoffset + x) % X, (y + yoffset + Y) % Y, image.get(x, y));
					}
				}
			}
		} else {
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					if ((x + xoffset >= 0) && (x + xoffset < X) && (y + yoffset >= 0)
							&& (y + yoffset < Y))
						temp.set(x, y, image.get(x + xoffset, y + yoffset));
					else
						temp.set(x, y, 0f);
				}
			}
		}
		return temp;
	}

	/**
	 * Shifts a ColorImage. This is done by shifting each plane seperately. Returned image is a
	 * ColorImage. <code>image</code> is not modified.
	 * 
	 * @param image ColorImage to shift.
	 * @return ColorImage.
	 */
	protected ColorImage apply(ColorImage image) {
		ColorImage temp = new ColorImage(image.X(), image.Y());
		temp.setPlane(0, apply(image.plane(0)));
		temp.setPlane(1, apply(image.plane(1)));
		temp.setPlane(2, apply(image.plane(2)));
		return temp;

	}

	/**
	 * Shifts a RealColorImage. This is done by shifting each plane seperately. Returned image is a
	 * RealColorImage. <code>image</code> is not modified.
	 * 
	 * @param image RealColorImage to shift.
	 * @return RealColorImage.
	 */
	protected RealColorImage apply(RealColorImage image) {
		RealColorImage temp = new RealColorImage(image.X(), image.Y());
		temp.setPlane(0, apply(image.plane(0)));
		temp.setPlane(1, apply(image.plane(1)));
		temp.setPlane(2, apply(image.plane(2)));
		return temp;

	}

}
