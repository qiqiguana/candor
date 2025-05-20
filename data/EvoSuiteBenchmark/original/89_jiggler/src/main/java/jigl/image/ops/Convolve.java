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
import jigl.image.io.ImageInputStream;
import jigl.image.io.ImageOutputStream;
import jigl.image.types.ColorImage;
import jigl.image.types.GrayImage;
import jigl.image.types.ImageKernel;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;
import jigl.image.utils.ImageConverter;

/**
 * Performs Convolution on an image using a supplied normalized kernel. Supports GrayImage,
 * RealGrayImage, ColorImage, RealColorImage.
 * <p>
 * If the kernel is not normalized, the convolution result may look strange.
 */
public class Convolve extends SimpleOperator {
	/** The image kernel for convolution */
	private ImageKernel kernel = null;

	/**
	 * Initializes Convolve. Assumes that the width and height of <code>k</code> are odd, and the
	 * kernel is normalized.
	 */
	public Convolve(ImageKernel k) {
		kernel = k;
	}

	/** Inner class for class Convolve. */
	class Threader implements Runnable {

		Thread runner = null;
		GrayImage img = null;
		Convolve conv = null;

		public Threader(GrayImage image, ImageKernel k) {
			img = image;
			conv = new Convolve(k);
		}

		public void start() {
			runner = new Thread(this);
			runner.start();
		}

		public void run() {
			try {
				img = conv.apply(img);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Applies the kernel to <code>image</code>. The center of the kernel is specified at the
	 * position of <code>(kernel.X()/2, kernel.Y()/2)</code>. Returned image is a GrayImage of the
	 * same size as the original image. <code>image</code> is not modified.
	 * 
	 * @param image GrayImage to convolve.
	 * @return GrayImage.
	 */
	protected GrayImage apply(GrayImage image) throws ImageNotSupportedException {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Applies the kernel to <code>image</code> in a Region of Interest. The enter of the kernel is
	 * specified at the position of <code>(kernel.X()/2, kernel.Y()/2)</code>. Returned image is a
	 * GrayImage of the same size with the area under the <code>roi</code> are convolved.
	 * <code>image</code> is not modified.
	 * 
	 * @param image GrayImage to convolve.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return GrayImage.
	 */
	protected GrayImage apply(GrayImage image, ROI roi) throws ImageNotSupportedException {
		GrayImage image2 = new GrayImage(image.X(), image.Y());
		int NumX = kernel.X();
		int NumY = kernel.Y();
		//		int X=image.X();
		//	  int Y=image.Y();
		int midX = NumX / 2;
		int midY = NumY / 2;

		double sum = 0;

		//if((kernel.X()%2!=1)||(kernel.Y()%2!=1))
		//	System.out.println("The width or height of Kernel is not odd. It still works but the result is not accurate.");

		// for every pixel in the original image
		for (int y = 0; y < image.Y(); y++) {
			//System.out.print('.');
			for (int x = 0; x < image.X(); x++) {
				if (y < roi.uy() || y > roi.ly() || x < roi.ux() || x > roi.lx()) {
					image2.set(x, y, image.get(x, y));

				} else {
					//Convolve with the kernel
					sum = 0;
					for (int j = -midY; j <= midY; j++) {
						if ((y + j < 0) || (y + j >= image.Y()))
							continue;
						for (int i = -midX; i <= midX; i++) {
							if ((x + i < 0) || (x + i >= image.X()))
								continue;
							try {
								sum += image.get((x + i), (y + j))
										* kernel.get((midX + i), (midY + j));
							} catch (Exception e) {
							}
						}
					}

					image2.set(x, y, (int) sum);
				}
			}
		}
		System.out.println("\nConvolution end.");
		return image2;
	}

	/**
	 * Applies the kernel to <code>image</code>. The center of the kernel is specified at the
	 * position of <code>(kernel.X()/2, kernel.Y()/2)</code>. Returned image is a RealGrayImage of
	 * the same size as the original image. <code>image</code> is not modified.
	 * 
	 * @param image RealGrayImage to convolve.
	 * @return RealGrayImage.
	 */
	protected RealGrayImage apply(RealGrayImage image) {
		return apply(image, new ROI(0, 0, image.X() - 1, image.Y() - 1));
	}

	/**
	 * Applies the kernel to <code>image</code> in a Region of Interest. The center of the kernel is
	 * specified at the position of <code>(kernel.X()/2, kernel.Y()/2)</code>. Returned image is a
	 * RealGrayImage of the same size with the area under the <code>roi</code> are convolved.
	 * <code>image</code> is not modified.
	 * 
	 * @param image RealGrayImage to convolve.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return RealGrayImage.
	 */
	protected RealGrayImage apply(RealGrayImage image, ROI roi) {
		RealGrayImage image2 = new RealGrayImage(image.X(), image.Y());
		int NumX = kernel.X();
		int NumY = kernel.Y();
		//		int X=image.X();
		//	  int Y=image.Y();
		int midX = NumX / 2;
		int midY = NumY / 2;

		double sum = 0;
		//if((kernel.X()%2!=1)||(kernel.Y()%2!=1))
		//	System.out.println("The width or height of Kernel is not odd. It still works but the result is not accurate.");

		// for every pixel in the original image
		for (int y = 0; y < image.Y(); y++) {
			System.out.print('.');
			for (int x = 0; x < image.X(); x++) {
				if (y < roi.uy() || y > roi.ly() || x < roi.ux() || x > roi.lx()) {
					image2.set(x, y, image.get(x, y));
				} else {
					//Convolve with the kernel
					sum = 0;

					for (int j = -midY; j <= midY; j++) {
						if ((y + j < 0) || (y + j >= image.Y()))
							continue;
						for (int i = -midX; i <= midX; i++) {
							if ((x + i < 0) || (x + i >= image.X()))
								continue;
							try {
								sum += image.get((x + i), (y + j))
										* kernel.get((midX + i), (midY + j));
							} catch (Exception e) {
							}
						}
					}

					image2.set(x, y, (float) sum);
				}
			}
		}
		System.out.println("\nConvolution end.");
		return image2;
	}

	/**
	 * Applies the kernel to <code>image</code>. Kernel is applied to each plane of the image
	 * separately. The center of the kernel is specified at the position of
	 * <code>(kernel.X()/2, kernel.Y()/2)</code>. Returned image is a ColorImage of the same size.
	 * 
	 * @param image ColorImage to convolve.
	 * @return ColorImage.
	 */
	protected ColorImage apply(ColorImage image) throws ImageNotSupportedException {
		ColorImage image2 = new ColorImage(image.X(), image.Y());
		image2.setPlane(0, apply(image.plane(0)));
		image2.setPlane(1, apply(image.plane(1)));
		image2.setPlane(2, apply(image.plane(2)));
		return image2;
	}

	/**
	 * Applies the kernel to <code>image</code> with a Region of Interest. Kernel is applied to each
	 * plane of the image separately. The center of the kernel is specified at the position of
	 * <code>(kernel.X()/2, kernel.Y()/2)</code>. Returned image is a ColorImage of the same size.
	 * <code>image</code> is not modified.
	 * 
	 * @param image ColorImage to convolve.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return ColorImage.
	 */
	protected ColorImage apply(ColorImage image, ROI roi) throws ImageNotSupportedException {
		ColorImage image2 = new ColorImage(image.X(), image.Y());
		image2.setPlane(0, apply(image.plane(0), roi));
		image2.setPlane(1, apply(image.plane(1), roi));
		image2.setPlane(2, apply(image.plane(2), roi));
		return image2;
	}

	/**
	 * Applies the kernel to a RealColorImage. Kernel is applied to each plane of the image
	 * separately. The center of the kernel is specified at the position of
	 * <code>(kernel.X()/2, kernel.Y()/2)</code>. <code>image</code> is not modified. Returned image
	 * is a RealColorImage of the same size.
	 * 
	 * @param image RealColorImage to convolve.
	 * @return RealColorImage.
	 */
	protected RealColorImage apply(RealColorImage image) {
		RealColorImage image2 = new RealColorImage(image.X(), image.Y());
		image2.setPlane(0, apply(image.plane(0)));
		image2.setPlane(1, apply(image.plane(1)));
		image2.setPlane(2, apply(image.plane(2)));
		return image2;
	}

	/**
	 * Applies the kernel to a RealColorImage with a Region of Interest. Kernel is applied to each
	 * plane of the image separately. The center of the kernel is specified at the position of
	 * <code>(kernel.X()/2, kernel.Y()/2)</code>. Returned image is a RealColorImage of the same
	 * size. <code>image</code> is not modified.
	 * 
	 * @param image RealColorImage to convolve.
	 * @param roi Region of Interest of <code>image</code>.
	 * @return RealColorImage.
	 */
	protected RealColorImage apply(RealColorImage image, ROI roi) {
		RealColorImage image2 = new RealColorImage(image.X(), image.Y());
		image2.setPlane(0, apply(image.plane(0), roi));
		image2.setPlane(1, apply(image.plane(1), roi));
		image2.setPlane(2, apply(image.plane(2), roi));
		return image2;
	}

	/** Applies a threaded version for faster execution on machine supporting multi-threads. */
	public ColorImage applyThreaded(ColorImage gr) {

		ColorImage image = new ColorImage(gr.X(), gr.Y());
		Threader th0 = new Threader(gr.plane(0), kernel);
		th0.start();

		Threader th1 = new Threader(gr.plane(1), kernel);
		th1.start();

		Threader th2 = new Threader(gr.plane(2), kernel);
		th2.start();

		try {
			th0.runner.join();
			th1.runner.join();
			th2.runner.join();
		} catch (Exception e) {
		}

		image.setPlane(0, th0.img);
		image.setPlane(1, th1.img);
		image.setPlane(2, th2.img);

		return image;
	}

	/**
	 * Allows for command line options. The first commandline parameter is the image file, the
	 * second commandline parameter is the kernel file and the third is the file for output.
	 */
	public static void main(String[] argv) {
		if (argv.length != 3) {
			System.out.println("Error: should be 3 commandline parameters.");
			return;
		}

		try {
			Image<?> image = null;
			String inputfile = argv[0];
			RealGrayImage image2 = null;
			String inputfile2 = argv[1];

			ImageInputStream is = new ImageInputStream(inputfile);
			image = is.read();
			is.close();

			is = new ImageInputStream(inputfile2);
			Image<?> temp = is.read();
			image2 = ImageConverter.toRealGray(temp);
			//image2 = 
			is.close();

			ImageKernel ker = new ImageKernel(image2);
			ker.normalize();
			Convolve convolve = new Convolve(ker);
			Image<?> newimage = convolve.apply(image);

			// create a new ImageOutputStream
			ImageOutputStream os = new ImageOutputStream(argv[2]);
			os.write(newimage);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
