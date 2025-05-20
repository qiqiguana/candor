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

import java.util.Date;

import jigl.gui.CloseableFrame;
import jigl.gui.ImageCanvas;
import jigl.image.io.ImageInputStreamJAI;
import jigl.image.types.ColorImage;
import jigl.image.types.RealColorImage;
import jigl.image.utils.ImageConverter;
import jigl.image.utils.KdTree;

/**
 * This class provides the basic Floyd-Steinberg algorithm for dithering. The weights used are 7/16,
 * 3/16, 5/16, 1/16. Supports ColorImage.
 * 
 * @see <a href="../../../others/dither.html">Floyd-Steinberg Dithering</a>
 */
public class Dither extends SimpleOperator {
	/** Stores the image. */
	private KdTree tree2;
	int threshold = 8;
	private int exact = -999;

	/**
	 * Creates a new instance of Dither. Note, this function uses a KdTree which is calculated in
	 * this constructor.
	 * 
	 * @see KdTree
	 */
	public Dither(RealColorImage paletteImage) {
		tree2 = new KdTree(3);
		tree2.buildTreePalette(paletteImage);
	}

	/**
	 * Applies the palette to a ColorImage applying Floyd-Steinberg dithering. Returned Image is a
	 * ColorImage. <code>image</code> is not modified.
	 * 
	 * @param image ColorImage to dither
	 * @return ColorImage
	 */
	protected ColorImage apply(ColorImage image) {
		RealColorImage realImage = ImageConverter.toRealColor(image);
		ColorImage newImage = new ColorImage(realImage.X(), realImage.Y());
		RealColorImage imageMap = new RealColorImage(realImage.X(), realImage.Y());
		//		int[] temp={0, 0, 0};
		Float[] temp2 = { 0f, 0f, 0f };
		Integer[] temp3 = new Integer[3];
		Float[] best = new Float[3];
		float[] diff = new float[3];
		Float[] map = new Float[3];
		Float[] value = new Float[3];
		Float[] total = new Float[3];
		int count = 0;
		int count2 = 0;

		imageMap.set(0, 0, temp2);
		Date startDate = new Date();
		for (int x = 0; x < realImage.X(); x++) {
			for (int y = 0; y < realImage.Y(); y++) {

				map = imageMap.get(x, y);
				value = realImage.get(x, y);
				total[0] = value[0] + map[0];
				total[1] = value[1] + map[1];
				total[2] = value[2] + map[2];
				exact = -999;
				do {
					tree2.findNearest(total, tree2.getRoot(), threshold);
					threshold *= 2;
				} while (tree2.getValues().size() == 0);
				//System.out.println(x+" "+y+" "+threshold+": "+tree2.values.size());
				if (tree2.getValues().size() != 1)
					best = findBest(total);
				else
					best = (Float[]) tree2.getValues().elementAt(0);

				threshold = 8;

				tree2.getValues().removeAllElements();
				diff[0] = total[0] - best[0];
				diff[1] = total[1] - best[1];
				diff[2] = total[2] - best[2];
				try {
					temp2[0] = (float) (diff[0] * 7.0 / 16.0);
					temp2[1] = (float) (diff[1] * 7.0 / 16.0);
					temp2[2] = (float) (diff[2] * 7.0 / 16.0);
					imageMap.add(x + 1, y, temp2);
				} catch (Exception e) {
				}
				try {
					temp2[0] = (float) (diff[0] * 3.0 / 16.0);
					temp2[1] = (float) (diff[1] * 3.0 / 16.0);
					temp2[2] = (float) (diff[2] * 3.0 / 16.0);
					imageMap.add(x - 1, y + 1, temp2);
				} catch (Exception e) {
				}
				try {
					temp2[0] = (float) (diff[0] * 5.0 / 16.0);
					temp2[1] = (float) (diff[1] * 5.0 / 16.0);
					temp2[2] = (float) (diff[2] * 5.0 / 16.0);
					imageMap.add(x, y + 1, temp2);
				} catch (Exception e) {
				}
				try {
					temp2[0] = (float) (diff[0] * 1.0 / 16.0);
					temp2[1] = (float) (diff[1] * 1.0 / 16.0);
					temp2[2] = (float) (diff[2] * 1.0 / 16.0);
					imageMap.add(x + 1, y + 1, temp2);
				} catch (Exception e) {
				}
				temp3[0] = best[0].intValue();
				temp3[1] = best[1].intValue();
				temp3[2] = best[2].intValue();

				newImage.set(x, y, temp3);
			}

		}
		Date endDate = new Date();
		double time = (endDate.getTime() - startDate.getTime()) / 1000.0;
		System.out.println(count + " " + count2 + "  " + time);
		return newImage;
	}

	/** Utility methods */
	private Float[] findBest(Float[] best) {

		float dist = 999999;
		float[] val = new float[3];
		float tempDist = 0;
		int location = 0;
		if (exact != -999)
			return (Float[]) tree2.getValues().elementAt(exact);
		else {
			for (int x = 0; x < tree2.getValues().size(); x++) {
				val = (float[]) tree2.getValues().elementAt(x);
				tempDist = (((val[0] - best[0]) * (val[0] - best[0]))
						+ ((val[1] - best[1]) * (val[1] - best[1])) + ((val[2] - best[2]) * (val[2] - best[2])));
				if (tempDist <= dist) {
					dist = tempDist;
					location = x;
				}
			}
		}
		//if (dist!=0) System.out.println("Best distance ="+dist);
		return (Float[]) tree2.getValues().elementAt(location);
	}

	/** This will test the functionality of this function */
	public static void main(String[] arg) throws java.lang.Throwable {
		try {
			ImageInputStreamJAI is = new ImageInputStreamJAI(arg[1]);
			ColorImage pal = (ColorImage) is.read();

			is = new ImageInputStreamJAI(arg[1]);
			ColorImage image = (ColorImage) is.read();
			System.out.println("ColorImage read in");
			Dither d = new Dither(ImageConverter.toRealColor(pal));
			System.out.println("Dither is initialized");
			ColorImage dithered = d.apply(image);
			System.out.println("Dither is done");

			CloseableFrame frame = new CloseableFrame("Test Dither");
			ImageCanvas c = new ImageCanvas(dithered);
			frame.add(c);
			frame.setSize(image.X() + 10, image.Y() + 25);
			frame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
