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

/**
 * Open performs an open operation on an image. Supports BinaryImage.
 * <p>
 * An opening operation is an erosion followed by a dilation.
 */
public class Open extends SimpleOperator {
	private ImageKernel kernel;
	private int center_x;
	private int center_y;

	/** Initilizes Open */
	public Open(ImageKernel ker, int x, int y) {
		kernel = ker;
		center_x = x;
		center_y = y;
	}

	/**
	 * Performs an Open on a BinaryImage. Returned image is a BinaryImage. <code>image</code> is not
	 * modified.
	 * 
	 * @param image BinaryImage to perform open on.
	 * @return BinaryImage.
	 */
	protected Image apply(BinaryImage image) {
		BinaryImage image2 = (BinaryImage) image.copy();
		Dilate d = new Dilate(kernel, center_x, center_y);
		Erode e = new Erode(kernel, center_x, center_y);
		image2 = (BinaryImage) e.apply(image);
		image2 = (BinaryImage) d.apply(image2);
		return image2;

	}

	/**
	 * Performs an Open on a BinaryImage in a Region of Interest. Returned image is a BinaryImage.
	 * <code>image</code> is not modified.
	 * 
	 * @param image BinaryImage to perform open on.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return BinaryImage.
	 */
	protected Image apply(BinaryImage image, ROI roi) {
		BinaryImage image2 = (BinaryImage) image.copy();
		Dilate d = new Dilate(kernel, center_x, center_y);
		Erode e = new Erode(kernel, center_x, center_y);
		image2 = (BinaryImage) e.apply(image, roi);
		image2 = (BinaryImage) d.apply(image2, roi);
		return image2;

	}

	/**
	 * For commandline option. The syntax is "java Close <u>input_filename</u>
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

			Open open = new Open((ImageKernel) image2, val1, val2);
			image3 = open.apply((BinaryImage) image);

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
