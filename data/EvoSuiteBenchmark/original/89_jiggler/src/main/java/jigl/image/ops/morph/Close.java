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
 * Close performs a close operation on an image. Supports BinaryImage.
 * <p>
 * A closing operation is a dilation operation followed by a erosion.
 */
public class Close extends SimpleOperator {
	private ImageKernel kernel;
	private int center_x;
	private int center_y;

	/**
	 * Initializes Close
	 * 
	 * @param ker ImageKernel
	 * @param x center x value
	 * @param y center y value
	 */
	public Close(ImageKernel ker, int x, int y) {
		kernel = ker;
		center_x = x;
		center_y = y;
	}

	/**
	 * Performs a Close operation on <code>image</code>. <code>image</code> is not modified.
	 * 
	 * @param image BinaryImage to close.
	 * @return BinaryImage.
	 */
	protected Image apply(BinaryImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Performs a Close operation on <code>image</code> in a Region of Interest. <code>image</code>
	 * is not modified.
	 * 
	 * @param image BinaryImage to close.
	 * @param roi Region of Interest of <code>image</code>
	 * @return BinaryImage.
	 */
	protected Image apply(BinaryImage image, ROI roi) {
		BinaryImage image2 = (BinaryImage) image.copy();
		Dilate d = new Dilate(kernel, center_x, center_y);
		Erode e = new Erode(kernel, center_x, center_y);
		image2 = (BinaryImage) d.apply(image, roi);
		image2 = (BinaryImage) e.apply(image2, roi);
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

			Close close = new Close((ImageKernel) image2, val1, val2);
			image3 = close.apply((BinaryImage) image);

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
