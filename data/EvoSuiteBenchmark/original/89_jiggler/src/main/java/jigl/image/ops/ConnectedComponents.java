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

import jigl.image.exceptions.InvalidArgumentException;
import jigl.image.types.BinaryImage;
import jigl.image.types.GrayImage;

/** Creates the connected regions of a BinaryImage */
public class ConnectedComponents extends SimpleOperator {

	/** Creates Connected Image from neighbors UP, DOWN, LEFT, and RIGHT */
	public static final int FOUR_CONNECTED = 0;
	/** Creates Connected Image from all it's neighbors */
	public static final int EIGHT_CONNECTED = 1;

	/**
	 * Number of neighbors for calculation, either <code>FOUR_CONNECTED</code> or
	 * <code>EIGHT_CONNECTED</code>
	 */
	private int neighbors = FOUR_CONNECTED;
	/** Background color, either 0 or 1 */
	private int background = 0;

	/**
	 * Connected component constructor
	 * 
	 * @param numberNeighbors either FOUR_CONNECTED, or EIGHT_CONNECTED
	 * @param backgroundColor either 0 or 1
	 */
	public ConnectedComponents(int numberNeighbors, int backgroundColor)
			throws InvalidArgumentException {
		neighbors = numberNeighbors;
		background = backgroundColor;
		if ((neighbors != FOUR_CONNECTED && neighbors != EIGHT_CONNECTED)
				|| (background != 1 && background != 0))
			throw new InvalidArgumentException();
	}

	/**
	 * Returns the GrayImage made up of Connected Regions. the background color is set to zero, and
	 * the region values start at 1
	 * 
	 * @param image BinaryImage to calculate connected regions from
	 * @return GrayImage.
	 */
	protected GrayImage apply(BinaryImage image) {
		int equiv[] = new int[100000];
		int current = 1;
		int min = 1000;
		int[] friends = new int[4];
		GrayImage gimage = new GrayImage(image.X(), image.Y());
		for (int x = 0; x < 1000; x++)
			equiv[x] = -1;

		for (int x = 0; x < image.X(); x++)
			for (int y = 0; y < image.Y(); y++) {
				if (image.get(x, y) == background)
					gimage.set(x, y, 0);
				else {
					if ((x == 0) && (y == 0)) {
						gimage.set(0, 0, current);
						current++;
					} else {
						if (x - 1 == -1) {
							friends[0] = -1;
							friends[1] = -1;
							friends[2] = gimage.get(x, y - 1);
							if (x + 1 >= image.X())
								friends[3] = -1;
							else
								friends[3] = gimage.get(x + 1, y - 1);
						} else if (y - 1 == -1) {
							friends[0] = gimage.get(x - 1, y);
							friends[1] = -1;
							friends[2] = -1;
							friends[3] = -1;
						} else {
							friends[0] = gimage.get(x - 1, y);
							friends[1] = gimage.get(x - 1, y - 1);
							friends[2] = gimage.get(x, y - 1);
							if (x + 1 >= image.X())
								friends[3] = -1;
							else
								friends[3] = gimage.get(x + 1, y - 1);
						}

						if (neighbors == FOUR_CONNECTED) {

							if ((friends[0] <= 0) && (friends[2] <= 0)) {
								gimage.set(x, y, current);
								current++;
							} else if (friends[0] <= 0) {
								gimage.set(x, y, friends[2]);

							} else if (friends[2] <= 0) {
								gimage.set(x, y, friends[0]);

							} else if (friends[0] < friends[2]) {
								gimage.set(x, y, friends[0]);
								equiv[friends[2]] = friends[0];

							} else {
								gimage.set(x, y, friends[2]);
								if (friends[0] != friends[2])
									equiv[friends[0]] = friends[2];
							}
						} else if (neighbors == EIGHT_CONNECTED) {
							if ((friends[0] <= 0) && (friends[1] <= 0) && (friends[2] <= 0)
									&& (friends[3] <= 0)) {
								gimage.set(x, y, current);
								current++;
							} else {
								int minVal = 1000;
								for (int z = 0; z < 4; z++)
									if ((friends[z] < minVal) && (friends[z] != 0)
											&& (friends[z] != -1)) {
										min = z;
										minVal = friends[z];
									}
								gimage.set(x, y, friends[min]);
								for (int z = 0; z < 4; z++)
									if ((friends[z] != friends[min]) && (friends[z] != 0)
											&& (friends[z] != -1))
										equiv[friends[z]] = friends[min];

							}
						}
					}

				}
			}
		//			int tempVal=0;
		int curVal = 0;
		for (int x = 0; x < gimage.X(); x++)
			for (int y = 0; y < gimage.Y(); y++) {
				curVal = gimage.get(x, y);
				while (equiv[curVal] != -1) {
					curVal = equiv[curVal];
				}
				gimage.set(x, y, curVal);
			}
		return gimage;
	}
}
