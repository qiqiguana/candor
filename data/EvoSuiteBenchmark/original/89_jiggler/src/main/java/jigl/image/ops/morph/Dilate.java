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

/** Dilate performs a dilate operation on an image. Supports BinaryImage. */
public class Dilate extends SimpleOperator {

	private ImageKernel kernel;
	private int center_x;
	private int center_y;

	/**
	 * Initializes Dilate
	 * 
	 * @param ker structuring element.
	 * @param x x coordinate of center of element.
	 * @param y y coordinate of center of element.
	 */
	public Dilate(ImageKernel ker, int x, int y) {
		kernel = ker;
		center_x = x;
		center_y = y;
	}

	/**
	 * Dilates a BinaryImage. <code>image</code> is not modified.
	 * 
	 * @param image BinaryImage to dilate.
	 * @return BinaryImage.
	 */
	protected Image apply(BinaryImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Dilates a BinaryImage in a Region of Interest. Returned image is a BinaryImage.
	 * <code>image</code> is not modified.
	 * 
	 * @param image BinaryImage to dilate.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return BinaryImage.
	 */
	protected Image apply(BinaryImage image, ROI roi) {
		BinaryImage image2 = (BinaryImage) image.copy();
		int x1 = 0;
		int y1 = 0;

		System.out.println("binary dilate");

		for (int y = roi.uy(); y <= roi.ly(); y++)
			for (int x = roi.ux(); x <= roi.lx(); x++)
				if (image.get(x, y) > 0)
					for (int a = 0 - center_x; a < kernel.X() - center_x; a++)
						for (int b = 0 - center_y; b < kernel.Y() - center_y; b++)
							if (kernel.get(a + center_x, b + center_y) > 0) {
								x1 = x + a;
								y1 = y + b;
								System.out.println("HI:" + x1 + " " + y1);

								if ((x1 < roi.lx()) && (y1 < roi.ly()) && (x1 >= roi.ux())
										&& (y1 >= roi.uy()))
									image2.set(x1, y1, (byte) 1);
							}
		return image2;
	}

	/**
	 * For commandline option. The syntax is "java Dilate <u>input_filename</u>
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

			Dilate dilate = new Dilate((ImageKernel) image2, val1, val2);
			image3 = dilate.apply(image);

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
