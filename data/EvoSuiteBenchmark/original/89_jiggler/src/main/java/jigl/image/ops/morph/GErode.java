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
import jigl.image.types.ColorImage;
import jigl.image.types.GrayImage;
import jigl.image.types.ImageKernel;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * GErode performs an erode operation on an image. Supports GrayImage, RealGrayImage, ColorImage,
 * RealColorImage.
 */
public class GErode extends SimpleOperator {

	private ImageKernel kernel;
	private int center_x;
	private int center_y;

	/** Initilizes Erode */
	public GErode(ImageKernel ker, int x, int y) {
		kernel = ker;
		center_x = x;
		center_y = y;
	}

	/**
	 * Erodes a GrayImage. Returned image is a GrayImage. <code>image</code> is not modified.
	 * 
	 * @param image GrayImage to erode.
	 * @return GrayImage.
	 */
	protected Image apply(GrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Erodes a RealGrayImage. Returned image is a RealGrayImage. <code>image</code> is not
	 * modified.
	 * 
	 * @param image RealGrayImage to erode.
	 * @return RealGrayImage.
	 */
	protected Image apply(RealGrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Erodes a GrayImage in a Region of Interest. Returned image is a GrayImage. <code>image</code>
	 * is not modified.
	 * 
	 * @param image GrayImage to erode.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return GrayImage.
	 */
	protected Image apply(GrayImage image, ROI roi) {
		float[][] temp = new float[kernel.X()][kernel.Y()];
		float ntemp = 0;
		int min = 255;

		GrayImage image2 = image.copy();

		for (int x = roi.ux(); x <= roi.lx(); x++) {
			for (int y = roi.uy(); y <= roi.ly(); y++) {

				for (int a = 0; a < kernel.X(); a++)
					for (int b = 0; b < kernel.Y(); b++) {
						if (center_x - a + x < 0)
							temp[a][b] = 0;
						else if (center_y - b + y < 0)
							temp[a][b] = 0;
						else if (center_x - a + x > image.X() - 1)
							temp[a][b] = 0;
						else if (center_y - b + y > image.Y() - 1)
							temp[a][b] = 0;
						else
							temp[a][b] = image.get(center_x - a + x, center_y - b + y);
					}

				for (int a = 0; a < kernel.X(); a++)
					for (int b = 0; b < kernel.Y(); b++) {
						ntemp = temp[a][b] - kernel.get(a, b);
						if (min > ntemp)
							min = (int) ntemp;
						//System.out.print(ntemp+" ");
					}
				//System.out.println("At "+x+" "+y+"   set to value "+max);  
				image2.set(x, y, min);
				min = 255;

			}
		}
		return image2;
	}

	/**
	 * Erodes a RealGrayImage in a Region of Interest. Returned image is a RealGrayImage.
	 * <code>image</code> is not modified.
	 * 
	 * @param image RealGrayImage to erode.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return RealGrayImage.
	 */
	protected Image apply(RealGrayImage image, ROI roi) {
		float[][] temp = new float[kernel.X()][kernel.Y()];
		float ntemp = 0;
		float min = 255;

		RealGrayImage image2 = (RealGrayImage) image.copy();

		for (int x = roi.ux(); x <= roi.lx(); x++) {
			for (int y = roi.uy(); y <= roi.ly(); y++) {

				for (int a = 0; a < kernel.X(); a++)
					for (int b = 0; b < kernel.Y(); b++) {
						if (center_x - a + x < 0)
							temp[a][b] = 0;
						else if (center_y - b + y < 0)
							temp[a][b] = 0;
						else if (center_x - a + x > image.X() - 1)
							temp[a][b] = 0;
						else if (center_y - b + y > image.Y() - 1)
							temp[a][b] = 0;
						else
							temp[a][b] = image.get(center_x - a + x, center_y - b + y);
					}

				for (int a = 0; a < kernel.X(); a++)
					for (int b = 0; b < kernel.Y(); b++) {
						ntemp = temp[a][b] - kernel.get(a, b);
						if (min > ntemp)
							min = ntemp;
						//System.out.print(ntemp+" ");
					}
				//System.out.println("At "+x+" "+y+"   set to value "+max);  
				image2.set(x, y, min);
				min = 255;

			}
		}
		return image2;
	}

	/**
	 * Erodes a ColorImage. This is done to each GrayImage plane separately. Returned image is a
	 * ColorImage. <code>image</code> is not modified.
	 * 
	 * @param image ColorImage to erode.
	 * @return ColorImage.
	 */
	protected Image apply(ColorImage image) {
		image.setPlane(0, (GrayImage) apply(image.plane(0), new ROI(0, 0, image.X() - 1,
				image.Y() - 1)));
		image.setPlane(1, (GrayImage) apply(image.plane(1), new ROI(0, 0, image.X() - 1,
				image.Y() - 1)));
		image.setPlane(2, (GrayImage) apply(image.plane(2), new ROI(0, 0, image.X() - 1,
				image.Y() - 1)));

		return image;
	}

	/**
	 * Erodes a ColorImage in a Region of Interest. This is done to each GrayImage plane separately.
	 * Returned image is a ColorImage. <code>image</code> is not modified.
	 * 
	 * @param image ColorImage to erode.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return ColorImage.
	 */
	protected Image apply(ColorImage image, ROI roi) {
		image.setPlane(0, (GrayImage) apply(image.plane(0), roi));
		image.setPlane(1, (GrayImage) apply(image.plane(1), roi));
		image.setPlane(2, (GrayImage) apply(image.plane(2), roi));

		return image;
	}

	/**
	 * Erodes a RealColorImage in a Region of Interest. This is done to each RealGrayImage plane
	 * separately. Returned image is a RealColorImage. <code>image</code> is not modified.
	 * 
	 * @param image RealColorImage to erode.
	 * @return RealColorImage.
	 */
	protected Image apply(RealColorImage image) {
		image.setPlane(0, (RealGrayImage) apply(image.plane(0), new ROI(0, 0, image.X() - 1, image
				.Y() - 1)));
		image.setPlane(1, (RealGrayImage) apply(image.plane(1), new ROI(0, 0, image.X() - 1, image
				.Y() - 1)));
		image.setPlane(2, (RealGrayImage) apply(image.plane(2), new ROI(0, 0, image.X() - 1, image
				.Y() - 1)));

		return image;
	}

	/**
	 * Erodes a RealColorImage in a Region of Interest. This is done to each RealGrayImage plane
	 * separately. Returned image is a RealColorImage. <code>image</code> is not modified.
	 * 
	 * @param image RealColorImage to erode.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return RealColorImage.
	 */
	protected Image apply(RealColorImage image, ROI roi) {
		image.setPlane(0, (RealGrayImage) apply(image.plane(0), roi));
		image.setPlane(1, (RealGrayImage) apply(image.plane(1), roi));
		image.setPlane(2, (RealGrayImage) apply(image.plane(2), roi));

		return image;
	}

	/**
	 * For commandline option. The syntax is "java GErode <u>input_filename</u>
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

			GErode erode = new GErode((ImageKernel) image2, val1, val2);
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
