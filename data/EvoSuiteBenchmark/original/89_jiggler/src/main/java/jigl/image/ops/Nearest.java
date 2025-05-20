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
import jigl.image.types.RealColorImage;
import jigl.image.utils.ImageConverter;
import jigl.image.utils.KdTree;

/** Produces an image from a palette of colors using a nearest neighbor algorithm. */
public class Nearest extends SimpleOperator {
	private KdTree tree2;
	int threshold = 8;
	private int exact = -999;

	/**
	 * Creates a new instance of Nearest. Note, this function uses a KdTree which is calculated in
	 * this constructor.
	 * 
	 * @see KdTree
	 */
	public Nearest(RealColorImage paletteImage) {
		tree2 = new KdTree(3);
		tree2.buildTreePalette(paletteImage);
	}

	/**
	 * Applies the palette to <code>image</code> using the KdTree to find the nearest neighbor.
	 * Returned Image is a ColorImage. <code>image</code> is not modified.
	 * 
	 * @param image ColorImage to apply palette to
	 * @return ColorImage
	 */
	protected ColorImage apply(ColorImage image) {

		RealColorImage realImage = ImageConverter.toRealColor(image);
		ColorImage newImage = new ColorImage(realImage.X(), realImage.Y());

		Float[] total = null;
		Float[] best = null;
		Integer[] temp = new Integer[3];

		for (int x = 0; x < realImage.X(); x++) {
			for (int y = 0; y < realImage.Y(); y++) {
				tree2.getValues().removeAllElements();
				do {
					total = realImage.get(x, y);
					tree2.findNearest(total, tree2.getRoot(), threshold);
					threshold += 2;
				} while (tree2.getValues().size() == 0);

				threshold = 8;
				if (tree2.getValues().size() != 1)
					best = findBest(total);
				else
					best = (Float[]) tree2.getValues().elementAt(0);
				temp[0] = best[0].intValue();
				temp[1] = best[1].intValue();
				temp[2] = best[2].intValue();
				newImage.set(x, y, temp);
			}

		}
		System.out.println("Done with the apply");
		return newImage;
	}

	/** Utility method for apply() */
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
		return (Float[]) tree2.getValues().elementAt(location);
	}

	//This will test the functionality of this function
	/*public static void main(String[] arg) throws java.lang.Object
	{
		try{
			ImageInputStreamJAI is=new ImageInputStreamJAI(arg[0]);
			ColorImage pal= (ColorImage)is.read();
				
			is = new ImageInputStreamJAI(arg[1]);
			ColorImage image=(ColorImage)is.read();
			
			Nearest d=new Nearest(ImageConverter.toRealColor(pal));
			ColorImage dithered=(ColorImage)d.apply(image);
		
			CloseableFrame frame=new CloseableFrame("Test Nearest");
			ImageCanvas c=new ImageCanvas(dithered);
			frame.add(c);
			frame.setSize(image.X()+10, image.Y()+25);
			frame.setVisible(true);
		
		}catch (Exception e){e.printStackTrace();}	
	}
	*/
}
