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
package jigl.image.ops.morph;

import jigl.image.Image;
import jigl.image.ROI;
import jigl.image.io.ImageInputStream;
import jigl.image.io.ImageOutputStream;
import jigl.image.ops.SimpleOperator;
import jigl.image.types.BinaryImage;
import jigl.image.types.ImageKernel;

/** Erode performs an erode operation on an image. Supports BinaryImage. */
public class Erode extends SimpleOperator {
	private ImageKernel kernel;
	private int center_x;
	private int center_y;

	/** Initializes Erode */
	public Erode(ImageKernel ker, int x, int y) {
		kernel = ker;
		center_x = x;
		center_y = y;
	}

	/**
	 * Erodes a BinaryImage. Returned image is a BinaryImage. <code>image</code> is not modified.
	 * 
	 * @param image BinaryImage to erode.
	 * @return BinaryImage.
	 */
	protected Image apply(BinaryImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Erodes a BinaryImage in a Region of Interest. Returned image is a BinaryImage.
	 * <code>image</code> is not modified.
	 * 
	 * @param image BinaryImage to erode.
	 * @param roi Region of Interest of <code>image</code>
	 * @return BinaryImage.
	 */
	protected Image apply(BinaryImage image, ROI roi) {

		byte[][] temp = new byte[kernel.X()][kernel.Y()];
		BinaryImage image2 = (BinaryImage) image.copy();

		for (int x = roi.ux(); x <= roi.lx(); x++) {
			for (int y = roi.uy(); y <= roi.ly(); y++) {

				if (image.get(x, y) == 1) {

					for (int a = 0 - center_x; a < kernel.X() - center_x; a++)
						for (int b = 0 - center_y; b < kernel.Y() - center_y; b++) {
							if (a + x < roi.ux())
								temp[a + center_x][b + center_y] = 0;
							else if (b + y < roi.uy())
								temp[a + center_x][b + center_y] = 0;
							else if (a + x > roi.lx())
								temp[a + center_x][b + center_y] = 0;
							else if (b + y > roi.ux())
								temp[a + center_x][b + center_y] = 0;
							else
								temp[a + center_x][b + center_y] = image.get(a + x, b + y);
						}
					set: {
						for (int a = 0; a < kernel.X(); a++)
							for (int b = 0; b < kernel.Y(); b++)
								if ((kernel.get(a, b) == 1) && (temp[a][b] != 1)) {
									image2.set(x, y, (byte) 0);
									break set;
								}

						image2.set(x, y, (byte) 1);
					}
				}
			}
		}
		return image2;
	}

	/**
	 * For commandline option. The syntax is "java Erode <u>input_filename</u>
	 * <u>kernel_filename</u> <u>center_x</u> <u>center_y</u> <u>output_filename</u>"
	 */
	public static void main(String[] argv) {

		try {
			Image image = null;
			Image image2 = null;
			Image image3 = null;
			String inputfile = argv[0];
			String kernelfile = argv[1];

			ImageInputStream is = new ImageInputStream(inputfile);
			image = is.read();
			is.close();

			is = new ImageInputStream(kernelfile);
			image2 = is.read();
			is.close();

			Integer f_val1 = Integer.valueOf(argv[2]);
			Integer f_val2 = Integer.valueOf(argv[3]);
			int val1 = f_val1.intValue();
			int val2 = f_val2.intValue();

			Erode erode = new Erode((ImageKernel) image2, val1, val2);
			image3 = erode.apply(image);

			//put command line stuff here.

			// create a new ImageOutputStream
			ImageOutputStream os = new ImageOutputStream(argv[4]);
			os.write(image3);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
