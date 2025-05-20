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
package jigl.image;

import java.util.ArrayList;
import java.util.List;

import jigl.image.exceptions.ColorModelNotSupportedException;
import jigl.image.exceptions.ColorModelUnknownException;
import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.types.GrayImage;
import jigl.image.types.RealGrayImage;
import jigl.image.utils.ImageConverter;

/** ImageSequence stores a sequence of images */
public class ImageSequence {

	/** sequence is a sequence of Images */
	private List<Image> sequence = null;

	/** Creates a new ImageSequence */
	public ImageSequence() {
		sequence = new ArrayList<Image>();// new Vector(0);
	}

	/**
	 * Adds an image to the end of a sequence. Note: Currently only GrayImages and RealGrayImages
	 * are supported.
	 */
	public void add(Image image) throws ImageNotSupportedException {
		if (image instanceof GrayImage || image instanceof RealGrayImage)
			sequence.add(image);
		else
			throw new ImageNotSupportedException();
	}

	/** Adds an Image to an <i>location</i> of a sequence */
	public void add(Image image, int location) throws ImageNotSupportedException {
		if (image instanceof GrayImage || image instanceof RealGrayImage)
			sequence.add(location, image);
		// sequence.insertElementAt(image, location);
		else
			throw new ImageNotSupportedException();
	}

	/** Gets the Image at <i>index</i> */
	public Image get(int index) {
		return sequence.get(index);
	}

	/** Removes the Image at <i>index</i> */
	public void remove(int index) {
		sequence.remove(index);
		// sequence.removeElementAt(index);
	}

	/** Finds the maximum value over a sequence */
	public float max() throws ColorModelUnknownException, ColorModelNotSupportedException {
		// jigl.image.Image image = null;
		GrayImage gimage = null;
		RealGrayImage rgimage = null;
		float tmax = 0;
		float max = 0;
		for (Image image : sequence) {
			// for (int a = 0; a < sequence.size(); a++) {
			// image = sequence.get(a);
			if (image instanceof GrayImage) {
				gimage = (GrayImage) (image);
				tmax = gimage.max();
			} else if (image instanceof RealGrayImage) {
				rgimage = (RealGrayImage) (image);
				tmax = rgimage.max();
			}
			if (max < tmax)
				max = tmax;
		}
		return max;
	}

	/** Finds the minimum value over a sequence */
	public float min() throws ColorModelUnknownException, ColorModelNotSupportedException {
		// jigl.image.Image image = null;
		GrayImage gimage = null;
		RealGrayImage rgimage = null;
		float tmin = 0;
		float min = 256;
		for (Image image : sequence) {
			// for (int a = 0; a < sequence.size(); a++) {
			// image = (jigl.image.Image) sequence.elementAt(a);
			if (image instanceof GrayImage) {
				gimage = (GrayImage) (image);
				tmin = gimage.min();
			} else if (image instanceof RealGrayImage) {
				rgimage = (RealGrayImage) (image);
				tmin = rgimage.min();
			}
			if (min > tmin)
				min = tmin;
		}
		return min;
	}

	/** Scales a Sequence to an arbitrary max/min */
	public ImageSequence scale(float min, float max) throws ColorModelUnknownException,
			ColorModelNotSupportedException {
		// jigl.image.Image image = null;
		// GrayImage gimage = null;
		RealGrayImage rgimage = null;
		float min_all = min();
		float max_all = max();
		// float r = 0;
		// float range = max - min;

		// convert to byte depth
		// for (int y = 0; y < sequence.size(); y++) {
		// image = get(y);
		for (Image image : sequence) {
			rgimage = ImageConverter.toRealGray(image);
			rgimage.subtract(min);
			rgimage.divide(max_all - min_all);
			rgimage.multiply(max - min);
			rgimage.add(min);
			if (image instanceof GrayImage)
				image = ImageConverter.toGray(rgimage);
			else
				image = rgimage;
		}

		// value = (((r-min)/(max-min)*(int_max-int_min))+int_min);

		return this;
	}

	/**
	 * ByteSizes the range of this sequence to an arbitrary min/max over a sequence
	 * 
	 * @param gr RealGrayImage
	 */
	public ImageSequence byteSize() throws ColorModelUnknownException,
			ColorModelNotSupportedException {
		float min = min();
		float max = max();
		RealGrayImage rgimage = null;
		// jigl.image.Image image = null;

		float range = max - min;

		// convert to byte depth
		// for (int y = 0; y < sequence.size(); y++) {
		// image = get(y);
		for (Image image : sequence) {
			rgimage = ImageConverter.toRealGray(image);
			rgimage.subtract(min);
			rgimage.multiply((float) (255.0 / range));
			// value = (int)((255.0/range) * ((float)gr.get(x,y) - min));

			if (image instanceof GrayImage)
				image = ImageConverter.toGray(rgimage);
			else
				image = rgimage;
		}

		return this;
	}

	/**
	 * Returns a subsequence of a sequence
	 * 
	 * @param start starting index
	 * @param end ending index
	 */
	public ImageSequence subSequence(int start, int end) throws ImageNotSupportedException {
		ImageSequence seq = new ImageSequence();
		for (int x = start; x < end; x++) {
			seq.add(sequence.get(x));
		}
		return seq;
	}

	/** Returns the number of images in this sequence */
	public int number() {
		return sequence.size();
	}

}
