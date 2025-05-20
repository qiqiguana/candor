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
package jigl.image.ops.levelOps;

import jigl.image.Image;
import jigl.image.io.ImageInputStream;
import jigl.image.io.ImageOutputStream;
import jigl.image.ops.SimpleOperator;
import jigl.image.types.ColorImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * Performs a ByteSize operation on an image. <code>image</code> is changed in all the methods of
 * this class. Supports GrayImage, RealGrayImage, ColorImage and RealColorImage.
 * <p>
 * ByteSize operation is to scale the range of each plane of an image to [0..255].
 */
public class ByteSize extends SimpleOperator {
	//private Image im=null;

	/** Constructor. */
	public ByteSize() {
	}

	/**
	 * ByteSizes the range of <code>image</code>.
	 * 
	 * @param image GrayImage to byte-size.
	 * @return <code>image</code>.
	 */
	protected GrayImage apply(GrayImage image) {
		float min = image.min();
		float max = image.max();

		float range = max - min;

		// convert to byte depth
		int value = 0;
		for (int y = 0; y < image.Y(); y++) {
			for (int x = 0; x < image.X(); x++) {
				value = (int) ((255.0 / range) * ((float) image.get(x, y) - min));
				value = 0x00FF & value;
				image.set(x, y, value);
			}
		}
		return image;
	}

	/**
	 * ByteSizes the range of <code>image</code>.
	 * 
	 * @param image RealGrayImage to byte-size
	 * @return <code>image</code>
	 */
	protected RealGrayImage apply(RealGrayImage image) {
		float min = image.min();
		float max = image.max();

		float range = max - min;

		// convert to byte depth
		float value = 0;
		for (int y = 0; y < image.Y(); y++) {
			for (int x = 0; x < image.X(); x++) {
				value = (float) ((255.0 / range) * (image.get(x, y) - min));
				//value = 0x00FF & (int)value;
				image.set(x, y, value);
			}
		}
		return image;
	}

	/**
	 * ByteSizes the range of <code>image</code>. The range used for all planes is the global
	 * maximum, minimum of the image.
	 * 
	 * @param image ColorImage to byte-size.
	 * @return <code>image</code>.
	 */
	protected ColorImage apply(ColorImage image) {
		GrayImage plane;
		
		float min = image.minComponent();
		float max = image.maxComponent();
		float range = max - min;

		int value;

		for (int i = 0; i < 3; i++) {
			plane = image.plane(i);

			for (int y = 0; y < plane.Y(); y++) {
				for (int x = 0; x < plane.X(); x++) {
					value = (int) ((255.0 / range) * ((float) plane.get(x, y) - min));
					value = 0x00FF & value;
					plane.set(x, y, value);
				}
			}

			image.setPlane(i, plane);
		}

		return image;
	}

	/**
	 * ByteSizes the range of <code>image</code>. The range used for all planes is the global max,
	 * min of the image.
	 * 
	 * @param image RealColorImage to byte-size.
	 * @return <code>image</code>.
	 */
	protected RealColorImage apply(RealColorImage image) {
		RealGrayImage plane;
		float min = image.minComponent();
		float max = image.maxComponent();
		float range = max - min;

		float value;

		for (int i = 0; i < 3; i++) {
			plane = image.plane(i);

			for (int y = 0; y < plane.Y(); y++) {
				for (int x = 0; x < plane.X(); x++) {
					value = (float) ((255.0 / range) * (plane.get(x, y) - min));
					//value = 0x00FF & (int)value;
					plane.set(x, y, value);
				}
			}

			image.setPlane(i, plane);
		}

		return image;
	}

	/**
	 * For commandline operation. You should provide two argument: the image file name, and the
	 * result image file name.
	 */
	public static void main(String[] argv) {

		try {
			Image<?> image = null;
			String inputfile = argv[0];
			Image<?> image2 = null;

			ImageInputStream is = new ImageInputStream(inputfile);
			image = is.read();
			is.close();

			ByteSize bytesize = new ByteSize();

			image2 = bytesize.apply(image);

			//put command line stuff here.

			// create a new ImageOutputStream
			ImageOutputStream os = new ImageOutputStream(argv[1]);
			os.write(image2);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
